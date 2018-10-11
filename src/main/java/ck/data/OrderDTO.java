/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.data;

import ck.logic.LegoException;
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
    private ArrayList<LineItemDTO> lineItems;
    
    public int getId(){return id;}
    public int getUserId(){return userId;}
    public Date getOrderDate(){return orderDate;}
    public Date getShippedDate(){return shippedDate;}
    public ArrayList<LineItemDTO> getLineItems(){return lineItems;}
    
    /**
     * Creates an OrderDTO object with all attributes initialized except its line items.
     * @see setLineItems(...)
     * @param id id of order.
     * @param customerId id of customer.
     * @param orderDate date of order.
     * @param shippedDate date of order shipment.     
     */
    public OrderDTO(int id, int customerId, Date orderDate, Date shippedDate)
    {
        this.id = id;
        this.userId = customerId;
        this.orderDate = orderDate;
        this.shippedDate = shippedDate;
        
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
     * @param rs ResultSet of tuples.
     * @return OrderDTO object with data initialized.
     * @throws LegoException 
     */
    public static OrderDTO mapOrder(ResultSet rs) throws LegoException
    {
        try
        {
            return new OrderDTO(rs.getInt("id"), rs.getInt("userId"), rs.getDate("orderDate"), rs.getDate("shippedDate"));
        }
        catch(SQLException e)
        {
            throw new LegoException("Order could not be found in the system.", e.getMessage(), "OrderDTO.mapOrder(ResultSet)");
        }
    }
}
