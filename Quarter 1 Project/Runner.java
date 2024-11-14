import javax.swing.JFrame;

public class Runner {
    public static void main(String[] args) {
        //make the Jframe and Table
        JFrame fr = new JFrame("Video Poker");
        Table table = new Table(1);

        fr.add(table);
        fr.pack();
        fr.setResizable(false);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setVisible(true);
        fr.setAlwaysOnTop(true);
        fr.setAlwaysOnTop(false);
        
        table.animate();
    }
}
