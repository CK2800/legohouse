/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.data;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Claus
 */
public class OrderDTO
{
    private int id;
    private int userId;
    private Date orderDate;
    private Date shippedDate;
    private int length;
    private int width;
    private int height;
    private String pattern;
    private ArrayList<LineItemDTO> lineItems;
    private UserDTO userDTO;
    
    
    public int getId(){return id;}
    public int getUserId(){return userId;}
    public Date getOrderDate(){return orderDate;}
    public Date getShippedDate(){return shippedDate;}
    public int getLength(){return length;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public String getPattern(){return pattern;}
    public ArrayList<LineItemDTO> getLineItems(){return lineItems;}
    public UserDTO getUserDTO(){return userDTO;}
    
    /**
     * Creates an OrderDTO object with all attributes initialized except its line items and user.
     * @see setLineItems(...) 
     * @see setUserDTO(...)
     * @param id id of order.
     * @param customerId id of customer.
     * @param orderDate date of order.
     * @param shippedDate date of order shipment.     
     */
    public OrderDTO(int id, int customerId, Date orderDate, Date shippedDate, int length, int width, int height, String pattern)
    {
        this.id = id;
        this.userId = customerId;
        this.orderDate = orderDate;
        this.shippedDate = shippedDate;
        this.length = length;
        this.width = width;
        this.height = height;
        this.pattern = pattern;
        
    }
    
    public void setUserDTO(UserDTO userDTO)
    {
        this.userDTO = userDTO;
    }
    
    /**
     * Sets lineitems for this order.
     * @param lineItems ArrayList of LineItemDTO objects.
     */
    public void setLineItems(ArrayList<LineItemDTO> lineItems)
    {
        this.lineItems = lineItems;
    }
    
    
    /**
     * Maps an order from a database ResultSet tuple into a OrderDTO object.
     * The ResultSet must have following columns: id, userId, orderDate, shippedDate.
     * @param rs ResultSet of tuples.
     * @return OrderDTO object with data initialized.
     * @throws SQLException 
     */
    public static OrderDTO mapOrder(ResultSet rs) throws SQLException
    {    
        return new OrderDTO(rs.getInt("id"), 
                            rs.getInt("userId"), 
                            rs.getDate("orderDate"), 
                            rs.getDate("shippedDate"), 
                            rs.getInt("length"), 
                            rs.getInt("width"), 
                            rs.getInt("height"),
                            rs.getString("pattern"));
    }
}
