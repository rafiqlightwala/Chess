/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.io.Serializable;

/**
 *
 * @author myst
 */
public class Location implements Serializable{
   
    int yPos;
    int xPos;
    
    public Location (int y1, int x1){
        xPos = x1;
        yPos = y1;       
        
    }
    
    public boolean equals(Location l){
        if ((l.getX() == xPos) && (l.getY() == yPos))
            return true;
        else
            return false;
    }
    
    public int getX(){
        return xPos;
    }
    public int getY(){
        return yPos;
    }
}