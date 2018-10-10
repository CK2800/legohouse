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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
     * Get details for order including its lineitems and brick of each line item.
     */
    private static final String GET_ORDER_SQL = "SELECT o.id, o.customerId, o.orderDate, o.shippedDate, " +
                                                "l.orderId, l.brickId, l.qty, " +
                                                "b.length, b.width " +
                                                "FROM orders o INNER JOIN lineitems l " + 
                                                "ON o.id = l.orderId " + 
                                                "INNER JOIN bricks b ON l.brickId = b.id " + 
                                                "WHERE o.id = ?;";
    
    private static final String CREATE_ORDER_SQL = "INSERT INTO orders(customerId) VALUES (?);"; HERTIL
    
    
    /**
     * Retrieves an OrderDTO object for the specified order id.
     * The OrderDTO lineItems are also populated as well as lineItems brick property.
     * @param orderId The orders id.
     * @return OrderDTO
     */
    public static OrderDTO getOrder(int orderId)
    {        
        OrderDTO order = null;        
        ArrayList<LineItemDTO> lineItems = new ArrayList<LineItemDTO>();
        
        try
        {            
            // get connected.
            Connection dbCon = DbConnection.getConnection();

            PreparedStatement pstm = dbCon.prepareStatement(GET_ORDER_SQL);
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
                    
                    // map the lineitem.
                    LineItemDTO lineItemDTO = LineItemDTO.mapLineItem(rs);
                    // map the brick and add to line items.
                    lineItemDTO.setBrick(BrickDTO.mapBrick(rs, "brickId", "width", "length"));
                    // add line item to collection.
                    lineItems.add(lineItemDTO);                                        
                }  
                // Add lineitems to orderDTO.
                order.setLineItems(lineItems);
            }
        }
        catch(Exception e)
        {
            // do something
        }
        
        return order;
        
    }
}