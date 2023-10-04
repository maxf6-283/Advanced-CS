import java.util.Iterator;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

public class Player {
    private static final int CARD_SPACING = 80;
    private static final double CARD_ROTATION = 0.25;

    private MyLinkedList<Card> hand;

    private int xPos;
    private int yPos;

    private Font font;

    private int points;

    private boolean selectEnabled;

    public Player(int x, int y, Font f) {
        xPos = x;
        yPos = y;
        hand = new MyLinkedList<>();
        font = f;
        points = 50;
    }

    /**
     * draw a card from a deck
     * 
     * @param deck - the deck to draw from
     */
    public void drawCard(Deck deck) {
        Card cardToDraw = deck.getCard(0);
        int i = 0;
        for (Card card : hand) {
            if (card.value() >= cardToDraw.value()) {
                break;
            }
            i++;
        }
        hand.add(i, cardToDraw);
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
     * Calculate the point value of the hand
     * 
     * @return the point value of the hand
     */
    public int pointValue() {
        boolean isRoyal = true;
        boolean isFourOfAKind = false;
        boolean isFullHouse = true;
        boolean isFlush = true;
        boolean isStraight = true;
        boolean isThreeOfAKind = true;
        boolean isTwoPairs = true;
        boolean isPairOfJacks = false;

        Card[] cards = new Card[5];
        cards = hand.toArray(cards);

        for (int i = 1; i < cards.length; i++) {
            if (cards[i].value() != cards[0].value() + i) {
                isStraight = false;
                
                break;
            }
        }
        if (cards[0].value() == 2 && cards[1].value() == 3 && cards[2].value() == 4 && cards[3].value() == 5
                && cards[4].value() == 14) {
            isStraight = true;
        }

        for (int i = 1; i < cards.length; i++) {
            if (cards[i].suite() != cards[0].suite()) {
                isFlush = false;
                break;
            }
        }

        if (cards[0].value() != 10 || !isStraight) {
            isRoyal = false;
        }

        int streak = 1;
        int pairCount = 0;
        int threeCount = 0;
        for (int i = 1; i < cards.length; i++) {
            if (cards[i].value() == cards[i - 1].value()) {
                streak++;
            } else {
                streak = 1;
            }

            if (streak == 2 && cards[i].value() >= 11) {
                isPairOfJacks = true;
            }

            if (streak == 2) {
                pairCount++;
            }
            if (streak == 3) {
                pairCount--;
                threeCount++;
            }
            if (streak == 4) {
                isFourOfAKind = true;
            }
        }
        isTwoPairs = pairCount == 2;
        isThreeOfAKind = threeCount > 0;
        isFullHouse = pairCount == 1 && threeCount == 1;

        if (isRoyal && isFlush) {
            // Royal flush!
            return 250;
        } else if (isStraight && isFlush) {
            // Straight flush
            return 50;
        } else if (isFourOfAKind) {
            return 25;
        } else if (isFullHouse) {
            return 9;
        } else if (isFlush) {
            return 6;
        } else if (isStraight) {
            return 4;
        } else if (isThreeOfAKind) {
            return 3;
        } else if (isTwoPairs) {
            return 2;
        } else if (isPairOfJacks) {
            return 1;
        }

        return 0;
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
        for (Card card : hand) {
            card.draw(g);
            card.faceUp();
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
    public void setSelectEnabled(boolean enabled) {
        selectEnabled = enabled;
    }

    public boolean selectEnabled() {
        return selectEnabled;
    }

    private Card highlightedCard = null;

    public void checkCardHighlighting(int mouseX, int mouseY) {
        if (!selectEnabled) {
            hand.forEach(e -> e.setHighlighted(false));
            return;
        }
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

    public int selectedCardCount() {
        return (int) hand.stream().filter(e -> e.selected()).count();
    }

    public void discardSelectedCards(Deck deckToDiscardTo) {
        Iterator<Card> iter = hand.iterator();
        while (iter.hasNext()) {
            Card card = iter.next();
            if (card.selected()) {
                deckToDiscardTo.addCard(card);
                iter.remove();
                card.setSelected(false);
            }
        }
    }

    public void drawCards(Deck deck, int cards) {
        for (int i = 0; i < cards; i++) {
            drawCard(deck);
        }
    }

    public int getPoints() {
        return points;
    }
}
