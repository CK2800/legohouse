/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation.command;

import ck.data.BrickPattern;
import ck.data.OrderDTO;
import ck.data.UserDTO;
import ck.logic.UserDAO;
import ck.logic.LegoException;
import ck.logic.LogicFacade;
import ck.presentation.Pages;
import java.util.ArrayList;
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
        boolean employee = (request.getParameter("employee") != null);
        
        UserDTO userDTO = LogicFacade.createUser(username, email, password, password2, employee);
        
        // request holds same parameters as it would if we were loggin in
        // so we can easily have the login command log us in and create
        // necessary objects for the view.
        return new LoginCommand().execute(request, response);
    }    
}
