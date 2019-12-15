package paper_io_parts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Box extends JPanel {
    private static int MOVE_SPEED = 5;
    private JPanel box;

    public Box() {

        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setBounds(600, 600, 100, 100);
        this.setBackground(Color.blue);
        box = this;
    }



}
