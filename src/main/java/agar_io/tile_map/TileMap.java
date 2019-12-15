package agar_io.tile_map;

import agar_io.main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

public class TileMap {
    //position
    private double x;
    private double y;

    //bounds
    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;

    private double tween;

    private int[][] map;
    private int tileSize;
    private int numRows;
    private int numCols;
    private int width;
    private int height;

    //tileset
    private BufferedImage tileSet;
    private int numTilesAcross;
    private Tile[] tiles;

    //drawing
    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numColsToDraw;

    //tile map size
    private int tileMapSize;

    public TileMap(int tileSize, int tileMapSize) {
        this.tileSize = tileSize;
        this.tileMapSize = tileMapSize;
        numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
        numColsToDraw = GamePanel.WIDTH / tileSize + 2;
        tween = 0.07;
    }

    public void loadTiles(String path) {
        try {
            tileSet = ImageIO.read(getClass().getResourceAsStream(path));
            numTilesAcross = tileSet.getWidth() / tileSize;
            tiles = new Tile[numTilesAcross];
            BufferedImage subImage;
            for (int col = 0; col < numTilesAcross; col++) {
                subImage = tileSet.getSubimage(col * tileSize, 0, tileSize, tileSize);
                tiles[col] = new Tile(subImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setTween(double tween) {
        this.tween = tween;
    }

    public void setPosotion(double x, double y) {
        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;

        fixBounds();
        colOffset = (int) -this.x / tileSize;
        rowOffset = (int) -this.y / tileSize;
    }

    private void fixBounds() {
        if (x < xMin) {
            x = xMin;
        }
        if (x > xMax) {
            x = xMax;
        }
        if (y < yMin) {
            y = yMin;
        }
        if (y > yMax) {
            y = yMax;
        }
    }

    public void loadMap() {
        numCols = numRows = tileMapSize;
        // 0 is empty map
        map = new int[numCols][numRows];
        width = numCols * tileSize;
        height = numRows * tileSize;

        xMin = GamePanel.WIDTH - width;
        xMax = 0;
        yMin = GamePanel.HEIGHT - height;
        yMax = 0;

    }

    public void draw(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
            if (row >= numRows) {
                break;
            }
            for (int col = colOffset; col < colOffset + numColsToDraw; col++) {
                if (col >= numCols) {
                    break;
                }
                int rc = map[row][col];
                int r = rc / numTilesAcross;

                graphics2D.drawImage(tiles[r].getImage(), (int) x + col * tileSize, (int) y + row * tileSize, null);
            }
        }

    }
}
