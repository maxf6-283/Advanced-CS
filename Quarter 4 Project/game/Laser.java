package game;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import comms.ClientEvent;
import comms.HostManager;

public class Laser {
    public static final BufferedImage laserImage;
    private double x;
    private double y;
    private double r;
    private double xVel;
    private double yVel;
    private double lastSentX;
    private double lastSentY;

    static {
        BufferedImage l = null;
        try {
            l = ImageIO.read(new File("resources/laser.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        laserImage = l;
    }

    public Laser(SubEvent l) {
        String[] vals = l.valueString().split(SubEvent.subSep);
        x = Double.parseDouble(vals[0]);
        y = Double.parseDouble(vals[1]);
        r = Double.parseDouble(vals[2]);
        xVel = Double.parseDouble(vals[3]);
        yVel = Double.parseDouble(vals[4]);
    }

    public Laser(double laserX, double laserY, double laserR, double laserXVel, double laserYVel) {
        x = laserX;
        y = laserY;
        r = laserR;
        xVel = laserXVel;
        yVel = laserYVel;
        lastSentX = x;
        lastSentY = y;
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

    public void move() {
        x += xVel;
        if (x > Player.fieldWidth) {
            x -= Player.fieldWidth;
        }
        if (x < 0) {
            x += Player.fieldWidth;
        }
        y += yVel;
        if (y > Player.fieldHeight) {
            y -= Player.fieldHeight;
        }
        if (y < 0) {
            y += Player.fieldHeight;
        }
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
        g.rotate(r + Math.PI / 2);
        g.drawImage(laserImage, -laserImage.getWidth() / 2, -laserImage.getHeight() / 2, null);
        g.setTransform(tx);
    }

    public void acceptLaserEvent(SubEvent ev) {
        switch (ev.type()) {
            case "setpos" -> {
                String[] xy = ev.valueString().split(SubEvent.subSep);
                x = Double.parseDouble(xy[0]);
                y = Double.parseDouble(xy[1]);
            }
            default -> {
                System.err.println("!!!LaserNoPos!!!");
            }
        }
    }

    public void sendMessages(HostManager m, int n) {
        double xDiff = lastSentX - x;
        double yDiff = lastSentY - y;
        if (xDiff * xDiff + yDiff * yDiff > 50) {
            lastSentX = x;
            lastSentY = y;
            m.sendEvent(new ClientEvent("laser", new SubEvent("setpos", n, x + SubEvent.subSep + y) + ""), false);
        }
    }

    public boolean collidesWith(Player p) {
        //I regret this collision code
        double x = p.x() - this.x > Player.fieldWidth / 2 ? this.x + Player.fieldWidth : this.x - p.x() > Player.fieldWidth / 2 ? this.x - Player.fieldWidth : this.x;
        double y = p.y() - this.y > Player.fieldWidth / 2 ? this.y + Player.fieldHeight : this.y - p.y() > Player.fieldHeight / 2 ? this.y - Player.fieldHeight : this.y;
        double totalDist = Player.blueRocket.getHeight() / 2 + laserImage.getWidth();
        double l1x = x - laserImage.getHeight() / 2 * Math.sin(r);
        double l1y = y + laserImage.getHeight() / 2 * Math.cos(r);
        double l2x = x + laserImage.getHeight() / 2 * Math.sin(r);
        double l2y = y - laserImage.getHeight() / 2 * Math.cos(r);
        double dist2 = (l1x - l2x) * (l1x - l2x) + (l1y - l2y) * (l1y - l2y);
        double t = ((p.x() - l1x) * (l2x - l1x) + (p.y() - l1y) * (l2y - l1y)) / dist2;
        t = Math.min(Math.max(t, 0), 1);
        double fPx = l1x + t * (l2x - l1x);
        double fPy = l1y + t * (l2y - l1y);
        double finalDist2 = (fPx - p.x()) * (fPx - p.x()) + (fPy - p.y()) * (fPy - p.y());
        return finalDist2 < totalDist * totalDist;
    }

    public boolean collidesWith(Asteroid a) { //I regret this collision code
        double x = a.x() - this.x > Player.fieldWidth / 2 ? this.x + Player.fieldWidth : this.x - a.x() > Player.fieldWidth / 2 ? this.x - Player.fieldWidth : this.x;
        double y = a.y() - this.y > Player.fieldWidth / 2 ? this.y + Player.fieldHeight : this.y - a.y() > Player.fieldHeight / 2 ? this.y - Player.fieldHeight : this.y;
        double totalDist = Asteroid.asteroidImage.getHeight() / 2 + laserImage.getWidth();
        double l1x = x - laserImage.getHeight() / 2 * Math.sin(r);
        double l1y = y + laserImage.getHeight() / 2 * Math.cos(r);
        double l2x = x + laserImage.getHeight() / 2 * Math.sin(r);
        double l2y = y - laserImage.getHeight() / 2 * Math.cos(r);
        double dist2 = (l1x - l2x) * (l1x - l2x) + (l1y - l2y) * (l1y - l2y);
        double t = ((a.x() - l1x) * (l2x - l1x) + (a.y() - l1y) * (l2y - l1y)) / dist2;
        t = Math.min(Math.max(t, 0), 1);
        double fPx = l1x + t * (l2x - l1x);
        double fPy = l1y + t * (l2y - l1y);
        double finalDist2 = (fPx - a.x()) * (fPx - a.x()) + (fPy - a.y()) * (fPy - a.y());
        return finalDist2 < totalDist * totalDist;
    }
}
