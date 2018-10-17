/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation;

import ck.data.LineItemDTO;
import ck.data.OrderDTO;
import ck.data.UserDTO;
import ck.presentation.command.Command;
import java.util.ArrayList;

/**
 * Provides static helper methods e.g. to convert collections to a HTML representation.
 * @author Claus
 */
public class Utils
{
    public static String createOrderForm(int length, int width, int height, String brickPattern)
    {
        String result = "";
        if (length > 0 && width > 0 && height > 0)
        {
            result = "<form action=\"FrontController\" method=\"post\">"
                   + "<input type=\"hidden\" name=\"command\" value=\"" + Command.CREATE_ORDER + "\" />"                      
                   + "<input type=\"hidden\" name=\"length\" value=\"" + length + "\" />"
                   + "<input type=\"hidden\" name=\"width\" value=\"" + width + "\" />"
                   + "<input type=\"hidden\" name=\"height\" value=\"" + height + "\" />"
                   + "<input type=\"hidden\" name=\"brickPattern\" value=\"" + brickPattern + "\" />"
                   + "<input type=\"submit\" value=\"Place order\" /></form>";
        }        
        
        return result;
    }
    
    /**
     * Converts an array of arraylists of LineItemDTO objects to a HTML table.
     * PRE: Each arraylist must have equal number of elements.
     * @param layers
     * @return 
     */
    public static String brickLayersToHtml(ArrayList<LineItemDTO>[] layers) 
    {
        String result = "<table class=\"table\"><thead><tr>$th</tr></thead><tbody>$trs</tbody></table>";

        String trs = "", tds = "", th = "";
        
        for(int i = 0; i < layers.length; i++)
        {
            ArrayList<LineItemDTO> aList = layers[i];
            
            String tr = "<tr>$td</tr>";
            String td = "<td>" + (i+1) + "</td>";
            if (i == 0)
                th += "<th>Layer</th>";
            for(LineItemDTO item:aList)
            {
                td += "<td>$qty</td>";
                td = td.replace("$qty", String.valueOf(item.getQty()));   
                
                if (i == 0) // first arraylist of array.
                {                    
                    th += "<th>$brick</th>";
                    th = th.replace("$brick", item.getBrick().toString());                    
                }
            }
            tr = tr.replace("$td", td);
            trs += tr;
        }
        result = result.replace("$th", th);
        result = result.replace("$trs", trs);
        return result;
    }
    
    /**
     * Converts an arraylist of strings to a html select element named 'brickPattern'.
     * @param brickPatterns
     * @return 
     */
    public static String brickPatternsToSelect(ArrayList<String> brickPatterns)
    {
        String result = "<select name=\"brickPattern\">$options</select>", options = "";
        for(String pattern : brickPatterns)
        {
            options += "<option value=\"$pattern\">$pattern</option>";
            options = options.replace("$pattern", pattern);
        }
        result = result.replace("$options", options);
        return result;
    }
    public static String logOutForm()
    {
        String result = "<form action=\"FrontController\" method=\"post\">";
        result +=       "<input type=\"hidden\" name=\"command\" value=\"" + Command.LOGOUT + "\">";
        result +=       "<input type=\"submit\" value=\"Log out\">";
        result +=       "</form>";
        return result;
    }
    /**
     * Converts an ArrayList of OrderDTO objects to a HTML table representation.
     * If orders are not createShipForm AND user is employee, the table will include a form to ship each order.
     * @param orders
     * @param createShipForm Indicates if unshipped orders in list should have a form for shipping.
     * @return String containing HTML representation of the collection.
     */
    public static String ordersToHtmlTable(ArrayList<OrderDTO> orders, boolean createShipForm)
    {
        
        String result = "<table class=\"table\"><thead><tr><th>$1</th><th>$2</th><th>$3</th><th>$4</th><th>$5</th><th></th><th></th></tr></thead><tbody>";
        result = result.replace("$1", "Id");
        result = result.replace("$2", "Order date");
        result = result.replace("$3", "Shipped date");
        result = result.replace("$4", "Username");
        result = result.replace("$5", "Email");
        for(OrderDTO order:orders)
        {                        
            result += "<tr><td>$1</td><td>$2</td><td>$3</td><td>$4</td><td>$5</td><td>$6</td><td>$7</td></tr>";
            result = result.replace("$1", String.valueOf(order.getId()));
            result = result.replace("$2", order.getOrderDate().toString());
            result = result.replace("$3", order.getShippedDate() != null ? order.getShippedDate().toString():"");
            result = result.replace("$4", order.getUserDTO().getUsername());
            result = result.replace("$5", order.getUserDTO().getEmail());            
            //result = result.replace("$6", (createShipForm && order.getShippedDate() == null)? createShipOrderForm(order.getId()) : ""); // unshipped orders gets a button.
            result = result.replace("$6", (createShipForm && order.getShippedDate() == null)? 
                createOrderForm(order.getId(), Pages.EMPLOYEE, Command.SHIP_ORDER, "Ship order") : ""); // unshipped orders gets a button.
            result = result.replace("$7", createOrderForm(order.getId(), order.getUserDTO().isEmployee() ? Pages.EMPLOYEE : Pages.CUSTOMER, Command.SHOW_ORDER, "Show order"));
        }
        result += "</tbody></table>";
        return result;
    }
    
