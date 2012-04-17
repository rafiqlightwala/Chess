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
public class Queen extends ChessPiece {

    public Queen(Location l, int color, int ID) {
        super.pieceID = ID;
        super.pLoc = l;
        super.firstmove = false;
        super.colorType = color;
        super.alive = true;
        super.selected = false;
        super.whoKilled = null;
        super.pieceType = Constants.QUEEN;

    }

    @Override
    public ChessPiece kill(ChessPiece a) {
        alive = false;
        whoKilled = a;
        return null;
    }

    @Override
    public boolean validateMove(Location destinationPosition) {
        boolean canmovestraight = false;
        boolean canmovediagnol = false;
        int tempx, tempy;
        int dist;
        
        if ((pLoc.getX() - destinationPosition.getX()) == (pLoc.getY() - destinationPosition.getY())) {
            //up right
            if ((destinationPosition.getX() - pLoc.getX() > 0) && (destinationPosition.getY() - pLoc.getY() > 0)) {
                canmovediagnol = true;
                dist = destinationPosition.getX() - pLoc.getX();
                tempy = pLoc.getY();
                tempx = pLoc.getX();
                for (int i = 0; i < dist - 1; i++) {
                    tempx++;
                    tempy++;
                    if (!Board.getBoardSpace(tempy, tempx).isEmpty()) {
                        canmovediagnol = false;
                    }
                }
            } 
            //down left
            else if ((destinationPosition.getX() - pLoc.getX() < 0) && (destinationPosition.getY() - pLoc.getY() < 0)) {
                canmovediagnol = true;
                dist = pLoc.getX() - destinationPosition.getX();
                tempy = pLoc.getY();
                tempx = pLoc.getX();
                for (int i = 0; i < dist - 1; i++) {
                    tempx--;
                    tempy--;
                    if (!Board.getBoardSpace(tempy, tempx).isEmpty()) {
                        canmovediagnol = false;
                    }
                }
            }
        } else if ((destinationPosition.getX() - pLoc.getX()) == (pLoc.getY() - destinationPosition.getY())) {
            //up left
            if ((destinationPosition.getX() - pLoc.getX() < 0) && (pLoc.getY() - destinationPosition.getY() < 0)) {
                canmovediagnol = true;
                dist = pLoc.getX() - destinationPosition.getX();
                tempy = pLoc.getY();
                tempx = pLoc.getX();
                for (int i = 0; i < dist - 1; i++) {
                    tempx--;
                    tempy++;
                    if (!Board.getBoardSpace(tempy, tempx).isEmpty()) {
                        canmovediagnol = false;
                    }
                }
            } 
            //down right
            else if ((destinationPosition.getX() - pLoc.getX() > 0) && (pLoc.getY() - destinationPosition.getY() > 0)) {
                canmovediagnol = true;
                dist = destinationPosition.getX()-pLoc.getX();
                tempy = pLoc.getY();
                tempx = pLoc.getX();
                for (int i = 0; i < dist - 1; i++) {
                    tempx++;
                    tempy--;
                    if (!Board.getBoardSpace(tempy, tempx).isEmpty()) {
                        canmovediagnol = false;
                    }
                }
            }
        }

        
        if (pLoc.getX() == destinationPosition.getX()) {
            //up
            if (pLoc.getY() - destinationPosition.getY() > 0) {
                canmovestraight = true;
                dist = pLoc.getY() - destinationPosition.getY();
                tempy = pLoc.getY();
                tempx = pLoc.getX();
                for (int i = 0; i < dist - 1; i++) {
                    tempy--;
                    if (!Board.getBoardSpace(tempy, tempx).isEmpty()) {
                        canmovestraight = false;
                    }
                }
            }
            //down
            else if (pLoc.getY() - destinationPosition.getY() < 0) {
                canmovestraight = true;
                dist = destinationPosition.getY() - pLoc.getY();
                tempy = pLoc.getY();
                tempx = pLoc.getX();
                for (int i = 0; i < dist - 1; i++) {
                    tempy++;
                    if (!Board.getBoardSpace(tempy, tempx).isEmpty()) {
                        canmovestraight = false;
                    }
                }
            }
        }
        else if (pLoc.getY() == destinationPosition.getY()) {
            //left
            if (pLoc.getX() - destinationPosition.getX() > 0) {
                canmovestraight = true;
                dist = pLoc.getX() - destinationPosition.getX();
                tempy = pLoc.getY();
                tempx = pLoc.getX();
                for (int i = 0; i < dist - 1; i++) {
                    tempx--;
                    if (!Board.getBoardSpace(tempy, tempx).isEmpty()) {
                        canmovestraight = false;
                    }
                }
            }
            //right
            else if (pLoc.getX() - destinationPosition.getX() < 0) {
                canmovestraight = true;
                dist = destinationPosition.getX() - pLoc.getX();
                tempy = pLoc.getY();
                tempx = pLoc.getX();
                for (int i = 0; i < dist - 1; i++) {
                    tempx++;
                    if (!Board.getBoardSpace(tempy, tempx).isEmpty()) {
                        canmovestraight = false;
                    }
                }
            }
        }
        
        boolean canmove = canmovediagnol ^ canmovestraight;
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
        boolean canmovestraight = false;
        boolean canmovediagnol = false;
        int tempx, tempy;
        int dist;
        
        if ((pLoc.getX() - positionOfOpposingKing.getX()) == (pLoc.getY() - positionOfOpposingKing.getY())) {
            //up right
            if ((positionOfOpposingKing.getX() - pLoc.getX() > 0) && (positionOfOpposingKing.getY() - pLoc.getY() > 0)) {
                canmovediagnol = true;
                dist = positionOfOpposingKing.getX() - pLoc.getX();
                tempy = pLoc.getY();
                tempx = pLoc.getX();
                for (int i = 0; i < dist - 1; i++) {
                    tempx++;
                    tempy++;
                    if (!Board.getBoardSpace(tempy, tempx).isEmpty()) {
                        canmovediagnol = false;
                    }
                }
            } 
            //down left
            else if ((positionOfOpposingKing.getX() - pLoc.getX() < 0) && (positionOfOpposingKing.getY() - pLoc.getY() < 0)) {
                canmovediagnol = true;
                dist = pLoc.getX() - positionOfOpposingKing.getX();
                tempy = pLoc.getY();
                tempx = pLoc.getX();
                for (int i = 0; i < dist - 1; i++) {
                    tempx--;
                    tempy--;
                    if (!Board.getBoardSpace(tempy, tempx).isEmpty()) {
                        canmovediagnol = false;
                    }
                }
            }
        } else if ((positionOfOpposingKing.getX() - pLoc.getX()) == (pLoc.getY() - positionOfOpposingKing.getY())) {
            //up left
            if ((positionOfOpposingKing.getX() - pLoc.getX() < 0) && (pLoc.getY() - positionOfOpposingKing.getY() < 0)) {
                canmovediagnol = true;
                dist = pLoc.getX() - positionOfOpposingKing.getX();
                tempy = pLoc.getY();
                tempx = pLoc.getX();
                for (int i = 0; i < dist - 1; i++) {
                    tempx--;
                    tempy++;
                    if (!Board.getBoardSpace(tempy, tempx).isEmpty()) {
                        canmovediagnol = false;
                    }
                }
            } 
            //down right
            else if ((positionOfOpposingKing.getX() - pLoc.getX() > 0) && (pLoc.getY() - positionOfOpposingKing.getY() > 0)) {
                canmovediagnol = true;
                dist = positionOfOpposingKing.getX()-pLoc.getX();
                tempy = pLoc.getY();
                tempx = pLoc.getX();
                for (int i = 0; i < dist - 1; i++) {
                    tempx++;
                    tempy--;
                    if (!Board.getBoardSpace(tempy, tempx).isEmpty()) {
                        canmovediagnol = false;
                    }
                }
            }
        }

        
        if (pLoc.getX() == positionOfOpposingKing.getX()) {
            //up
            if (pLoc.getY() - positionOfOpposingKing.getY() > 0) {
                canmovestraight = true;
                dist = pLoc.getY() - positionOfOpposingKing.getY();
                tempy = pLoc.getY();
                tempx = pLoc.getX();
                for (int i = 0; i < dist - 1; i++) {
                    tempy--;
                    if (!Board.getBoardSpace(tempy, tempx).isEmpty()) {
                        canmovestraight = false;
                    }
                }
            }
            //down
            else if (pLoc.getY() - positionOfOpposingKing.getY() < 0) {
                canmovestraight = true;
                dist = positionOfOpposingKing.getY() - pLoc.getY();
                tempy = pLoc.getY();
                tempx = pLoc.getX();
                for (int i = 0; i < dist - 1; i++) {
                    tempy++;
                    if (!Board.getBoardSpace(tempy, tempx).isEmpty()) {
                        canmovestraight = false;
                    }
                }
            }
        }
        else if (pLoc.getY() == positionOfOpposingKing.getY()) {
            //left
            if (pLoc.getX() - positionOfOpposingKing.getX() > 0) {
                canmovestraight = true;
                dist = pLoc.getX() - positionOfOpposingKing.getX();
                tempy = pLoc.getY();
                tempx = pLoc.getX();
                for (int i = 0; i < dist - 1; i++) {
                    tempx--;
                    if (!Board.getBoardSpace(tempy, tempx).isEmpty()) {
                        canmovestraight = false;
                    }
                }
            }
            //right
            else if (pLoc.getX() - positionOfOpposingKing.getX() < 0) {
                canmovestraight = true;
                dist = positionOfOpposingKing.getX() - pLoc.getX();
                tempy = pLoc.getY();
                tempx = pLoc.getX();
                for (int i = 0; i < dist - 1; i++) {
                    tempx++;
                    if (!Board.getBoardSpace(tempy, tempx).isEmpty()) {
                        canmovestraight = false;
                    }
                }
            }
        }
        
        boolean checked = canmovediagnol ^ canmovestraight;
        return checked;
        
    }
}
