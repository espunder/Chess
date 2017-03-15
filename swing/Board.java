package swing;

import chess.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board extends Component {

        public JPanel board1;
        private JLabel imageLabelB8;
        private JLabel imageLabelA8;
        private JLabel imageLabelA7;
        private JLabel imageLabelG8;
        private JLabel imageLabelH8;
        private JLabel imageLabelE8;
        private JLabel imageLabelC8;
        private JLabel imageLabelD8;
        private JLabel imageLabelF8;
        private JLabel imageLabelA1;
        private JLabel imageLabelA2;
        private JLabel imageLabelB1;
        private JLabel imageLabelC1;
        private JLabel imageLabelD1;
        private JLabel imageLabelE1;
        private JLabel imageLabelB7;
        private JLabel imageLabelC6;
        private JLabel imageLabelC7;
        private JLabel imageLabelD7;
        private JLabel imageLabelE7;
        private JLabel imageLabelF7;
        private JLabel imageLabelG7;
        private JLabel imageLabelH7;
        private JLabel imageLabelA6;
        private JLabel imageLabelB5;
        private JLabel imageLabelB6;
        private JLabel imageLabelD6;
        private JLabel imageLabelE6;
        private JLabel imageLabelF6;
        private JLabel imageLabelG6;
        private JLabel imageLabelH6;
        private JLabel imageLabelA5;
        private JLabel imageLabelC5;
        private JLabel imageLabelD5;
        private JLabel imageLabelE5;
        private JLabel imageLabelF5;
        private JLabel imageLabelG5;
        private JLabel imageLabelH5;
        private JLabel imageLabelA4;
        private JLabel imageLabelB4;
        private JLabel imageLabelC4;
        private JLabel imageLabelD4;
        private JLabel imageLabelE4;
        private JLabel imageLabelF4;
        private JLabel imageLabelG4;
        private JLabel imageLabelH4;
        private JLabel imageLabelA3;
        private JLabel imageLabelB3;
        private JLabel imageLabelC3;
        private JLabel imageLabelD3;
        private JLabel imageLabelE3;
        private JLabel imageLabelF3;
        private JLabel imageLabelG3;
        private JLabel imageLabelH3;
        private JLabel imageLabelB2;
        private JLabel imageLabelC2;
        private JLabel imageLabelD2;
        private JLabel imageLabelE2;
        private JLabel imageLabelF2;
        private JLabel imageLabelG2;
        private JLabel imageLabelH2;
        private JLabel imageLabelF1;
        private JLabel imageLabelG1;
        private JLabel imageLabelH1;
        private JPanel cellA8;
        private JPanel cellA7;
        private JPanel cellB7;
        private JPanel cellC6;
        private JPanel cellD5;
        private JPanel cellD7;
        private JPanel cellC8;
        private JPanel cellA6;
        private JPanel cellB5;
        private JPanel cellB8;
        private JPanel cellD8;
        private JPanel cellC7;
        private JPanel cellB6;
        private JPanel cellA5;
        private JPanel cellC5;
        private JPanel cellD6;
        private JPanel cellE8;
        private JPanel cellF8;
        private JPanel cellG8;
        private JPanel cellH8;
        private JPanel cellF7;
        private JPanel cellG6;
        private JPanel cellH5;
        private JPanel cellG4;
        private JPanel cellF3;
        private JPanel cellE2;
        private JPanel cellD1;
        private JPanel cellC2;
        private JPanel cellB3;
        private JPanel cellA4;
        private JPanel cellB4;
        private JPanel cellA3;
        private JPanel cellB2;
        private JPanel cellC3;
        private JPanel cellD4;
        private JPanel cellE3;
        private JPanel cellE5;
        private JPanel cellA1;
        private JPanel cellC1;
        private JPanel cellE1;
        private JPanel cellD2;
        private JPanel cellF2;
        private JPanel cellF4;
        private JPanel cellG3;
        private JPanel cellH2;
        private JPanel cellG1;
        private JPanel cellH4;
        private JPanel cellG5;
        private JPanel cellF6;
        private JPanel cellE7;
        private JPanel cellG7;
        private JPanel cellH6;
        private JPanel cellE6;
        private JPanel cellC4;
        private JPanel cellE4;
        private JPanel cellF5;
        private JPanel cellH7;
        private JPanel cellH3;
        private JPanel cellG2;
        private JPanel cellD3;
        private JPanel cellF1;
        private JPanel cellH1;
        private JPanel cellA2;
        private JPanel cellB1;


        private Map<String, JLabel> imageLabels;
        private Map<String, ImageIcon> whiteItems;
        private Map<String, ImageIcon> blackItems;
        private Map<String, JPanel> jPanels;

        Color opaqueGreen = new Color(58, 249, 62, 121);

        private String st = null;
        private String ep = null;
        private MouseEvent eLast;


    public Board() { // конструктор
        imageLabels = initImageLabels();
        whiteItems = initWhiteItems();
        blackItems = initBlackItems();
        jPanels = initJPanels();
        for (Map.Entry<String, JPanel> jp : jPanels.entrySet()) {
            jp.getValue().setName(jp.getKey());
            jp.getValue().setBackground(opaqueGreen);
            jp.getValue().setOpaque(false);
        }
        initMouseClickListeners();
    }


    public void endGame(){
        for (Map.Entry<String, JPanel> jp : jPanels.entrySet()) {  // удаляем листенеры по завершении партии
            for (MouseListener ms : jp.getValue().getMouseListeners()) {
                jp.getValue().removeMouseListener(ms);
            }
        }

    }

    private void initMouseClickListeners(){
        for (Map.Entry<String, JPanel> jp : jPanels.entrySet()) {
            jp.getValue().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);

                    if (st == null) {
                        eLast = e;
                        st = e.getComponent().getName();

                        ((JPanel)e.getComponent()).setOpaque(true);
                        Chess.mainFrame.repaint();

                        if (ChessBoard.hasItem(st.charAt(0) - 96, Integer.parseInt(st.substring(1)))) {
                            Chess.chessBoard.setLocalItem(ChessBoard.isItem(st.charAt(0) - 96, Integer.parseInt(st.substring(1))));
                            for (int x = 1; x < 9; x++) {
                                for (int y = 1; y < 9; y++) {
                                    if (Chess.canMove((byte)x, (byte) y)) {
                                        for (Map.Entry<String, JPanel> jpair : jPanels.entrySet()) {
                                            if (jpair.getKey().equals("" + (char) (x + 96) + y)) {
                                                jpair.getValue().setOpaque(true);
                                                Chess.mainFrame.repaint();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else if (ep == null) {
                        ep = e.getComponent().getName();
                        ((JPanel)eLast.getComponent()).setOpaque(false);
                        Chess.mainFrame.repaint();
                        for (JPanel j : jPanels.values()) {
                            j.setOpaque(false);
                            Chess.mainFrame.repaint();
                        }
                        Chess.step = st + "-" + ep;
                        st = null;
                        ep = null;
                    }
                }
            });
        }
    }

    private Map<String, JPanel> initJPanels() {
        jPanels = new HashMap<>();

        jPanels.put("a1", cellA1);
        jPanels.put("a2", cellA2);
        jPanels.put("a3", cellA3);
        jPanels.put("a4", cellA4);
        jPanels.put("a5", cellA5);
        jPanels.put("a6", cellA6);
        jPanels.put("a7", cellA7);
        jPanels.put("a8", cellA8);

        jPanels.put("b1", cellB1);
        jPanels.put("b2", cellB2);
        jPanels.put("b3", cellB3);
        jPanels.put("b4", cellB4);
        jPanels.put("b5", cellB5);
        jPanels.put("b6", cellB6);
        jPanels.put("b7", cellB7);
        jPanels.put("b8", cellB8);

        jPanels.put("c1", cellC1);
        jPanels.put("c2", cellC2);
        jPanels.put("c3", cellC3);
        jPanels.put("c4", cellC4);
        jPanels.put("c5", cellC5);
        jPanels.put("c6", cellC6);
        jPanels.put("c7", cellC7);
        jPanels.put("c8", cellC8);

        jPanels.put("d1", cellD1);
        jPanels.put("d2", cellD2);
        jPanels.put("d3", cellD3);
        jPanels.put("d4", cellD4);
        jPanels.put("d5", cellD5);
        jPanels.put("d6", cellD6);
        jPanels.put("d7", cellD7);
        jPanels.put("d8", cellD8);

        jPanels.put("e1", cellE1);
        jPanels.put("e2", cellE2);
        jPanels.put("e3", cellE3);
        jPanels.put("e4", cellE4);
        jPanels.put("e5", cellE5);
        jPanels.put("e6", cellE6);
        jPanels.put("e7", cellE7);
        jPanels.put("e8", cellE8);

        jPanels.put("f1", cellF1);
        jPanels.put("f2", cellF2);
        jPanels.put("f3", cellF3);
        jPanels.put("f4", cellF4);
        jPanels.put("f5", cellF5);
        jPanels.put("f6", cellF6);
        jPanels.put("f7", cellF7);
        jPanels.put("f8", cellF8);

        jPanels.put("g1", cellG1);
        jPanels.put("g2", cellG2);
        jPanels.put("g3", cellG3);
        jPanels.put("g4", cellG4);
        jPanels.put("g5", cellG5);
        jPanels.put("g6", cellG6);
        jPanels.put("g7", cellG7);
        jPanels.put("g8", cellG8);

        jPanels.put("h1", cellH1);
        jPanels.put("h2", cellH2);
        jPanels.put("h3", cellH3);
        jPanels.put("h4", cellH4);
        jPanels.put("h5", cellH5);
        jPanels.put("h6", cellH6);
        jPanels.put("h7", cellH7);
        jPanels.put("h8", cellH8);

        return jPanels;
    }

    private Map<String, JLabel> initImageLabels() {

        imageLabels = new HashMap<>();

        imageLabels.put("a1", imageLabelA1);
        imageLabels.put("a2", imageLabelA2);
        imageLabels.put("a3", imageLabelA3);
        imageLabels.put("a4", imageLabelA4);
        imageLabels.put("a5", imageLabelA5);
        imageLabels.put("a6", imageLabelA6);
        imageLabels.put("a7", imageLabelA7);
        imageLabels.put("a8", imageLabelA8);

        imageLabels.put("b1", imageLabelB1);
        imageLabels.put("b2", imageLabelB2);
        imageLabels.put("b3", imageLabelB3);
        imageLabels.put("b4", imageLabelB4);
        imageLabels.put("b5", imageLabelB5);
        imageLabels.put("b6", imageLabelB6);
        imageLabels.put("b7", imageLabelB7);
        imageLabels.put("b8", imageLabelB8);

        imageLabels.put("c1", imageLabelC1);
        imageLabels.put("c2", imageLabelC2);
        imageLabels.put("c3", imageLabelC3);
        imageLabels.put("c4", imageLabelC4);
        imageLabels.put("c5", imageLabelC5);
        imageLabels.put("c6", imageLabelC6);
        imageLabels.put("c7", imageLabelC7);
        imageLabels.put("c8", imageLabelC8);

        imageLabels.put("d1", imageLabelD1);
        imageLabels.put("d2", imageLabelD2);
        imageLabels.put("d3", imageLabelD3);
        imageLabels.put("d4", imageLabelD4);
        imageLabels.put("d5", imageLabelD5);
        imageLabels.put("d6", imageLabelD6);
        imageLabels.put("d7", imageLabelD7);
        imageLabels.put("d8", imageLabelD8);

        imageLabels.put("e1", imageLabelE1);
        imageLabels.put("e2", imageLabelE2);
        imageLabels.put("e3", imageLabelE3);
        imageLabels.put("e4", imageLabelE4);
        imageLabels.put("e5", imageLabelE5);
        imageLabels.put("e6", imageLabelE6);
        imageLabels.put("e7", imageLabelE7);
        imageLabels.put("e8", imageLabelE8);

        imageLabels.put("f1", imageLabelF1);
        imageLabels.put("f2", imageLabelF2);
        imageLabels.put("f3", imageLabelF3);
        imageLabels.put("f4", imageLabelF4);
        imageLabels.put("f5", imageLabelF5);
        imageLabels.put("f6", imageLabelF6);
        imageLabels.put("f7", imageLabelF7);
        imageLabels.put("f8", imageLabelF8);

        imageLabels.put("g1", imageLabelG1);
        imageLabels.put("g2", imageLabelG2);
        imageLabels.put("g3", imageLabelG3);
        imageLabels.put("g4", imageLabelG4);
        imageLabels.put("g5", imageLabelG5);
        imageLabels.put("g6", imageLabelG6);
        imageLabels.put("g7", imageLabelG7);
        imageLabels.put("g8", imageLabelG8);

        imageLabels.put("h1", imageLabelH1);
        imageLabels.put("h2", imageLabelH2);
        imageLabels.put("h3", imageLabelH3);
        imageLabels.put("h4", imageLabelH4);
        imageLabels.put("h5", imageLabelH5);
        imageLabels.put("h6", imageLabelH6);
        imageLabels.put("h7", imageLabelH7);
        imageLabels.put("h8", imageLabelH8);

        return imageLabels;
    }

    private Map<String, ImageIcon> initWhiteItems() {
        whiteItems = new HashMap<>();
        whiteItems.put("King", new ImageIcon(getClass().getResource("/swing/models/chessitems/whiteKing.png"))); //npe
        whiteItems.put("Queen", new ImageIcon(getClass().getResource("/swing/models/chessitems/whiteQueen.png")));
        whiteItems.put("Rook", new ImageIcon(getClass().getResource("/swing/models/chessitems/whiteRook.png")));
        whiteItems.put("Knight", new ImageIcon(getClass().getResource("/swing/models/chessitems/whiteKnight.png")));
        whiteItems.put("Bishop", new ImageIcon(getClass().getResource("/swing/models/chessitems/whiteBishop.png")));
        whiteItems.put("Pawn", new ImageIcon(getClass().getResource("/swing/models/chessitems/whitePawn.png")));
        return whiteItems;
    }

    private Map<String, ImageIcon> initBlackItems() {
        blackItems = new HashMap<>();
        blackItems.put("King", new ImageIcon(getClass().getResource("/swing/models/chessitems/blackKing.png")));
        blackItems.put("Queen", new ImageIcon(getClass().getResource("/swing/models/chessitems/blackQueen.png")));
        blackItems.put("Rook", new ImageIcon(getClass().getResource("/swing/models/chessitems/blackRook.png")));
        blackItems.put("Knight", new ImageIcon(getClass().getResource("/swing/models/chessitems/blackKnight.png")));
        blackItems.put("Bishop", new ImageIcon(getClass().getResource("/swing/models/chessitems/blackBishop.png")));
        blackItems.put("Pawn", new ImageIcon(getClass().getResource("/swing/models/chessitems/blackPawn.png")));
        return blackItems;
    }

    public void draw(ArrayList<ChessItem> items) { // установка всех фигур из ARRAYLIST ChessItems на доску.
        try {
            for (Map.Entry<String, JLabel> jlabelpair : imageLabels.entrySet()) {
                outer:
                {
                    for (ChessItem item : items) {
                        if (jlabelpair.getKey().equals(String.valueOf((char) (item.getX() + 96) + String.valueOf(item.getY())))) {
                            if (item.getColor().equals("black")) {
                                for (Map.Entry<String, ImageIcon> blackpair : blackItems.entrySet()) {
                                    if (item.toString().equals(blackpair.getKey())) {
                                        jlabelpair.getValue().setIcon(blackpair.getValue());

                                        break outer;
                                    }
                                }

                            } else if (item.getColor().equals("white")) {
                                for (Map.Entry<String, ImageIcon> whitepair : whiteItems.entrySet()) {
                                    if (item.toString().equals(whitepair.getKey())) {
                                        jlabelpair.getValue().setIcon(whitepair.getValue());

                                        break outer;
                                    }
                                }

                            } else continue;

                        } else continue;
                    }
                    jlabelpair.getValue().setIcon(null);
                }
                continue;

            }
        } catch (Exception e) {
            if (!(e instanceof InterruptedException)) {
                e.printStackTrace();
                System.out.println("CATCH! void draw()");
                Chess.writeLogs("CATCH! void draw()");
            }
        }
    }

}








