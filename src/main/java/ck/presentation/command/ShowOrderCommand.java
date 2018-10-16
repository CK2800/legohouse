/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation.command;

import ck.data.OrderDTO;
import ck.data.UserDTO;
import ck.logic.LegoException;
import ck.logic.LogicFacade;
import ck.presentation.Pages;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Claus
 */
public class ShowOrderCommand extends Command
{    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LegoException
    {
        
        UserDTO userDTO = (UserDTO)request.getSession().getAttribute("userDTO");
        OrderDTO orderDTO = LogicFacade.getOrder(Integer.valueOf(request.getParameter("orderId")));
        // stopping conditions.
        if (userDTO == null) // no user found.
        {
            throw new LegoException("No user logged in.", "No user logged in.", "ShowOrderCommand.execute()");
        }
        else if (orderDTO == null) // no order found.
        {
            throw new LegoException("Order was not found.", "Order was not found.", "ShowOrderCommand.execute()");
        }
        else if (!userDTO.isEmployee() && userDTO.getId() != orderDTO.getUserId()) // user not emp or user not belonging to order.
        {
            throw new LegoException("You are not allowed to view this order.", "User not employee or user does not belong to order", "ShowOrderCommand.execute()");
        }
        
        // Set order on request.
        request.setAttribute("orderDTO", orderDTO);
        return Pages.ORDER;
        
    }
}
