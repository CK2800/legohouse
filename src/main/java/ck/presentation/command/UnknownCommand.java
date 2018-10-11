/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation.command;

import ck.logic.LegoException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Claus
 */
public class UnknownCommand extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LegoException
    {
        // Here we will just throw an exception.
        throw new LegoException("Unknown command: " + command, "Unknown command: " + command, "UnknownCommand.execute() - command not found: " + command);        
    }
    
}
