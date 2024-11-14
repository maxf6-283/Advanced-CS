import javax.swing.JFrame;

public class Client {
    public static void main(String[] args) {
        JFrame fr = new JFrame();
        Screen sc = new Screen();
        fr.add(sc);

        
        fr.pack();
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setVisible(true);

        sc.startClient("10.210.98.213", 1024);
    }
}
