/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Toolkit;
import javax.swing.JOptionPane;

/**
 *
 * @author myst
 */
public class King extends ChessPiece {

    public King(Location l, int color, int ID) {
        super.pieceID = ID;
        super.firstmove = false;
        super.pLoc = l;
        super.colorType = color;
        super.alive = true;
        super.selected = false;
        super.whoKilled = null;
        super.pieceType = Constants.KING;
    }

    @Override
    public ChessPiece kill(ChessPiece a) {
        alive = false;
        whoKilled = a;
        return null;
    }

    @Override
    public boolean validateMove(Location destinationPosition) {
        boolean canmove = false;

        if ((destinationPosition.getX() == pLoc.getX()) && (destinationPosition.getY() == pLoc.getY() + 1)) {
            canmove = true;
        } else if ((destinationPosition.getX() == pLoc.getX()) && (destinationPosition.getY() == pLoc.getY() - 1)) {
            canmove = true;
        } else if ((destinationPosition.getY() == pLoc.getY()) && (destinationPosition.getX() == pLoc.getX() + 1)) {
            canmove = true;
        } else if ((destinationPosition.getY() == pLoc.getY()) && (destinationPosition.getX() == pLoc.getX() - 1)) {
            canmove = true;
        } else if ((destinationPosition.getY() == pLoc.getY() + 1) && (destinationPosition.getX() == pLoc.getX() + 1)) {
            canmove = true;
        } else if ((destinationPosition.getY() == pLoc.getY() - 1) && (destinationPosition.getX() == pLoc.getX() - 1)) {
            canmove = true;
        } else if ((destinationPosition.getY() == pLoc.getY() + 1) && (destinationPosition.getX() == pLoc.getX() - 1)) {
            canmove = true;
        } else if ((destinationPosition.getY() == pLoc.getY() - 1) && (destinationPosition.getX() == pLoc.getX() + 1)) {
            canmove = true;
        }

        if (canmove) {
          return  makeMove(destinationPosition);
          
        } else {
            return false;
        }
    }

    @Override
    public boolean makeMove(Location newLocation) {
        //checking for check by self
        Location temploc = new Location(pLoc.getY(), pLoc.getX());
        pLoc = newLocation;

        int index = 50;
        int colort = 10;
        boolean check = Board.getBoardSpace(pLoc).isEmpty();
        if (!check) {
            index = Board.getArrayIndex(Board.getBoardSpace(pLoc).getChessPiece());
            colort = Board.getBoardSpace(pLoc).getChessPiece().getColorType();
        }
        Board.getBoardSpace(pLoc).setChessPiece(this);
        Board.getBoardSpace(Board.getSelectedLocation()).setChessPiece(null);

        if (Board.checkIfChecked(colorType)) {

            if (!check) {
                if (colort == Constants.WHITE) {
                    Board.getBoardSpace(pLoc).setChessPiece(Board.whitePieces.get(index));
                } else if (colort == Constants.BLACK) {
                    Board.getBoardSpace(pLoc).setChessPiece(Board.blackPieces.get(index));
                }
                pLoc = temploc;
                Board.getBoardSpace(Board.getSelectedLocation()).setChessPiece(this);
            } else {
                Board.getBoardSpace(pLoc).setChessPiece(null);
                pLoc = temploc;
                Board.getBoardSpace(Board.getSelectedLocation()).setChessPiece(this);
            }
            JOptionPane.showMessageDialog(null, "CHECK. Move not allowed as per game rules");
            return false;
        } else {

            if (!check){
                if (colort == Constants.WHITE) {
                    Board.getBoardSpace(pLoc).setChessPiece(Board.whitePieces.get(index));
                } else if (colort == Constants.BLACK) {
                    Board.getBoardSpace(pLoc).setChessPiece(Board.blackPieces.get(index));
                }
            }
            Board.getBoardSpace(pLoc).setChessPiece(null);
            Board.getBoardSpace(Board.getSelectedLocation()).setChessPiece(this);
        }
        // Continue
        if (!Board.getBoardSpace(pLoc).isEmpty()) {
            ChessPiece tempchess = Board.getBoardSpace(pLoc).getChessPiece();
            int tempint = Board.getArrayIndex(tempchess);
            tempchess.kill(this);
            if (tempchess.getColorType() == Constants.WHITE) {
                Board.whitePieces.get(tempint).setChessPiece(tempchess);
            } else if (tempchess.getColorType() == Constants.BLACK) {
                Board.blackPieces.get(tempint).setChessPiece(tempchess);
            }
            Board.getBoardSpace(pLoc).setChessPiece(null);
        }
        return true;
    }

    @Override
    public boolean hasCheckOnOpposingKing(Location positionOfOpposingKing) {
        boolean checked = false;

        if ((positionOfOpposingKing.getX() == pLoc.getX()) && (positionOfOpposingKing.getY() == pLoc.getY() + 1)) {
            checked = true;
        } else if ((positionOfOpposingKing.getX() == pLoc.getX()) && (positionOfOpposingKing.getY() == pLoc.getY() - 1)) {
            checked = true;
        } else if ((positionOfOpposingKing.getY() == pLoc.getY()) && (positionOfOpposingKing.getX() == pLoc.getX() + 1)) {
            checked = true;
        } else if ((positionOfOpposingKing.getY() == pLoc.getY()) && (positionOfOpposingKing.getX() == pLoc.getX() - 1)) {
            checked = true;
        } else if ((positionOfOpposingKing.getY() == pLoc.getY() + 1) && (positionOfOpposingKing.getX() == pLoc.getX() + 1)) {
            checked = true;
        } else if ((positionOfOpposingKing.getY() == pLoc.getY() - 1) && (positionOfOpposingKing.getX() == pLoc.getX() - 1)) {
            checked = true;
        } else if ((positionOfOpposingKing.getY() == pLoc.getY() + 1) && (positionOfOpposingKing.getX() == pLoc.getX() - 1)) {
            checked = true;
        } else if ((positionOfOpposingKing.getY() == pLoc.getY() - 1) && (positionOfOpposingKing.getX() == pLoc.getX() + 1)) {
            checked = true;
        }
        return checked;
    }
}
