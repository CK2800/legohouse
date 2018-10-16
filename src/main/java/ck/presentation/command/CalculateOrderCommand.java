/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation.command;

import ck.data.BrickPattern;
import ck.data.LineItemDTO;
import ck.logic.BrickCalculator;
import ck.logic.LegoException;
import ck.logic.LogicFacade;
import ck.presentation.Pages;
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
        String brickPattern = request.getParameter("brickPattern");
        int length = Integer.valueOf(request.getParameter("length"));
        int width = Integer.valueOf(request.getParameter("width"));
        int height = Integer.valueOf(request.getParameter("height"));
        // Calculate and set result on session and return to customers page.
        ArrayList<LineItemDTO>[] layers = LogicFacade.calculate(brickPattern, length, width, height);        
        request.getSession().setAttribute("layers", layers);
        
        // Customer should be able to select from patterns.
        ArrayList<String> brickPatterns = BrickPattern.getPatterns();
        request.setAttribute("brickPatterns", brickPatterns);
        
        return Pages.CUSTOMER;
    }
}
