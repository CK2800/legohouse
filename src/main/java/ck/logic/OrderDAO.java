/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.logic;

import ck.data.BrickDTO;
import ck.data.DbConnection;
import ck.data.LineItemDTO;
import ck.data.OrderDTO;
import ck.data.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class works on a (sub)set of DTO classes to implement 
 * functionality needed in the GUI.
 * 
 * @author Claus
 */
public class OrderDAO
{
    /**
     * Get details for order including its user, lineitems and brick of each line item.
     */
    private static final String GET_ORDER_SQL = "SELECT o.id, o.userId, o.orderDate, o.shippedDate," +
                                                "o.length, o.width, o.height, o.pattern," +
                                                "l.orderId, l.brickId, l.qty, " +
                                                "b.length as brickLength, b.width as brickWidth, " +
                                                "u.username, u.email, u.employee " +
                                                "FROM orders o INNER JOIN lineitems l " + 
                                                "ON o.id = l.orderId " + 
                                                "INNER JOIN bricks b ON l.brickId = b.id " + 
                                                "INNER JOIN users u ON o.userId = u.id " +
                                                "WHERE o.id = ?;";
    
    /**
     * Generic sql for retrieving a list of orders matching an 
     * optional WHERE clause ordered by an optional ORDERBY clause.
     */
    private static final String GET_ORDERS_SQL = "SELECT o.id, o.userId, o.orderDate, o.shippedDate, o.length, o.width, o.height, o.pattern, u.username, u.email, u.employee " +
                                                       "FROM orders o INNER JOIN users u ON o.userId = u.id $WHERE $ORDERBY;";
    
    private static final String CREATE_ORDER_SQL    = "INSERT INTO orders(userId, length, width, height, pattern) VALUES (?,?,?,?,?);"; 
    private static final String CREATE_LINEITEM_SQL = "INSERT INTO lineitems(orderId, brickId, qty) VALUES (?, ?, ?);";
    private static final String SHIP_ORDER_SQL      = "UPDATE orders SET shippedDate = now() WHERE shippedDate is NULL AND id = ?;";
    //private static final String GET_USER_ORDERS_SQL = "SELECT o.id, o.userId, o.orderDate, o.shippedDate FROM orders o WHERE userId = ?;";
    
    
    /**
     * Gets a list of orders.
     * If user is an employee, all orders will be returned.
     * If user is not an employee, the users orders will be returned.
     * @param userId Id of user
     * @param employee Indicates whether the user is an employee.
     * @return ArrayList of OrderDTO objects.
     * @throws LegoException 
     */
    protected static ArrayList<OrderDTO> getOrders(int userId, boolean employee) throws LegoException
    {
        ArrayList<OrderDTO> orders = new ArrayList<>();
        try
        {
            Connection connection = DbConnection.getConnection();
            //PreparedStatement pstm = connection.prepareStatement(GET_USER_ORDERS_SQL);
            // Append correct WHERE clause to generic sql
            String sql = GET_ORDERS_SQL;
            sql = sql.replace("$WHERE", employee ? "" : "WHERE userId = ?"); // No where clause for employees.
            //sql = sql.replace("$ORDERBY", employee ? "ORDER BY shippedDate ASC" : "ORDER BY orderDate DESC"); 
            sql = sql.replace("$ORDERBY", "ORDER BY orderDate DESC"); 
            PreparedStatement pstm = connection.prepareStatement(sql);
            if (!employee) // customers id must be parsed in.
                pstm.setInt(1, userId);
            try(ResultSet rs = pstm.executeQuery();)
            {
                while(rs.next())
                {
                    OrderDTO order = OrderDTO.mapOrder(rs);
                    // create user once first (without storing the password - should not be retrievable in client).                    
                    UserDTO user = new UserDTO(rs.getInt("userId"), rs.getString("username"), null, rs.getString("email"), rs.getBoolean("employee") );                    
                    // add user to order.
                    order.setUserDTO(user);
                    orders.add(order);
                }
            }
        }
        catch(SQLException | ClassNotFoundException e)
        {
            throw new LegoException("Your orders could not be retrieved.", e.getMessage(), "OrderDAO.getUserOrders(int)");
        }
        
        return orders;
    }
    
    
            
    
    /**
     * Gets a list of orders along with user information.
     * @param shipped indicates if list has shipped (true) or unshipped (false) orders.
     * @return ArrayList of OrderUserComposite objects.
     */
//    public static ArrayList<OrderUserComposite> getOrders(boolean shipped) throws LegoException
//    {
//        ArrayList<OrderUserComposite> orders = new ArrayList<>();        
//        try
//        {
//            // get connected
//            Connection connection = DbConnection.getConnection();
//            PreparedStatement pstm;
//            String sql = "";// UNSHIPPED_ORDERS_SQL;
//            if (shipped)
//                sql = sql.replace("$NOT", "NOT");               
//            else
//                sql = sql.replace("$NOT", "");
//            
//            pstm = connection.prepareStatement(sql);                
//            try(ResultSet rs = pstm.executeQuery();)
//            {
//                // map each tuple to OrderDTO, add OrderDTO and additional user info to the collection.
//                while(rs.next())
//                {
//                    OrderDTO orderDTO = OrderDTO.mapOrder(rs);
//                    orders.add(new OrderUserComposite(orderDTO, rs.getString("username"), rs.getString("email")));
//                }
//            }
//        }
//        catch(Exception e)
//        {
//            throw new LegoException("Unshipped orders could not be retrieved", e.getMessage(), "OrderDAO.getUnshippedOrders()");
//        }
//        
//        return orders;
//    }
    
