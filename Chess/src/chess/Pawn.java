/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author myst
 */
public class Pawn extends ChessPiece {

    public Pawn(Location l, int color, int ID) {
        super.pieceID = ID;
        super.firstmove = true;
        super.pLoc = l;
        super.colorType = color;
        super.alive = true;
        super.selected = false;
        super.whoKilled = null;
        super.pieceType = Constants.PAWN;
    }

    @Override
    public ChessPiece kill(ChessPiece a) {

        if ((a.getPieceType() == Constants.PAWN) && (a.getColorType() == colorType)) {
            ChessPiece nawiqueen = new Queen(a.getLocation(), a.getColorType(), Board.getWhiteQueenNumber() + 1);
            
            if (a.getColorType() == Constants.WHITE) {
                Board.getBoardSpace(a.getLocation()).setChessPiece(nawiqueen);
                Board.whitePieces.add(nawiqueen);
                Board.incrementWhiteQueenNumber();                
            } else if (a.getColorType() == Constants.BLACK) {
                nawiqueen = new Queen(a.getLocation(), a.getColorType(), Board.getBlackQueenNumber() + 1);
                Board.getBoardSpace(a.getLocation()).setChessPiece(nawiqueen);
                Board.blackPieces.add(nawiqueen);
                Board.incrementBlackQueenNumber();
            }
            alive = false;
            whoKilled = a;
            return nawiqueen;
        }

        alive = false;
        whoKilled = a;
        return null;
    }

    @Override
    public boolean validateMove(Location destinationPosition) {
        boolean canmove = false;
        if (colorType == Constants.WHITE) {
            if (firstmove) {
                if ((destinationPosition.xPos == pLoc.xPos) && (destinationPosition.yPos == pLoc.yPos + 1)) {
                    if (Board.getBoardSpace(destinationPosition.yPos, destinationPosition.xPos).isEmpty()) {
                        canmove = true;
                    }
                } else if ((destinationPosition.xPos == pLoc.xPos) && (destinationPosition.yPos == pLoc.yPos + 2)) {
                    if (Board.getBoardSpace(pLoc.yPos + 1, pLoc.xPos).isEmpty() && Board.getBoardSpace(pLoc.yPos + 2, pLoc.xPos).isEmpty()) {
                        canmove = true;
                    }
                }
            } else if (!firstmove) {
                if ((destinationPosition.xPos == pLoc.xPos) && (destinationPosition.yPos == pLoc.yPos + 1)) {
                    if (Board.getBoardSpace(destinationPosition.yPos, destinationPosition.xPos).isEmpty()) {
                        canmove = true;
                    }
                }
            }
        } else if (colorType == Constants.BLACK) {
            if (firstmove) {
                if ((destinationPosition.xPos == pLoc.xPos) && (destinationPosition.yPos == pLoc.yPos - 1)) {
                    if (Board.getBoardSpace(destinationPosition.yPos, destinationPosition.xPos).isEmpty()) {
                        canmove = true;
                    }
                } else if ((destinationPosition.xPos == pLoc.xPos) && (destinationPosition.yPos == pLoc.yPos - 2)) {
                    if (Board.getBoardSpace(pLoc.yPos - 1, pLoc.xPos).isEmpty() && Board.getBoardSpace(pLoc.yPos - 2, pLoc.xPos).isEmpty()) {
                        canmove = true;
                    }
                }
            } else if (!firstmove) {
                if ((destinationPosition.xPos == pLoc.xPos) && (destinationPosition.yPos == pLoc.yPos - 1)) {
                    if (Board.getBoardSpace(destinationPosition.yPos, destinationPosition.xPos).isEmpty()) {
                        canmove = true;
                    }
                }
            }
        }
        //Checking Kill
        if (canmove == false) {
            if (colorType == Constants.WHITE) {
                if ((destinationPosition.xPos == pLoc.xPos - 1) && (destinationPosition.yPos == pLoc.yPos + 1)) {
                    if (!Board.getBoardSpace(destinationPosition.yPos, destinationPosition.xPos).isEmpty()) {
                        canmove = true;
                    }
                } else if ((destinationPosition.xPos == pLoc.xPos + 1) && (destinationPosition.yPos == pLoc.yPos + 1)) {

                    if (!Board.getBoardSpace(destinationPosition.yPos, destinationPosition.xPos).isEmpty()) {
                        canmove = true;
                    }
                }
            } else if (colorType == Constants.BLACK) {
                if ((destinationPosition.xPos == pLoc.xPos + 1) && (destinationPosition.yPos == pLoc.yPos - 1)) {
                    if (!Board.getBoardSpace(destinationPosition.yPos, destinationPosition.xPos).isEmpty()) {
                        canmove = true;
                    }
                } else if ((destinationPosition.xPos == pLoc.xPos - 1) && (destinationPosition.yPos == pLoc.yPos - 1)) {
                    if (!Board.getBoardSpace(destinationPosition.yPos, destinationPosition.xPos).isEmpty()) {
                        canmove = true;
                    }
                }
            }

        }
        if (canmove) {

            return makeMove(destinationPosition);
        } else {
            return false;
        }
    }

