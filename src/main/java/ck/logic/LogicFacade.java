/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.logic;

import ck.data.BrickDTO;
import ck.data.LineItemDTO;
import ck.data.OrderDTO;
import ck.data.UserDTO;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

/**
 * Facade for all protected/public methods of classes in ck.logic
 * @author Claus
 */
public class LogicFacade
{
    /**
     * ------------- BrickCalculator methods -------------
     */
    
    /**
     * Calculates the bricks needed for a construction of width x length x height and the selected brick pattern.
     * @param patternName
     * @param length
     * @param width
     * @param height
     * @return
     * @throws LegoException 
     */
    public static ArrayList<LineItemDTO>[] calculate(String patternName, int length, int width, int height) throws LegoException
    {
        return BrickCalculator.calculate(patternName, length, width, height);
    }
    
    /**
     * Get the array of ArrayLists of LineItemDTOs that are calculated 
     * in the BrickCalculator.
     * @return 
     */
    public static ArrayList<LineItemDTO>[] getLayers()
    {
        return BrickCalculator.getLayers();
    }
    
    /**
     * ------------- BrickDAO methods -------------
     */
    public static BrickDTO getBrick(int id) throws LegoException
    {
        return BrickDAO.getBrick(id);
    }
    
    /**
     * ------------- OrderDAO methods -------------
     */
    
    public static OrderDTO getOrder(int orderId) throws LegoException
    {
        return OrderDAO.getOrder(orderId);
    }
    
    public static ArrayList<OrderDTO> getOrders(int userId, boolean employee) throws LegoException
    {
        return OrderDAO.getOrders(userId, employee);
    }
    
    public static boolean shipOrder(int orderId) throws LegoException
    {
        return OrderDAO.shipOrder(orderId);
    }
    
    /**
     * ------------- UserDAO methods -------------
     */
    
    public static UserDTO createUser(String username, String email, String password, String password2, boolean employee) throws LegoException
    {
        return UserDAO.createUser(username, email, password, password2, employee);
    }
    
    public static UserDTO validateUser(String email, String password) throws LegoException
    {
        return UserDAO.validateUser(email, password);
    }
    
    /**
     * ------------- LogicFacade (this) methods ------------- 
     */
    
    /**
     * Creates an order based on session attribute "layers". 
     * If the order is successfully created, the session attribute is removed.
     * @param session
     * @param length
     * @param width
     * @param height
     * @param pattern
     * @return
     * @throws LegoException 
     */
    public static boolean createOrder(HttpSession session, int length, int width, int height, String pattern) throws LegoException
    {
        boolean orderCreated = false;
        
        // Get user from session.
        UserDTO userDTO = (UserDTO)session.getAttribute("userDTO");    
        // Calculate the construction.
        ArrayList<LineItemDTO>[] array = calculate(pattern, length, width, height);
        // Get the array of ArrayList of LineItemDTO from session.
        //ArrayList<LineItemDTO>[] array = (ArrayList[])session.getAttribute("layers");
        ArrayList<LineItemDTO> totals = null, temp = null;
        
        if (array != null && userDTO != null)        
        {            
            // First ArrayList will be totals.
            totals = array[0];
            // Subsequent ArrayLists will be added to totals.
            for(int i = 1; i < array.length; i++)
            {
                temp = array[i];
                // Loop through subsequent array list.
                for(LineItemDTO lineItemDTO : temp)
                {
                    // loop through array list holding totals
                    for(LineItemDTO total : totals)                    
                    {                        
                        // compare brick ids of lineitems, if equal, add qty.
                        if (total.getBrickId() == lineItemDTO.getBrickId())
                            total.setQty(total.getQty() + lineItemDTO.getQty());
                    }
                }
            }
            
            // create the order.
            OrderDTO orderDTO = OrderDAO.createOrder(userDTO.getId(), length, width, height, pattern, totals);
            orderCreated = orderDTO != null;
            if (orderCreated) // remove session attr. layers.                
                session.removeAttribute("layers");
        }
        else
        {
            throw new LegoException("No lineitems have been calculated for this lego house.", "No lineitems are calculated and stored in session.", "LogicFacade.createOrder(HttpSession)");
        }
        return orderCreated;
    }
    
    
}
