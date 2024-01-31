import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Screen extends JPanel implements ActionListener {
    private BST<Integer> tree;
    private JButton addButton;
    private JButton clear;
    private JButton delete;
    private JTextField number;
    
    public Screen() {
        setLayout(null);
        tree = new BST<>();
        int [] nums = new int[] {10, 5, 15, 1, 9, 11, 19};
        for(int each : nums) {
            tree.add(each);
        }
        
        number = new JTextField();
        number.setBounds(25, 350, 150, 30);
        this.add(number);
        
        addButton = new JButton("Add");
        addButton.setBounds(200, 350, 100, 30);
        addButton.addActionListener(this);
        this.add(addButton);
        
        clear = new JButton("Clear");
        clear.setBounds(300, 25, 100, 30);
        clear.addActionListener(this);
        this.add(clear);

        delete = new JButton("Delete");
        delete.setBounds(300, 350, 100, 30);
        delete.addActionListener(this);
        this.add(delete);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        tree.draw(g, 200, 20, 200);
        g.setColor(Color.BLACK);
        g.drawString("Height: " + tree.getHeight(), 25, 25);
        g.drawString("Level: " + tree.getLevel(), 25, 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addButton) {
            int num = Integer.parseInt(number.getText());
            tree.add(num);
            number.setText("");
        } else if (e.getSource() == clear) {
            tree.clear();
        } else if (e.getSource() == delete) {
            int num = Integer.parseInt(number.getText());
            tree.remove(num);
            number.setText("");
        }
        repaint();
    }
}
