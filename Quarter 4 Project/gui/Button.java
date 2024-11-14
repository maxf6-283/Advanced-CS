package gui;

import javax.swing.JButton;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.awt.Font;
import java.io.File;
import java.awt.FontFormatException;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import javax.swing.SwingConstants;
import java.awt.image.BufferedImage;

public class Button extends JButton {
    public static final Font font;
    private static final BufferedImage buttonImage;
    private static final BufferedImage buttonPressedImage;
    private static final BufferedImage buttonHoverImage;
    private static final BufferedImage buttonDisabledImage;

    static {
        Font f = null;
        try {
            GraphicsEnvironment ge = 
                GraphicsEnvironment.getLocalGraphicsEnvironment();
            f = Font.createFont(Font.TRUETYPE_FONT, new File("resources/Orbitron-regular.ttf"));
            ge.registerFont(f);
            
       } catch (IOException|FontFormatException e) {
           e.printStackTrace();
       }
       font = f;

       BufferedImage bi = null;
       BufferedImage bpi = null;
       BufferedImage bhi = null;
       BufferedImage bdi = null;

       try {
           bi = ImageIO.read(new File("resources/button.png"));
           bpi = ImageIO.read(new File("resources/buttonPressed.png"));
           bhi = ImageIO.read(new File("resources/buttonHover.png"));
           bdi = ImageIO.read(new File("resources/buttonDisabled.png"));
       } catch (IOException e) {
           e.printStackTrace();
       }

       buttonImage = bi;
       buttonPressedImage = bpi;
       buttonHoverImage = bhi;
       buttonDisabledImage = bdi;
    }
    
    public Button(String name) {
        super(name);
        setRolloverEnabled(true);
        setIcon(new ImageIcon(buttonImage));
        setPressedIcon(new ImageIcon(buttonPressedImage));
        setRolloverIcon(new ImageIcon(buttonHoverImage));
        setDisabledIcon(new ImageIcon(buttonDisabledImage));
        setHorizontalTextPosition(SwingConstants.CENTER);
        setFont(font.deriveFont(24f));
        setBorderPainted(false);
    }
}
