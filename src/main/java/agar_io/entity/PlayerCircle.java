package agar_io.entity;

import agar_io.tile_map.TileMap;

import java.awt.*;

public class PlayerCircle extends MapObject {

    //player stuff
    private int maxDiameter;
    private boolean dead;
    private int minSpeed;
    private int speed;

    //mouse position
    private Point mousePosition;

    public PlayerCircle(TileMap tileMap) {
        super(tileMap);
        //TODO: Random position
        position = new Point(100, 100);
        diameter = 50;
        maxDiameter = 200;
        speed = 3;
        minSpeed = 1;
    }

    public void update() {
        if (mousePosition != null) {
            int centerX = position.x + (diameter / 2);
            int centerY = position.y + (diameter / 2);
            if (mousePosition.x != centerX) {
                position.x += mousePosition.x < centerX ? -speed : speed;
            }
            if (mousePosition.y != centerY) {
                position.y += mousePosition.y < centerY ? -speed : speed;
            }
        }
    }

    public void draw(Graphics graphics) {
        setMapPosition();
        super.draw(graphics);
    }

    public void setMousePosition(Point mousePosition) {
        this.mousePosition = mousePosition;
    }
}
