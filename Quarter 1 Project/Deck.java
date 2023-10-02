import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class Deck {
    private ArrayList<Card> cards;

    private int xPos;
    private int yPos;
    private double rotation;
    
    private boolean flipped;

    //"buttony" values
    private boolean highlighted;
    private boolean pressed;
    private boolean enabled;

    public Deck(int x, int y, double rot, int deckCount, boolean flipped) {
        cards = new ArrayList<>();
        xPos = x;
        yPos = y;
        rotation = rot;
        enabled = true;
        this.flipped = flipped;

        for(int j = 0; j < deckCount; j++) {

            for (int i = 2; i <= 9; i++) {
                cards.add(new Card(Integer.toString(i), i, Card.Suite.CLUBS));
                cards.add(new Card(Integer.toString(i), i, Card.Suite.SPADES));
                cards.add(new Card(Integer.toString(i), i, Card.Suite.DIAMONDS));
                cards.add(new Card(Integer.toString(i), i, Card.Suite.HEARTS));
            }
            cards.add(new Card("J", 10, Card.Suite.CLUBS));
            cards.add(new Card("J", 10, Card.Suite.SPADES));
            cards.add(new Card("J", 10, Card.Suite.DIAMONDS));
            cards.add(new Card("J", 10, Card.Suite.HEARTS));

            cards.add(new Card("Q", 10, Card.Suite.CLUBS));
            cards.add(new Card("Q", 10, Card.Suite.SPADES));
            cards.add(new Card("Q", 10, Card.Suite.DIAMONDS));
            cards.add(new Card("Q", 10, Card.Suite.HEARTS));

            cards.add(new Card("K", 10, Card.Suite.CLUBS));
            cards.add(new Card("K", 10, Card.Suite.SPADES));
            cards.add(new Card("K", 10, Card.Suite.DIAMONDS));
            cards.add(new Card("K", 10, Card.Suite.HEARTS));

            cards.add(new Card("A", 11, Card.Suite.CLUBS));
            cards.add(new Card("A", 11, Card.Suite.SPADES));
            cards.add(new Card("A", 11, Card.Suite.DIAMONDS));
            cards.add(new Card("A", 11, Card.Suite.HEARTS));

            for (Card card : cards) {
                card.setPos(xPos, yPos);
                card.setRot(rotation);
                card.setFlipped(flipped ? -1 : 1);
            }

            shuffle();
        }
    }

    public void addCard(Card c) {
        cards.add(0, c);
    }

    public void addCard(int index, Card c) {
        cards.add(index, c);
    }

    public void removeCard(int index) {
        cards.remove(index);
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    /**
     * Draw the deck
     * @param g - the Graphics object
     */
    public void draw(Graphics g) {
        //draw the "shadow" of the deck
        g.setColor(new Color(0, 0, 0, 50));
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(xPos, yPos);
        g2d.rotate(rotation);
        g2d.translate(-xPos, -yPos);
        g2d.fillRoundRect(xPos - Card.WIDTH / 2, yPos - Card.HEIGHT / 2, Card.WIDTH, Card.HEIGHT, 20, 20);
        g2d.translate(xPos, yPos);
        g2d.rotate(-rotation);
        g2d.translate(-xPos, -yPos);

        //draw the cards in the deck
        if (flipped) {
            for (int i = 0; i < cards.size(); i++) {
                cards.get(i).draw(g);
            }
        } else {
            for (int i = cards.size() - 1; i >= 0; i--) {
                cards.get(i).draw(g);
            }
        }

        //do the cool button highlight things
        if (highlighted && enabled) {
            //make the higlight darker if the button is being pressed
            if(pressed) {
                g.setColor(new Color(0, 0, 0, 50));
            } else {
                g.setColor(new Color(0, 0, 0, 25));
            }
            //draw the higlight
            g2d.translate(xPos, yPos);
            g2d.rotate(rotation);
            g2d.translate(-xPos, -yPos);
            g2d.fillRoundRect(xPos- 10 - Card.WIDTH / 2, yPos - 10 - Card.HEIGHT / 2, Card.WIDTH + 20, Card.HEIGHT + 20, 40, 40);
            g2d.translate(xPos, yPos);
            g2d.rotate(-rotation);
            g2d.translate(-xPos, -yPos);
        }
    }

    /**
     * Lerp all the cards to their proper location
     */
    public void move() {
        for (Card card : cards) {
            card.lerpTo(xPos, yPos, rotation, 0.1);
            if(flipped) {
                card.faceDown();
            } else {
                card.faceUp();
            }
        }
    }

    public void setHighlighted(boolean h) {
        highlighted = h;
    }

    public void setEnabled(boolean e) {
        enabled = e;
    }

    public void setPressed(boolean p) {
        pressed = p;
    }

    /**
     * Get if the given position is over the deck
     * @param x - the x position of the point
     * @param y - the y position of the point
     * @return - if the point is contained in the bounds of the deck
     */
    public boolean hoveringOver(int x, int y) {
        if(Math.abs(xPos - x) <= Card.WIDTH/2) {
            if(Math.abs(yPos - y) <= Card.HEIGHT/2) {
                return true;
            }
        }

        return false;
    }

    public boolean enabled() {
        return enabled;
    }

    public void shuffle() {
        for(int i = 0; i < cards.size(); i++) {
            int j = (int)(Math.random() * cards.size());
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }

    /**
     * @return the number of cards in the deck
     */
    public int size() {
        return cards.size();
    }

    /**
     * Take the cards from another deck to this deck
     * @param deck - the deck to be taken from
     */
    public void addOnto(Deck deck) {
        while(cards.size() > 0) {
            deck.addCard(cards.get(0));
            cards.remove(0);
        }
    }
}
