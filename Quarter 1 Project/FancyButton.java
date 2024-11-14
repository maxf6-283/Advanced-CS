import java.awt.Graphics;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Font;

/**
 * A custom button because the defualt ones are ugly
 */
public class FancyButton {
    private int xPos, yPos, width, height;
    private Font font;
    private boolean enabled;
    private String text;
    private boolean hovering;
    private boolean pressed;

    public FancyButton(int x, int y, int width, int height, Font font, String text) {
        this.font = font;
        xPos = x;
        yPos = y;
        this.width = width;
        this.height = height;
        this.text = text;
        hovering = false;
        pressed = false;
        enabled = true;
    }

    /**
     * Draw the button
     * @param g - the Graphics object
     */
    public void draw(Graphics g) {
        Color backCol;
        Color frontCol;
        Color textCol;

        //make it transparent if not enabled
        if (enabled) {
            backCol = new Color(255, 200, 200);
            frontCol = new Color(255, 150, 100);
            textCol = new Color(0, 0, 0);
        } else {
            backCol = new Color(255, 200, 200, 50);
            frontCol = new Color(255, 150, 100, 50);
            textCol = new Color(0, 0, 0, 50);
        }

        //draw the rectangles
        g.setColor(backCol);
        g.fillRoundRect(xPos - width / 2, yPos - height / 2, width, height, 20, 20);

        g.setColor(frontCol);
        g.fillRoundRect(xPos + 5 - width / 2, yPos + 5 - height / 2, width - 10, height - 10,
                10, 10);

        //draw the text
        g.setColor(textCol);
        drawCenteredString(g, text, xPos, yPos);

        //draw the cool highlight
        if(hovering && enabled) {
            if(pressed) {
                g.setColor(new Color(0, 0, 0, 50));
            } else {
                g.setColor(new Color(0, 0, 0, 25));
            }
            g.fillRoundRect(xPos - 10 - width / 2, yPos - 10 - height / 2, width + 20, height + 20, 40, 40);
        }
    }

    /**
     * Draw a centered string
     * @param g - the Graphics object
     * @param text - the String to draw
     * @param xPos - the x position
     * @param yPos - the y position
     */
    private void drawCenteredString(Graphics g, String text, int xPos, int yPos) {
        FontMetrics metrics = g.getFontMetrics(font);
        
        int x = xPos - metrics.stringWidth(text) / 2;
        int y = yPos - metrics.getHeight() / 2 + metrics.getAscent();
        
        g.setFont(font);
        g.drawString(text, x, y);
    }

    public void setHovering(boolean h) {
        hovering = h;
    }

    public void setPressed(boolean p) {
        pressed = p;
    }

    public void setEnabled(boolean e) {
        enabled = e;
    }

    /**
     * Get if the given position is over the button
     * @param x - the x position of the point
     * @param y - the y position of the point
     * @return - if the point is contained in the bounds of the buttom
     */
    public boolean hoveringOver(int x, int y) {
        if(Math.abs(x - xPos) < width/2) {
            if(Math.abs(y-yPos) < height/2) {
                return true;
            }
        }
        return false;
    }

    public boolean enabled() {
        return enabled;
    }
}
