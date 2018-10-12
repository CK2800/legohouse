/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation.viewmodels;

import ck.data.OrderDTO;
import java.sql.ResultSet;

/**
 * A fabricated composite of OrderDTO and user info
 * to display in the view.
 * @author Claus
 */
public class OrderUserComposite
{
    private OrderDTO orderDTO;
    private String username;
    private String email;
    public OrderDTO getOrder(){return orderDTO;}
    public String getUsername(){return username;}
    public String getEmail(){return email;}
    
    public OrderUserComposite(OrderDTO orderDTO, String username, String email)
    {
        this.orderDTO = orderDTO;
        this.username = username;
        this.email = email;
    }  
    
    @Override
    public String toString()
    {
        String result = "username: $username, email: $email, order: $order";    
        result = result.replace("$username", username);
        result = result.replace("$email", email);
        result = result.replace("$order", orderDTO.toString());
        return result;        
    }
}
