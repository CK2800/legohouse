/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.logic;

import ck.data.BrickDTO;
import ck.data.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Claus
 */
public class BrickDAO
{
    private static final String GET_BRICK_SQL = "SELECT id, width, length FROM bricks where id = ?;";
    private static final String GET_BRICKS_SQL = "SELECT id, width, length FROM bricks";
    private static final String GET_LENGTH_SQL = "SELECT SUM(length) as totalLength FROM bricks WHERE id IN ($params);";
        
    protected static BrickDTO getBrick(int id) throws LegoException
    {
        BrickDTO brickDTO = null;
        try
        {
            // get connected
            Connection connection = DbConnection.getConnection();
            PreparedStatement pstm = connection.prepareStatement(GET_BRICK_SQL);
            pstm.setInt(1,id);
            try(ResultSet rs = pstm.executeQuery();)
            {
                if (rs.next())
                    brickDTO = BrickDTO.mapBrick(rs, "id", "width", "length");                    
            }
        }
        catch(ClassNotFoundException | SQLException e)
        {
            throw new LegoException("Brick not found.", e.getMessage(), "BrickDAO.getBrick(int)");
        }
        return brickDTO;
        
    }

    static ArrayList<BrickDTO> getBricks() throws LegoException
    {
        ArrayList<BrickDTO> bricks = new ArrayList<>();
        try
        {
            // get connected
            Connection connection = DbConnection.getConnection();
            PreparedStatement pstm = connection.prepareStatement(GET_BRICKS_SQL);
            
            try(ResultSet rs = pstm.executeQuery();)
            {
                while (rs.next())
                    bricks.add(BrickDTO.mapBrick(rs, "id", "width", "length"));                    
            }
        }
        catch(ClassNotFoundException | SQLException e)
        {
            throw new LegoException("Bricks not found.", e.getMessage(), "BrickDAO.getBricks()");
        }
        
        return bricks;
    }
}
