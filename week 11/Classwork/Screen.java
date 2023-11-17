import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Dimension;
import java.awt.Color;

public class Screen extends JPanel implements MouseListener {

  private Circle[] circles;
  private long startTime;

  public Screen() {
    circles = new Circle[25];
    for (int i = 0; i < circles.length; i++) {
      circles[i] = new Circle((int) (Math.random() * 600), (int) (Math.random() * 600),
          (int) (Math.random() * 2.5) + 2);
      (new Thread(circles[i])).start();
      if (i == 24) {
        break;
      }
    }
    (new Thread(new Animate(this))).start();
    addMouseListener(this);
    startTime = System.currentTimeMillis();
  }

  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(Color.BLACK);
    if (System.currentTimeMillis() < startTime + 30000) {
      for (Circle c : circles) {
        c.drawMe(g);
      }
      g.drawString(String.format("%.3f", 30.0 - (System.currentTimeMillis() - startTime) / 1000.0), 500, 50);
      boolean finished = true;
      for (Circle c : circles) {
        if (c.exists()) {
          finished = false;
          break;
        }
      }
      if(finished) {
        startTime = 0;
      }
    } else {
      boolean finished = true;
      for (Circle c : circles) {
        if (c.exists()) {
          finished = false;
          break;
        }
      }
      if (finished == true) {
        g.drawString("You win!", 300, 300);
      } else if (finished == false) {
        g.drawString("You lose!", 300, 300);
      }
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(600, 600);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    for (Circle c : circles) {
      if (c.contains(e.getX(), e.getY())) {
        c.remove();
      }
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }
}
