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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Claus
 */
public class OrderDAOJUnitTest
{
    
    Connection connection = null;
    
    public OrderDAOJUnitTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
        try
        {
            connection = DbConnection.getConnection();
        }
        catch(Exception e)
        {
            System.out.println("No database connection established: " + e.getMessage());
        }
    }
    
    @After
    public void tearDown()
    {
        try
        {
        DbConnection.closeConnection();
        }
        catch(Exception e)
        {
            System.out.println("Database connection was not closed: " + e.getMessage());
        }
    }
    
    /**
     * Test that connection is working.
     */
    @Test
    public void testConnection()
    {
        assertNotNull(connection);
    }
    
    /** 
     * Test that we can retrieve an order.
     */
    @Test
    public void testGetOrder()
    {
        OrderDTO order = OrderDAO.getOrder(1);        
        assertTrue(order != null);
    }
    
    /**
     * Test that retrieved order has lineitems.
     */
    @Test
    public void testGetOrder2()
    {
        OrderDTO order = OrderDAO.getOrder(1);
        assertTrue(order.getLineItems().size() > 0); 
    }
    
    /**
     * Test that retrieved orders first lineitem has brick.
     */
    @Test
    public void testGetOrder3()
    {
        OrderDTO order = OrderDAO.getOrder(1);
        assertTrue(order.getLineItems().get(0).getBrick() != null);
    }
}
