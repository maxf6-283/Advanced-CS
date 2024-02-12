import javax.swing.*;

public class Runner{
    public static void main (String[] args) {
        JFrame f = new JFrame("Binary Search Tree");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Screen2 sc = new Screen2();
        f.add(sc);
        f.pack();
        f.setVisible(true);
    }
}