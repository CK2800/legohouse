/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation;

import ck.data.LineItemDTO;
import ck.data.OrderDTO;
import ck.presentation.command.Command;
import ck.presentation.viewmodels.OrderUserComposite;
import java.util.ArrayList;

/**
 * Provides static helper methods e.g. to convert collections to a HTML representation.
 * @author Claus
 */
public class Utils
{
    public static String logOutForm()
    {
        String result = "<form action=\"FrontController\" method=\"post\">";
        result +=       "<input type=\"hidden\" name=\"command\" value=\"" + Command.LOGOUT + "\">";
        result +=       "<input type=\"submit\" value=\"Log out\">";
        result +=       "</form>";
        return result;
    }
    /**
     * Converts an ArrayList of OrderUserComposites to a HTML table representation.
     * If orders are not shipped, the table will include a form to ship each order.
     * @param items
     * @param shipped Indicates if orders in list are shipped.
     * @return String containing HTML representation of the collection.
     */
    public static String OrderUserCompositesToHtmlTable(ArrayList<OrderUserComposite> items, boolean shipped)
    {
        OrderDTO order;
        String result = "<table><thead><tr><th>$1</th><th>$2</th><th>$3</th><th>$4</th><th></th></tr></thead><tbody>";
        result = result.replace("$1", "Id");
        result = result.replace("$2", "Order date");
        result = result.replace("$3", "Username");
        result = result.replace("$4", "Email");
        for(OrderUserComposite item:items)
        {            
            order = item.getOrder();
            result += "<tr><td>$1</td><td>$2</td><td>$3</td><td>$4</td><td>$5</td></tr>";
            result = result.replace("$1", String.valueOf(order.getId()));
            result = result.replace("$2", order.getOrderDate().toString());
            result = result.replace("$3", item.getUsername());
            result = result.replace("$4", item.getEmail());            
            result = result.replace("$5", shipped ? "" : createShipOrderForm(order.getId())); // unshipped orders gets a button.
        }
        result += "</tbody></table>";
        return result;
    }
    
    /**
     * Returns a HTML representation of an OrderDTO object.
     * PRE: OrderDTO object MUST have line items and each 
     * lineitem MUST have a brick.
     * @see OrderDAO.getOrder(int).
     * @param orderDTO
     * @return A nicely formatted HTML representation of the order.
     */
    public static String OrderToHtmlTable(OrderDTO orderDTO)
    {
       // get user info.
       
       String result = 
               "<div><table><tbody><tr>" + 
               "<td>$oid</td><td>$uname</td><td>$email</td><td>$oDate</td><td>$sDate</td>" + 
               "</tr></tbody></table></div>";
       
       result = result.replace("$oid", String.valueOf(orderDTO.getId()));
       result = result.replace("$uname", orderDTO.)
       
       result += "<div><table><thead><tr><th>Brick id</th><th>Qty</th><th>Brick</th></thead><tbody>";
       for(LineItemDTO lineitem : orderDTO.getLineItems())
       {
           String liResult = "<tr><td>$1</td><td>$2</td><td>$3</td></tr>";
           liResult = liResult.replace("$1", String.valueOf(lineitem.getBrickId()));
           liResult = liResult.replace("$2", String.valueOf(lineitem.getQty()));
           liResult = liResult.replace("$3", lineitem.getBrick().toString());
           result += liResult;
       }
       result += "</tbody></table></div>";
       
       return result;
    }
    
    /**
     * Private helper to create a html form to ship the order with provided orderId.
     * @param orderId
     * @return 
     */
    private static String createShipOrderForm(int orderId)
    {
        String result = "<form action=\"FrontController\" method=\"post\">";
        // if shipping yields error return to /WEB-INF/employee.jsp
        result +=       "<input type=\"hidden\" name=\"$1\" value=\"" + Pages.EMPLOYEE + "\">";
        result +=       "<input type=\"hidden\" name=\"command\" value=\"$2\">";
        result +=       "<input type=\"hidden\" name=\"orderId\" value=\"$3\">";
        result +=       "<input type=\"submit\" value=\"Ship\">";
        result +=       "</form>";
        result = result.replace("$1", FrontController.ERROR_PATH);
        result = result.replace("$2", Command.SHIP_ORDER);
        result = result.replace("$3", String.valueOf(orderId));
        return result;
    }
    // We only have static members, so no instantiation.
    private Utils(){}
}
