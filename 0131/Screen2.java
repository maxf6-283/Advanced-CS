import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class Screen2 extends JPanel implements ActionListener {
    private BST<Integer> tree;
    private JButton addButton;
    private JButton clear;
    private JButton delete;
    private JButton balance;
    private JButton search;
    private JButton checkBalance;
    private JButton test1;
    private JButton test2;
    private JButton test3;
    private PlaceholderTextField integer;
    private JLabel isBalanced;
    
    public Screen2() {
        setLayout(null);
        tree = new BST<>();
        // tree.add(new Student("Julia", "Johnson", 1111, "MVHS"));
        // tree.add(new Student("Jack", "Thompson", 1234, "LAHS"));
        // tree.add(new Student("Thomas", "Jenkins", 4321, "MVHS"));
        int [] nums = new int[] {1};
        for(int each : nums) {
        tree.add(each);
        }

        integer = new PlaceholderTextField("Integer");
        integer.setBounds(25, 350, 150, 30);
        this.add(integer);
        
        addButton = new JButton("Add");
        addButton.setBounds(350, 350, 100, 30);
        addButton.addActionListener(this);
        this.add(addButton);

        delete = new JButton("Delete");
        delete.setBounds(450, 350, 100, 30);
        delete.addActionListener(this);
        this.add(delete);

        search = new JButton("Search");
        search.setBounds(350, 390, 100, 30);
        search.addActionListener(this);
        this.add(search);

        clear = new JButton("Clear");
        clear.setBounds(400, 25, 100, 30);
        clear.addActionListener(this);
        this.add(clear);

        balance = new JButton("Balance");
        balance.setBounds(400, 75, 100, 30);
        balance.addActionListener(this);
        this.add(balance);

        checkBalance = new JButton("Check Balance");
        checkBalance.setBounds(500, 75, 150, 30);
        checkBalance.addActionListener(this);
        this.add(checkBalance);
        
        test1 = new JButton ("Test 1");
        test1.setBounds(50, 300, 100, 30);
        test1.addActionListener(this);
        this.add(test1);

        test2 = new JButton ("Test 2");
        test2.setBounds(200, 300, 100, 30);
        test2.addActionListener(this);
        this.add(test2);

        test3 = new JButton ("Test 3");
        test3.setBounds(350, 300, 100, 30);
        test3.addActionListener(this);
        this.add(test3);

        isBalanced = new JLabel();
        isBalanced.setBounds(25, 70, 500, 30);
        this.add(isBalanced);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 550);
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
        if (e.getSource() == addButton) {
           tree.add(Integer.parseInt(integer.getText()));
           integer.clear();
        } else if (e.getSource() == clear) {
            tree.clear();
        } else if (e.getSource() == delete) {
            tree.remove(Integer.parseInt(integer.getText()));
            integer.clear();
        } else if (e.getSource() == balance) {
            tree.balance();
        } else if (e.getSource() == search) {
            tree.get(Integer.parseInt(integer.getText()));
            integer.clear();
        } else if (e.getSource() == checkBalance) {
            if (tree.isBalanced()) {
                isBalanced.setText("The tree is balanced.");
            } else {
                isBalanced.setText("The tree is unbalanced");
            }
        } else if (e.getSource() == test1) {
            tree.clear();
            int [] nums = new int[] {1, 2, 3, 4, 5, 6, 7};
            for(int each : nums) {
                tree.add(each);
            }
        } else if (e.getSource() == test2) {
            tree.clear();
            int[] nums = {4, 3, 1, 2, 5, 6, 7};
            for(int each : nums) {
                tree.add(each);
            }
        } else if (e.getSource() == test3) {
            tree.clear();
            int [] nums = new int[] {4, 5, 7, 6, 8};
            for(int each : nums) {
                tree.add(each);
            }
        } 
        repaint();
    }
}
