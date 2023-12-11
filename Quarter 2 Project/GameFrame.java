import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

public class GameFrame extends JPanel {
    private static final int rows = 200, cols = 200;
    private HashTable<Square, TileObject> tileMap;
    private int keyFrame;
    private double centerX, centerY, zoomAmt;

    // these are references to things that are used elsewhere
    private HashMap<Integer, Boolean> keysPressed;
    private Queue<MouseWheelEvent> scrollQueue;
    private Queue<Click> clickQueue;
    private Square lastUsedSquare = new Square(Integer.MAX_VALUE, Integer.MAX_VALUE);
    private int mouseXPos = Integer.MAX_VALUE;
    private int mouseYPos = Integer.MAX_VALUE;

    private Square startPos;
    private Player player;
    private boolean playing;
    private DLList<WanderingObject> wanderingObjects;

    public GameFrame(HashMap<Integer, Boolean> keysPressed, Queue<Click> clickQueue,
            Queue<MouseWheelEvent> scrollQueue) {
        setLayout(null);
        this.keysPressed = keysPressed;
        this.scrollQueue = scrollQueue;
        this.clickQueue = clickQueue;
        keyFrame = 0;
        tileMap = new HashTable<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tileMap.put(new Square(i, j), TileObject.WATER);
            }
        }
        centerX = rows * 25 / 2;
        centerY = cols * 25 / 2;
        zoomAmt = 1.0;

        wanderingObjects = new DLList<>();

        new Thread(new GameAnimator(this, 60)).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getWidth());
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform prevTrans = g2d.getTransform();
        g2d.translate(getWidth() / 2, getHeight() / 2);
        g2d.scale(zoomAmt, zoomAmt);
        if (playing) {
            g2d.translate(-player.x(), -player.y());
        } else {
            g2d.translate(-centerX, -centerY);
        }

        startPos = null;
        double x = playing ? player.x() : centerX;
        double y = playing ? player.y() : centerY;
        int leftmostTilePos = (int) (((x - getWidth() / 2 / zoomAmt) / 25)) - 1;
        int rightmostTilePos = (int) (((x + getWidth() / 2 / zoomAmt) / 25)) + 1;
        int topmostTilePos = (int) (((y - getHeight() / 2 / zoomAmt) / 25)) - 1;
        int bottommostTilePos = (int) (((y + getHeight() / 2 / zoomAmt) / 25)) + 1;
        

        for (Square s : tileMap.keySet()) {
            for (TileObject t : tileMap.get(s)) {
                if (s.x() > leftmostTilePos && s.x() < rightmostTilePos && s.y() > topmostTilePos
                        && s.y() < bottommostTilePos) {
                    t.paint(s.x() * 25, s.y() * 25, keyFrame + s.y()*10*7, g);
                }
                if (t == TileObject.START) {
                    startPos = s;
                }
            }
        }

        if (playing) {
            player.paint(g2d);
            for(WanderingObject w : wanderingObjects) {
                w.paint(g2d);
                w.move();
            }
        } else {
            double mouseX = (mouseXPos - getWidth() / 2) / zoomAmt + centerX;
            double mouseY = (mouseYPos - getHeight() / 2) / zoomAmt + centerY;
            g2d.setColor(new Color(255, 255, 255, 100));
            g2d.fillRect((int) mouseX / 25 * 25, (int) mouseY / 25 * 25, 25, 25);
        }

        g2d.setTransform(prevTrans);
    }

    public void update() {
        keyFrame++;
        boolean fast = keysPressed.getOrDefault(16, false);
        if (playing) {
            if (keysPressed.getOrDefault(87, false)) {
                player.moveY(fast ? -4.0 : -2.0);
            }
            if (keysPressed.getOrDefault(65, false)) {
                player.moveX(fast ? -4.0 : -2.0);
            }
            if (keysPressed.getOrDefault(83, false)) {
                player.moveY(fast ? 4.0 : 2.0);
            }
            if (keysPressed.getOrDefault(68, false)) {
                player.moveX(fast ? 4.0 : 2.0);
            }

            while (!scrollQueue.isEmpty()) {
                double newZoomAmt = zoomAmt * (1 - (scrollQueue.peek().getPreciseWheelRotation() / 10.0));
                if (newZoomAmt < 0.5) {
                    newZoomAmt = 0.5;
                }
                if (newZoomAmt > 10) {
                    newZoomAmt = 10;
                }
                zoomAmt = newZoomAmt;
                scrollQueue.pop();
            }

            clickQueue.clear();

        } else {
            if (keysPressed.getOrDefault(87, false)) {
                centerY -= 3.0 / zoomAmt * (fast ? 4.0 : 1.0);
            }
            if (keysPressed.getOrDefault(65, false)) {
                centerX -= 3.0 / zoomAmt * (fast ? 4.0 : 1.0);
            }
            if (keysPressed.getOrDefault(83, false)) {
                centerY += 3.0 / zoomAmt * (fast ? 4.0 : 1.0);
            }
            if (keysPressed.getOrDefault(68, false)) {
                centerX += 3.0 / zoomAmt * (fast ? 4.0 : 1.0);
            }
            while (!scrollQueue.isEmpty()) {
                double newZoomAmt = zoomAmt * (1 - (scrollQueue.peek().getPreciseWheelRotation() / 10.0));
                if (newZoomAmt < 0.5) {
                    newZoomAmt = 0.5;
                }
                if (newZoomAmt > 10) {
                    newZoomAmt = 10;
                }
                double mouseX = (scrollQueue.peek().getX() - getWidth() / 2) / zoomAmt + centerX;
                double mouseY = (scrollQueue.peek().getY() - getHeight() / 2) / zoomAmt + centerY;
                double newMouseX = (scrollQueue.peek().getX() - getWidth() / 2) / newZoomAmt + centerX;
                double newMouseY = (scrollQueue.peek().getY() - getHeight() / 2) / newZoomAmt + centerY;
                centerX += mouseX - newMouseX;
                centerY += mouseY - newMouseY;
                zoomAmt = newZoomAmt;
                scrollQueue.pop();
            }

            while (!clickQueue.isEmpty()) {
                double mouseX = (clickQueue.peek().x() - getWidth() / 2) / zoomAmt + centerX;
                double mouseY = (clickQueue.peek().y() - getHeight() / 2) / zoomAmt + centerY;
                Square sq = new Square((int) mouseX / 25, (int) mouseY / 25);
                if (clickQueue.peek().isDragged() && sq.equals(lastUsedSquare)) {
                    clickQueue.pop();
                    continue;
                }

                switch (clickQueue.peek().type()) {
                    case "Delete" -> {
                        tileMap.removeLast(sq);
                    }
                    case "Grass" -> {
                        addBgTile(sq, TileObject.GRASS);
                    }
                    case "Water" -> {
                        addBgTile(sq, TileObject.WATER);
                    }
                    case "Dirt" -> {
                        addBgTile(sq, TileObject.DIRT);
                    }
                    case "Stone" -> {
                        addBgTile(sq, TileObject.STONE);
                    }
                    case "Lava" -> {
                        addBgTile(sq, TileObject.LAVA);
                    }
                    case "Tree" -> {
                        addFgTile(sq, TileObject.TREE);
                    }
                    case "Rock" -> {
                        addFgTile(sq, TileObject.ROCK);
                    }
                    case "Hole" -> {
                        addFgTile(sq, TileObject.HOLE);
                    }
                    case "Start" -> {
                        addFgTile(sq, TileObject.START);
                    }
                    default -> {
                        throw new IllegalArgumentException("Unexpected click type: " + clickQueue.peek().type());
                    }
                }
                lastUsedSquare = sq;
                clickQueue.pop();
            }
        }

        repaint();
    }

    private void addBgTile(Square sq, TileObject t) {
        if (tileMap.containsKey(sq)) {
            if (tileMap.get(sq).get(0).isBackground()) {
                tileMap.get(sq).set(0, t);
            } else {
                tileMap.get(sq).add(0, t);
            }
        } else {
            tileMap.put(sq, t);
        }
    }

    private void addFgTile(Square sq, TileObject t) {
        if (tileMap.containsKey(sq)) {
            if (tileMap.get(sq).contains(t)) {
                return;
            } else {
                tileMap.put(sq, t);
            }
        } else {
            tileMap.put(sq, t);
        }
    }

    public void loadNewMap(HashTable<Square, TileObject> map) {
        tileMap = map;
        // centerX = rows * 25 / 2;
        // centerY = cols * 25 / 2;
        // zoomAmt = 1.0;
        // keyFrame = 0;
    }

    public HashTable<Square, TileObject> getTileMap() {
        return tileMap;
    }

    public void updateMousePos(int x, int y) {
        mouseXPos = x;
        mouseYPos = y;
    }

    public void start() {
        player = new Player(startPos.x() * 25, startPos.y() * 25, tileMap);
        for(Square s : tileMap.keySet()) {
            if(tileMap.get(s).get(0) == TileObject.WATER) {
                if(Math.random() < 0.01) {
                    wanderingObjects.add(new Fish(s.x() * 25, s.y() * 25, tileMap));
                }
            }
        }
        playing = true;
    }

    public void stop() {
        playing = false;
    }

    public boolean hasStartPos() {
        return startPos != null;
    }
}