    /**
     * Ships an order.      
     * @param orderId Id of order to ship.
     * @return true if exactly one UNSHIPPED order is shipped, false otherwise (multiple orders are not shipped).
     * @throws LegoException 
     */
    protected static boolean shipOrder(int orderId) throws LegoException
    {
        int recordsAffected = 0;
        boolean autocommit = false;
        try
        {   
            // Get connection, prepare statement and replace values.
            Connection connection = DbConnection.getConnection();                        
            // store the current auto-commit state.
            autocommit = connection.getAutoCommit();
            // Don't auto commit changes to database.
            connection.setAutoCommit(false);
            PreparedStatement pstm = connection.prepareStatement(SHIP_ORDER_SQL);
            pstm.setInt(1, orderId);
            recordsAffected = pstm.executeUpdate();            
            if (recordsAffected == 1)
                connection.commit();
            else
                connection.rollback();
            
            // Reset the auto-commit state.
            connection.setAutoCommit(autocommit);
        }
        catch(ClassNotFoundException | SQLException e)
        {
            throw new LegoException("Order was not marked as shipped.", e.getMessage(), "OrderDAO.shipOrder(int)");            
        }
        
        // Records affected should only be one.
        return recordsAffected == 1;
    }
    
    /**
     * Creates an order with the provided collection of line items.
     * @param customerId Id of customer.
     * @param length Length of building.
     * @param width Width of building.
     * @param height Height of building.
     * @param pattern Pattern of bricks.
     * @param lineItems ArrayList of LineItemDTO objects.
     * @return OrderDTO object representing the order created.
     * @throws LegoException 
     */
    protected static OrderDTO createOrder(int customerId, int length, int width, int height, String pattern, ArrayList<LineItemDTO> lineItems) throws LegoException
    {
        OrderDTO order = null;
        try
        {
            // Get connection.
            Connection connection = DbConnection.getConnection();
            // Create a new order.
            PreparedStatement pstm = connection.prepareStatement( CREATE_ORDER_SQL, Statement.RETURN_GENERATED_KEYS );
            // Replace question mark with real value.
            pstm.setInt(1, customerId);
            pstm.setInt(2, length);
            pstm.setInt(3, width);
            pstm.setInt(4, height);
            pstm.setString(5, pattern);
                    
            
            // Execute the sql.
            pstm.executeUpdate();
            // Get the resultset with the key of newly created order.
            ResultSet rs = pstm.getGeneratedKeys();
            // Move the cursor to first record.
            rs.next();
            // Read the id from first (and only) column.
            int orderId = rs.getInt(1); // can we get more? Date e.g. Apparently no, ie. we must query db again to populate order completely.
            // Create the line items.
            for(LineItemDTO lineItem : lineItems)
            {
                pstm = connection.prepareStatement(CREATE_LINEITEM_SQL);
                pstm.setInt(1, orderId);
                pstm.setInt(2, lineItem.getBrickId());
                pstm.setInt(3, lineItem.getQty());
                pstm.executeUpdate();
            }
            
            // If we have come this far, we can retrieve the order from the database again.
            order = getOrder(orderId);
        }
        catch(SQLException | ClassNotFoundException e)
        {
            throw new LegoException("Order was not created", e.getMessage(), "OrderDAO.createOrder(ArrayList<LineItemDTO>)");
        }
        
        return order;
        
    }
    /**
     * Retrieves an OrderDTO object for the specified order id.
     * The OrderDTO User attribute is populated.
     * The OrderDTO lineItems are also populated as well as lineItems brick property.
     * @param orderId The orders id.
     * @throws LegoException
     * @return OrderDTO
     */
    protected static OrderDTO getOrder(int orderId) throws LegoException
    {        
        OrderDTO order = null;  
        UserDTO user = null;
        ArrayList<LineItemDTO> lineItems = new ArrayList<>();
        
        try
        {            
            // get connected.
            Connection connection = DbConnection.getConnection();

            PreparedStatement pstm = connection.prepareStatement(GET_ORDER_SQL);
            pstm.setInt(1, orderId);
            
            try(ResultSet rs = pstm.executeQuery();)
            {
                while(rs.next())
                {
                    // map order once first.
                    if (order == null)
                    {
                        order = OrderDTO.mapOrder(rs);
                    }
                    // create user once first (without storing the password - should not be retrievable in client).
                    if (user == null)
                    {
                        user = new UserDTO(rs.getInt("userId"), rs.getString("username"), null, rs.getString("email"), rs.getBoolean("employee") );
                    }
                    
                    // map the lineitem.
                    LineItemDTO lineItemDTO = LineItemDTO.mapLineItem(rs);
                    // map the brick and add to line items.
                    lineItemDTO.setBrick(BrickDTO.mapBrick(rs, "brickId", "brickWidth", "brickLength"));
                    // add line item to collection.
                    lineItems.add(lineItemDTO);                                        
                }  
                // Add lineitems to orderDTO.
                order.setLineItems(lineItems);
                // Add user to orderDTO.
                order.setUserDTO(user);
            }
        }
        catch(SQLException | ClassNotFoundException | NullPointerException e)
        {
            throw new LegoException("Order was not found.", e.getMessage(), "OrderDAO.getOrder(int)");
        }
        
        return order;
        
    }    
}