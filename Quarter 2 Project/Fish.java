import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;

import javax.imageio.ImageIO;

public class Fish implements WanderingObject {
    private double x;
    private double y;
    private double vel;
    private double accel;
    private double angleVel;
    private double angle;
    private HashTable<Square, TileObject> tiles;
    private static BufferedImage image;

    static {
        try {
            image = ImageIO.read(new File("images/fish/fish.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Fish(double x, double y, HashTable<Square, TileObject> tileMap) {
        this.x = x;
        this.y = y;
        tiles = tileMap;
    }

    public void paint(Graphics g) {
        g.drawImage(image, (int) x - 10, (int) y - 10, 20, 20, null);
    }

    public void move() {
        moveX(vel * Math.cos(angle));
        moveY(vel * Math.sin(angle));
        vel += accel;
        angle += angleVel;

        accel += (5 - vel) * Math.random() / 10;
        accel *= 0.9;
        angleVel += (Math.random()-0.5) / 50 * vel;
        angleVel *= 0.95;
    }

    private void moveX(double x) {
        this.x += x;
        if (x > 0) {
            if (blocker(new Square((int) (this.x + 10.0) / 25, (int) this.y / 25))) {
                this.x -= ((this.x + 10.0) % 25.0 + 25.0) % 25.0;
            }
            if ((this.y % 25.0 + 25.0) % 25.0 > 15.0) {
                if (blocker(new Square((int) (this.x + 10.0) / 25, (int) this.y / 25 + 1))) {
                    this.x -= ((this.x + 10.0) % 25.0 + 25.0) % 25.0;
                }
            } else if ((this.y % 25.0 + 25.0) % 25.0 < 10.0) {
                if (blocker(new Square((int) (this.x + 10.0) / 25, (int) this.y / 25 - 1))) {
                    this.x -= ((this.x + 10.0) % 25.0 + 25.0) % 25.0;
                }
            }
        } else {
            if (blocker(new Square((int) (this.x - 10.0) / 25, (int) this.y / 25))) {
                this.x += 25.0 - ((this.x - 10.0) % 25.0 + 25.0) % 25.0;
            }
            if ((this.y % 25.0 + 25.0) % 25.0 > 15.0) {
                if (blocker(new Square((int) (this.x - 10.0) / 25, (int) this.y / 25 + 1))) {
                    this.x += 25.0 - ((this.x - 10.0) % 25.0 + 25.0) % 25.0;
                }
            } else if ((this.y % 25.0 + 25.0) % 25.0 < 10.0) {
                if (blocker(new Square((int) (this.x - 10.0) / 25, (int) this.y / 25 - 1))) {
                    this.x += 25.0 - ((this.x - 10.0) % 25.0 + 25.0) % 25.0;
                }
            }
        }
    }

    private void moveY(double y) {
        this.y += y;
        if (y > 0) {
            if (blocker(new Square((int) this.x / 25, (int) (this.y + 10.0) / 25))) {
                this.y -= ((this.y + 10.0) % 25.0 + 25.0) % 25.0;
            }
            if ((this.x % 25.0 + 25.0) % 25.0 > 15.0) {
                if (blocker(new Square((int) this.x / 25 + 1, (int) (this.y + 10.0) / 25))) {
                    this.y -= ((this.y + 10.0) % 25.0 + 25.0) % 25.0;
                }
            } else if ((this.x % 25.0 + 25.0) % 25.0 < 10.0) {
                if (blocker(new Square((int) this.x / 25 - 1, (int) (this.y + 10.0) / 25))) {
                    this.y -= ((this.y + 10.0) % 25.0 + 25.0) % 25.0;
                }
            }
        } else {
            if (blocker(new Square((int) this.x / 25, (int) ((this.y - 10.0)) / 25))) {
                this.y += 25.0 - ((this.y - 10.0) % 25.0 + 25.0) % 25.0;
            }
            if ((this.x % 25.0 + 25.0) % 25.0 > 15.0) {
                if (blocker(new Square((int) this.x / 25 + 1, (int) ((this.y - 10.0)) / 25))) {
                    this.y += 25.0 - ((this.y - 10.0) % 25.0 + 25.0) % 25.0;
                }
            } else if ((this.x % 25.0 + 25.0) % 25.0 < 10.0) {
                if (blocker(new Square((int) this.x / 25 - 1, (int) ((this.y - 10.0)) / 25))) {
                    this.y += 25.0 - ((this.y - 10.0) % 25.0 + 25.0) % 25.0;
                }
            }
        }
    }

    private boolean blocker(Square tile) {
        if (!tiles.containsKey(tile) || !tiles.get(tile).get(0).isBackground()) {
            return true;
        }
        return tiles.get(tile).stream().anyMatch(e -> (e != TileObject.WATER && e != TileObject.LAVA));
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

}
