import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class PlaceholderTextField extends JTextField {
    private String placeText;

    public PlaceholderTextField(String placeholderText) {
        super(placeholderText + "​");
        setForeground(Color.GRAY);
        placeText = placeholderText + "​";
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeText)) {
                    setText("");
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setForeground(Color.GRAY);
                    setText(placeText);
                }
            }
        });
    }

    public void clear() {
        setForeground(Color.GRAY);
        setText(placeText + "​");
    }
}
