import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JPanel;

public class Screen extends JPanel implements KeyListener {
    private Player player;

    public Screen() {
        player = readPlayer();

        setFocusable(true);
        addKeyListener(this);
    }

    private static Player readPlayer() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("player.dat"));
            Player toRet = (Player)in.readObject();
            in.close();
            return toRet;
        } catch (FileNotFoundException e) {
            return new Player(400, 400);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void writePlayer(Player p) {
         try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("player.dat"));

            out.writeObject(p);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 800);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(0, 0, getWidth(), getHeight());
        player.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w' -> {
                player.moveUp();
            }
            case 'a' -> {
                player.moveLeft();
            }
            case 's' -> {
                player.moveDown();
            }
            case 'd' -> {
                player.moveRight();
            }
            default -> {
                return;
            }
        }
        writePlayer(player);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
