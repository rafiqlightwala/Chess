/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;
import java.rmi.*;
/**
 *
 * @author myst
 */
public interface RemoteChess extends Remote {
    boolean gameOn() throws RemoteException;
    ChessPiece getNextMove() throws RemoteException;
    boolean sendNextMove(ChessPiece p) throws RemoteException;
}
