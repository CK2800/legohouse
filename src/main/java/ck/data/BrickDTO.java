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
     * @param idCol Name of column in ResultSet that holds id of brick.
     * @param widthCol Name of column in ResultSet that holds width of brick.
     * @param lengthCol Name of column in ResultSet that holds length of brick.
     * @return BrickDTO object with data initialized.
     * @throws LegoException
     */
    public static BrickDTO mapBrick(ResultSet rs, String idCol, String widthCol, String lengthCol) throws LegoException
    {
        try
        {
            return new BrickDTO(rs.getInt(idCol), rs.getInt(widthCol), rs.getInt(lengthCol));
        }
        catch(SQLException e)
        {
            throw new LegoException("Brick could not be found in the system.", e.getMessage(), "BrickDTO.mapBrick(ResultSet)");
        }
    }
    
    /**
     * Friendly string representation of the brick.
     * @return String
     */
    @Override
    public String toString()
    {
        return width + " x " + length;
    }
}
