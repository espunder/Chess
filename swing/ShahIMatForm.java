package swing;

import javax.swing.*;


public class ShahIMatForm {

        public JPanel panel1;
        private JLabel pic;

        ShahIMatForm(boolean shah )
        {
            if (shah)
                pic.setIcon(new ImageIcon(getClass().getResource("/swing/models/shah.png")));
            else
                pic.setIcon(new ImageIcon(getClass().getResource("/swing/models/mat.png")));
        }
    }
