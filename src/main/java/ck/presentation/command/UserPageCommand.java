/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation.command;

import ck.data.BrickPattern;
import ck.data.OrderDTO;
import ck.data.UserDTO;
import ck.logic.LegoException;
import ck.logic.LogicFacade;
import ck.presentation.Pages;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This command validates the logged in user and returns to either his
 * customer- or employee page based on his type.
 * @author Claus
 */
public class UserPageCommand extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LegoException
    {
        // get the logged in user.
        UserDTO userDTO = (UserDTO)request.getSession().getAttribute("userDTO");
        // Revalidate user just in case.
        if (userDTO != null && LogicFacade.validateUser(userDTO.getEmail(), userDTO.getPassword()) != null)
        {
            // Employee must se a list of shipped / unshipped orders.
            if (userDTO.isEmployee())
            {
                // Get list of orders. user id in parameters doesn't really matter, since we are employee.
                ArrayList<OrderDTO> orders = LogicFacade.getOrders(userDTO.getId(), true);

                request.setAttribute("orders", orders);            

            }
            else // Customer must...
            {
                // ... see a list of his/her orders.
                ArrayList<OrderDTO> orders = LogicFacade.getOrders(userDTO.getId(), false); // not an employee.
                request.setAttribute("orders", orders);
                // ... be able to make a new order.
                ArrayList<String> brickPatterns = BrickPattern.getPatterns();
                request.setAttribute("brickPatterns", brickPatterns);
            }
        
            return userDTO.isEmployee() ? Pages.EMPLOYEE : Pages.CUSTOMER;
        }
        else
        {
            LoginCommand.invalidateSession(request);
            throw new LegoException("You do not have credentials to view the page.","No logged in user or logged in user invalid", "UserPageCommand.execute()");
        }
    }
}
