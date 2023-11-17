import java.net.URI;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class MyImage {
    private BufferedImage image;
    private String name;

    public MyImage(String URL, String name) {
        this.name = name;
        try {
            image = ImageIO.read((new URI(URL)).toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String name() {
        return name;
    }

    public BufferedImage image() {
        return image;
    }
}
