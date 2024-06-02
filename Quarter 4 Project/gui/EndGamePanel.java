package gui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import game.Player;

public class EndGamePanel extends Panel {
    private boolean active;
    private Frame parentFrame;
    private Button backToMainMenu;
    private Label winnerLabel;

    public EndGamePanel(Frame p) {
        setLayout(null);
        parentFrame = p;

        winnerLabel = new Label("");
        winnerLabel.setFont(Button.font.deriveFont(40.0f));
        add(winnerLabel);

        backToMainMenu = new Button("Back to Main Menu");
        add(backToMainMenu);
        backToMainMenu.addActionListener(e -> {
            parentFrame.reset();
            parentFrame.showPanel("Main Menu");
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (active) {
                    arrangeComponents();
                }
            }
        });
    }

    @Override
    public void setActive(boolean a) {
        active = a;
        if (a) {
            Player winner = parentFrame.gamePanel().winner();
            winnerLabel.setText("<html>Winner: <b>" + winner.username() + "</b></html>");
            arrangeComponents();
        }
    }

    private void arrangeComponents() {
        int cX = getWidth() / 2;
        int cY = getHeight() / 2;

        winnerLabel.setBounds(cX - 400, cY - 200, 800, 200);
        backToMainMenu.setBounds(cX - 200, cY + 50, 400, 100);
    }
}
