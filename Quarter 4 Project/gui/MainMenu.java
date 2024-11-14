package gui;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;

public class MainMenu extends Panel{
    private Frame parentFrame;
    private Button instructionButton;
    private Button hostButton;
    private Button joinButton;
    private boolean active = true;

    public MainMenu(Frame parentFrame) {
        setLayout(null);

        this.parentFrame = parentFrame;
        instructionButton = new Button("Instructions");
        add(instructionButton);
        instructionButton.addActionListener(e -> this.parentFrame.showPanel("Instructions"));
        hostButton = new Button("Host Game");
        add(hostButton);
        hostButton.addActionListener(e -> this.parentFrame.showPanel("Host Menu"));
        joinButton = new Button("Join Game");
        add(joinButton);
        joinButton.addActionListener(e -> this.parentFrame.showPanel("Join Menu"));
        arrangeButtons();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if(active) {
                    arrangeButtons();
                }
            }
        });
    }

    private void arrangeButtons() {
        //buttons are centered
        //buttons are 400x100
        //buttons are separated by 50
        instructionButton.setBounds(getWidth()/2-200, getHeight()/2-200, 400, 100);
        hostButton.setBounds(getWidth()/2-200, getHeight()/2-50, 400, 100);
        joinButton.setBounds(getWidth()/2-200, getHeight()/2+100, 400, 100);
    }

    @Override
    public void setActive(boolean a) {
        active = a;
    }
}
