import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Table extends JPanel implements MouseInputListener {
    /**
     * The states of the game
     */
    private enum State {
        DEALING,
        YOUR_TURN,
        FINISHED,
        RESETTING
    }
    private State state;

    private Deck mainDeck;
    private Deck discardPile;

    //a counter to keep track of how long a state is taking
    private int frame;

    //the players
    private Player player;

    //the fonts for buttons and players
    private Font buttonFont;
    private Font playerFont;

    //reset and stand buttons
    private FancyButton replaceButton;

    public Table(int deckCount) {
        buttonFont = new Font("Lucida Sans Unicode", Font.BOLD, 20);
        playerFont = new Font("Lucida Sans Unicode", Font.BOLD, 40);

        mainDeck = new Deck(125, 300, 0, deckCount, true);
        discardPile = new Deck(275, 300, 0, 0, false);

        player = new Player(400, 600, playerFont);
        player.setSelectEnabled(false);

        replaceButton = new FancyButton(400, 420, 300, 50, buttonFont, "Replace Cards and Proceed");
        replaceButton.setEnabled(false);

        addMouseListener(this);
        addMouseMotionListener(this);

        state = State.FINISHED;

        frame = 0;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 800);
    }

    @Override
    public void paintComponent(Graphics g) {
        //background
        g.setColor(new Color(100, 200, 100));
        g.fillRect(0, 0, 800, 800);

        //decks
        mainDeck.draw(g);
        discardPile.draw(g);

        //draw the players
        player.draw(g);

        //draw the buttons
        replaceButton.draw(g);

        //draw the rules
        displayRules(g);
    }

    public void animate() {
        while (true) {
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mainDeck.move();
            discardPile.move();

            player.move();

            switch (state) {
                case DEALING -> {
                    //deal cards
                    if (mainDeck.size() < 10) {
                        discardPile.addOnto(mainDeck);
                        mainDeck.shuffle();
                    }


                    if(frame%25 == 0) {
                        player.drawCard(mainDeck);
                        if(player.cardCount() >= 5) {
                            state = State.YOUR_TURN;
                            frame = 0;
                            replaceButton.setEnabled(true);
                        }
                    }
                    
                    frame++;
                }
                case YOUR_TURN -> {
                    if (frame == 0) {
                        //enable the discard button
                        replaceButton.setEnabled(true);
                        player.setSelectEnabled(true);
                    }
                    frame++;
                }
                case FINISHED -> {
                    //shuffle the discard pile into the main pile if the main deck is too small
                }
                case RESETTING -> {
                    frame++;
                    //redeal after 25 frames
                    if (frame > 25) {
                        state = State.FINISHED;
                        mainDeck.setEnabled(true);
                        frame = 0;
                    }
                }
            }

            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //handle button presses
        if (mainDeck.hoveringOver(e.getX(), e.getY()) && mainDeck.enabled()) {
            //draw a card
            player.discardAll(discardPile);
            state = State.DEALING;
            mainDeck.setEnabled(false);
            frame = 0;
            player.setSelectEnabled(true);
            player.addPoints(-1);
            
        } else if (replaceButton.hoveringOver(e.getX(), e.getY()) && replaceButton.enabled()) {
            //Replace cards
            int cardsToDraw = player.selectedCardCount();
            player.discardSelectedCards(discardPile);
            player.drawCards(mainDeck, cardsToDraw);
            player.setSelectEnabled(false);

            state = State.RESETTING;
            frame = 0;
            replaceButton.setEnabled(false);
        }

        if(player.highlightedCard() != null) {
            player.highlightedCard().toggleSelected();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //handle the cool button highlighting
        if (mainDeck.hoveringOver(e.getX(), e.getY()) && mainDeck.enabled()) {
            mainDeck.setPressed(true);
        } else if (replaceButton.hoveringOver(e.getX(), e.getY()) && replaceButton.enabled()) {
            replaceButton.setPressed(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //handle the cool button highlighting
        mainDeck.setPressed(false);
        replaceButton.setPressed(false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //dragging the mouse is the same as moving it normally
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //handle the cool button highlighting
        if (mainDeck.hoveringOver(e.getX(), e.getY())) {
            mainDeck.setHighlighted(true);
        } else {
            mainDeck.setHighlighted(false);
        }

        if (replaceButton.hoveringOver(e.getX(), e.getY())) {
            replaceButton.setHovering(true);
        } else {
            replaceButton.setHovering(false);
        }

        player.checkCardHighlighting(e.getX(), e.getY());
    }

    private void displayRules(Graphics g) {
        String text = """
                Royal Flush: 250 Points
                Straight Flush: 50 Points
                Four of a Kind: 25 Points
                Full House: 9 Points
                Flush: 6 Points
                Straight: 4 Points
                3 of a Kind: 3 Points
                2 Pairs: 2 Points
                Pair of Jacks or higher: 1 Point
                Anything Else: 0 points
                """;

        g.setColor(new Color(0, 100, 0));
        g.drawRoundRect(400, 200, 200, 400, 20, 20);
    }
}