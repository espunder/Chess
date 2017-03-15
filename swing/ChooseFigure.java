package swing;

import chess.Chess;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Сергей on 05.10.2016.
 */
public class ChooseFigure extends Thread {
    private JPanel mainLabel;
    private JPanel main;
    private JButton button1;
    private JPanel b1;
    private JPanel b2;
    private JButton button2;
    private JPanel b3;
    private JButton button3;
    private JPanel b4;
    private JButton button4;
    public String s;

    public ChooseFigure(String color) {
        initFigures(color);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                s = "queen";
                interrupt();
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                s = "rook";
                interrupt();
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                s = "bishop";
                interrupt();
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                s = "knight";
                interrupt();
            }
        });
    }

    public void run() {
        JFrame frame = new JFrame();
        frame.setContentPane(this.main);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//закрыте окна крестиком ( и выход из программы)
        frame.setLocationRelativeTo(Chess.mainFrame);// положение окна на экране
        frame.pack();//?
        frame.setVisible(true);
        while (!isInterrupted()) {
        }
        frame.setVisible(false);
    }

    private void initFigures(String color) {
        if ("white".equals(color)) {
            button1.setIcon(new ImageIcon(getClass().getResource("/swing/models/chessitems/whiteQueen.png")));
            button2.setIcon(new ImageIcon(getClass().getResource("/swing/models/chessitems/whiteRook.png")));
            button3.setIcon(new ImageIcon(getClass().getResource("/swing/models/chessitems/whiteBishop.png")));
            button4.setIcon(new ImageIcon(getClass().getResource("/swing/models/chessitems/whiteKnight.png")));
        } else {
            button1.setIcon(new ImageIcon(getClass().getResource("/swing/models/chessitems/blackQueen.png")));
            button2.setIcon(new ImageIcon(getClass().getResource("/swing/models/chessitems/blackRook.png")));
            button3.setIcon(new ImageIcon(getClass().getResource("/swing/models/chessitems/blackBishop.png")));
            button4.setIcon(new ImageIcon(getClass().getResource("/swing/models/chessitems/blackKnight.png")));
        }

    }

}
