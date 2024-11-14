package gui;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;

public class Instr extends Panel {
    private Frame parentFrame;
    private Label instructions;
    private Button mainMenu;
    private boolean active = false;

    public Instr(Frame parentFrame) {
        setLayout(null);

        this.parentFrame = parentFrame;
        mainMenu = new Button("Back to Menu");
        add(mainMenu);
        mainMenu.addActionListener(e -> this.parentFrame.showPanel("Main Menu"));
        instructions = new Label(
                "<html><h1>Instructions:</h1><br><p>This is an asteroids-based battler! Press the left/right arrow keys to turn, up arrow to go forward, and z to shoot. You can shoot other players, or asteroids. Asteroids contain power-ups that alter your laser. Enjoy!</p></html>");
        add(instructions);
        arrangeButtons();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (active) {
                    arrangeButtons();
                }
            }
        });
    }

    private void arrangeButtons() {
        // buttons are centered
        // buttons are 400x100
        // buttons are separated by 50
        instructions.setBounds(getWidth() / 2 - 400, getHeight() / 2 - 300, 800, 300);
        mainMenu.setBounds(getWidth() / 2 - 200, getHeight() / 2 + 100, 400, 100);
    }

    @Override
    public void setActive(boolean a) {
        active = a;
        if(active) {
            arrangeButtons();
        }
    }
}
