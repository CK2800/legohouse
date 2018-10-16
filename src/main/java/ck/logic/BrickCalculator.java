/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.logic;

import ck.data.BrickDTO;
import ck.data.BrickPattern;
import ck.data.LineItemDTO;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class calculates the pieces for a house with the chosen pattern.
 * @author Claus
 */
public class BrickCalculator
{
    private static ArrayList<LineItemDTO> lineItems;
    private static ArrayList<BrickDTO> bricks;
    
    public static ArrayList<LineItemDTO> getLineItems(){return lineItems;}
    
    
    
    /**
     * Helper method to fill gap on wall.
     * PRE: static variable bricks must be initialized.
     * PRE: bricks must be ordered descending on brick length.
     * @param length The length that needs filling.
     * 
     */
    public static void fillGap(int length)
    {     
        System.out.println("filling " + length);
        // As long as a gap exists...
        if(length > 0)
        {
            // ...loop through bricks.
            for(BrickDTO brick:bricks)
            {
                // If brick can fit in gap, increment its quantity, subtract its length. 
                if(brick.getLength() <= length)
                {
                    AddQty(brick, 1);
                    length -= brick.getLength();
                    break;
                }                    
            }                        
        }
        if (length > 0)
        {
            // Call recursively untill length has been filled.
            fillGap(length); 
        }
        
    }
    
    /**
     * Helper method to add a quantity of a certain brick to the line items.
     */
    private static void AddQty(BrickDTO brickDTO, int qty)
    {
        for(LineItemDTO lineItem : lineItems)
        {
            if (lineItem.getBrickId() == brickDTO.getId())
                lineItem.setQty(lineItem.getQty() + qty);
        }
    }
    
    public static void initialize() throws LegoException
    {
        // Initialize lineItems.
        lineItems = new ArrayList<>();        
        // get bricks from system.
        bricks = BrickDAO.getBricks();
        // Sort list on brick length descending.
        Collections.sort(bricks);        
        Collections.reverse(bricks);
        
        // Add a line item for each brick, with qty = 0.
        for(BrickDTO brick:bricks)
        {
            LineItemDTO lineitem = new LineItemDTO(brick.getId(), 0);
            lineitem.setBrick(brick);
            lineItems.add(lineitem);
        }   
    }
    
    private static void calculateSide(int[] sequence, int length)
    {
        // offset for sequence is at position 0.
        int offset = sequence[0];
        
        // Fill gaps before sequence if necessary.
        if (offset > 0)
        {
            fillGap(Math.abs(offset-2));
            // adjust length to factor in the offset.
            length -= (Math.abs(offset-2));            
        }
        
        length = fillSequence(sequence, length);       
                
        // Fill the remaining gap if any.
        fillGap(length);    
    }
    
    public static ArrayList<LineItemDTO> calculate(String patternName, int length, int width, int height) throws LegoException
    {   
        // Set up needed variables.
        initialize();
        
        // subtract 2 from length and width since their values include brick width of adjacent wall.
        length -= 2;
        width -= 2;
        
        // get pattern.
        int[][]pattern = BrickPattern.getPattern(patternName);        
                
        // stopping conditions:
        //if (patternLength > length)
        if (false)
        {
            throw new LegoException("Your house length is not suitable for this brick pattern", "House length < brick pattern length", "BrickCalculator.Calculate()");
        }// more...
        else
        {    
            for (int layer = 0; layer < height; layer++)
            {
                // find sequence for this layer, remember we may have more layerns than sequences in pattern.
                int[] sequence = pattern[layer % pattern.length];
                
                // Divide and CONQUER.... YAAARGH !
                calculateSide(sequence, length);
                calculateSide(sequence, width);
                calculateSide(sequence, length);
                calculateSide(sequence, width);                
            }
        }
        return lineItems;
    }

    /**
     * Fills a wall with bricks in pattern according to the sequence.
     * @param sequence Array of integers indicating sequence of brick id's.
     * @param length The length to be filled.
     * @return 
     */
    private static int fillSequence(int[] sequence, int length)
    {        
        // Loop through sequence, first index indicates offset so skip 0.
        for(int a = 1; a < sequence.length; a++)
        {
            // get brick id.
            int brickId = sequence[a];
            // get brick.
            BrickDTO brick = null;
            for(BrickDTO b:bricks)
            {
                if (b.getId() == brickId)
                {
                    brick = b;
                    break;
                }
            }
            
            // Do we have enough length to accommodate brick?
            if (length >= brick.getLength())
            {
                AddQty(brick, 1);
                length -= brick.getLength();
                
                // Repeat sequence if we had room for the brick.
                if (a == sequence.length - 1) 
                    a = 0; 
            }
            else 
                break;
        }
        return length;
    }
}
