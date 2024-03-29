/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation;

import ck.logic.LegoException;
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
     * Name of variable in the request object that contains an error text. 
     */
    public static final String ERROR_TEXT = "errorText";
    /**
     * Name of variable in form or querystring that contains path to return to if error occurs.
     */
    public static final String ERROR_PATH = "errorPath";
    
    
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
            // Forward the request to the resulting servlet that must reside in WEB-INF folder.             
            // Remember, jsp is compiled, run and the result sent to browser.
            request.getRequestDispatcher(view).forward(request, response);
        }
        catch(LegoException e)
        {
            // save error message in request.
            request.setAttribute(ERROR_TEXT, e.getMessage());
//            // If the request has specified a path to return to, we will do this now. 
//            // The path must be the complete path from root.
//            String view = request.getParameter(ERROR_PATH);
//            if (view == null) // no error path, go to home/index
//                view = Pages.INDEX;            
            String view = Pages.INDEX;
            try
            {
                view = new UserPageCommand().execute(request, response);
            }
            catch(LegoException le)
            {
                // do nothing, we have catched the original 
                // LegoException and set errormessage previously.
            }
            request.getRequestDispatcher(view).forward(request, response);
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
