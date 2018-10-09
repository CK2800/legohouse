/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.logic;

import ck.data.DbConnection;
import ck.data.LineItemDTO;
import ck.data.OrderDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Claus
 */
public class OrderDAO
{
    /**
     * Get details for order including its lineitems.
     */
    private static final String GET_ORDER_SQL = "SELECT o.id, o.customerId, o.orderDate, o.shippedDate, l.orderId, l.brickId, l.qty " +
                                                "FROM orders o INNER JOIN lineitems l " + 
                                                "ON o.id = l.orderId WHERE o.id = ?;";
    
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
                    lineItems.add(LineItemDTO.mapLineItem(rs));
                }                
            }
        }
        catch(Exception e)
        {
            // do something
        }
        
    }
}