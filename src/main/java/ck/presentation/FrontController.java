/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ck.presentation.command.*;

/**
 *
 * @author Claus
 */
@WebServlet(name = "FrontController", urlPatterns = { "/FrontController" })
public class FrontController extends HttpServlet
{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {            
            // Get the right command from the request.
            Command command = Command.from(request);
            // Execute the command and get the view.
            String view = command.execute(request, response);
            // Forward the request to the resulting servlet - 
            // remember, jsp is compiled, run and the result sent to browser.
            request.getRequestDispatcher("/WEB-INF/" + view + ".jsp").forward(request, response);
        }
        catch(Exception e)
        {
            // save error message in request.
            request.setAttribute("errorText", e.getMessage());
            // If the request has specified a path to return to, we will do this now.
            String view = request.getParameter("errorPath");
            if (view == null) // no error path, go to home/index
                view = Pages.INDEX;            
            
            request.getRequestDispatcher("/WEB-INF/" + view + ".jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
