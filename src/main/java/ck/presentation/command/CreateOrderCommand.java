/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation.command;

import ck.data.LineItemDTO;
import ck.data.UserDTO;
import ck.logic.LegoException;
import ck.logic.OrderDAO;
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
        int customerId = ((UserDTO)session.getAttribute("userDTO")).getId();
        ArrayList<LineItemDTO> lineItems = (ArrayList<LineItemDTO>)session.getAttribute("lineItems");
        // create order.
        OrderDAO.createOrder(customerId, lineItems);
        // Show page of order details.
        return Pages.ORDER;        
    }    
}
