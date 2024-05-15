package game;

import java.util.Optional;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.geom.AffineTransform;

import gui.Frame;
import gui.Panel;
import utils.ArrayList;
import comms.HostEvent;

public class GamePanel extends Panel implements Runnable, KeyListener {
    public static final int gasConstant = 10;
    private Frame parentFrame;
    private boolean active;
    private Player meee;
    private double camX;
    private double camY;
    private boolean focused = false;
    private int[] starPositionXs;
    private int[] starPositionYs;
    private float[][] gas;

    public GamePanel(Frame f) {
        parentFrame = f;
        addKeyListener(this);
        setFocusable(true);
        starPositionXs = new int[10000];
        starPositionYs = new int[starPositionXs.length];
        for (int i = 0; i < starPositionXs.length; i++) {
            starPositionXs[i] = (int) (Math.random() * Player.fieldWidth);
            starPositionYs[i] = (int) (Math.random() * Player.fieldHeight);
        }
        gas = new float[Player.fieldWidth / gasConstant][Player.fieldHeight / gasConstant];
    }

    private void updateGas() {
        float[][] newGas = new float[gas.length][gas[0].length];
        for (int i = 0; i < newGas.length; i++) {
            for (int j = 0; j < newGas[i].length; j++) {
                newGas[i][j] = gas[i][j] * 0.87f
                        + gas[(i - 1 + gas.length) % gas.length][j] * 0.03f
                        + gas[(i + 1) % gas.length][j] * 0.03f
                        + gas[i][(j - 1 + gas[i].length) % gas[i].length] * 0.03f
                        + gas[i][(j + 1) % gas[i].length] * 0.03f;
            }
        }

        gas = newGas;
    }

    private ArrayList<Optional<Player>> players() {
        return parentFrame.hostManager().players();
    }

    @Override
    public void setActive(boolean a) {
        active = a;
        if (a == true) {
            meee = new Player(new HostEvent("create", players().indexOf(Optional.empty()), "you"));
            (new Thread(this)).start();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int s = getWidth() > getHeight() ? getWidth() : getHeight();
        double scale = s / 2000.0;

        AffineTransform tx = g2d.getTransform();
        g2d.setColor(new Color(26, 26, 26));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.scale(scale, scale);
        g2d.setColor(Color.WHITE);
        for (int i = 0; i < starPositionXs.length; i++) {
            g2d.fillRect((starPositionXs[i] + (int) camX - (int)meee.x() + Player.fieldWidth) % Player.fieldWidth,
                    (starPositionYs[i] + (int) camY - (int)meee.y() + Player.fieldHeight) % Player.fieldHeight, 4, 4);
        }

        int cX = (int)camX;
        int cY = (int)camY;
        int w = (int)(getWidth() / scale);
        int h = (int)(getHeight() / scale);        
        for (int i = (cX - w / 2) / gasConstant
                - 1; i < (cX + w / 2) / gasConstant + 1; i++) {
            for (int j = (cY - h / 2) / gasConstant
                    - 1; j < (cY + h / 2) / gasConstant + 1; j++) {
                int iA = (i + gas.length) % gas.length;
                int jA = (j + gas[iA].length) % gas[iA].length;
                if (gas[iA][jA] > 0.05) {
                    g2d.setColor(new Color(1.0f - gas[iA][jA], 1.0f, 1.0f, gas[iA][jA]));
                    g2d.fillRect(i * gasConstant - cX + w / 2,
                            j * gasConstant - cY + h / 2, gasConstant, gasConstant);
                }
            }
        }

        g2d.setTransform(tx);

        for (Optional<Player> p : players()) {
            if (p.isPresent()) {
                p.get().draw(g2d, camX, camY, getWidth(), getHeight(), false);
            }
        }
        meee.draw(g2d, camX, camY, getWidth(), getHeight(), true);
    }

    @Override
    public void run() {
        //updateGas's very own thread
        (new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateGas();
            }
        })).start();

        long lastLoop = System.currentTimeMillis();
        int frameDelay = 1000 / 60;
        // framerate is 60fps (gaming)
        while (active) {
            try {
                int remainingDelay = (int) (frameDelay - (System.currentTimeMillis() - lastLoop));
                if (remainingDelay > 0) {
                    Thread.sleep(remainingDelay);
                } else {
                    System.out.println("LAGG");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lastLoop = System.currentTimeMillis();
            repaint();

            if (!focused)
                requestFocus();

            for (Optional<Player> p : players()) {
                if (p.isPresent()) {
                    p.get().move(gas);
                }
            }
            meee.move(gas);
            meee.sendMessages(parentFrame.hostManager());
            parentFrame.hostManager().flush();

            // lerp the camera to the player at 0.85 lerpiness
            if (Math.abs(camX - meee.x()) > Player.fieldWidth / 2) {
                if (camX > meee.x()) {
                    camX -= Player.fieldWidth;
                } else {
                    camX += Player.fieldWidth;
                }
            }
            if (Math.abs(camY - meee.y()) > Player.fieldHeight / 2) {
                if (camY > meee.y()) {
                    camY -= Player.fieldHeight;
                } else {
                    camY += Player.fieldHeight;
                }
            }
            camX = camX * 0.85 + meee.x() * 0.15;
            camY = camY * 0.85 + meee.y() * 0.15;

            // updateGas();
            //updateGas needs its own fricking thread bc its so slow and i don't want to figure out how to optimize it
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // What is even the point of this method
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                meee.setThrust(true);
            }
            case KeyEvent.VK_LEFT -> {
                meee.setTurn(-1);
            }
            case KeyEvent.VK_RIGHT -> {
                meee.setTurn(1);
            }
        }
        focused = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                meee.setThrust(false);
            }
            case KeyEvent.VK_LEFT -> {
                if(meee.getTurn() == -1)
                    meee.setTurn(0);
                }
                case KeyEvent.VK_RIGHT -> {
                if(meee.getTurn() == 1)
                    meee.setTurn(0);
            }
        }
    }
}
