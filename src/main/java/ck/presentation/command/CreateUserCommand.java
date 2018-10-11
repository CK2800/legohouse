/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation.command;

import ck.data.UserDTO;
import ck.logic.UserDAO;
import ck.logic.LegoException;
import ck.presentation.Pages;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Claus
 */
public class CreateUserCommand extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LegoException
    {
        // Get parameters from request.
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        boolean employee = Boolean.parseBoolean(request.getParameter("employee"));

        // do passwords match?
        if (password.equals(password2))            
        {
            UserDTO userDTO = UserDAO.createUser(username, email, password, employee);

            // Log in the new customer.
            LoginCommand.invalidateSession(request);
            LoginCommand.loginUser(request, userDTO);                

            return employee ? Pages.EMPLOYEE : Pages.CUSTOMER;
        }
        else
            throw new LegoException("Passwords do not match.", "Passwords do not match.", "CreateCustomerCommand.execute()");                            
        
    }    
}
