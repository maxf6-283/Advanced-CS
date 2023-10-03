import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.HashMap;

public class Card {
    /**
     * The suite of a card (hearts, diamonds, etc.)
     */
    public enum Suite {
        HEARTS, SPADES, DIAMONDS, CLUBS
    }

    //width and height constants for cards
    public static final int WIDTH = 100;
    public static final int HEIGHT = 150;

    //shared images so multiple decks don't need to load multiple images
    private static HashMap<String, BufferedImage> images;

    private int value;
    private String name;
    private Suite suite;

    private double xPos;
    private double yPos;
    private double flipAmount; // -1 is backside, 1 is frontside
    private double rotation;

    private BufferedImage cardPicture;

    private boolean highlighted;
    private boolean selected;

    public Card(String name, int value, Suite suite) {
        this.name = name;
        this.suite = suite;
        this.value = value;

        flipAmount = 0;
        xPos = 0;
        yPos = 0;
        highlighted = false;

        //make the images hashmap if it doesn't exist.
        if (images == null) {
            images = new HashMap<>();
        }

        //get the image of the card
        String imageName = name;
        switch (suite) {
            case HEARTS:
                imageName += "H";
                break;
            case SPADES:
                imageName += "S";
                break;
            case DIAMONDS:
                imageName += "D";
                break;
            case CLUBS:
                imageName += "C";
                break;
        }

        imageName += ".png";

        //first check if the image is already in the hashmap (no need to load it if so)
        if (images.containsKey(imageName)) {
            cardPicture = images.get(imageName);
        } else {
            try {
                cardPicture = ImageIO.read(new File("./Images/" + imageName));
                //put a reference to the image in the hashmap
                images.put(imageName, cardPicture);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPos(double x, double y) {
        xPos = x;
        yPos = y;
    }

    public void setX(double x) {
        xPos = x;
    }

    public void setY(double y) {
        yPos = y;
    }

    public void setFlipped(double flipMount) {
        flipAmount = flipMount;
    }

    public double getFlipped() {
        return flipAmount;
    }

    /** bring flipAmount to 1 at a rate of 0.1 */
    public void faceUp() {
        if (flipAmount < 1) {
            flipAmount += 0.1;
        }
        if (flipAmount > 1) {
            flipAmount = 1;
        }
    }
    
    
    /** bring flipAmount to -1 at a rate of 0.1 */
    public void faceDown() {
        if (flipAmount > -1) {
            flipAmount -= 0.1;
        }
        if (flipAmount < -1) {
            flipAmount = -1;
        }
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    public int value() {
        return value;
    }

    public String name() {
        return name;
    }

    public Suite suite() {
        return suite;
    }

    public void rotate(double rot) {
        rotation += rot;
    }

    public void setRot(double rot) {
        rotation = rot;
    }

    public double rotation() {
        return rotation;
    }

    /**
     * Draw the card
     * @param g - the Graphics object
     */
    public void draw(Graphics g) {
        //make a graphics2d so it can rotate
        Graphics2D g2d = (Graphics2D) g;

        //rotate and translate the card
        AffineTransform prevTransform = g2d.getTransform();
        g2d.translate(xPos, yPos);
        g2d.rotate(rotation);

        //get whether to show the back or front of the card
        boolean back = flipAmount < 0;
        double width = Math.abs(WIDTH * flipAmount);

        if (back) {
            //show the back
            g.setColor(new Color(255, 200, 200));
            g.fillRoundRect((int) (-width / 2), -HEIGHT / 2, (int) width, HEIGHT, 20, 20);
            g.setColor(new Color(255, 100, 100));
            g.fillRoundRect((int) (-width / 2 + 10), -HEIGHT / 2 + 10, (int) width - 20, HEIGHT - 20,
                    10, 10);
        } else {
            //show the front
            g.setColor(selected ? highlighted ? Color.GRAY : Color.LIGHT_GRAY : highlighted ? Color.LIGHT_GRAY : Color.WHITE);
            g.fillRoundRect((int) (-width / 2), -HEIGHT / 2, (int) width, HEIGHT, 20, 20);
            g.drawImage(cardPicture, (int) (-width / 2), -HEIGHT / 2, (int) width, HEIGHT, null);
        }

        //remove the transformations
        g2d.setTransform(prevTransform);
    }

    /**
     *  Lerp the card
     * @param x - the x position to lerp to
     * @param y - the y position to lerp to
     * @param rot - the rotation to lerp to
     * @param amt - the amount to lerp
     */
    public void lerpTo(double x, double y, double rot, double amt) {
        xPos = (x * amt + xPos * (1 - amt));
        yPos = (y * amt + yPos * (1 - amt));
        if(rotation == rot) {
            return;
        }
        // rotation can be at increments of every PI, the card will rotate to the
        // closest increment
        double rotChange = rotation - rot;
        rotChange %= Math.PI;
        if(rotChange > Math.PI / 2) {
            rotChange -= Math.PI;
        } else if (rotChange < -Math.PI/2) {
            rotChange += Math.PI;
        }

        rotChange *= amt;
        rotation -= rotChange;
    }

    public boolean hoveringOver(int mouseX, int mouseY) {
        //rotate the mouse position around the card
        mouseX -= xPos;
        mouseY -= yPos;

        double newMouseX = Math.sin(rotation) * mouseY + Math.cos(rotation) * mouseX;
        double newMouseY = Math.cos(rotation) * mouseY - Math.sin(rotation) * mouseX;

        return Math.abs(newMouseX) < WIDTH / 2 && Math.abs(newMouseY) < HEIGHT / 2;
    }

    public void setHighlighted(boolean h) {
        highlighted = h;
    }

    public void setSelected(boolean s) {
        selected = s;
    }

    public void toggleSelected() {
        selected = !selected;
    }

    public boolean selected() {
        return selected;
    }
}