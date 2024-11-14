package game;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class PowerUp {
    private static final BufferedImage burstShot;
    private static final BufferedImage rapidFire;

    static {
        BufferedImage bs = null;
        BufferedImage rf = null;

        try {
            bs = ImageIO.read(new File("resources/burst.png"));
            rf = ImageIO.read(new File("resources/rapidFire.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        burstShot = bs;
        rapidFire = rf;
    }

    private double x;
    private double y;
    private PowerUpType type;

    public static enum PowerUpType {
        RAPIDFIRE,
        BURST,
    }

    public PowerUp(SubEvent l) {
        String[] vals = l.valueString().split(SubEvent.subSep);
        x = Double.parseDouble(vals[0]);
        y = Double.parseDouble(vals[1]);
        type = PowerUpType.valueOf(vals[2]);
    }

    public PowerUp(double pX, double pY) {
        x = pX;
        y = pY;
        PowerUpType[] values = PowerUpType.values();
        type = values[(int) (Math.random() * values.length)];
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public void draw(Graphics2D g, double cX, double cY, int screenWidth, int screenHeight) {
        int maxDim = screenHeight > screenWidth ? screenHeight : screenWidth;
        double scale = maxDim / 2000.0;
        cX %= Player.fieldWidth;
        cY %= Player.fieldHeight;

        if (cX < x - Player.fieldWidth / 2) {
            cX += Player.fieldWidth;
        }
        if (cX > x + Player.fieldWidth / 2) {
            cX -= Player.fieldWidth;
        }
        if (cY < y - Player.fieldHeight / 2) {
            cY += Player.fieldHeight;
        }
        if (cY > y + Player.fieldHeight / 2) {
            cY -= Player.fieldHeight;
        }

        AffineTransform tx = g.getTransform();
        g.translate(-cX * scale, -cY * scale);
        g.translate(x * scale + screenWidth / 2, y * scale + screenHeight / 2);
        g.scale(scale, scale);
        // g.drawImage(laserImage, -laserImage.getWidth() / 2, -laserImage.getHeight() /
        // 2, null);
        switch (type) {
            case RAPIDFIRE -> {
                g.drawImage(rapidFire, -rapidFire.getWidth() / 2, -rapidFire.getHeight() / 2, null);
            }
            case BURST -> {
                g.drawImage(burstShot, -burstShot.getWidth() / 2, -burstShot.getHeight() / 2, null);
            }
        }
        g.setTransform(tx);
    }

    public PowerUpType type() {
        return type;
    }

    public boolean collidesWith(Player p) {
        double x = p.x() - this.x > Player.fieldWidth / 2 ? this.x + Player.fieldWidth
                : this.x - p.x() > Player.fieldWidth / 2 ? this.x - Player.fieldWidth : this.x;
        double y = p.y() - this.y > Player.fieldWidth / 2 ? this.y + Player.fieldHeight
                : this.y - p.y() > Player.fieldHeight / 2 ? this.y - Player.fieldHeight : this.y;
        double totalDistSq = (p.x() - x) * (p.x() - x) + (p.y() - y) * (p.y() - y);
        int a = (Player.redRocket.getHeight() / 2 + PowerUp.burstShot.getHeight()/2);
        return totalDistSq < a * a;

    }
}
