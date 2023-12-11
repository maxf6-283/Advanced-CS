import java.io.Serializable;
import java.awt.Color;
import java.awt.Graphics;

public class Player implements Serializable {
    private int x;
    private int y;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x - 10, y - 10, 20, 20);
    }

    public void moveUp() {
        y -= 5;
    }

    public void moveDown() {
        y += 5;
    }

    public void moveLeft() {
        x -= 5;
    }

    public void moveRight() {
        x += 5;
    }
}
