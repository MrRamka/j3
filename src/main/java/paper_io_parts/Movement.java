package paper_io_parts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Movement extends JPanel implements Runnable,  MouseListener, MouseMotionListener{

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static int MOVE_SPEED = 5;
    Ball userBall = new Ball(WIDTH/2, HEIGHT/2, 50, 0,0);

    private BufferedImage image;
    private Graphics2D g;
    private static JTextField tf = new JTextField(30);
    public Movement() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        addMouseMotionListener(this);
        addMouseListener(this);

    }
    public Graphics2D draw(Graphics2D graphics2D){
        Graphics2D graphics = graphics2D;
        graphics = drawBall(graphics);

        return graphics;
    }
    private Graphics2D drawBall(Graphics2D graphics2D){
        Graphics2D graphics = graphics2D;
        graphics.fillOval(userBall.getxPosition(), userBall.getyPosition(), userBall.getDiameter(), userBall.getDiameter());
        userBall.update();
        return graphics;
    }

    @Override
    public void run() {

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        while (true){
            g = draw(g);
            g.fillOval(userBall.getxPosition(), userBall.getyPosition(), userBall.getDiameter(), userBall.getDiameter());



        }


    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