    /**
     * Creates a set of navigation links related to the user.
     * @param userDTO
     * @return 
     */
    public static String createNavBar(UserDTO userDTO)
    {
        String result = "<nav class=\"navbar\"><ul><li>$logOut</li><li>$page</li></ul></nav>";
        result = result.replace("$logOut", Utils.logOutForm());
        result = result.replace("$page", Utils.toUserPageForm(userDTO.isEmployee()));
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
               "<div><table class=\"table\"><thead><tr>" + 
               "<th>User id:</th><th>Username</th><th>Email</th><th>Order date</th><th>Shipped date</th><th>Length</th><th>Width</th><th>Height</th><th>Pattern</th>" + 
               "</tr></thead><tbody><tr>" + 
               "<td>$oid</td><td>$uname</td><td>$email</td><td>$oDate</td><td>$sDate</td>" + 
               "<td>$length</td><td>$width</td><td>$height</td><td>$pattern</td>" +
               "</tr></tbody></table></div>";
       
       result = result.replace("$oid", String.valueOf(orderDTO.getId()));
       result = result.replace("$uname", orderDTO.getUserDTO().getUsername());
       result = result.replace("$email", orderDTO.getUserDTO().getEmail());
       result = result.replace("$oDate", orderDTO.getOrderDate().toString());
       // replace with shippedDate - check for null.
       result = result.replace("$sDate", orderDTO.getShippedDate() != null ? orderDTO.getShippedDate().toString() : "");
       result = result.replace("$length", String.valueOf(orderDTO.getLength()));
       result = result.replace("$width", String.valueOf(orderDTO.getWidth()));
       result = result.replace("$height", String.valueOf(orderDTO.getHeight()));
       result = result.replace("$pattern", orderDTO.getPattern());
       
       result += "<div><table class=\"table\"><thead><tr><th>Brick id</th><th>Qty</th><th>Brick</th></thead><tbody>";
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
     * Generic form generator for orders. 
     * Action is FrontController, method is POST.
     * @param orderId Id of the order.     
     * @param errorPath The path to return to, if an error occurs. Use Pages contsnts.
     * @param command The command that should be performed by the FrontController. Use Command constants. 
     * @param btnValue The caption of the forms submit button.
     * @return String with HTML content.
     */
    private static String createOrderForm(int orderId, String errorPath, String command, String btnValue)
    {
        String result = "<form action=\"FrontController\" method=\"post\">";
        result +=       "<input type=\"hidden\" name=\"$1\" value=\"$2\">";
        result +=       "<input type=\"hidden\" name=\"command\" value=\"$3\">";
        result +=       "<input type=\"hidden\" name=\"orderId\" value=\"$4\">";
        result +=       "<input type=\"submit\" value=\"$5\">";
        result +=       "</form>";
        result = result.replace("$1", FrontController.ERROR_PATH);
        result = result.replace("$2", errorPath);
        result = result.replace("$3", command);
        result = result.replace("$4", String.valueOf(orderId));
        result = result.replace("$5", btnValue);
        
        return result;
    }

    private static String toUserPageForm(boolean employee)
    {
        String result ="<form action=\"FrontController\" method=\"post\">"
                + "<input type=\"hidden\" name=\"command\" value=\"" + Command.USER_PAGE + "\">"
                + "<input type=\"submit\" value=\"My page\">"
                + "</form>";
        return result;
    }
    
    /**
     * Private helper to create a html form to ship the order with provided orderId.
     * @param orderId
     * @return 
     */
//    private static String createShipOrderForm(int orderId)
//    {
//        String result = "<form action=\"FrontController\" method=\"post\">";
//        // if shipping yields error return to /WEB-INF/employee.jsp
//        result +=       "<input type=\"hidden\" name=\"$1\" value=\"" + Pages.EMPLOYEE + "\">";
//        result +=       "<input type=\"hidden\" name=\"command\" value=\"$2\">";
//        result +=       "<input type=\"hidden\" name=\"orderId\" value=\"$3\">";
//        result +=       "<input type=\"submit\" value=\"Ship\">";
//        result +=       "</form>";
//        result = result.replace("$1", FrontController.ERROR_PATH);
//        result = result.replace("$2", Command.SHIP_ORDER);
//        result = result.replace("$3", String.valueOf(orderId));
//        return result;
//    }
    // We only have static members, so no instantiation.
    private Utils(){}
}