    @Override
    public boolean makeMove(Location newLocation) {

         //checking for check by self
        Location temploc = new Location (pLoc.getY(), pLoc.getX());
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
            }
            else {
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
        if (firstmove) {
            firstmove = false;
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
        if (colorType == Constants.WHITE) {
            if (firstmove) {
                if ((positionOfOpposingKing.xPos == pLoc.xPos) && (positionOfOpposingKing.yPos == pLoc.yPos + 1)) {
                    if (Board.getBoardSpace(positionOfOpposingKing.yPos, positionOfOpposingKing.xPos).isEmpty()) {
                        checked = true;
                    }
                } else if ((positionOfOpposingKing.xPos == pLoc.xPos) && (positionOfOpposingKing.yPos == pLoc.yPos + 2)) {
                    if (Board.getBoardSpace(pLoc.yPos + 1, pLoc.xPos).isEmpty() && Board.getBoardSpace(pLoc.yPos + 2, pLoc.xPos).isEmpty()) {
                        checked = true;
                    }
                }
            } else if (!firstmove) {
                if ((positionOfOpposingKing.xPos == pLoc.xPos) && (positionOfOpposingKing.yPos == pLoc.yPos + 1)) {
                    if (Board.getBoardSpace(positionOfOpposingKing.yPos, positionOfOpposingKing.xPos).isEmpty()) {
                        checked = true;
                    }
                }
            }
        } else if (colorType == Constants.BLACK) {
            if (firstmove) {
                if ((positionOfOpposingKing.xPos == pLoc.xPos) && (positionOfOpposingKing.yPos == pLoc.yPos - 1)) {
                    if (Board.getBoardSpace(positionOfOpposingKing.yPos, positionOfOpposingKing.xPos).isEmpty()) {
                        checked = true;
                    }
                } else if ((positionOfOpposingKing.xPos == pLoc.xPos) && (positionOfOpposingKing.yPos == pLoc.yPos - 2)) {
                    if (Board.getBoardSpace(pLoc.yPos - 1, pLoc.xPos).isEmpty() && Board.getBoardSpace(pLoc.yPos - 2, pLoc.xPos).isEmpty()) {
                        checked = true;
                    }
                }
            } else if (!firstmove) {
                if ((positionOfOpposingKing.xPos == pLoc.xPos) && (positionOfOpposingKing.yPos == pLoc.yPos - 1)) {
                    if (Board.getBoardSpace(positionOfOpposingKing.yPos, positionOfOpposingKing.xPos).isEmpty()) {
                        checked = true;
                    }
                }
            }
        }
        //Checking Kill
        if (checked == false) {
            if (colorType == Constants.WHITE) {
                if ((positionOfOpposingKing.xPos == pLoc.xPos - 1) && (positionOfOpposingKing.yPos == pLoc.yPos + 1)) {
                    if (!Board.getBoardSpace(positionOfOpposingKing.yPos, positionOfOpposingKing.xPos).isEmpty()) {
                        checked = true;
                    }
                } else if ((positionOfOpposingKing.xPos == pLoc.xPos + 1) && (positionOfOpposingKing.yPos == pLoc.yPos + 1)) {
                    if (!Board.getBoardSpace(positionOfOpposingKing.yPos, positionOfOpposingKing.xPos).isEmpty()) {
                        checked = true;
                    }
                }
            } else if (colorType == Constants.BLACK) {
                if ((positionOfOpposingKing.xPos == pLoc.xPos + 1) && (positionOfOpposingKing.yPos == pLoc.yPos - 1)) {
                    if (!Board.getBoardSpace(positionOfOpposingKing.yPos, positionOfOpposingKing.xPos).isEmpty()) {
                        checked = true;
                    }
                } else if ((positionOfOpposingKing.xPos == pLoc.xPos - 1) && (positionOfOpposingKing.yPos == pLoc.yPos - 1)) {
                    if (!Board.getBoardSpace(positionOfOpposingKing.yPos, positionOfOpposingKing.xPos).isEmpty()) {
                        checked = true;
                    }
                }
            }

        }
        return checked;
    }
}
