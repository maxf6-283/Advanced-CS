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
    private FancyButton resetButton;

    public Table(int deckCount) {
        buttonFont = new Font("Lucida Sans Unicode", Font.BOLD, 20);
        playerFont = new Font("Lucida Sans Unicode", Font.BOLD, 40);

        mainDeck = new Deck(325, 300, 0, deckCount, true);
        discardPile = new Deck(475, 300, 0, 0, false);

        player = new Player(400, 600, playerFont);

        replaceButton = new FancyButton(400, 420, 200, 50, buttonFont, "Replace Cards");
        resetButton = new FancyButton(400, 400, 200, 100, playerFont, "Reset");

        addMouseListener(this);
        addMouseMotionListener(this);

        state = State.DEALING;

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
        if (state == State.FINISHED) {
            resetButton.draw(g);
        }
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
                    if (frame == 0) {
                        //disable the buttons while dealing
                        replaceButton.setEnabled(false);
                        mainDeck.setEnabled(false);
                        player.setHoldEnabled(false);
                    }
                    frame++;

                    //deal cards

                    if(frame%25 == 24) {
                        player.drawCard(mainDeck);
                        if(player.cardCount() >= 5) {
                            state = State.YOUR_TURN;
                            frame = 0;
                        }
                    }
                    
                }
                case YOUR_TURN -> {
                    if (frame == 0) {
                        //enable the stand and draw buttons
                        replaceButton.setEnabled(true);
                        mainDeck.setEnabled(true);
                        player.setHoldEnabled(true);
                    }
                    frame++;

                    
                }
                case FINISHED -> {
                    frame = 0;
                }
                case RESETTING -> {
                    frame++;

                    //shuffle the discard pile into the main pile if the main deck is too small
                    if (mainDeck.size() < 10) {
                        discardPile.addOnto(mainDeck);
                        mainDeck.shuffle();
                    }

                    //redeal after 25 frames
                    if (frame > 25) {
                        state = State.DEALING;
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
            player.drawCard(mainDeck);
            mainDeck.setEnabled(false);
            replaceButton.setEnabled(false);
            
        } else if (replaceButton.hoveringOver(e.getX(), e.getY()) && replaceButton.enabled()) {
            //Don't draw a card
            replaceButton.setEnabled(false);
            mainDeck.setEnabled(false);

        } else if (resetButton.hoveringOver(e.getX(), e.getY()) && state == State.FINISHED) {
            //discard all cards into the discard pile
            player.discardAll(discardPile);
            state = State.RESETTING;
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
        } else if (resetButton.hoveringOver(e.getX(), e.getY()) && state == State.FINISHED) {
            resetButton.setPressed(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //handle the cool button highlighting
        mainDeck.setPressed(false);
        replaceButton.setPressed(false);
        resetButton.setPressed(false);
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

        if (resetButton.hoveringOver(e.getX(), e.getY())) {
            resetButton.setHovering(true);
        } else {
            resetButton.setHovering(false);
        }

        player.checkCardHighlighting(e.getX(), e.getY());
    }
}