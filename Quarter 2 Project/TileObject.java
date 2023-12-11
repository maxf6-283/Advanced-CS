import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum TileObject {
    WATER,
    LAVA,
    DIRT,
    GRASS,
    STONE,

    TREE,
    ROCK,
    HOLE,

    START;

    private static HashMap<TileObject, BufferedImage[]> images;

    static {
        images = new HashMap<>();
        // water
        images.put(WATER, new BufferedImage[14]);
        for (int i = 0; i < 14; i++) {
            try {
                images.get(WATER)[i] = ImageIO.read(new File("images/water/water_" + i + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isBackground() {
        return this == WATER || this == LAVA || this == DIRT || this == GRASS || this == STONE;
    }

    public boolean isBlocker() {
        return this == WATER || this == LAVA || this == TREE || this == ROCK || this == HOLE;
    }

    public void paint(int x, int y, int keyFrame, Graphics g) {
        if (images.containsKey(this)) {
            g.drawImage(images.get(this)[(keyFrame / 10) % images.get(this).length], x, y, null);
            return;
        }
        switch (this) {
            case WATER -> {
                g.setColor(Color.BLUE);
                g.fillRect(x, y, 25, 25);
            }
            case DIRT -> {
                g.setColor(new Color(255, 100, 100));
                g.fillRect(x, y, 25, 25);
            }
            case GRASS -> {
                g.setColor(Color.GREEN);
                g.fillRect(x, y, 25, 25);
            }
            case LAVA -> {
                g.setColor(Color.ORANGE);
                g.fillRect(x, y, 25, 25);
            }
            case STONE -> {
                g.setColor(Color.DARK_GRAY);
                g.fillRect(x, y, 25, 25);
            }
            case ROCK -> {
                g.setColor(Color.GRAY);
                g.fillOval(x, y, 25, 25);
            }
            case TREE -> {
                g.setColor(new Color(100, 50, 50));
                g.fillRect(x + 10, y + 15, 5, 10);
                g.setColor(Color.GREEN);
                g.fillOval(x + 5, y, 15, 20);
            }
            case HOLE -> {
                g.setColor(Color.BLACK);
                g.fillOval(x, y + 1, 25, 15);
            }
            case START -> {
                g.setColor(Color.BLACK);
                g.fillRect(x + 12, y + 5, 2, 20);
                g.setColor(new Color(0, 100, 0));
                g.fillRect(x + 14, y + 5, 10, 5);
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + this);
        }
    }
}
