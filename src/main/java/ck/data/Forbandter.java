/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.data;

/**
 * Matricer med forsk. forbandter.
 * Hvert forbandt består af et antal skifter. 
 * Første tal i hvert skifte angiver "offset" ift. nederste skifte. 
 * Efterfølgende tal i hvert skifte, angiver brick ids.
 * Positiv offset betyder "antal prikker mod højre".
 * Et forbandt består af x skifter. Når der ikke er flere skifter i forbandtet, begyndes forfra.
 * 
 * @author Claus
 */
public class Forbandter
{
    public static final String[] FORBANDTER = {"Løberforbandt 1", "Løberforbandt 2"};
    
    public static final int[][] LOEBERFORBANDT1 = { {0,3}, {1,3}, {2,3}, {1,3} };    
    public static final int[][] LOEBERFORBANDT2 = { {0,3}, {2,3}, {-1,3}, {1,3} };    
    
}
