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
           
    public static OrderDTO createOrder(int customerId, int length, int width, int height, ArrayList<LineItemDTO> lineItems) throws LegoException
    {
        return OrderDAO.createOrder(customerId, length, width, height, lineItems);
    }
    
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
    
    
}
