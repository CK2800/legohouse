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
public class BrickDTO
{
    private int id;
    private int width;
    private int length;
    public int getId(){return id;}
    public int getWidth(){return width;}
    public int getLength(){return length;}
    
    /**
     * Creates a BrickDTO object with all required attributes.
     * @param id id of the brick.
     * @param width width of the brick.
     * @param length length of the brick.
     */
    public BrickDTO(int id, int width, int length)
    {
        this.id = id;
        this.width = width;
        this.length = length;
    }
    
    /**
     * Maps a brick from a database ResultSet tuple into a BrickDTO object.
     * @param rs ResultSet of tuples.
     * @return BrickDTO object with data initialized.
     * @throws LegoException
     */
    public static BrickDTO mapBrick(ResultSet rs) throws LegoException
    {
        try
        {
            return new BrickDTO(rs.getInt("id"), rs.getInt("width"), rs.getInt("length"));
        }
        catch(SQLException e)
        {
            throw new LegoException("Brick could not be found in the system.", e.getMessage(), "BrickDTO.mapBrick(ResultSet)");
        }
    }
}
