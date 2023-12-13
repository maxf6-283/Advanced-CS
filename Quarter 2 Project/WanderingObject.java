import java.awt.Graphics2D;

public interface WanderingObject {
    public void move();

    public void paint(Graphics2D g);

    public double x();
    public double y();
}
