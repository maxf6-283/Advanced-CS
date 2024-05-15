package gui;

import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;

import javax.imageio.ImageIO;

public abstract class Panel extends JPanel {
    public static final BufferedImage starryBg;
    static {
        BufferedImage b = null;
        try {
            b = ImageIO.read(new File("resources/starryBackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        starryBg = b;
    }

    @Override
    public void paintComponent(Graphics g) {
        int x = getWidth() > getHeight() ? getWidth() : getHeight();
        g.drawImage(starryBg, 0, 0, x, x, null);
    }

    public abstract void setActive(boolean a);
}
