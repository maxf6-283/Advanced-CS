import java.awt.Graphics;

public class Circle implements Runnable {

  private int x;
  private int y;
  private int speed;
  private boolean exists = true;

  public Circle(int x, int y, int speed) {
    this.x = x;
    this.y = y;
    this.speed = speed;
  }

  public void drawMe(Graphics g) {
    if (exists) {
      g.fillOval(x - 15, y - 15, 30, 30);
    }
  }

  public void run() {
    while (exists) {
      y -= speed;

      if (y < -10) {
        y = 610;
      }
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        System.out.println(e);
      }
    }
  }

  public void remove() {
    exists = false;
  }

  public boolean contains(int mx, int my) {
    return (mx - x) * (mx - x) + (my - y) * (my - y) <= 225;
  }

  public boolean exists() {
    return exists;
  }

}