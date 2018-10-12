/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.logic;

import ck.data.DbConnection;
import ck.data.LineItemDTO;
import ck.data.OrderDTO;
import ck.presentation.viewmodels.OrderUserComposite;
import java.sql.Connection;
import java.util.ArrayList;
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
     * @throws LegoException
     */
    @Test
    public void testGetOrder() throws LegoException
    {
        OrderDTO order = OrderDAO.getOrder(1);        
        assertTrue(order != null);
    }
    
    /**
     * Test that retrieved order has lineitems.
     * @throws LegoException
     */
    @Test
    public void testGetOrder2() throws LegoException
    {
        OrderDTO order = OrderDAO.getOrder(1);
        assertTrue(order.getLineItems().size() > 0); 
    }
    
    /**
     * Test that retrieved orders first lineitem has brick.
     * @throws LegoException
     */
    @Test
    public void testGetOrder3() throws LegoException
    {
        OrderDTO order = OrderDAO.getOrder(1);
        assertTrue(order.getLineItems().get(0).getBrick() != null);
    }
    
    /**
     * Test creation of order.
     * @throws LegoException 
     */
    @Test
    public void testCreateOrder() throws LegoException
    {
        ArrayList<LineItemDTO> lineitems = new ArrayList();
        lineitems.add(new LineItemDTO(1,2));
        lineitems.add(new LineItemDTO(2,3));
        lineitems.add(new LineItemDTO(3,4));
        
        OrderDTO newOrder = OrderDAO.createOrder(1, lineitems);
        assertNotNull(newOrder);
        
    }
    
    @Test
    public void testShipOrder() throws LegoException
    {
        assertTrue(OrderDAO.shipOrder(1));
    }
    
    @Test
    public void testGetUserOrders() throws LegoException
    {
        ArrayList<OrderDTO> orders = OrderDAO.getUserOrders(1);
        assertTrue(orders.size() > 0);
    }
    
    @Test
    public void testGetUnshippedOrders() throws LegoException
    {
        ArrayList<OrderUserComposite> orders = OrderDAO.getUnshippedOrders();
        System.out.println(orders.get(0).toString());
        assertTrue(orders.size() > 0);
    }
}
