import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fish implements WanderingObject {
    private double x;
    private double y;
    private double xVel;
    private double yVel;

    private HashTable<Square, TileObject> tiles;
    private static BufferedImage image;

    private static DLList<Fish> fish;

    static {
        fish = new DLList<>();
        try {
            image = ImageIO.read(new File("images/fish/fish.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Fish(double x, double y, HashTable<Square, TileObject> tileMap) {
        this.x = x;
        this.y = y;
        xVel = 0;
        yVel = 0;
        tiles = tileMap;
        fish.add(this);
    }

    public void paint(Graphics2D g) {
        double angle = Math.atan(yVel / xVel);
        if (xVel < 0) {
            angle += Math.PI;
        }
        AffineTransform prevTrans = g.getTransform();
        g.translate(x, y);
        g.rotate(angle);
        g.translate(-10, -10);
        g.drawImage(image, 0, 0, null);
        g.setTransform(prevTrans);
    }

    public void move() {
        synchronized (Fish.class) {
            moveX(xVel);
            moveY(yVel);

            double closeFish = 0;
            double avgFishX = 0;
            double avgFishY = 0;
            double avgFishVX = 0;
            double avgFishVY = 0;
            DLList<Fish> closeFishes = new DLList<>();
            for (Fish f : fish) {
                double dist2 = dist2(f);
                if (dist2 < 10000) {
                    if (f != this && dist2 != 0) {
                        closeFish += 1 / dist2;
                        avgFishX += f.x / dist2;
                        avgFishY += f.y / dist2;
                        avgFishVX += f.xVel / dist2;
                        avgFishVY += f.yVel / dist2;
                    }
                    if (f != this && dist2 < 1600) {
                        closeFishes.add(f);
                    }
                }
            }
            if (closeFish == 0) {
                return;
            }
            avgFishX /= closeFish;
            avgFishY /= closeFish;
            avgFishVX /= closeFish;
            avgFishVY /= closeFish;
            // move closer to fish center of fish
            double distToAvgX = avgFishX - x;
            double distToAvgY = avgFishY - y;
            xVel += distToAvgX / 1000;
            yVel += distToAvgY / 1000;

            // align velocity with other fish, keeping the same magnitude of velocity as
            // before
            double oldMagnitude = Math.sqrt(xVel * xVel + yVel * yVel);
            xVel = xVel * 0.9 + avgFishVX * 0.1;
            yVel = yVel * 0.9 + avgFishVY * 0.1;
            double newMagnitude = Math.sqrt(xVel * xVel + yVel * yVel);
            if (newMagnitude != 0 && oldMagnitude != 0) {
                xVel = xVel * oldMagnitude / newMagnitude;
                yVel = yVel * oldMagnitude / newMagnitude;
            }

            // repel from the closest fish (1/d)
            for (Fish f : closeFishes) {
                double closestFishDX = f.x - x;
                double closestFishDY = f.y - y;
                if (closestFishDX != 0) {
                    xVel -= 15 / (closestFishDX + 5 * Math.abs(closestFishDX) / closestFishDX);
                } else {
                    xVel += Math.random() - 0.5;
                }
                if (closestFishDY != 0) {
                    yVel -= 15 / (closestFishDY + 5 * Math.abs(closestFishDY) / closestFishDY);
                } else {
                    yVel += Math.random() - 0.5;
                }
            }

            // lerp normalize velocity to 2 at 0.95/frame
            double magnitude = Math.sqrt(xVel * xVel + yVel * yVel);
            if (magnitude != 0) {
                double lerpedXVel = xVel * 2 / magnitude;
                double lerpedYVel = yVel * 2 / magnitude;
                xVel = 0.05 * xVel + 0.95 * lerpedXVel;
                yVel = 0.05 * yVel + 0.95 * lerpedYVel;
            }
        }
    }

    private double dist2(Fish fish2) {
        double dx = x - fish2.x;
        double dy = y - fish2.y;
        return dx * dx + dy * dy;
    }

    private void moveX(double x) {
        this.x += x;
        if (x > 0) {
            if (blocker(new Square((int) (this.x + 10.0) / 25, (int) this.y / 25))) {
                this.x -= ((this.x + 10.0) % 25.0 + 25.0) % 25.0;
                xVel = 0;
            }
            if ((this.y % 25.0 + 25.0) % 25.0 > 15.0) {
                if (blocker(new Square((int) (this.x + 10.0) / 25, (int) this.y / 25 + 1))) {
                    this.x -= ((this.x + 10.0) % 25.0 + 25.0) % 25.0;
                    xVel = 0;
                }
            } else if ((this.y % 25.0 + 25.0) % 25.0 < 10.0) {
                if (blocker(new Square((int) (this.x + 10.0) / 25, (int) this.y / 25 - 1))) {
                    this.x -= ((this.x + 10.0) % 25.0 + 25.0) % 25.0;
                    xVel = 0;
                }
            }
        } else {
            if (blocker(new Square((int) (this.x - 10.0) / 25, (int) this.y / 25))) {
                this.x += 25.0 - ((this.x - 10.0) % 25.0 + 25.0) % 25.0;
                xVel = 0;
            }
            if ((this.y % 25.0 + 25.0) % 25.0 > 15.0) {
                if (blocker(new Square((int) (this.x - 10.0) / 25, (int) this.y / 25 + 1))) {
                    this.x += 25.0 - ((this.x - 10.0) % 25.0 + 25.0) % 25.0;
                    xVel = 0;
                }
            } else if ((this.y % 25.0 + 25.0) % 25.0 < 10.0) {
                if (blocker(new Square((int) (this.x - 10.0) / 25, (int) this.y / 25 - 1))) {
                    this.x += 25.0 - ((this.x - 10.0) % 25.0 + 25.0) % 25.0;
                    xVel = 0;
                }
            }
        }
    }

    private void moveY(double y) {
        this.y += y;
        if (y > 0) {
            if (blocker(new Square((int) this.x / 25, (int) (this.y + 10.0) / 25))) {
                this.y -= ((this.y + 10.0) % 25.0 + 25.0) % 25.0;
                yVel = 0;
            }
            if ((this.x % 25.0 + 25.0) % 25.0 > 15.0) {
                if (blocker(new Square((int) this.x / 25 + 1, (int) (this.y + 10.0) / 25))) {
                    this.y -= ((this.y + 10.0) % 25.0 + 25.0) % 25.0;
                    yVel = 0;
                }
            } else if ((this.x % 25.0 + 25.0) % 25.0 < 10.0) {
                if (blocker(new Square((int) this.x / 25 - 1, (int) (this.y + 10.0) / 25))) {
                    this.y -= ((this.y + 10.0) % 25.0 + 25.0) % 25.0;
                    yVel = 0;
                }
            }
        } else {
            if (blocker(new Square((int) this.x / 25, (int) ((this.y - 10.0)) / 25))) {
                this.y += 25.0 - ((this.y - 10.0) % 25.0 + 25.0) % 25.0;
                yVel = 0;
            }
            if ((this.x % 25.0 + 25.0) % 25.0 > 15.0) {
                if (blocker(new Square((int) this.x / 25 + 1, (int) ((this.y - 10.0)) / 25))) {
                    this.y += 25.0 - ((this.y - 10.0) % 25.0 + 25.0) % 25.0;
                    yVel = 0;
                }
            } else if ((this.x % 25.0 + 25.0) % 25.0 < 10.0) {
                if (blocker(new Square((int) this.x / 25 - 1, (int) ((this.y - 10.0)) / 25))) {
                    this.y += 25.0 - ((this.y - 10.0) % 25.0 + 25.0) % 25.0;
                    yVel = 0;
                }
            }
        }
    }

    private boolean blocker(Square tile) {
        if (!tiles.containsKey(tile) || !tiles.get(tile).get(0).isBackground()) {
            return true;
        }
        return tiles.get(tile).stream().anyMatch(e -> (e != TileObject.WATER));
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
