package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.Color;
import java.awt.FontMetrics;

import comms.ClientEvent;
import comms.HostEvent;
import comms.HostManager;
import game.PowerUp.PowerUpType;
import gui.Button;
import utils.ArrayList;

public class Player {
    public static final int fieldWidth = 5000;
    public static final int fieldHeight = 5000;

    public static final BufferedImage redRocket;
    public static final BufferedImage blueRocket;

    private static final Font usernameFont;

    static {
        BufferedImage rr = null;
        BufferedImage br = null;
        try {
            rr = ImageIO.read(new File("resources/redRocket.png"));
            br = ImageIO.read(new File("resources/blueRocket.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        redRocket = rr;
        blueRocket = br;

        usernameFont = Button.font.deriveFont(20.0f);
    }

    private int num;

    private double x;
    private double lastSentX;
    private double y;
    private double lastSentY;
    private double xVel;
    private double lastSentXVel;
    private double yVel;
    private double lastSentYVel;
    private double r;
    private double lastSentR;
    private double rVel;
    private double lastSentRVel;
    private boolean thrusting;
    private boolean lastSentThrusting;
    private int turning;
    private int lastSentTurning;
    private String username;
    private boolean dead = false;
    private boolean sentDead = false;
    private double health = 1.0;
    private double lastSentHealth = 1.0;

    private ArrayList<Laser> lasers = new ArrayList<>();
    private ArrayList<Integer> laserTime = new ArrayList<>();
    private ArrayList<Laser> destroyedLasers = new ArrayList<>();
    private static final int laserExpiration = 50;
    private int sentLastLaser = 0;

    private boolean sentAsteroid = false;
    private ArrayList<ClientEvent> eventQueue = new ArrayList<>();
    private Asteroid asteroid = new Asteroid();

    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private ArrayList<PowerUp> powerUpsToSend = new ArrayList<>();
    private PowerUpType powerUpEffect = null;
    private int powerUpDuration = 0;

    public static Player meee;

    public Player(HostEvent createEvent) {
        if (createEvent.type().equals("create")) {
            num = createEvent.playerNum();
            username = createEvent.valueString();
        } else {
            throw new IllegalArgumentException("Player cannot be created from non-create event");
        }
    }

    public String username() {
        return username;
    }

    @Override
    public String toString() {
        return username;
    }

    public synchronized void acceptHostEvent(HostEvent e) {
        // if (dead) {
        // System.out.println("Wait. That's illegal: " + e);
        // }
        if (e.playerNum() != num) {
            throw new IllegalArgumentException(
                    "Host Event for player " + e.playerNum() + " cannot be applied to " + num);
        }
        switch (e.type()) {
            case "create" -> {
                throw new IllegalArgumentException("Cannot create already created player " + num);
            }
            case "quit" -> {
                throw new IllegalArgumentException("Player quitting event must be applied externally");
            }
            case "setname" -> {
                username = e.valueString();
            }
            case "setx" -> {
                x = e.valueDouble();
            }
            case "sety" -> {
                y = e.valueDouble();
            }
            case "setxvel" -> {
                xVel = e.valueDouble();
            }
            case "setyvel" -> {
                yVel = e.valueDouble();
            }
            case "setr" -> {
                r = e.valueDouble();
            }
            case "setrvel" -> {
                rVel = e.valueDouble();
            }
            case "setthrusting" -> {
                thrusting = e.valueString().equals("true");
            }
            case "setturning" -> {
                turning = e.valueInt();
            }
            case "die" -> {
                dead = true;
            }
            case "laser" -> {
                SubEvent ev = e.valueSub();
                if (ev.type().equals("create")) {
                    if (lasers.size() != ev.laserNum()) {
                        throw new IllegalArgumentException(
                                "Cannot add laser " + ev.laserNum() + " to laser array of size " + lasers.size());
                    }
                    lasers.add(new Laser(ev));
                    laserTime.add(laserExpiration);
                } else if (ev.type().equals("destroy")) {
                    lasers.remove(ev.laserNum());
                    laserTime.remove(ev.laserNum());
                } else {
                    lasers.get(ev.laserNum()).acceptLaserEvent(ev);
                }
            }
            case "asteroid" -> {
                SubEvent ev = e.valueSub();
                asteroid = new Asteroid(ev);
            }
            case "destroyedlaser" -> {
                SubEvent ev = e.valueSub();
                if (Integer.parseInt(ev.valueString()) == num) {
                    laserTime.set(ev.laserNum(), -1);
                }
            }
            case "powerup" -> {
                SubEvent ev = e.valueSub();
                if (ev.type().equals("create")) {
                    powerUps.add(new PowerUp(ev));
                } else if (ev.type().equals("destroy")) {
                    if (ev.valueInt() == meee.num) {
                        meee.powerUp(powerUps.get(ev.laserNum()));
                    }
                    powerUps.remove(ev.laserNum());
                } else {
                    System.err.println("WHY ARE POWERUPS LIKE THIS");
                }
            }
            case "sethealth" -> {
                health = e.valueDouble();
            }
        }
    }

    private void powerUp(PowerUp powerUp) {
        powerUpEffect = powerUp.type();
        powerUpDuration = 1000;
    }

    public synchronized void move(float[][] gas) {
        for (Laser laser : lasers) {
            laser.move();
        }

        if (asteroid != null) {
            asteroid.move();
        }
        if (powerUpDuration > 0) {
            powerUpDuration--;
        }
        if (powerUpDuration == 0) {
            powerUpEffect = null;
            powerUpDuration--;
        }
        if (dead) {
            return;
        }
        x += xVel;
        if (x > fieldWidth) {
            x -= fieldWidth;
        }
        if (x < 0) {
            x += fieldWidth;
        }
        y += yVel;
        if (y > fieldWidth) {
            y -= fieldWidth;
        }
        if (y < 0) {
            y += fieldWidth;
        }
        r += rVel;

        yVel *= 0.99;
        xVel *= 0.99;
        rVel *= 0.8;

        if (thrusting) {
            xVel += 0.2 * Math.cos(r);
            yVel += 0.2 * Math.sin(r);
        }
        if (turning != 0) {
            rVel += turning * 0.01;
        }

        if (thrusting) {
            // its time to gas
            double tailX = x - redRocket.getHeight() / 2 * Math.cos(r) * 0.9;
            double tailY = y - redRocket.getHeight() / 2 * Math.sin(r) * 0.9;

            int gasX = (int) (tailX / GamePanel.gasConstant);
            int gasY = (int) (tailY / GamePanel.gasConstant);

            gasX = (gasX + gas.length) % gas.length;
            gasY = (gasY + gas[gasX].length) % gas[gasX].length;
            gas[gasX][gasY] += 1.0f;
            gasX = (gasX - 1 + gas.length) % gas.length;
            gasY = (gasY + gas[gasX].length) % gas[gasX].length;
            gas[gasX][gasY] += 0.5f;
            gasX = (gasX + 1 + gas.length) % gas.length;
            gasY = (gasY - 1 + gas[gasX].length) % gas[gasX].length;
            gas[gasX][gasY] += 0.5f;
            gasX = (gasX + gas.length) % gas.length;
            gasY = (gasY + 2 + gas[gasX].length) % gas[gasX].length;
            gas[gasX][gasY] += 0.5f;
            gasX = (gasX + 1 + gas.length) % gas.length;
            gasY = (gasY - 1 + gas[gasX].length) % gas[gasX].length;
            gas[gasX][gasY] += 0.5f;
            gasX = (gasX + gas.length) % gas.length;
            gasY = (gasY - 1 + gas[gasX].length) % gas[gasX].length;
            gas[gasX][gasY] += 0.25f;
            gasX = (gasX - 2 + gas.length) % gas.length;
            gasY = (gasY + gas[gasX].length) % gas[gasX].length;
            gas[gasX][gasY] += 0.25f;
            gasX = (gasX + gas.length) % gas.length;
            gasY = (gasY + 2 + gas[gasX].length) % gas[gasX].length;
            gas[gasX][gasY] += 0.25f;
            gasX = (gasX + 2 + gas.length) % gas.length;
            gasY = (gasY + gas[gasX].length) % gas[gasX].length;
            gas[gasX][gasY] += 0.25f;
        }
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public boolean draw(Graphics2D g, double cX, double cY, int screenWidth, int screenHeight, boolean meee) {
        if (asteroid != null) {
            asteroid.draw(g, cX, cY, screenWidth, screenHeight);
        }

        for (PowerUp p : powerUps) {
            p.draw(g, cX, cY, screenWidth, screenHeight);
        }

        if (dead) {
            return false;
        }
        // max screen size is 2000 x 2000 - scale should make whatever is rendered match
        // that - e.g. if actual screen is 1000x1000, scale is 1/2
        // 2000x1000 or 1000x2000, scale is 1
        int maxDim = screenHeight > screenWidth ? screenHeight : screenWidth;
        double scale = maxDim / 2000.0;
        cX %= fieldWidth;
        cY %= fieldHeight;

        if (cX < x - fieldWidth / 2) {
            cX += fieldWidth;
        }
        if (cX > x + fieldWidth / 2) {
            cX -= fieldWidth;
        }
        if (cY < y - fieldHeight / 2) {
            cY += fieldHeight;
        }
        if (cY > y + fieldHeight / 2) {
            cY -= fieldHeight;
        }

        // username
        AffineTransform tx = g.getTransform();
        g.translate(-cX * scale, -cY * scale);
        g.translate(x * scale + screenWidth / 2, y * scale + screenHeight / 2);
        g.setColor(meee ? new Color(150, 200, 200) : new Color(200, 150, 150));
        g.setFont(usernameFont);
        FontMetrics metrics = g.getFontMetrics(usernameFont);
        int xOffs = metrics.stringWidth(username) / 2;
        g.drawString(username, -xOffs, -redRocket.getHeight() / 2 - 15);

        // healthbar
        if (health < 1.0) {
            g.setColor(Color.GREEN);
            g.fillRect(-50, -redRocket.getHeight() / 2 - 5, 100, 10);
            g.setColor(Color.RED);
            g.fillRect(-50 + (int) (100 * health), -redRocket.getHeight() / 2 - 5, 100 - (int) (100 * health), 10);
        }

        g.scale(scale, scale);
        g.rotate(r + Math.PI / 2);
        if (meee) {
            g.drawImage(blueRocket, -redRocket.getWidth() / 2, -redRocket.getHeight() / 2, null);
        } else {
            g.drawImage(redRocket, -redRocket.getWidth() / 2, -redRocket.getHeight() / 2, null);
        }
        g.setTransform(tx);

        for (Laser l : lasers) {
            l.draw(g, cX, cY, screenWidth, screenHeight);
        }

        return true;
    }

    public void setThrust(boolean thrust) {
        thrusting = thrust;
    }

    public void setTurn(int turn) {
        turning = turn;
    }

    public int getTurn() {
        return turning;
    }

    public void sendMessages(HostManager m) {
        for (int i = 0; i < lasers.size(); i++) {
            if (lasers.size() - i <= sentLastLaser) {
                m.sendEvent(new ClientEvent("laser", new SubEvent(lasers.get(i), i) + ""), false);
                sentLastLaser--;
            } else {
                lasers.get(i).sendMessages(m, i);
                if (laserTime.get(i) < 0) {
                    m.sendEvent(new ClientEvent("laser", new SubEvent("destroy", i, "nullius") + ""), false);
                    lasers.remove(i);
                    laserTime.remove(i);
                    i--;
                } else {
                    laserTime.set(i, laserTime.get(i) - 1);
                }
            }
        }

        for (ClientEvent e : eventQueue) {
            m.sendEvent(e, false);
        }

        if (sentAsteroid == false) {
            m.sendEvent(new ClientEvent("asteroid", new SubEvent(asteroid) + ""), false);
        }
        asteroid.sendEvents(m);
        synchronized (powerUpsToSend) {
            for (PowerUp p : powerUpsToSend) {
                m.sendEvent(new ClientEvent("powerup", new SubEvent(p) + ""), false);
            }
            powerUpsToSend.clear();
        }
        if (dead && sentDead) {
            return;
        }
        if (Math.abs(x - lastSentX) > 5) {
            m.sendEvent(new ClientEvent("setx", x), false);
            lastSentX = x;
        }
        if (Math.abs(y - lastSentY) > 5) {
            m.sendEvent(new ClientEvent("sety", y), false);
            lastSentY = y;
        }
        if (Math.abs(xVel - lastSentXVel) > 0.1) {
            m.sendEvent(new ClientEvent("setxvel", xVel), false);
            lastSentXVel = xVel;
        }
        if (Math.abs(yVel - lastSentYVel) > 0.1) {
            m.sendEvent(new ClientEvent("setyvel", yVel), false);
            lastSentYVel = yVel;
        }
        if (Math.abs(r - lastSentR) > 0.1) {
            m.sendEvent(new ClientEvent("setr", r), false);
            lastSentR = r;
        }
        if (Math.abs(rVel - lastSentRVel) > 0.05) {
            m.sendEvent(new ClientEvent("setrvel", rVel), false);
            lastSentRVel = rVel;
        }
        if (thrusting != lastSentThrusting) {
            m.sendEvent(new ClientEvent("setthrusting", "" + thrusting), false);
            lastSentThrusting = thrusting;
        }
        if (turning != lastSentTurning) {
            m.sendEvent(new ClientEvent("setturning", "" + turning), false);
            lastSentTurning = turning;
        }
        if (dead != sentDead) {
            m.sendEvent(new ClientEvent("die", "" + dead), false);
            sentDead = dead;
        }
        if (health != lastSentHealth) {
            m.sendEvent(new ClientEvent("sethealth", health), false);
            lastSentHealth = health;
        }
    }

    public void addLaser() {
        double laserX = x + (redRocket.getHeight() + Laser.laserImage.getHeight()) / 2 * Math.cos(r);
        double laserY = y + (redRocket.getHeight() + Laser.laserImage.getHeight()) / 2 * Math.sin(r);
        double laserR = r;
        double laserXVel = xVel + 50 * Math.cos(r);
        double laserYVel = yVel + 50 * Math.sin(r);
        lasers.add(new Laser(laserX, laserY, laserR, laserXVel, laserYVel));
        laserTime.add(laserExpiration);
        sentLastLaser++;
        GamePanel.shootLaserSound();
        if (powerUpEffect == PowerUpType.BURST) {
            // more lasers - separation at 15 deg (Math.PI/)
            laserX = x + (redRocket.getHeight() + Laser.laserImage.getHeight()) / 2 * Math.cos(r + Math.PI / 12);
            laserY = y + (redRocket.getHeight() + Laser.laserImage.getHeight()) / 2 * Math.sin(r + Math.PI / 12);
            laserR = r + Math.PI / 12;
            laserXVel = xVel + 50 * Math.cos(r + Math.PI / 12);
            laserYVel = yVel + 50 * Math.sin(r + Math.PI / 12);
            lasers.add(new Laser(laserX, laserY, laserR, laserXVel, laserYVel));
            laserTime.add(laserExpiration);
            sentLastLaser++;

            laserX = x + (redRocket.getHeight() + Laser.laserImage.getHeight()) / 2 * Math.cos(r - Math.PI / 12);
            laserY = y + (redRocket.getHeight() + Laser.laserImage.getHeight()) / 2 * Math.sin(r - Math.PI / 12);
            laserR = r - Math.PI / 12;
            laserXVel = xVel + 50 * Math.cos(r - Math.PI / 12);
            laserYVel = yVel + 50 * Math.sin(r - Math.PI / 12);
            lasers.add(new Laser(laserX, laserY, laserR, laserXVel, laserYVel));
            laserTime.add(laserExpiration);
            sentLastLaser++;
        }
    }

    public void resetAsteroid() {
        PowerUp p = asteroid.generatePowerUp();
        if (p != null) {
            powerUps.add(p);
            powerUpsToSend.add(p);
        }
        asteroid = new Asteroid();
        sentAsteroid = false;
    }

    public void takeDamage(double damage) {
        health -= damage;
        if (health < 0.0) {
            health = 0.0;
            die();
        }
    }

    public void die() {
        dead = true;
        GamePanel.blowUpSound();
    }

    public boolean dead() {
        return dead;
    }

    public boolean lasered(Player p) {
        boolean lasered = false;
        for (int i = 0; i < lasers.size(); i++) {
            if ((!destroyedLasers.contains(lasers.get(i))) && lasers.get(i).collidesWith(p)) {
                lasered = true;
                if (p == this) {
                    laserTime.set(i, -1);
                } else {
                    p.eventQueue
                            .add(new ClientEvent("destroyedlaser", new SubEvent("laser", i, "" + num) + ""));
                }
                destroyedLasers.add(lasers.get(i));
            }
        }
        return lasered;
    }

    public boolean asteroidLasered(Player p) {
        boolean lasered = false;
        for (int i = 0; i < lasers.size(); i++) {
            if ((!destroyedLasers.contains(lasers.get(i))) && lasers.get(i).collidesWith(p.asteroid)) {
                lasered = true;
                if (p == this) {
                    laserTime.set(i, -1);
                } else {
                    p.eventQueue
                            .add(new ClientEvent("destroyedlaser", new SubEvent("laser", i, "" + num) + ""));
                }
                destroyedLasers.add(lasers.get(i));
                i--;
            }
        }
        return lasered;
    }

    public void takeAsteroidScaledDamage(double damage) {
        asteroid.takeScaledDamage(damage);
        if (asteroid.health() < 0) {
            GamePanel.blowUpSound();
            resetAsteroid();
        }
    }

    public void checkPowerUpCollision(Player p) {
        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp pow = powerUps.get(i);
            if (pow.collidesWith(p)) {
                eventQueue.add(new ClientEvent("powerup", "" + new SubEvent("destroy", i, "" + p.num)));
                powerUps.remove(i);
                if (p == this) {
                    powerUp(pow);
                }
                i--;
            }
        }
    }

    public PowerUpType powerUp() {
        return powerUpEffect;
    }
}
