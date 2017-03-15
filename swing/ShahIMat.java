package swing;

import chess.Chess;

import javax.swing.*;
import java.awt.*;

public class ShahIMat
{
    public static void runShahIMat(boolean shah)
    {
        JFrame frame = new JFrame();
        frame.setLocationRelativeTo(Chess.mainBoard);
        frame.setUndecorated(true);
        frame.setResizable(true); // неизменный размер окна
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width/2,screenSize.height/2); // положение окна на экране

        ShahIMatForm shahimatform = new ShahIMatForm(shah);
        frame.setContentPane(shahimatform.panel1);

        frame.pack();// подгоняет размер окна под компоненты
        frame.setVisible(true);
        try
        {
            Thread.sleep(3000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        frame.dispose();

    }
}
