package swing;

import chess.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StartHello {
    public JPanel panelMain;
    private JButton continueBtn;
    private JButton newGameBtn;
    private JButton saveBtn;
    private JButton exitBtn;


    public StartHello() {

        Chess.mainFrame.setContentPane(new Hello());
        Container cont1 = Chess.mainFrame.getContentPane();
        cont1.setLayout(new BoxLayout(cont1,1));
        cont1.add(panelMain);
        Chess.mainFrame.setResizable(false); // неизменный размер окна
        Chess.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//закрыте окна крестиком ( и выход из программы)
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Chess.mainFrame.setLocation(screenSize.width / 4, screenSize.height / 15); // положение окна на экране
        Chess.mainFrame.pack();// подгоняет размер окна под компоненты
        Chess.mainFrame.setVisible(true);


        newGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chess.mainFrame.setContentPane(new BoardGraphic());
                Container cont2 = Chess.mainFrame.getContentPane();
                cont2.setLayout(new BoxLayout(cont2,1));
                cont2.add(Chess.mainBoard.board1);
                Chess.mainFrame.pack();// подгоняет размер окна под компоненты
            }
        });

    }

}
class Hello extends JPanel {
    public void paintComponent(Graphics g) {
        Image im = null;
        try {
            im = ImageIO.read((getClass().getResource("/swing/models/chessitems/helloPic.jpg")));
        } catch (IOException e) {
            System.out.println("Catch Imageread");
            e.printStackTrace();
        }

        g.drawImage(im, 0, 0, null);
    }
}


class BoardGraphic extends JPanel {
    public void paintComponent(Graphics g) {
        Image im = null;
        try {
            im = ImageIO.read((getClass().getResource("/swing/models/chessboard.jpg")));
        } catch (IOException e) {
            System.out.println("Catch Imageread");
            e.printStackTrace();
        }

        g.drawImage(im, 0, 0, null);
    }
}
