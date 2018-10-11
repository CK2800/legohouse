/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.logic;

import ck.data.DbConnection;
import ck.data.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Claus
 */
public class UserDAOJUnitTest
{
    static Connection connection = null;
    
    public UserDAOJUnitTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
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
    
    @AfterClass
    public static void tearDownClass() throws SQLException
    {        
        // Delete user created in TestCreateEmployee.
        String SQL = "DELETE FROM users WHERE email = 'anders@and.dk' OR email = 'andersine@and.dk';";
        PreparedStatement pstm = connection.prepareStatement(SQL);
        pstm.execute();
        
        DbConnection.closeConnection();
    }
    
    @Before
    public void setUp()
    {
        
    }
    
    @After
    public void tearDown() throws SQLException
    {           
        // done after each test.
    }

    
    @Test
    public void testConnection()
    {        
        assertNotNull(connection);
    }
    
    @Test
    public void testCreateEmployee() throws LegoException
    {        
        String username = "Anders";
        String email = "anders@and.dk";
        String password = "rapraprap";
        boolean employee = true;
        UserDTO user = UserDAO.createUser(username, email, password, employee);        
        assertNotNull(user);
    }
    
    @Test
    public void testCreateUser() throws LegoException
    {        
        String username = "Andersine";
        String email = "andersine@and.dk";
        String password = "rapraprap";
        boolean employee = false;
        UserDTO user = UserDAO.createUser(username, email, password, employee);
        assertNotNull(user);
    }
    @Test
    public void testValidateUser() throws LegoException
    {        
        UserDTO user = UserDAO.validateUser("anders@and.dk", "rapraprap");
        System.out.println("User validated: " + user.getEmail() + " is an employee ? " + user.getEmployee());
        assertNotNull(user);
    }
    @Test(expected = LegoException.class )
    public void testValidateUserFails() throws LegoException
    {
        // Using a wrong password should yield a LegoException.
        UserDTO user = UserDAO.validateUser("anders@and.dk", "izNoGood");
        
    }
}
