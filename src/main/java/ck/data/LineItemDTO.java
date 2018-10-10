/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.data;

import ck.logic.LegoException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Claus
 */
public class LineItemDTO
{
    // fields.
    private int orderId;
    private int brickId;
    private int qty;
    private BrickDTO brickDTO;
    // getters.
    public int getOrderId(){return orderId;}
    public int getBrickId(){return brickId;}
    public BrickDTO getBrick(){return brickDTO;}
    public int getQty(){return qty;}
    
    /**
     * Constructor with all attributes initialized.
     * @param orderId id of order.
     * @param brickId id of brick.
     * @param qty quantity of bricks.
     */
    public LineItemDTO(int orderId, int brickId, int qty)
    {
        this.orderId = orderId;
        this.brickId = brickId;
        this.qty = qty;
    }
    
    /**
     * Constructor for line items on a pending order.
     * @param brickId id of brick.
     * @param qty quantity of bricks.
     */
    public LineItemDTO(int brickId, int qty)
    {
        this.brickId = brickId;
        this.qty = qty;
    }
    
    /**
     * Sets brick for this lineitem.
     * @param brickDTO BrickDTO object.
     */
    public void setBrick(BrickDTO brickDTO)
    {
        this.brickDTO = brickDTO;
    }
    
    public static LineItemDTO mapLineItem(ResultSet rs) throws LegoException
    {
        try
        {
            return new LineItemDTO(rs.getInt("orderId"), rs.getInt("brickId"), rs.getInt("qty"));
        }
        catch(SQLException e)
        {
            throw new LegoException("Line item could not be found in database", e.getMessage(), "LineItemDTO.mapLineItem(ResultSet)");
        }
        
    }
    
}
