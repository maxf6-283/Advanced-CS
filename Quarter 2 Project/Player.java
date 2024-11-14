import java.awt.Color;
import java.awt.Graphics;

public class Player {
    private double x;
    private double y;
    private HashTable<Square, TileObject> tiles;

    public Player(double x, double y, HashTable<Square, TileObject> tileMap) {
        this.x = x;
        this.x += 12.5;
        this.y = y;
        this.y += 12.5;
        tiles = tileMap;
    }

    public void paint(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect((int) x - 10, (int) y - 10, 20, 20);
        g.setColor(Color.BLACK);
        g.fillOval((int) x - 5, (int) y - 5, 2, 5);
        g.fillOval((int) x + 3, (int) y - 5, 2, 5);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void moveX(double x) {
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

    public void moveY(double y) {
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
        return tiles.get(tile).stream().anyMatch(e -> e.isBlocker());
    }
}
