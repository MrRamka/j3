package agar_io.game_state;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GameStateManager {
    private ArrayList<GameState> gameStates;
    private int currentState;

    public static final int MENU_STATE = 0;
    public static final int PLAY_STATE = 1;

    public GameStateManager() {
        gameStates = new ArrayList<>();
        currentState = PLAY_STATE;
        gameStates.add(new MenuState(this));
        gameStates.add(new PlayState(this));
    }

    public void setState(int state) {
        currentState = state;
        gameStates.get(currentState).init();
    }

    public void update() {
        gameStates.get(currentState).update();
    }

    public void draw(Graphics graphics) {
        gameStates.get(currentState).draw(graphics);
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        if (mouseEvent != null) {
            gameStates.get(currentState).mouseMoved(mouseEvent);
        }
    }
}
