/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 * Abstract command class to be extended by children.
 * Functionality common to all implementing classes is assembled here.
 * @author Claus
 */
abstract class Command
{
    public static final String LOGIN = "login";
    public static final String CREATE_ORDER = "createOrder";
    public static final String SHIP_ORDER = "shipOrder";
    // HashMap of available commands.
    private static HashMap<String, String> commands;
    
    /**
     * Intializes the hash map of commands.
     */
    private static void initializeCommands()
    {
        commands = new HashMap<>();
        commands.put( LOGIN, new LoginCommand() );
        commands.put( CREATE_ORDER, new CreateOrderCommand() );
        commands.put( SHIP_ORDER, new ShipOrderCommand() );
    }
    
    /**
     * Gets the parameter named 'command' from the request and returns
     * the corresponding Command child object.
     * 
     * @param request HttpServletRequest
     * @return Command object.
     */
    static Command from(HttpServletRequest request)
    {
        // Get command from request.
        String command = request.getParameter("command");
        // Initialize the hash map.
        if (commands == null)
            initializeCommands();
        
        // Hent Command objekt fra hashmap hvis det findes, ellers returner 
        // objekt af UnknownCommand.
        return commands.getOrDefault(command, new UnknownCommand());        
    }
    
    // Abstract method execute must be implemented in children.
    abstract String execute(HttpServletRequest request, HttpServletResponse response) throws LegoException;
}
