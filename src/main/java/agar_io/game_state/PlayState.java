package agar_io.game_state;

import agar_io.entity.PlayerCircle;
import agar_io.main.GamePanel;
import agar_io.tile_map.TileMap;


import java.awt.*;
import java.awt.event.MouseEvent;

public class PlayState extends GameState {
    private TileMap tileMap;

    private PlayerCircle playerCircle;

    public PlayState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        init();
    }

    @Override
    public void init() {
        tileMap = new TileMap(100, 100);
        tileMap.loadTiles("/tiles/tileset.png");
        tileMap.setPosotion(0, 0);
        tileMap.loadMap();
        tileMap.setTween(1);
        playerCircle = new PlayerCircle(tileMap);
        playerCircle.setPosotion(new Point(100, 100));
        playerCircle.setColor(Color.orange);
    }

    @Override
    public void update() {
        playerCircle.update();
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, GamePanel.WIDTH * GamePanel.SCALE, GamePanel.HEIGHT * GamePanel.SCALE);

        tileMap.draw(graphics);
        tileMap.setPosotion(GamePanel.WIDTH / 2 - playerCircle.getPosition().x,
                GamePanel.HEIGHT / 2 - playerCircle.getPosition().y);
        playerCircle.draw(graphics);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        playerCircle.setMousePosition(mouseEvent.getPoint());
    }
}
