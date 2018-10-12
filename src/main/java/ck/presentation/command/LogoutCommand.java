/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation.command;

import ck.logic.LegoException;
import ck.presentation.Pages;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Claus
 */
public class LogoutCommand extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LegoException
    {
        // Invalidate the session.
        LoginCommand.invalidateSession(request);
        return Pages.INDEX;
    }
}
