/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.data;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Claus
 */
public class UserDTO
{
    private int id;
    private String username;
    private String password;
    private String email;
    private boolean employee;
    
    public int getId(){return id;}
    public String getUsername(){return username;}    
    public String getPassword(){return password;} // note to self - if this is plaintext, it's no good!
    public String getEmail(){return email;}
    /**
     * Indicates whether the user is an employee.
     * @return true if user is employee.
     */
    public boolean isEmployee(){return employee;}
    
    /**
     * Creates a new UserDTO object with all required arguments.
     * @param id id of user.
     * @param username users username.
     * @param password users password.
     * @param email users unique email.
     * @param employee indicates if user is employee.
     */
    public UserDTO(int id, String username, String password, String email, boolean employee)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.employee = employee;
    }
            
    /**
     * Maps a user from a database ResultSet tuple into a UserDTO object.
     * @param rs ResultSet of tuples.
     * @return UserDTO object with data initialized.
     * @throws SQLException 
     */
    public static UserDTO mapUser(ResultSet rs) throws SQLException
    {
        return new UserDTO(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getBoolean("employee"));        
    }
    
    /**
     * Overload of Object.equals() to ensure that 
     * two UserDTO objects have same member values.     
     * @param other
     * @return true if all members hold equal values.
     */
    @Override
    public boolean equals(Object other)
    {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof UserDTO))
            return false;
        
        UserDTO otherUserDTO = (UserDTO)other;
        return (this.id == (otherUserDTO.getId()) &&
                this.email.equals(otherUserDTO.getEmail()) && 
                this.password.equals(otherUserDTO.getPassword()) && 
                this.username.equals(otherUserDTO.getUsername()));                
    }
    
    @Override
    public String toString()
    {
        String strUser = "id: $id, username: $username, email: $email, type: $type";
        strUser = strUser.replace("$id", String.valueOf(id));
        strUser = strUser.replace("$username", username);
        strUser = strUser.replace("$email", email);
        strUser = strUser.replace("$type", (employee ? "employee" : "customer"));
        
        return strUser;
    }
    
        
    
}
