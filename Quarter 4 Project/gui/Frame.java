package gui;

import javax.swing.JFrame;

import comms.Host;
import comms.HostManager;
import game.GamePanel;

import java.awt.CardLayout;

public class Frame extends JFrame {
    private CardLayout layout;

    private MainMenu mainMenu;
    private HostMenu hostMenu;
    private JoinMenu joinMenu;
    private WaitingRoom waitingRoom;
    private GamePanel gamePanel;
    private EndGamePanel endGamePanel;
    private Panel activePanel;

    private HostManager hostManager;

    public Frame() {
        layout = new CardLayout();
        setLayout(layout);
        
        mainMenu = new MainMenu(this);
        add(mainMenu);
        layout.addLayoutComponent(mainMenu, "Main Menu");

        hostMenu = new HostMenu(this);
        add(hostMenu);
        layout.addLayoutComponent(hostMenu, "Host Menu");

        joinMenu = new JoinMenu(this);
        add(joinMenu);
        layout.addLayoutComponent(joinMenu, "Join Menu");

        waitingRoom = new WaitingRoom(this);
        add(waitingRoom);
        layout.addLayoutComponent(waitingRoom, "Waiting Room");

        gamePanel = new GamePanel(this);
        add(gamePanel);
        layout.addLayoutComponent(gamePanel, "Game");

        endGamePanel = new EndGamePanel(this);
        add(endGamePanel);
        layout.addLayoutComponent(endGamePanel, "Endgame");

        activePanel = mainMenu;

        setSize(1920, 1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void showPanel(String panelName) {
        activePanel.setActive(false);
        switch (panelName) {
            case "Main Menu" -> {
                activePanel = mainMenu;
            }
            case "Host Menu" -> {
                activePanel = hostMenu;
            }
            case "Join Menu" -> {
                activePanel = joinMenu;
            }
            case "Waiting Room" -> {
                activePanel = waitingRoom;
            }
            case "Game" -> {
                activePanel = gamePanel;
            }
            case "Endgame" -> {
                activePanel = endGamePanel;
            }
        }
        activePanel.setActive(true);
        layout.show(mainMenu.getParent(), panelName);
    }

    public HostManager hostManager() {
        return hostManager;
    }

    public void setHostManager(HostManager h) {
        hostManager = h;
        (new Thread(h)).start();
    }

    public void setNameText(String text) {
        if(hostMenu != activePanel) {
            hostMenu.setNameText(text);
        }
        if(joinMenu != activePanel) {
            joinMenu.setNameText(text);
        }
        if(waitingRoom != activePanel) {
            waitingRoom.setNameText(text);
        }
    }

    public Host host() {
        return joinMenu.selectedHost();
    }

    public GamePanel gamePanel() {
        return gamePanel;
    }
}
