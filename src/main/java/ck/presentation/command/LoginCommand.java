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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Claus
 */
public class LoginCommand extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LegoException
    {
        // Start by invalidating current session.
        invalidateSession(request);
        
        // Get info from request.
        String email = request.getParameter("email");        
        String password = request.getParameter("password");
        
        // Validate user.
        UserDTO userDTO = LogicFacade.validateUser(email, password);
        // log in user.
        loginUser(request, userDTO);
        
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

    /**
     * Invalidates a current session, creates a new one and sets the user in it.
     * PRE: UserDTO must not be null.
     * @param request The current request.
     * @param userDTO The user to be logged in.
     */
    static void loginUser(HttpServletRequest request, UserDTO userDTO) // no access modifiers means class- and package access.
    {   
        // Set user in session if it is not null.
        if (userDTO != null)
            request.getSession().setAttribute("userDTO", userDTO);
    }
    
    /**
     * // Invalidate old session if one exists.
     * @param request 
     */
    public static void invalidateSession(HttpServletRequest request)
    {
        
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null)
            oldSession.invalidate();
    }
        
}
