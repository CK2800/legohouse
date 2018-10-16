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
public class CreateOrderCommand extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LegoException
    {        
        // get current session.
        HttpSession session = request.getSession();
        // get customer and line items.
        UserDTO userDTO = (UserDTO)session.getAttribute("userDTO");
        int length = Integer.valueOf(request.getParameter("length"));
        int width = Integer.valueOf(request.getParameter("width"));
        int height = Integer.valueOf(request.getParameter("height"));
        
        // Create the order and remove the layers from session.
        LogicFacade.createOrder(session, length, width, height);
        // Get users orders.
        ArrayList<OrderDTO> orders = LogicFacade.getOrders(userDTO.getId(), false); // not an employee.
        request.setAttribute("orders", orders);
        // Get brick patterns.
        ArrayList<String> brickPatterns = BrickPattern.getPatterns();
        request.setAttribute("brickPatterns", brickPatterns);
        
        return Pages.CUSTOMER;
        
        
        
    }    
}
