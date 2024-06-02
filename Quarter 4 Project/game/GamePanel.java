package game;

import java.util.Optional;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Clip;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.LineUnavailableException;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;

import gui.Frame;
import gui.Panel;
import utils.ArrayList;
import comms.ClientEvent;
import comms.HostEvent;
import game.PowerUp.PowerUpType;

public class GamePanel extends Panel implements Runnable, KeyListener {
    // sounds
    private static final int soundBufferSize = 4096;

    private static Clip laserClip;
    private static Clip blowUpClip;

    static {
        InputStream laserInputStream = GamePanel.class.getClassLoader().getResourceAsStream("resources/laser.wav");
        InputStream blowUpInputStream = GamePanel.class.getClassLoader().getResourceAsStream("resources/blowUp.wav");
        try {
            AudioInputStream laserAudioInputStream = AudioSystem.getAudioInputStream(laserInputStream);
            AudioInputStream blowUpAudioInputStream = AudioSystem.getAudioInputStream(blowUpInputStream);
            laserClip = AudioSystem.getClip();
            laserClip.open(laserAudioInputStream);
            laserClip.start();
            blowUpClip = AudioSystem.getClip();
            blowUpClip.open(blowUpAudioInputStream);
            blowUpClip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void shootLaserSound() {
        laserClip.loop(1);
    }

    public static void blowUpSound() {
        blowUpClip.loop(1);
    }

    public static final int gasConstant = 10;

    private Frame parentFrame;
    private boolean active;
    private Player meee;
    private Player camFollowee;
    private double camX;
    private double camY;
    private boolean focused = false;
    private int[] starPositionXs;
    private int[] starPositionYs;
    private float[][] gas;
    private int cooldownTime = 0;
    private boolean firing = false;
    private Player winner = null;

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
                if (gas[i][j] < 1.0f) {
                    newGas[i][j] = gas[i][j] * 0.8f
                            + gas[(i - 1 + gas.length) % gas.length][j] * 0.048f
                            + gas[(i + 1) % gas.length][j] * 0.048f
                            + gas[i][(j - 1 + gas[i].length) % gas[i].length] * 0.048f
                            + gas[i][(j + 1) % gas[i].length] * 0.048f;
                } else {
                    newGas[i][j] = Math.max(0.99f, gas[i][j] * 0.75f);
                }
            }
        }
        // newGas[0][0] = 10.0f;

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
            Player.meee = meee;
            (new Thread(this)).start();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (camFollowee == null)
            camFollowee = meee;
        Graphics2D g2d = (Graphics2D) g;

        int s = getWidth() > getHeight() ? getWidth() : getHeight();
        double scale = s / 2000.0;

        AffineTransform tx = g2d.getTransform();
        g2d.setColor(new Color(26, 26, 26));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.scale(scale, scale);
        g2d.setColor(Color.WHITE);
        for (int i = 0; i < starPositionXs.length; i++) {
            g2d.fillRect(
                    (starPositionXs[i] + (int) camX - (int) camFollowee.x() + Player.fieldWidth) % Player.fieldWidth,
                    (starPositionYs[i] + (int) camY - (int) camFollowee.y() + Player.fieldHeight) % Player.fieldHeight,
                    4, 4);
        }

        // behold: the gas code of beauty
        int cX = (int) camX;
        int cY = (int) camY;
        int w = (int) (getWidth() / scale);
        int h = (int) (getHeight() / scale);
        for (int i = (cX - w / 2) / gasConstant
                - 1; i < (cX + w / 2) / gasConstant + 1; i++) {
            for (int j = (cY - h / 2) / gasConstant
                    - 1; j < (cY + h / 2) / gasConstant + 1; j++) {
                int iA = (i + gas.length) % gas.length;
                int jA = (j + gas[iA].length) % gas[iA].length;
                if (gas[iA][jA] > 0.05) {
                    float val = gas[iA][jA] > 0.95f ? 0.95f : gas[iA][jA];
                    g2d.setColor(new Color(1.0f - val, 1.0f, 1.0f, val));
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
        camFollowee = meee;
        // updateGas's very own thread
        (new Thread(() -> {
            while (active) {
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
            if (Math.abs(camX - camFollowee.x()) > Player.fieldWidth / 2) {
                if (camX > camFollowee.x()) {
                    camX -= Player.fieldWidth;
                } else {
                    camX += Player.fieldWidth;
                }
            }
            if (Math.abs(camY - camFollowee.y()) > Player.fieldHeight / 2) {
                if (camY > camFollowee.y()) {
                    camY -= Player.fieldHeight;
                } else {
                    camY += Player.fieldHeight;
                }
            }
            camX = camX * 0.85 + camFollowee.x() * 0.15;
            camY = camY * 0.85 + camFollowee.y() * 0.15;

            if (cooldownTime > 0) {
                cooldownTime--;
            }
            if (firing && cooldownTime == 0 && !meee.dead()) {
                cooldownTime = meee.powerUp() == PowerUpType.RAPIDFIRE ? 5 : 30;
                meee.addLaser();
            }

            // collision detection
            playerCollision: for (Optional<Player> p : players()) {
                if (p.isPresent()) {
                    if (p.get().lasered(meee)) {
                        meee.takeDamage(0.1);
                        if (meee.dead()) {
                            camFollowee = p.get();
                            break playerCollision;
                        }
                    }
                }
            }

            // asteroid collision
            for (Optional<Player> p : players()) {
                if (p.isPresent()) {
                    if (p.get().asteroidLasered(meee)) {
                        meee.takeAsteroidScaledDamage(0.1);
                    }
                } else {
                    if (meee.asteroidLasered(meee)) {
                        meee.takeAsteroidScaledDamage(0.1);
                    }
                }
            }

            // powerup collision
            for (Optional<Player> p : players()) {
                if (p.isPresent()) {
                    meee.checkPowerUpCollision(p.get());
                } else {
                    meee.checkPowerUpCollision(meee);
                }
            }

            // detect winner
            int living = 0;
            for (Optional<Player> p : players()) {
                if (p.isPresent() && !p.get().dead()) {
                    living++;
                } else if (p.isEmpty() && !meee.dead()) {
                    living++;
                }
            }
            if (living <= 1) {
                // WE HAVE A WINNER
                for (Optional<Player> p : players()) {
                    if (p.isPresent() && !p.get().dead()) {
                        winner = p.get();
                    } else if (p.isEmpty() && !meee.dead()) {
                        winner = meee;
                        parentFrame.hostManager().sendEvent(new ClientEvent("WINNER", "nullius"), true);
                    }
                }
                parentFrame.setHostManager(null);
                parentFrame.showPanel("Endgame");
            }
        }
    }

    public Player winner() {
        return winner;
    }

    @Override
    public void keyTyped(KeyEvent e) {
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
            case KeyEvent.VK_Z -> {
                firing = true;
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
                if (meee.getTurn() == -1)
                    meee.setTurn(0);
            }
            case KeyEvent.VK_RIGHT -> {
                if (meee.getTurn() == 1)
                    meee.setTurn(0);
            }
            case KeyEvent.VK_Z -> {
                firing = false;
            }
        }
    }
}
