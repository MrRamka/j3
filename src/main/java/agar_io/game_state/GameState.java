package agar_io.game_state;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public abstract class GameState {
    protected GameStateManager gameStateManager;

    public abstract void init();
    public abstract void update();
    public abstract void draw(Graphics graphics);
    public abstract void mouseMoved(MouseEvent mouseEvent);
}
