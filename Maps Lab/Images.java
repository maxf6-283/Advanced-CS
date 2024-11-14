import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Images {
    private static MyHashMap<String, BufferedImage> images;

    static {
        images = new MyHashMap<>();
    }

    @SuppressWarnings("deprecated")
    public static BufferedImage getImage(String url) {
        if (!images.containsKey(url)) {
            try {
                images.put(url, ImageIO.read(new URL(url)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return images.get(url);
    }
}