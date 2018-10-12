/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation.command;

import ck.logic.LegoException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Abstract command class to be extended by children.
 * Functionality common to all implementing classes is assembled here.
 * Use static String members to specify the supported commands in the views.
 * @author Claus
 */
public abstract class Command
{
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String CREATE_ORDER = "createOrder";
    public static final String SHIP_ORDER = "shipOrder";
    public static final String CREATE_USER = "createUser";
    public static final String SHOW_ORDER = "showOrder";
    
    public static String command;
    // HashMap of available commands.
    private static HashMap<String, Command> commands;
    
    /**
     * Intializes the hash map of commands.
     */
    private static void initializeCommands()
    {
        commands = new HashMap<>();
        commands.put( LOGIN, new LoginCommand() );
        commands.put( CREATE_ORDER, new CreateOrderCommand() );
        commands.put( SHIP_ORDER, new ShipOrderCommand() );
        commands.put( CREATE_USER, new CreateUserCommand() );
        commands.put( LOGOUT, new LogoutCommand() );
        commands.put( SHOW_ORDER, new ShowOrderCommand() );
        
    }
    
    /**
     * Gets the parameter named 'command' from the request and returns
     * the corresponding Command child object.
     * 
     * @param request HttpServletRequest
     * @return Command object.
     */
    public static Command from(HttpServletRequest request)
    {
        // Get command from request.
        command = request.getParameter("command");
        // Initialize the hash map.
        if (commands == null)
            initializeCommands();
        
        // Hent Command objekt fra hashmap hvis det findes, ellers returner 
        // objekt af UnknownCommand.
        return commands.getOrDefault(command, new UnknownCommand());        
    }
    
    /**
     * Abstract method execute must be implemented in children.
     * @param request
     * @param response
     * @return
     * @throws LegoException 
     */
    abstract public String execute(HttpServletRequest request, HttpServletResponse response) throws LegoException;
}
