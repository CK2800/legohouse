/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.data;

import java.util.HashMap;

/**
 * Matrices with various brick patterns.
 * Each pattern consists of length and a number of layers.
 * First sub array indicates the brick pattern length.
 * First digit of each of the following arrays indicates an offset from base layer. 
 * The rest of the digits indicates brick ids.
 * A positive offset means "number of dots to the right." 
 * 
 * This class is a singleton.
 * 
 * @author Claus
 */
public class BrickPattern
{   

    
    public static final String LOEBERFORBANDT_1 = "Løberforbandt 1";
    public static final String LOEBERFORBANDT_2 = "Løberforbandt 2";
    public static final String KVARTSTENSFORBANDT_A = "1/4 stensforbandt A";
    public static final String KVARTSTENSFORBANDT_B = "1/4 stensforbandt B";
    public static final String HALVSTENSFORBANDT = "1/2 stensforbandt";
    public static final String BLOKFORBANDT = "Blokforbandt";
    
    // jagged arrays of {offset from base, length of brick}.
    private static final int[][] LOEBERFORBANDT_1_ARR = { {0,3}, {1,3}, {2,3}, {1,3} };    
    private static final int[][] LOEBERFORBANDT_2_ARR = { {0,3}, {2,3}, {1,3}, {3,3} };    
    private static final int[][] KVARTSTENSFORBANDT_A_ARR = { {0,3}, {1,3} };
    private static final int[][] KVARTSTENSFORBANDT_B_ARR = { {0,3}, {1,3}, {2,3}, {3,3} };
    private static final int[][] HALVSTENSFORBANDT_ARR = { {0,3}, {2,3} };
    private static final int[][] BLOKFORBANDT_ARR = { {0,3}, {1,2} };
    
    private static HashMap<String, int[][]> patterns;
    
    private BrickPattern(){}; // No instantiation.
    
    private static void initializePatterns()
    {
        patterns = new HashMap<>();
        patterns.put(LOEBERFORBANDT_1, LOEBERFORBANDT_1_ARR);
        patterns.put(LOEBERFORBANDT_2, LOEBERFORBANDT_2_ARR);
        patterns.put(KVARTSTENSFORBANDT_A, KVARTSTENSFORBANDT_A_ARR);
        patterns.put(KVARTSTENSFORBANDT_B, KVARTSTENSFORBANDT_B_ARR);
        patterns.put(HALVSTENSFORBANDT, HALVSTENSFORBANDT_ARR);
        patterns.put(BLOKFORBANDT, BLOKFORBANDT_ARR);        
    }
    
    /**
     * Retrieves the pattern matching the name.
     * @param name
     * @return 2 dimensional jagged int array.
     */
    public static int[][] getPattern(String name)
    {
        if (patterns == null)
            initializePatterns();
        
        // Get pattern with name or LOEBERFORBANDT_1_ARR
        return patterns.getOrDefault(name, LOEBERFORBANDT_1_ARR); 
    }
}
