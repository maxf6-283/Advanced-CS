import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Screen extends JPanel {
    private BST<Integer> tree;
    public Screen() {
        tree = new BST<>();
        for(int i = 0; i < 10; i++) {
            tree.add((int)(Math.random() * 100));
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

    @Override
    public void paintComponent(Graphics g) {
        tree.draw(g, 200, 20, 200);
    }
}
