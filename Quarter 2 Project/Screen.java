import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;

public class Screen extends JFrame implements KeyListener, MouseInputListener, MouseWheelListener, ActionListener {
    private GameFrame gameFrame;
    private JButton save;
    private JButton load;
    private JButton play;
    private JComboBox<String> tileSelector;
    private HashMap<Integer, Boolean> keysPressed;
    private Queue<Click> clickQueue;
    private Queue<MouseWheelEvent> scrollQueue;
    private Prompt prompt;
    private boolean playing = false;

    public Screen() {
        super("Map Editor");
        setLayout(null);
        setSize(800, 600);
        keysPressed = new HashMap<>();
        clickQueue = new Queue<>();
        scrollQueue = new Queue<>();
        gameFrame = new GameFrame(keysPressed, clickQueue, scrollQueue);
        save = new JButton("Save");
        load = new JButton("Load");
        play = new JButton("Play");
        prompt = new Prompt(this);
        tileSelector = new JComboBox<>(new String[] {
                "Grass",
                "Dirt",
                "Stone",
                "Water",
                "Lava",
                "Rock",
                "Tree",
                "Hole",
                "Start",
                "Delete"
        });

        arrangeComponents();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                arrangeComponents();
            }
        });

        add(gameFrame);
        gameFrame.add(save);
        gameFrame.add(load);
        gameFrame.add(play);
        gameFrame.add(tileSelector);
        save.addActionListener(this);
        load.addActionListener(this);
        play.addActionListener(this);
        tileSelector.addActionListener(this);
        save.setFocusable(false);
        load.setFocusable(false);
        play.setFocusable(false);
        tileSelector.setFocusable(false);
        addKeyListener(this);
        gameFrame.addMouseListener(this);
        gameFrame.addMouseMotionListener(this);
        gameFrame.addMouseWheelListener(this);
        setFocusable(true);
    }

    private void arrangeComponents() {
        gameFrame.setBounds(0, 0, getWidth(), getHeight());
        save.setBounds(getWidth() - 100, 25, 75, 25);
        load.setBounds(getWidth() - 100, 63, 75, 25);
        play.setBounds(getWidth() - 100, 100, 75, 25);
        tileSelector.setBounds(getWidth() - 100, 138, 75, 25);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            prompt.saveFile();
        } else if (e.getSource() == load) {
            prompt.loadFile();
        } else if (e.getSource() == play) {
            if (playing) {
                playing = false;
                save.setEnabled(true);
                load.setEnabled(true);
                tileSelector.setEnabled(true);
                play.setText("Play");
                gameFrame.stop();
            } else if (gameFrame.hasStartPos()) {
                playing = true;
                save.setEnabled(false);
                load.setEnabled(false);
                tileSelector.setEnabled(false);
                play.setText("Stop");
                gameFrame.start();
            } else {
                prompt.error("No Start Position");
            }
        } else if (e.getSource() == tileSelector) {

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {
            clickQueue.push(new Click(e.getX(), e.getY(), tileSelector.getSelectedItem().toString(), false));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == 1 || clickQueue.lastAdded().isDragged()) {
            gameFrame.finishChangeEvent();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed.put(e.getKeyCode(), true);
        if (keysPressed.getOrDefault(17, false)) {
            if (e.getKeyCode() == 90) {
                if (keysPressed.getOrDefault(16, false)) {
                    gameFrame.redo();
                } else {
                    gameFrame.undo();
                }
            }
        }
        if (e.getKeyCode() == 27) {
            gameFrame.cancelChangeEvent();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed.put(e.getKeyCode(), false);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scrollQueue.push(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        clickQueue.push(new Click(e.getX(), e.getY(), tileSelector.getSelectedItem().toString(), true));
        updateMousePos(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        updateMousePos(e.getX(), e.getY());
    }

    private void updateMousePos(int x, int y) {
        gameFrame.updateMousePos(x, y);
    }

    public void loadNewMap(HashTable<Square, TileObject> map) {
        gameFrame.loadNewMap(map);
    }

    public HashTable<Square, TileObject> getTileMap() {
        return gameFrame.getTileMap();
    }
}
