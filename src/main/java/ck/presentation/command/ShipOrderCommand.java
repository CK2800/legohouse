/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation.command;

import ck.data.UserDTO;
import ck.logic.LegoException;
import ck.logic.OrderDAO;
import ck.logic.UserDAO;
import ck.presentation.Pages;
import ck.presentation.viewmodels.OrderUserComposite;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Claus
 */
public class ShipOrderCommand extends Command        
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LegoException
    {
        // Get logged in user from session.
        UserDTO loggedInUser = (UserDTO)request.getSession().getAttribute("userDTO");
        // Get user from database to validate equality. 
        if (loggedInUser != null)
        {
            UserDTO userDTO = UserDAO.validateUser(loggedInUser.getEmail(), loggedInUser.getPassword());        
            // Get order id from request.
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            // Check if logged in user is in fact an employee.
            if (loggedInUser.equals(userDTO) && loggedInUser.isEmployee())
            {               
                // set unshipped orders on request.
                ArrayList<OrderUserComposite> unshippedOrders = OrderDAO.getOrders(false);
                ArrayList<OrderUserComposite> shippedOrders = OrderDAO.getOrders(true);
                request.setAttribute("unshippedOrders", unshippedOrders);   
                request.setAttribute("shippedOrders", shippedOrders); 

                // Is order shipped, remove it from collection and return to employee page.
                if (OrderDAO.shipOrder(orderId))
                {   
                    int i = 0;
                    for (; i < unshippedOrders.size(); i++)
                    {
                        if (unshippedOrders.get(i).getOrder().getId() == orderId)
                            break;
                    }
                    // move order from unshipped to shipped.
                    shippedOrders.add(unshippedOrders.remove(i));                

                    // return to employee page
                    return Pages.EMPLOYEE;
                }
                else
                {
                    throw new LegoException("Order was not shipped.", "Order was not shipped.", "ShipOrderCommand.execute()");            
                }
            }
            else // not an employee - invalidate session and throw an error.
            {
                LoginCommand.invalidateSession(request);
                String friendlyMessage = "You are not allowed to ship orders.";
                String detailedMessage = "User $userId tried to ship order $orderId. Either user in session has been manipulated or user is no employee:\n $user1\n$user2";
                detailedMessage = detailedMessage.replace("$userId", String.valueOf(userDTO.getId()));
                detailedMessage = detailedMessage.replace("$orderId", String.valueOf(orderId));
                detailedMessage = detailedMessage.replace("$user1", loggedInUser.toString());
                detailedMessage = detailedMessage.replace("$user2", userDTO.toString());
                String originOfException = "ShipOrderCommand.execute()";

                throw new LegoException(friendlyMessage, detailedMessage, originOfException);
            }
        }
        else
        {
            throw new LegoException("You must log in to complete this action.", "ShipOrderCommand called without a user logged in.", "ShipOrderCommand.execute()");
        }
    }
}
