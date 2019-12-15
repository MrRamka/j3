package agar_io.main;

import agar_io.game_state.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, MouseMotionListener {

    //dimensions
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int SCALE = 1;

    //game thread
    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;

    //image
    private BufferedImage image;
    private Graphics graphics;

    //game state manager
    private GameStateManager gameStateManager;


    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            addMouseMotionListener(this);
            thread.start();
        }
    }

    private void init() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics = image.getGraphics();
        running = true;

        gameStateManager = new GameStateManager();
    }

    @Override
    public void run() {
        init();

        long startTime;
        long elapsedTime;
        long waitTime;

        while (running) {
            startTime = System.nanoTime();

            update();
            draw();
            drawToScreen();

            elapsedTime = System.nanoTime() - startTime;
            waitTime = targetTime - elapsedTime / 1000000;
            if (waitTime < 5) {
                waitTime = 5;
            }
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        gameStateManager.update();
    }

    private void draw() {
        gameStateManager.draw(graphics);
    }

    private void drawToScreen() {
        Graphics2D graphics2D = (Graphics2D) getGraphics();
        graphics2D.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        graphics2D.dispose();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (e != null) {
            gameStateManager.mouseMoved(e);
        }
    }
}
