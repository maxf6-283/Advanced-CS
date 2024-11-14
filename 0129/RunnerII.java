import javax.swing.*;

public class RunnerII{
    public static void main (String[] args) {
        JFrame f = new JFrame("Binary Search Tree");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Screen sc = new Screen();
        f.add(sc);
        f.pack();
        f.setVisible(true);
    }
}