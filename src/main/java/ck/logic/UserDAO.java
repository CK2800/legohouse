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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Claus
 */
public class UserDAO
{
    
    private static final String CREATE_USER_SQL = "INSERT INTO users(username, email, password, employee) VALUES (?,?,?,?);";
    private static final String VALIDATE_USER_SQL = "SELECT * FROM users WHERE email = ? AND password = ?;";
    
    /**
     * Create a new user.
     * @param username
     * @param email
     * @param password
     * @param password2 Must match password.
     * @param employee boolean to indicate whether user is an employee.
     * @return A UserDTO object representing the created user.
     * @throws LegoException 
     */
    protected static UserDTO createUser(String username, String email, String password, String password2, boolean employee) throws LegoException
    {    
        // trim whitespace.
        username = username.trim();
        email = email.trim();
        password = password.trim();
        password2 = password2.trim();
        // to do set up regex validation.
        
        if (username.length() > 0 && email.length() > 0 && password.length() > 0 && password.equals(password2))
        {        
            try
            {
                // Get connected and set flag for key retrieval.
                Connection connection = DbConnection.getConnection();
                PreparedStatement pstm = connection.prepareStatement(CREATE_USER_SQL, Statement.RETURN_GENERATED_KEYS);
                // Substitute placeholders with real values.
                pstm.setString(1, username);
                pstm.setString(2, email);
                pstm.setString(3, password);
                pstm.setBoolean(4, employee);
                // execute.
                pstm.executeUpdate();
                // get id of created user.
                ResultSet rs = pstm.getGeneratedKeys();
                rs.next(); // move cursor from BOF to 1st tuple.
                int customerId = rs.getInt(1);
                // Create the UserDTO and return it.
                return new UserDTO(customerId, username, email, password, employee);            
            }
            catch(ClassNotFoundException | SQLException e)
            {
                throw new LegoException("User was not created.", e.getMessage(), "UserDAO.createUser(String, String, String)");
            }       
        }
        else // empty username, email or password.
        {
            throw new LegoException("User input not valid.", "Username, email or password empty.", "UserDAO.createUser(String, String, String)");
        }
    }
    
    /**
     * Validats a user against the database.
     * @param email Users email.
     * @param password Users password.
     * @return The validated user as a UserDTO.
     * @throws LegoException If the user is not validated or an error occurs.
     */
    protected static UserDTO validateUser(String email, String password) throws LegoException
    {   
        // Trim input
        email = email.trim();        
        password = password.trim();
        // todo set up regex validation
        
        if (email.length() > 0 && password.length() > 0)
        {
            try
            {
                // Get connected.
                Connection connection = DbConnection.getConnection();
                PreparedStatement pstm = connection.prepareStatement(VALIDATE_USER_SQL);
                pstm.setString(1, email);
                pstm.setString(2, password);
                // execute query.
                ResultSet rs = pstm.executeQuery();
                rs.next();            
                return UserDTO.mapUser(rs);                
            }
            catch(ClassNotFoundException | SQLException e)
            {
                throw new LegoException("User could not be validated", e.getMessage(), "UserDAO.loginUser(String, String)");
            }
        }
        else // empty email or password
        {
            throw new LegoException("User input not valid", "Email or password empty.", "UserDAO.loginUser(String, String)");
        }
    }
    
}
