/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.logic;

import ck.data.BrickPattern;
import ck.data.DbConnection;
import ck.data.LineItemDTO;
import ck.data.OrderDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    
    @Test
    public void testGetOrder4() throws LegoException
    {
        OrderDTO order = OrderDAO.getOrder(1);
        assertNotNull(order.getUserDTO());
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
        
        OrderDTO newOrder = OrderDAO.createOrder(1, 10,5,4,"junittest", lineitems);
        assertNotNull(newOrder);
        
    }
    
    @Test
    public void testShipOrder() throws LegoException, SQLException
    {
        String SQL = "UPDATE orders SET shippedDate = null WHERE id = 1;";
        PreparedStatement pstm = connection.prepareStatement(SQL);
        pstm.execute();
        
        assertTrue(OrderDAO.shipOrder(1));
    }
    
    @Test
    public void testGetUserOrders() throws LegoException
    {
        ArrayList<OrderDTO> orders = OrderDAO.getOrders(1, false);
        System.out.println("orders: " + orders.size());
        assertTrue(orders.size() > 0);
    }
    
    @Test
    public void testGetOrdersAsEmployee() throws LegoException
    {
        ArrayList<OrderDTO> orders = OrderDAO.getOrders(1, true);        
        System.out.println("orders: " + orders.size());
        assertTrue(orders.size() > 0);
    }
    
//    @Test
//    public void testFillGap() throws LegoException
//    {
//        int length = 13;
//        BrickCalculator.initialize();
//        BrickCalculator.fillGap(length);
//        int dotsCalculated = 0;
//        for(LineItemDTO lineitem:BrickCalculator.getLayers())
//        {
//            dotsCalculated += lineitem.getBrick().getLength() * lineitem.getQty();
//        }
//        
//        System.out.println("Dots calculated: " + dotsCalculated);
//        
//        
//    }
    
//      @Test
//      public void testFillSequence() throws LegoException
//      {
//          int height = 1;
//          int length = 10;
//          BrickCalculator.initialize(height);
//          for(int i = 0; i < height; i++)
//          {
//              BrickCalculator.initializeLayer();
//              BrickCalculator.calculateSide(new int[]{1,3}, length);
//          }
//          assertTrue(height>2);
//
//      }
    @Test
    public void testCalculate() throws LegoException
    {
        int length = 13;
        int width = 9;
        int height = 2;
        int dotsNeeded = ((length-2) + (width-2))*2 * height;
        System.out.println("Dots needed: " + dotsNeeded);
        ArrayList<LineItemDTO>[] layers = BrickCalculator.calculate(BrickPattern.BLOKFORBANDT, length, width, height);
        //ArrayList<LineItemDTO> lineitems = BrickCalculator.calculate(BrickPattern.KVARTSTENSFORBANDT_B, length, width, height);
        int dotsCalculated = 0;
        for(ArrayList<LineItemDTO> al:layers)
            for(LineItemDTO lineitem:al)
                dotsCalculated += lineitem.getBrick().getLength() * lineitem.getQty();
                
        assertEquals(dotsNeeded,dotsCalculated);
    }
}
