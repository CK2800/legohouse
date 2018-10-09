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
public class CustomerDTO
{
    private int id;
    private String username;
    private String password;
    private String email;
    
    public int getId(){return id;}
    public String getUsername(){return username;}    
    public String getPassword(){return password;} // note to self - if this is plaintext, it's no good!
    public String getEmail(){return email;}
    
    /**
     * Creates a new customerDTO object with all required arguments.
     * @param id id of customer.
     * @param username customers username.
     * @param password customers password.
     * @param email customers unique email.
     */
    public CustomerDTO(int id, String username, String password, String email)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    /**
     * Maps a customer from a database ResultSet tuple into a CustomerDTO object.
     * @param rs ResultSet of tuples.
     * @return CustomerDTO object with data initialized.
     * @throws LegoException 
     */
    public static CustomerDTO mapCustomer(ResultSet rs) throws LegoException
    {
        try
        {
            return new CustomerDTO(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"));
        }
        catch(SQLException e)
        {
            throw new LegoException("Customer could not be found in the system.", e.getMessage(), "CustomerDTO.mapCustomer(ResultSet)");
        }
    }
    
        
    
}
