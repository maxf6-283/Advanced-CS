import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

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
    private static Random random = new Random();
    private static final Color waterColor = new Color(50, 100, 200);
    private static final Color waterWaveColor = new Color(100, 150, 200);
    private static final Color dirtColor = new Color(200, 150, 100);
    private static final Color grassColor = new Color(100, 250, 50);
    private static final Color lavaColor = new Color(250, 100, 50);
    private static final Color lavaBubbleColor = new Color(250, 175, 125);
    private static final Color treeTrunkColor = new Color(100, 50, 50);
    private static final Color startFlagColor = new Color(0, 100, 0);

    static {
        images = new HashMap<>();
        // water (no longer used due to lag)
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

    public void paint(int x, int y, int keyFrame, Graphics2D g) {
        // if (images.containsKey(this)) {
        // g.drawImage(images.get(this)[(keyFrame / 10) % images.get(this).length], x,
        // y, null);
        // return;
        // }
        switch (this) {
            case WATER -> {
                g.setColor(waterColor);
                g.fillRect(x, y, 25, 25);
                g.setColor(waterWaveColor);
                g.fill(new Rectangle2D.Double(x, y + (11 + 10 * Math.sin(keyFrame / 100.0 + x)), 25, 4));
            }
            case DIRT -> {
                g.setColor(dirtColor);
                g.fillRect(x, y, 25, 25);
                // texturing
                random.setSeed(((long) x) ^ (((long) y) << 32));
                random.nextInt();
                for (int i = 0; i < 10; i++) {
                    int redness = random.nextInt(100, 150);
                    int brightness = random.nextInt(50, 100);
                    Color moundColor = new Color(redness + brightness, 3 * brightness / 2, brightness);
                    double diameter = random.nextDouble(2, 8);
                    double xPos = random.nextDouble(x, x + 25 - diameter);
                    double yPos = random.nextDouble(y, y + 25 - diameter);
                    g.setColor(moundColor);
                    g.fill(new Ellipse2D.Double(xPos, yPos, diameter, diameter));
                }
            }
            case GRASS -> {
                g.setColor(grassColor);
                g.fillRect(x, y, 25, 25);
                // texturing
                random.setSeed(((long) x) ^ (((long) y) << 32));
                random.nextInt();
                for (int i = 0; i < 10; i++) {
                    int brightness = random.nextInt(75, 200);
                    Color bladeColor = new Color((brightness + 100) / 4, brightness, (brightness + 100) / 4);
                    double width = random.nextDouble(1, 2);
                    double height = random.nextDouble(5, 10);
                    double xPos = random.nextDouble(x, x + 25 - width);
                    double yPos = random.nextDouble(y, y + 25 - height);
                    g.setColor(bladeColor);
                    g.fill(new Rectangle2D.Double(xPos, yPos, width, height));
                }
            }
            case LAVA -> {
                g.setColor(lavaColor);
                g.fillRect(x, y, 25, 25);
                // bubbles
                keyFrame += x;
                keyFrame -= y & x;
                keyFrame += y / 2;
                random.setSeed(((keyFrame) / 100) ^ (((long) x) << 16) ^ (((long) y) << 32));
                random.nextDouble();
                double xPos = random.nextDouble(x + 2, x + 23);
                double yPos = random.nextDouble(y + 2, y + 23);
                double radius = 2.0 * Math.sin(((keyFrame) % 100) / 100.0 * Math.PI);
                g.setColor(lavaBubbleColor);
                g.fill(new Ellipse2D.Double(xPos - radius, yPos - radius, 2 * radius, 2 * radius));
            }
            case STONE -> {
                g.setColor(Color.GRAY);
                g.fillRect(x, y, 25, 25);
                // texturing
                random.setSeed(((long) x) ^ (((long) y) << 32));
                random.nextInt();
                for (int i = 0; i < 10; i++) {
                    int brightness = random.nextInt(50, 200);
                    Color pebbleColor = new Color(brightness, brightness, brightness);
                    double width = random.nextDouble(2, 4);
                    double height = random.nextDouble(2, 4);
                    double xPos = random.nextDouble(x, x + 25 - width);
                    double yPos = random.nextDouble(y, y + 25 - height);
                    g.setColor(pebbleColor);
                    g.fill(new Rectangle2D.Double(xPos, yPos, width, height));
                }
            }
            case ROCK -> {
                g.setColor(Color.DARK_GRAY);
                g.fillOval(x, y, 25, 25);
            }
            case TREE -> {
                g.setColor(treeTrunkColor);
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
                g.setColor(startFlagColor);
                g.fillRect(x + 14, y + 5, 10, 5);
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + this);
        }
    }
}
