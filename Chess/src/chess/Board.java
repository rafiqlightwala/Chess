/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.util.ArrayList;
import java.util.*;
import java.net.*;
import java.rmi.registry.*;
import java.rmi.*;
import java.io.*;
import java.rmi.server.*;
import javax.swing.*;
import javax.swing.JOptionPane;

/**
 *
 * @author myst
 */
public class Board {

    public static ArrayList<ChessPiece> whitePieces = new ArrayList<ChessPiece>();
    public static ArrayList<ChessPiece> blackPieces = new ArrayList<ChessPiece>();
    public static BoardSpace[][] myBoard = new BoardSpace[8][8];
    private static int whosTurn = Constants.WHITE;
    private static boolean spaceselected;
    private static Location currentlocation;
    private static Location locationselected;
    private static int blackQueenNumber = 1;
    private static int whiteQueenNumber = 1;
    public static int playerColor;
    public static final int PORT = 19900;
    public static boolean isGameOn = false;
    public static BoardGUI boardgui;
    public static Waiting wait;
    public static ChessPiece pieceMoved;

    public Board(int playerCol) {
        playerColor = playerCol;
        beginGame();
        boardgui = new BoardGUI();
        if (playerCol == Constants.WHITE) {
            boardgui.setTitle("*****WHITE***** Player - (Server)");
        }
        else if (playerCol == Constants.BLACK) {
            boardgui.setTitle("*****BLACK***** Player - (Client)");
        }

        //boardgui.setVisible(true);



    }

    public static int getWhiteQueenNumber() {
        return whiteQueenNumber;
    }

    public static void incrementWhiteQueenNumber() {
        whiteQueenNumber = whiteQueenNumber + 1;
    }

    public static int getBlackQueenNumber() {
        return blackQueenNumber;
    }

    public static void incrementBlackQueenNumber() {
        blackQueenNumber = blackQueenNumber + 1;
    }

    public static void setSpaceSelected(boolean b) {
        spaceselected = b;
    }

