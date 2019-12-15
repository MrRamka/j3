package agar_io.tile_map;

import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage bufferedImage;
    public Tile(BufferedImage image){
        this.bufferedImage = image;
    }

    public BufferedImage getImage() {
        return bufferedImage;
    }
}
