/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.logic;

/**
 *
 * @author Claus
 */
public class LegoException extends Exception
{
    private String detailedMessage = "no details.";
    private String originOfException = "no origin.";
    public String getDetailedMessage(){return detailedMessage;}
    public String getOriginOfException(){return originOfException;}
    /**
     * Wraps friendly and detailed info for use in GUI as well as logging system.
     * @param friendlyMessage Message to display to user in GUI.
     * @param detailedMessage Message to log or otherwise display, e.g. console.
     * @param originOfException Describes method name where the exception occured.
     */
    public LegoException(String friendlyMessage, String detailedMessage, String originOfException)
    {
        super(friendlyMessage);
        this.detailedMessage = detailedMessage; // or log or print to sout ?
    }
}
