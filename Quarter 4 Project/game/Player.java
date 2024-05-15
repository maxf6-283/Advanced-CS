package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import java.awt.geom.AffineTransform;

import comms.ClientEvent;
import comms.HostEvent;
import comms.HostManager;

public class Player {
    public static final int fieldWidth = 5000;
    public static final int fieldHeight = 5000;

    public static final BufferedImage redRocket;
    public static final BufferedImage blueRocket;

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
        }
    }

    public synchronized void move(float[][] gas) {
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
            gas[gasX][gasY] = 1.0f;
            gasX = (gasX - 1 + gas.length) % gas.length;
            gasY = (gasY + gas[gasX].length) % gas[gasX].length;
            gas[gasX][gasY] = 1.0f;
            gasX = (gasX + 1 + gas.length) % gas.length;
            gasY = (gasY - 1 + gas[gasX].length) % gas[gasX].length;
            gas[gasX][gasY] = 1.0f;
            gasX = (gasX + gas.length) % gas.length;
            gasY = (gasY + 2 + gas[gasX].length) % gas[gasX].length;
            gas[gasX][gasY] = 1.0f;
            gasX = (gasX + 1 + gas.length) % gas.length;
            gasY = (gasY - 1 + gas[gasX].length) % gas[gasX].length;
            gas[gasX][gasY] = 1.0f;
        }
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public boolean draw(Graphics2D g, double cX, double cY, int screenWidth, int screenHeight, boolean meee) {
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

        AffineTransform tx = g.getTransform();
        g.translate(-cX * scale, -cY * scale);
        g.translate(x * scale + screenWidth / 2, y * scale + screenHeight / 2);
        g.scale(scale, scale);
        g.rotate(r + Math.PI / 2);
        if (meee) {
            g.drawImage(blueRocket, -redRocket.getWidth() / 2, -redRocket.getHeight() / 2, null);
        } else {
            g.drawImage(redRocket, -redRocket.getWidth() / 2, -redRocket.getHeight() / 2, null);
        }
        g.setTransform(tx);

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
    }
}