    public static boolean checkIfChecked(int whatcolor) {
        boolean isChecked = false;
        if (whatcolor == Constants.WHITE) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (myBoard[i][j].getChessPiece() != null) {
                        if (myBoard[i][j].getChessPiece().getColorType() == Constants.BLACK) {
                            if (myBoard[i][j].getChessPiece().hasCheckOnOpposingKing(whitePieces.get(12).getLocation())) {
                                isChecked = true;
                                break;
                            }
                        }
                    }
                }
                if (isChecked) {
                    break;
                }
            }
        } else if (whatcolor == Constants.BLACK) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (myBoard[i][j].getChessPiece() != null) {
                        if (myBoard[i][j].getChessPiece().getColorType() == Constants.WHITE) {
                            if (myBoard[i][j].getChessPiece().hasCheckOnOpposingKing(blackPieces.get(12).getLocation())) {
                                isChecked = true;
                                break;
                            }
                        }
                    }
                }
                if (isChecked) {
                    break;
                }
            }
        }
        return isChecked;
    }

    public static int getWhosTurn() {
        return whosTurn;
    }

    public static int peekNextTurn() {
        if (whosTurn == Constants.BLACK) {
            return Constants.WHITE;
        } else if (whosTurn == Constants.WHITE) {
            return Constants.BLACK;
        }
        return 0;
    }

    public static int changeWhosTurn() {
        if (whosTurn == Constants.BLACK) {
            whosTurn = Constants.WHITE;
        } else if (whosTurn == Constants.WHITE) {
            whosTurn = Constants.BLACK;
        }
        return whosTurn;
    }

    public static Location getSelectedLocation() {
        return locationselected;
    }

    public static Location getCurrentLocation() {
        return currentlocation;
    }

    public static boolean setCurrentLocation(Location l) {
        currentlocation = l;
        return true;
    }

    public static boolean setSelectedLocation(Location l) {
        locationselected = l;
        return true;
    }

    public static BoardSpace getBoardSpace(int y, int x) {
        return myBoard[y - 1][x - 1];
    }

    public static BoardSpace getBoardSpace(Location l) {
        return myBoard[l.getY() - 1][l.getX() - 1];
    }

    public static boolean setPieceSelected(Location l) {
        if (myBoard[l.yPos - 1][l.xPos - 1].getChessPiece() != null) {
            if (myBoard[l.yPos - 1][l.xPos - 1].getChessPiece().getColorType() == Board.getWhosTurn()) {
                locationselected = l;
                spaceselected = true;
                myBoard[locationselected.yPos - 1][locationselected.xPos - 1].getChessPiece().setSelected(true);

                return true;
            }
        }
        return false;
    }

    public static boolean removePieceSelected() {
        if (spaceselected == true) {
            if (myBoard[locationselected.yPos - 1][locationselected.xPos - 1].getChessPiece() != null) {
                myBoard[locationselected.yPos - 1][locationselected.xPos - 1].getChessPiece().setSelected(false);
            }
            locationselected = null;
            spaceselected = false;
            return true;
        }
        return false;
    }

    public static ChessPiece getPiecefromLocation(Location l) {
        if (myBoard[l.getY() - 1][l.getX() - 1].getChessPiece() != null) {
            return myBoard[l.getY() - 1][l.getX() - 1].getChessPiece();
        } else {
            return null;
        }
    }

    public static int getArrayIndex(ChessPiece p) {
        if (p != null) {
            if (p.getColorType() == Constants.WHITE) {
                for (int i = 0; i < whitePieces.size(); i++) {
                    if (p.PieceEquals(whitePieces.get(i))) {
                        return i;
                    }
                }
            } else if (p.getColorType() == Constants.BLACK) {
                for (int i = 0; i < blackPieces.size(); i++) {
                    if (blackPieces.get(i).PieceEquals(p)) {
                        return i;
                    }
                }
            }
        }
        return 50;
    }

    public static boolean isSpaceSelected() {
        return spaceselected;
    }

    public final void beginGame() {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                Location templocation = new Location(i, j);
                BoardSpace tempboardspace = new BoardSpace(templocation);
                myBoard[i - 1][j - 1] = tempboardspace;
            }
        }

        for (int j = 1; j < 9; j++) {
            Location templocationwhite = new Location(2, j);
            ChessPiece tempchesswhite = new Pawn(templocationwhite, Constants.WHITE, j);
            myBoard[2 - 1][j - 1].setChessPiece(tempchesswhite);
            whitePieces.add(tempchesswhite);

            Location templocationblack = new Location(7, j);
            ChessPiece tempchessblack = new Pawn(templocationblack, Constants.BLACK, j);
            myBoard[7 - 1][j - 1].setChessPiece(tempchessblack);
            blackPieces.add(tempchessblack);
        }
        for (int j = 1; j < 9; j++) {
            Location templocationwhite = new Location(1, j);
            ChessPiece tempchesswhite;
            if (j == 1) {
                tempchesswhite = new Rook(templocationwhite, Constants.WHITE, 1);
            } else if (j == 2) {
                tempchesswhite = new Knight(templocationwhite, Constants.WHITE, 1);
            } else if (j == 3) {
                tempchesswhite = new Bishop(templocationwhite, Constants.WHITE, 1);
            } else if (j == 4) {
                tempchesswhite = new Queen(templocationwhite, Constants.WHITE, 1);
            } else if (j == 5) {
                tempchesswhite = new King(templocationwhite, Constants.WHITE, 1);
            } else if (j == 6) {
                tempchesswhite = new Bishop(templocationwhite, Constants.WHITE, 2);
            } else if (j == 7) {
                tempchesswhite = new Knight(templocationwhite, Constants.WHITE, 2);
            } else if (j == 8) {
                tempchesswhite = new Rook(templocationwhite, Constants.WHITE, 2);
            } else {
                tempchesswhite = null;
            }

            myBoard[1 - 1][j - 1].setChessPiece(tempchesswhite);
            whitePieces.add(tempchesswhite);



            Location templocationblack = new Location(8, j);

            ChessPiece tempchessblack;
            if (j == 1) {
                tempchessblack = new Rook(templocationblack, Constants.BLACK, 1);
            } else if (j == 2) {
                tempchessblack = new Knight(templocationblack, Constants.BLACK, 1);
            } else if (j == 3) {
                tempchessblack = new Bishop(templocationblack, Constants.BLACK, 1);
            } else if (j == 4) {
                tempchessblack = new Queen(templocationblack, Constants.BLACK, 1);
            } else if (j == 5) {
                tempchessblack = new King(templocationblack, Constants.BLACK, 1);
            } else if (j == 6) {
                tempchessblack = new Bishop(templocationblack, Constants.BLACK, 2);
            } else if (j == 7) {
                tempchessblack = new Knight(templocationblack, Constants.BLACK, 2);
            } else if (j == 8) {
                tempchessblack = new Rook(templocationblack, Constants.BLACK, 2);
            } else {
                tempchessblack = null;
            }
            myBoard[8 - 1][j - 1].setChessPiece(tempchessblack);
            blackPieces.add(tempchessblack);
        }
    }

    public static void main(String args[]) throws Exception {
        //Board chessBoard = new Board(Constants.WHITE);

        String sres = null;
        sres = JOptionPane.showInputDialog("Welcome to Chess. Please select any of the options below. \n \n1- Make a new game. \n2- Join an already running game.");

        int i = Integer.parseInt(sres);
        switch (i) {
            case 1:
                RemoteChess remotechess = new ChessPiece();
                RemoteChess remotechessexport = (RemoteChess) UnicastRemoteObject.exportObject(remotechess, 0);
                Registry registry = LocateRegistry.getRegistry();
                registry.rebind("chesspiece", remotechessexport);
                Board chessBoard = new Board(Constants.WHITE);
                wait = new Waiting();
                wait.setVisible(true);

                break;
            case 2:
                String ip = JOptionPane.showInputDialog("Enter IP Address to Connect");
                Registry registry1 = LocateRegistry.getRegistry(ip);
                RemoteChess clientchess = (RemoteChess) registry1.lookup("chesspiece");
                boolean startgame = false;
                startgame = clientchess.gameOn();
                if (startgame) {
                    Board chessBoardClient = new Board(Constants.BLACK);
                    chessBoardClient.boardgui.setVisible(true);
                }

                while (true) {
                    ChessPiece receivedPiece = clientchess.getNextMove();
                    int indexr = getArrayIndex(receivedPiece);
                    spaceselected = true;
                    int xr = whitePieces.get(indexr).getLocation().getX();
                    int yr = whitePieces.get(indexr).getLocation().getY();
                    Location recselected = new Location(yr, xr);
                    locationselected = recselected;
                    boardgui.makeOppositeMove(receivedPiece);

                    while (true) {
                        if (Board.getWhosTurn() == Constants.WHITE) {
                            boolean hold = clientchess.sendNextMove(Board.pieceMoved);
                            break;
                        }
                    }
                }
        }

    }
}
