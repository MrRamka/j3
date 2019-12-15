package agar_io.entity;

import agar_io.tile_map.TileMap;

import java.awt.*;

public abstract class MapObject {
    //tile stuff
    protected TileMap tileMap;
    protected int tileSize;
    protected double xMap;
    protected double yMap;

    //position and vector
    protected Point position;

    //dimensions
    protected int diameter;

    //collision box
    protected int collisionWidth;
    protected int collisionHeight;

    //color
    protected Color objColor;

    public MapObject(TileMap tileMap) {
        this.tileMap = tileMap;
        tileSize = tileMap.getTileSize();
    }

    public boolean intersects(MapObject o) {
        Rectangle r1 = getRectangle();
        Rectangle r2 = o.getRectangle();
        return r1.intersects(r2);
    }

    public Rectangle getRectangle() {
        return new Rectangle(position.x - collisionWidth,
                position.y - collisionHeight,
                collisionWidth,
                collisionHeight);
    }

    public void setPosotion(Point position){
        this.position = position;
    }

    public Color getObjColor(){
        return objColor;
    }

    public void draw(Graphics graphics){
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setColor(objColor);
        graphics2D.fillOval(position.x, position.y, diameter, diameter);

    }
    public void setMapPosition(){
        xMap = tileMap.getX();
        yMap = tileMap.getY();
    }

    public void setColor(Color color){
        this.objColor = color;
    }
    public Point getPosition(){
        return position;
    }
}
