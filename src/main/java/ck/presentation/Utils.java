/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck.presentation;

import ck.data.OrderDTO;
import ck.presentation.viewmodels.OrderUserComposite;
import java.util.ArrayList;

/**
 * Provides static helper methods e.g. to convert collections to a HTML representation.
 * @author Claus
 */
public class Utils
{
    /**
     * Converts an ArrayList of OrderUserComposites to a HTML table representation.
     * @param items
     * @return String containing HTML representation of the collection.
     */
    public static String OrderUserCompositesToHtmlTable(ArrayList<OrderUserComposite> items)
    {
        OrderDTO order;
        String result = "<table><thead><tr><th>$1</th><th>$2</th><th>$3</th><th>$4</th><th></th></tr></thead><tbody>";
        result = result.replace("$1", "Id");
        result = result.replace("$2", "Order date");
        result = result.replace("$3", "Username");
        result = result.replace("$4", "Email");
        for(OrderUserComposite item:items)
        {            
            order = item.getOrder();
            result += "<tr><td>$1</td><td>$2</td><td>$3</td><td>$4</td><td>button</td></tr>";
            result = result.replace("$1", String.valueOf(order.getId()));
            result = result.replace("$2", order.getOrderDate().toString());
            result = result.replace("$3", item.getUsername());
            result = result.replace("$4", item.getEmail());            
        }
        result += "</tbody></table>";
        return result;
    }
    
    // We only have static members, so no instantiation.
    private Utils(){}
}
