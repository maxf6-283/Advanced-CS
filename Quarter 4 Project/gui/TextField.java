package gui;

import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Graphics;

public class TextField extends JTextField {
    public TextField(String t) {
        super(t);
        setFont(Button.font.deriveFont(24f));
        setOpaque(false);
        setForeground(Color.WHITE);
        setCaretColor(getForeground());
        setBorder(null);
        setHorizontalAlignment(JTextField.CENTER);
    }

    public TextField() {
        this("");
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
