import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

public class Player {
    private static final int CARD_SPACING = 80;
    private static final double CARD_ROTATION = 0.25;

    private ArrayList<Card> hand;

    private int xPos;
    private int yPos;

    private Font font;

    private int points = 0;

    private boolean holdEnabled;

    public Player(int x, int y, Font f) {
        xPos = x;
        yPos = y;
        hand = new ArrayList<>();
        font = f;
    }

    /**
     * draw a card from a deck
     * 
     * @param deck - the deck to draw from
     */
    public void drawCard(Deck deck) {
        hand.add(deck.getCard(0));
        deck.removeCard(0);
    }

    /**
     * discard all cards into a deck
     * 
     * @param deck - the deck to discard into
     */
    public void discardAll(Deck deck) {
        while (hand.size() > 0) {
            deck.addCard(hand.get(0));
            hand.remove(0);
        }
    }

    /**
     * Calculate the point value of the hand, aces are 11 unless you would bust
     * because of it, in which case they're 1
     * 
     * @return the point value of the hand
     */
    public int pointValue() {
        int points = 0;
        int aces = 0;

        for (Card card : hand) {
            points += card.value();
            if (card.name().equals("A")) {
                aces++;
            }
        }

        while (points > 21 && aces > 0) {
            points -= 10;
            aces--;
        }

        return points;
    }

    /**
     * Draw the player
     * 
     * @param g             - the Graphics object
     * @param hideFirstCard - whether or not to show the first card in the hand
     * @param showScore     - whether or not to display the point value
     */
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 50));

        // draw the cards
        for (int i = 0; i < hand.size(); i++) {
            hand.get(i).draw(g);
            hand.get(i).faceUp();
        }

        // draw the number of wins
        drawPoints(g);
    }

    /**
     * Lerp the cards to their rightful places
     */
    public void move() {
        double cardXPos = -CARD_SPACING * (hand.size() - 1) / 2.0 + xPos;
        double cardRot = -CARD_ROTATION * (hand.size() - 1) / 2.0;
        for (Card card : hand) {
            double cardYPos = yPos - Math.cos(cardRot) * CARD_SPACING * Math.PI + CARD_SPACING * Math.PI;
            card.lerpTo(cardXPos, cardYPos, cardRot, 0.1);
            cardXPos += CARD_SPACING;
            cardRot += CARD_ROTATION;
        }
    }

    /**
     * Get the number of aces
     * 
     * @return the number of aces in the hand
     */
    public int aceCount() {
        int aceCount = 0;
        for (Card card : hand) {
            if (card.name().equals("A")) {
                aceCount++;
            }
        }

        return aceCount;
    }

    /**
     * Draw a centered string
     * 
     * @param g    - the Graphics object
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

    /**
     * Add one to the number of points
     */
    public void addPoints(int pointsToAdd) {
        points += pointsToAdd;
    }

    /**
     * Display the number of points
     * 
     * @param g - the Graphics object
     */
    private void drawPoints(Graphics g) {
        int pointWidth = 200;
        int pointHeight = 50;

        double pointX = xPos;
        double pointY = yPos + 150;

        g.setColor(new Color(51, 51, 51));
        g.fillRoundRect((int) pointX - pointWidth / 2, (int) pointY - pointHeight / 2, pointWidth, pointHeight, 10, 10);
        g.setColor(Color.WHITE);
        drawCenteredString(g, "" + points, (int) pointX, (int) pointY);
    }

    /**
     * The number of cards
     */
    public int cardCount() {
        return hand.size();
    }

    /**
     * Set whether or not the player can select to hold cards
     */
    public void setHoldEnabled(boolean enabled) {
        holdEnabled = enabled;
    }

    public boolean holdEnabled() {
        return holdEnabled;
    }

    private Card highlightedCard = null;

    public void checkCardHighlighting(int mouseX, int mouseY) {
        highlightedCard = null;
        for (Card card : hand) {
            if (card.hoveringOver(mouseX, mouseY)) {
                highlightedCard = card;
            }
        }
        hand.forEach(e -> e.setHighlighted(false));
        if (highlightedCard != null) {
            highlightedCard.setHighlighted(true);
        }
    }

    public Card highlightedCard() {
        return highlightedCard;
    }
}
