package paper_io_parts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BallMovement {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;


    public static void main(String[] args) {
        BallMovement ballMovement = new BallMovement();
        ballMovement.start();
    }

    private void start() {
        JFrame window = new JFrame("Ball");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyDrawBall ball = new MyDrawBall();
        window.getContentPane().add(ball);
        window.setSize(WIDTH, HEIGHT);
        window.setVisible(true);
    }

    class MyDrawBall extends JPanel implements MouseMotionListener {
        private Point imagePosition = new Point(150, 150);
        private Point mousePoint;
        private int diameter = 100;

        @Override
        public void paintComponent(Graphics graphics2D) {
            super.paintComponent(graphics2D);
            Graphics2D graphics = (Graphics2D) graphics2D;
            graphics.setRenderingHint(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            graphics.setColor(Color.orange);
            graphics.fillOval(imagePosition.x, imagePosition.y, diameter, diameter);

        }

        public MyDrawBall() {
            addMouseMotionListener(this);
            Timer timer = new Timer(40, e -> {
                if (mousePoint != null) {

                    int centerX = imagePosition.x + (diameter / 2);
                    int centerY = imagePosition.y + (diameter / 2);
                    if (mousePoint.x != centerX) {
                        imagePosition.x += mousePoint.x < centerX ? -1 : 1;
                    }
                    if (mousePoint.y != centerY) {
                        imagePosition.y += mousePoint.y < centerY ? -1 : 1;
                    }
                    repaint();
                }
            });
            timer.start();
        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mousePoint = e.getPoint();
        }
    }

}