/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation.command;

import ck.data.LineItemDTO;
import ck.logic.LegoException;
import ck.logic.LogicFacade;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Calculates the amount of bricks needed for the house with the specified dimensions.
 * @author Claus
 */
public class CalculateOrderCommand extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LegoException
    {   
        String brickPattern = null;
        int length, width, height;
        try
        {
            brickPattern = request.getParameter("brickPattern");
            length = Integer.valueOf(request.getParameter("length"));
            width = Integer.valueOf(request.getParameter("width"));
            height = Integer.valueOf(request.getParameter("height"));
        }
        catch(NumberFormatException e)
        {        
            throw new LegoException("You need to fill in dimensions!", "Dimensions needed: " + e.getMessage(), "CalculateOrderCommand.execute()");
        }
        // Calculate and set result in request and return to customers page.
        ArrayList<LineItemDTO>[] layers = LogicFacade.calculate(brickPattern, length, width, height);        
        //request.getSession().setAttribute("layers", layers);
        request.setAttribute("layers", layers);
        
        // Calculation is stored in session, return to user page.
        return new UserPageCommand().execute(request, response);  
    }
}
