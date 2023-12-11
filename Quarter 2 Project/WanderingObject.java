import java.awt.Graphics;

public interface WanderingObject {
    public void move();

    public void paint(Graphics g);

    public double x();
    public double y();
}
