import javax.swing.JFrame;

public class Runner {
    public static void main(String[] args) {
        Screen screen = new Screen();

        screen.setBounds(0, 0, 1000, 800);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setVisible(true);
    }
}
