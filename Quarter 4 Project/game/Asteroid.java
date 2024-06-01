package game;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import comms.ClientEvent;
import comms.HostManager;

import java.io.IOException;
import java.io.File;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Color;

public class Asteroid {
    public static final BufferedImage asteroidImage;

    static {
        BufferedImage astIm = null;

        try {
            astIm = ImageIO.read(new File("resources/asteroid.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        asteroidImage = astIm;
    }

    private double x;
    private double lastX;
    private double y;
    private double lastY;
    private double r;
    private double xVel;
    private double yVel;
    private double rVel;

    private double size;
    private double health;
    private double lastHealth;

    public Asteroid() {
        x = Math.random() * Player.fieldWidth;
        y = Math.random() * Player.fieldHeight;
        r = Math.random() * Math.TAU;
        xVel = (Math.random() - 0.5) * 25.0;
        yVel = (Math.random() - 0.5) * 25.0;
        rVel = (Math.random() - 0.5);
        size = 0.01;
        health = 1.0;
    }

    public Asteroid(SubEvent a) {
        String[] vals = a.valueString().split(SubEvent.subSep);
        x = Double.parseDouble(vals[0]);
        y = Double.parseDouble(vals[1]);
        r = Double.parseDouble(vals[2]);
        xVel = Double.parseDouble(vals[3]);
        yVel = Double.parseDouble(vals[4]);
        rVel = Double.parseDouble(vals[5]);
        size = Double.parseDouble(vals[6]);
        health = Double.parseDouble(vals[7]);
    }

    public void move() {
        x += xVel;
        x = (x % Player.fieldWidth + Player.fieldWidth) % Player.fieldWidth;
        y += yVel;
        y = (x % Player.fieldWidth + Player.fieldWidth) % Player.fieldWidth;
        r += rVel;
        r = (r % Math.TAU);

        size += 0.0005;
        xVel *= (size - 0.0001) / size;
        yVel *= (size - 0.0001) / size;
        rVel *= (size - 0.0005) / size;
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

        // healthbar
        if (health < 1.0) {
            g.setColor(Color.GREEN);
            g.fillRect(-50, (int) (size * -asteroidImage.getHeight() / 2 - 5), 100, 10);
            g.setColor(Color.RED);
            g.fillRect(-50 + (int) (100 * health), (int) (size * -asteroidImage.getHeight() / 2 - 5),
                    100 - (int) (100 * health), 10);
        }

        g.scale(scale, scale);
        g.rotate(r + Math.PI / 2);
        g.scale(size, size);
        g.drawImage(asteroidImage, -asteroidImage.getWidth() / 2, -asteroidImage.getHeight() / 2, null);
        g.setTransform(tx);
    }

    public void takeScaledDamage(double damage) {
        health -= damage / size;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double r() {
        return r;
    }

    public double xVel() {
        return xVel;
    }

    public double yVel() {
        return yVel;
    }

    public double rVel() {
        return rVel;
    }

    public double size() {
        return size;
    }

    public double health() {
        return health;
    }

    public PowerUp generatePowerUp() {
        // the probability of generating a powerup goes up in size
        // the exact probability is size/0.5+size
        // so a 0.1 size asteroid would only have a 1/6 chance
        // but a size 1 would have a 2/3
        // and a size 2 would have a 4/5
        double toComp = size / (0.5 + size);
        if(Math.random() < toComp) {
            return new PowerUp(x, y);
        } else {
            return null;
        }
    }

    public void sendEvents(HostManager m) {
        if (Math.abs(lastX - x) > 5.0 || Math.abs(lastY - y) > 5.0 || health != lastHealth) {
            m.sendEvent(new ClientEvent("asteroid", new SubEvent(this)+""), false);
            // System.out.println("Sending asteroid change event");
            lastX = x;
            lastY = y;
            lastHealth = health;
            
        }
    }
}
