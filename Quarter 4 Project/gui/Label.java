package gui;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;

public class Label extends JLabel {
    public Label(String text) {
        super(text);
        setFont(Button.font.deriveFont(24f));
        setForeground(Color.WHITE);
        setHorizontalAlignment(SwingConstants.CENTER);
    }
}
