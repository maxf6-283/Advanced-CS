import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spider implements WanderingObject {
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
            image = ImageIO.read(new File("images/spider/spider.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Spider(double x, double y, HashTable<Square, TileObject> tileMap) {
        this.x = x;
        this.y = y;
        tiles = tileMap;
    }

    public void paint(Graphics2D g) {
        g.drawImage(image, (int) (x - 3.5), (int) y - 3, null);
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
            if (blocker(new Square((int) (this.x + 3.5) / 25, (int) this.y / 25))) {
                this.x -= ((this.x + 3.5) % 25.0 + 25.0) % 25.0;
            }
            if ((this.y % 25.0 + 25.0) % 25.0 > 22.0) {
                if (blocker(new Square((int) (this.x + 3.5) / 25, (int) this.y / 25 + 1))) {
                    this.x -= ((this.x + 3.5) % 25.0 + 25.0) % 25.0;
                }
            } else if ((this.y % 25.0 + 25.0) % 25.0 < 3.0) {
                if (blocker(new Square((int) (this.x + 3.5) / 25, (int) this.y / 25 - 1))) {
                    this.x -= ((this.x + 3.5) % 25.0 + 25.0) % 25.0;
                }
            }
        } else {
            if (blocker(new Square((int) (this.x - 3.5) / 25, (int) this.y / 25))) {
                this.x += 25.0 - ((this.x - 3.5) % 25.0 + 25.0) % 25.0;
            }
            if ((this.y % 25.0 + 25.0) % 25.0 > 22.0) {
                if (blocker(new Square((int) (this.x - 3.5) / 25, (int) this.y / 25 + 1))) {
                    this.x += 25.0 - ((this.x - 3.5) % 25.0 + 25.0) % 25.0;
                }
            } else if ((this.y % 25.0 + 25.0) % 25.0 < 3.0) {
                if (blocker(new Square((int) (this.x - 3.5) / 25, (int) this.y / 25 - 1))) {
                    this.x += 25.0 - ((this.x - 3.5) % 25.0 + 25.0) % 25.0;
                }
            }
        }
    }

    private void moveY(double y) {
        this.y += y;
        if (y > 0) {
            if (blocker(new Square((int) this.x / 25, (int) (this.y + 3.0) / 25))) {
                this.y -= ((this.y + 3.0) % 25.0 + 25.0) % 25.0;
            }
            if ((this.x % 25.0 + 25.0) % 25.0 > 21.5) {
                if (blocker(new Square((int) this.x / 25 + 1, (int) (this.y + 3.0) / 25))) {
                    this.y -= ((this.y + 3.0) % 25.0 + 25.0) % 25.0;
                }
            } else if ((this.x % 25.0 + 25.0) % 25.0 < 3.5) {
                if (blocker(new Square((int) this.x / 25 - 1, (int) (this.y + 3.0) / 25))) {
                    this.y -= ((this.y + 3.0) % 25.0 + 25.0) % 25.0;
                }
            }
        } else {
            if (blocker(new Square((int) this.x / 25, (int) ((this.y - 3.0)) / 25))) {
                this.y += 25.0 - ((this.y - 3.0) % 25.0 + 25.0) % 25.0;
            }
            if ((this.x % 25.0 + 25.0) % 25.0 > 21.5) {
                if (blocker(new Square((int) this.x / 25 + 1, (int) ((this.y - 3.0)) / 25))) {
                    this.y += 25.0 - ((this.y - 3.0) % 25.0 + 25.0) % 25.0;
                }
            } else if ((this.x % 25.0 + 25.0) % 25.0 < 3.5) {
                if (blocker(new Square((int) this.x / 25 - 1, (int) ((this.y - 3.0)) / 25))) {
                    this.y += 25.0 - ((this.y - 3.0) % 25.0 + 25.0) % 25.0;
                }
            }
        }
    }

    private boolean blocker(Square tile) {
        if (!tiles.containsKey(tile) || !tiles.get(tile).get(0).isBackground()) {
            return true;
        }
        return tiles.get(tile).stream().anyMatch(e -> (e.isBlocker()));
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
