/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;
import java.awt.Image;
/**
 *
 * @author myst
 */
public interface ChessPieceInterface {
    
    public ChessPiece setChessPiece(ChessPiece p);
    public boolean validateMove(Location destinationPosition); //Before the player’s move is committed, it needs to validate if its correct per the rule of the game
    public boolean makeMove(Location newLocation); //move the chess piece to the destination location, return false if an error occurs and the move is not made
    public boolean hasCheckOnOpposingKing(Location positionOfOpposingKing); //Before the opposing player can complete his move, we need to check for this on all active chess pieces
    public boolean isActive(); //to see if the piece has been killed
    public int getColorType(); // get Color
    public ChessPiece kill(ChessPiece a); //The current object is being killed by ChessPiece a. Each piece should maintain who killed it and when it was killed. Use this function when converting a pawn to a queen when it reaches the other side by passing it itself. Only in this latter case should a non-null value be returned, the reference to the Queen chesspiece.
    public Location getLocation();
    public boolean setLocation(Location l);
    public boolean isSelected();
    public boolean setSelected(boolean b);
    public int getPieceType();
    public int getPieceID();
    public boolean setPieceID(int id);
    public ChessPiece getWhoKilled();
    public boolean PieceEquals(ChessPiece p);
    public boolean getFirstMove();
}
