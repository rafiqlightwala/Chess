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
public class ChessPiece implements ChessPieceInterface, RemoteChess, Serializable {

    protected Location pLoc;
    protected boolean alive;
    protected ChessPiece whoKilled;
    protected boolean selected;
    protected int colorType;
    protected int pieceType;
    protected int pieceID;
    protected boolean firstmove;

    public ChessPiece() {
    }

    @Override
    public boolean gameOn() {
        Board.isGameOn = true;
        Board.wait.setVisible(false);
        Board.wait.dispose();
        Board.boardgui.setVisible(true);
        return true;
    }

    @Override
    public ChessPiece getNextMove() {
        while (true) {
            if (Board.getWhosTurn() == Constants.BLACK) {
                return Board.pieceMoved;
            }
        }
    }

    @Override
    public boolean sendNextMove(ChessPiece p) {
        int indexr = Board.getArrayIndex(p);
        Board.setSpaceSelected(true);
        int xr = Board.blackPieces.get(indexr).getLocation().getX();
        int yr = Board.blackPieces.get(indexr).getLocation().getY();
        Location recselected = new Location(yr, xr);
        Board.setSelectedLocation(recselected);
        Board.boardgui.makeOppositeMove(p);
        return true;
    }

    @Override
    public ChessPiece setChessPiece(ChessPiece p) {
        if (p == null) {
            return null;
        }
        this.alive = p.isActive();
        this.pLoc = p.getLocation();
        this.pieceType = p.getPieceType();
        this.colorType = p.getColorType();
        this.whoKilled = p.getWhoKilled();
        this.selected = p.isSelected();
        this.pieceID = p.getPieceID();
        this.firstmove = p.getFirstMove();
        return this;

    }

    @Override
    public boolean PieceEquals(ChessPiece p) {
        if ((this.pieceType == p.getPieceType())
                && (this.colorType == p.getColorType())
                && (this.pieceID == p.getPieceID())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean getFirstMove() {
        return firstmove;
    }

    @Override
    public ChessPiece getWhoKilled() {
        return whoKilled;
    }

    @Override
    public boolean setPieceID(int id) {
        pieceID = id;
        return true;
    }

    @Override
    public int getPieceID() {
        return pieceID;
    }

    @Override
    public Location getLocation() {
        return pLoc;
    }

    @Override
    public boolean setLocation(Location l) {
        pLoc = l;
        return true;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public boolean setSelected(boolean b) {
        selected = b;
        return true;
    }

    @Override
    public int getPieceType() {
        return pieceType;
    }

    @Override
    public boolean isActive() {
        return alive;
    }

    @Override
    public int getColorType() {
        return colorType;
    }

    @Override
    public boolean validateMove(Location destinationPosition) {
        return false;
    }

    @Override
    public boolean makeMove(Location newLocation) { //move the chess piece to the destination location, return false if an error occurs and the move is not made
        return false;
    }

    @Override
    public boolean hasCheckOnOpposingKing(Location positionOfOpposingKing) { //Before the opposing player can complete his move, we need to check for this on all active chess pieces
        return false;
    }

    @Override
    public ChessPiece kill(ChessPiece a) {
        return null;
    }
}
