package paper_io_parts;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TestInner {
    private JFrame f;
    private JTextField tf;
    private JPanel box = new Box();
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    public TestInner() {
        f = new JFrame("Inner classes example");
        tf = new JTextField(30);

    }

    class MyMouseMotionListener extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e) {
            int mouseXPosition = e.getX();
            int mouseYPosition = e.getY();
            String s = " X = " + mouseXPosition
                    + " Y = " + mouseYPosition;
            tf.setText(s);
            int boxXCoord = box.getLocation().x;
            int boxYCoord = box.getLocation().y;
            System.out.println("boxX: " + boxXCoord);
            System.out.println("boxY: " + boxYCoord);
            int xDelta = mouseXPosition - boxXCoord;
            int yDelta = mouseYPosition - boxYCoord;

            double hypotenuse = Math.sqrt(Math.pow(xDelta, 2) + Math.pow(yDelta, 2));

            double cosAngle = (xDelta / hypotenuse);
            double sinAngle = (yDelta / hypotenuse);
            System.out.println("xDelta: " + xDelta);
            System.out.println("yDelta: " + yDelta);
            System.out.println("hypotenuse: " + hypotenuse);
            System.out.println("arcCos: " + Math.toDegrees(Math.acos(cosAngle)));
            System.out.println("arcSin: " +sinAngle);
            System.out.println("newX:" + (box.getLocation().x + sinAngle));
            System.out.println("newY:" + (box.getLocation().y + cosAngle));
            System.out.println();

            box.setLocation((int) (box.getLocation().x + sinAngle), (int) (box.getLocation().y + cosAngle));



        }

    }

    public void launchFrame() {

        f.add(tf);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addMouseMotionListener(new MyMouseMotionListener());
        f.addMouseListener(new MouseClickHandler());
        f.setLayout(null);
        f.setSize(WIDTH, HEIGHT);
        f.setVisible(true);
        f.add(box);
        tf.setBounds(WIDTH/2, HEIGHT/2, 100, 20);
    }


    public static void main(String args[]) {
        TestInner obj = new TestInner();
        obj.launchFrame();
    }
}

class MouseClickHandler extends MouseAdapter {

// We just need the mouseClick handler, so we use
    // an adapter to avoid having to write all the
    // event handler methods

    public void mouseClicked(MouseEvent e) {
        // Do stuff with the mouse click...
    }
}