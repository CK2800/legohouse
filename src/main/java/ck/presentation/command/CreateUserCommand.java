/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation.command;

import ck.data.BrickPattern;
import ck.data.OrderDTO;
import ck.data.UserDTO;
import ck.logic.UserDAO;
import ck.logic.LegoException;
import ck.logic.LogicFacade;
import ck.presentation.Pages;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Claus
 */
public class CreateUserCommand extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LegoException
    {
        // Get parameters from request.
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        boolean employee = (request.getParameter("employee") != null);
        
        UserDTO userDTO = LogicFacade.createUser(username, email, password, password2, employee);

        // Log in the new customer.
        LoginCommand.invalidateSession(request);
        LoginCommand.loginUser(request, userDTO);                

        // Opret ordrer osv.
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
        
        return employee ? Pages.EMPLOYEE : Pages.CUSTOMER;
    }    
}
