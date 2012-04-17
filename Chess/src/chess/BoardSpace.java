/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 *
 * @author myst
 */
public class BoardSpace {
    private Location loc;
    private ChessPiece piece;

    public BoardSpace(Location l){
        loc = l;
        piece = null;
    }
    
    public boolean isEmpty(){
        if(piece == null)
            return true;
        else
            return false;
    }
    
    public Location getLocation(){
        return loc;
    }
    
    public ChessPiece getChessPiece() {
        return piece;
    }
    
    public void setChessPiece(ChessPiece p){
        piece = p;
    }
}
