/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JComponent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author myst
 */
public class BoardGUI extends javax.swing.JFrame implements MouseListener {

    /**
     * Creates new form BoardGUI
     */
    public BoardGUI() {
        initComponents();
        setLocationRelativeTo(null);
        setContentPane(new BoardPaint());
        getContentPane().addMouseListener(this);

    }

    static class BoardPaint extends JComponent {

        @Override
        public void paint(Graphics g) {
            for (int i = 0; i < 8; i = i + 2) {
                for (int j = 0; j < 8; j = j + 2) {
                    g.setColor(Color.gray);
                    g.fillRect(j * 50, (1 + i) * 50, 50, 50);
                    g.fillRect((1 + j) * 50, i * 50, 50, 50);
                    g.setColor(Color.white);
                    g.fillRect(j * 50, (i) * 50, 50, 50);
                    g.fillRect((1 + j) * 50, (i + 1) * 50, 50, 50);
                }
            }
            for (int i = 1; i < 9; i++) {
                for (int j = 1; j < 9; j++) {
                    if (Board.myBoard[i - 1][j - 1].getChessPiece() != null) {
                        ChessPiece tempPiece = Board.myBoard[i - 1][j - 1].getChessPiece();
                        if ((tempPiece.getColorType() == Constants.BLACK)&& (tempPiece.getPieceType() == Constants.PAWN) ){
                            g.drawImage(Toolkit.getDefaultToolkit().getImage("PNGs\\bp.png"), 50 * (j - 1), 50 * (8 - i), this);
                        }
                        else if ((tempPiece.getColorType() == Constants.BLACK)&& (tempPiece.getPieceType() == Constants.ROOK) ){
                            g.drawImage(Toolkit.getDefaultToolkit().getImage("PNGs\\br.png"), 50 * (j - 1), 50 * (8 - i), this);
                        }
                        else if ((tempPiece.getColorType() == Constants.BLACK)&& (tempPiece.getPieceType() == Constants.BISHOP) ){
                            g.drawImage(Toolkit.getDefaultToolkit().getImage("PNGs\\bb.png"), 50 * (j - 1), 50 * (8 - i), this);
                        }
                        else if ((tempPiece.getColorType() == Constants.BLACK)&& (tempPiece.getPieceType() == Constants.KNIGHT) ){
                            g.drawImage(Toolkit.getDefaultToolkit().getImage("PNGs\\bh.png"), 50 * (j - 1), 50 * (8 - i), this);
                        }
                        else if ((tempPiece.getColorType() == Constants.BLACK)&& (tempPiece.getPieceType() == Constants.KING) ){
                            g.drawImage(Toolkit.getDefaultToolkit().getImage("PNGs\\bk.png"), 50 * (j - 1), 50 * (8 - i), this);
                        }
                        else if ((tempPiece.getColorType() == Constants.BLACK)&& (tempPiece.getPieceType() == Constants.QUEEN) ){
                            g.drawImage(Toolkit.getDefaultToolkit().getImage("PNGs\\bq.png"), 50 * (j - 1), 50 * (8 - i), this);
                        }
                        else if ((tempPiece.getColorType() == Constants.WHITE)&& (tempPiece.getPieceType() == Constants.PAWN) ){
                            g.drawImage(Toolkit.getDefaultToolkit().getImage("PNGs\\wp.png"), 50 * (j - 1), 50 * (8 - i), this);
                        }
                        else if ((tempPiece.getColorType() == Constants.WHITE)&& (tempPiece.getPieceType() == Constants.BISHOP) ){
                            g.drawImage(Toolkit.getDefaultToolkit().getImage("PNGs\\wb.png"), 50 * (j - 1), 50 * (8 - i), this);
                        }
                        else if ((tempPiece.getColorType() == Constants.WHITE)&& (tempPiece.getPieceType() == Constants.ROOK) ){
                            g.drawImage(Toolkit.getDefaultToolkit().getImage("PNGs\\wr.png"), 50 * (j - 1), 50 * (8 - i), this);
                        }
                        else if ((tempPiece.getColorType() == Constants.WHITE)&& (tempPiece.getPieceType() == Constants.KNIGHT) ){
                            g.drawImage(Toolkit.getDefaultToolkit().getImage("PNGs\\wh.png"), 50 * (j - 1), 50 * (8 - i), this);
                        }
                        else if ((tempPiece.getColorType() == Constants.WHITE)&& (tempPiece.getPieceType() == Constants.KING) ){
                            g.drawImage(Toolkit.getDefaultToolkit().getImage("PNGs\\wk.png"), 50 * (j - 1), 50 * (8 - i), this);
                        }
                        else if ((tempPiece.getColorType() == Constants.WHITE)&& (tempPiece.getPieceType() == Constants.QUEEN) ){
                            g.drawImage(Toolkit.getDefaultToolkit().getImage("PNGs\\wq.png"), 50 * (j - 1), 50 * (8 - i), this);
                        }
                        
                            
                            //g.drawImage(Board.myBoard[i - 1][j - 1].getChessPiece().getImage(), 50 * (j - 1), 50 * (8 - i), this);
                    }
                }
            }
            if (Board.isSpaceSelected() == true) {
                g.setColor(Color.red);
                g.drawRect(50 * (Board.getSelectedLocation().getX() - 1), 50 * (8 - Board.getSelectedLocation().getY()), 49, 49);
                g.setColor(Color.red);
                g.drawRect((50 * (Board.getSelectedLocation().getX() - 1)) + 1, (50 * (8 - Board.getSelectedLocation().getY())) + 1, 47, 47);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (Board.playerColor == Board.getWhosTurn()){
        int x = e.getX() / 50;
        int y = e.getY() / 50;
        Board.setCurrentLocation(getLocationFromScreen(x, y));

        if (Board.isSpaceSelected()) {
            if (Board.getSelectedLocation().equals(Board.getCurrentLocation())) {
                Board.removePieceSelected();
            } else if (Board.getPiecefromLocation(Board.getCurrentLocation()) != null) {
                if (Board.getPiecefromLocation(Board.getCurrentLocation()).getColorType() == Board.getWhosTurn()) {
                    Board.removePieceSelected();
                } else {
                    ChessPiece tempp = Board.getBoardSpace(Board.getSelectedLocation()).getChessPiece();
                    int tempn = Board.getArrayIndex(tempp);
                    int tempcolor = tempp.getColorType();
                    if (tempp.validateMove(Board.getCurrentLocation())) {
                        Board.getBoardSpace(Board.getSelectedLocation()).setChessPiece(null);
                        Board.getBoardSpace(Board.getCurrentLocation()).setChessPiece(tempp);
                        if (tempcolor == Constants.WHITE) {
                            Board.whitePieces.get(tempn).setChessPiece(tempp);
                        } else if (tempcolor == Constants.BLACK) {
                            Board.blackPieces.get(tempn).setChessPiece(tempp);
                        }
                        ChessPiece tobesent = new ChessPiece();
                        tobesent.setChessPiece(tempp);
                        Board.pieceMoved = tobesent;

                        if (tempp.getPieceType() == Constants.PAWN) {
                            if ((tempp.getLocation().getY() == 8) || (tempp.getLocation().getY() == 1)) {
                                tempp.kill(tempp);
                            }
                        }
                        if (Board.checkIfChecked(Board.peekNextTurn())) {
                            JOptionPane.showMessageDialog(null, "CHECK");
                        }

                        Board.changeWhosTurn();

                    }
                    Board.removePieceSelected();
                }
            } else {
                ChessPiece tempp = Board.getBoardSpace(Board.getSelectedLocation()).getChessPiece();
                int tempn = Board.getArrayIndex(tempp);
                int tempcolor = tempp.getColorType();
                if (tempp.validateMove(Board.getCurrentLocation())) {
                    Board.getBoardSpace(Board.getSelectedLocation()).setChessPiece(null);
                    Board.getBoardSpace(Board.getCurrentLocation()).setChessPiece(tempp);
                    if (tempcolor == Constants.WHITE) {
                        Board.whitePieces.get(tempn).setChessPiece(tempp);
                    } else if (tempcolor == Constants.BLACK) {
                        Board.blackPieces.get(tempn).setChessPiece(tempp);
                    }
                    ChessPiece tobesent = new ChessPiece();
                    tobesent.setChessPiece(tempp);
                    Board.pieceMoved = tobesent;
                    if (tempp.getPieceType() == Constants.PAWN) {
                        if ((tempp.getLocation().getY() == 8) || (tempp.getLocation().getY() == 1)) {
                            tempp.kill(tempp);
                        }
                    }
                    if (Board.checkIfChecked(Board.peekNextTurn())) {
                        JOptionPane.showMessageDialog(null, "CHECK");
                    }
                    Board.changeWhosTurn();
                }

                Board.removePieceSelected();
            }
        } else {
            Board.setPieceSelected(Board.getCurrentLocation());
            //ChessPiece newtemp = Board.myBoard[Board.getCurrentLocation().yPos - 1][Board.getCurrentLocation().xPos - 1].getChessPiece();
            //int xtemp = Board.getArrayIndex(newtemp);
            //System.out.println(xtemp);
        }
        repaint();
    }
    }
    

    public void makeOppositeMove(ChessPiece cp) {
        Board.setCurrentLocation(cp.getLocation());

        if (Board.isSpaceSelected()) {
            if (Board.getSelectedLocation().equals(Board.getCurrentLocation())) {
                Board.removePieceSelected();
            } else if (Board.getPiecefromLocation(Board.getCurrentLocation()) != null) {
                if (Board.getPiecefromLocation(Board.getCurrentLocation()).getColorType() == Board.getWhosTurn()) {
                    Board.removePieceSelected();
                } else {
                    ChessPiece tempp = Board.getBoardSpace(Board.getSelectedLocation()).getChessPiece();
                    int tempn = Board.getArrayIndex(tempp);
                    int tempcolor = tempp.getColorType();
                    if (tempp.validateMove(Board.getCurrentLocation())) {
                        Board.getBoardSpace(Board.getSelectedLocation()).setChessPiece(null);
                        Board.getBoardSpace(Board.getCurrentLocation()).setChessPiece(tempp);
                        if (tempcolor == Constants.WHITE) {
                            Board.whitePieces.get(tempn).setChessPiece(tempp);
                        } else if (tempcolor == Constants.BLACK) {
                            Board.blackPieces.get(tempn).setChessPiece(tempp);
                        }

                        if (tempp.getPieceType() == Constants.PAWN) {
                            if ((tempp.getLocation().getY() == 8) || (tempp.getLocation().getY() == 1)) {
                                tempp.kill(tempp);
                            }
                        }
                        if (Board.checkIfChecked(Board.peekNextTurn())) {
                            JOptionPane.showMessageDialog(null, "CHECK");
                        }

                        Board.changeWhosTurn();

                    }
                    Board.removePieceSelected();
                }
            } else {
                ChessPiece tempp = Board.getBoardSpace(Board.getSelectedLocation()).getChessPiece();
                int tempn = Board.getArrayIndex(tempp);
                int tempcolor = tempp.getColorType();
                if (tempp.validateMove(Board.getCurrentLocation())) {
                    Board.getBoardSpace(Board.getSelectedLocation()).setChessPiece(null);
                    Board.getBoardSpace(Board.getCurrentLocation()).setChessPiece(tempp);
                    if (tempcolor == Constants.WHITE) {
                        Board.whitePieces.get(tempn).setChessPiece(tempp);
                    } else if (tempcolor == Constants.BLACK) {
                        Board.blackPieces.get(tempn).setChessPiece(tempp);
                    }

                    if (tempp.getPieceType() == Constants.PAWN) {
                        if ((tempp.getLocation().getY() == 8) || (tempp.getLocation().getY() == 1)) {
                            tempp.kill(tempp);
                        }
                    }
                    if (Board.checkIfChecked(Board.peekNextTurn())) {
                        JOptionPane.showMessageDialog(null, "CHECK");
                    }
                    Board.changeWhosTurn();
                }

                Board.removePieceSelected();
            }
        } else {
            Board.setPieceSelected(Board.getCurrentLocation());
            //ChessPiece newtemp = Board.myBoard[Board.getCurrentLocation().yPos - 1][Board.getCurrentLocation().xPos - 1].getChessPiece();
            //int xtemp = Board.getArrayIndex(newtemp);
            //System.out.println(xtemp);
        }
        repaint();
    }

    public Location getLocationFromScreen(int x, int y) {
        int tempx = x + 1;
        int tempy = 8 - y;
        Location temploc = new Location(tempy, tempx);
        return temploc;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
