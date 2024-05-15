package gui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Optional;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

import comms.ClientEvent;
import comms.HostManager;
import game.Player;
import utils.ArrayList;

public class WaitingRoom extends Panel {
    private Frame parentFrame;
    private TextField username;
    private Button backToHostList;
    private Button mainMenu;
    private List<Optional<Player>> playerList;
    private ScrollPane s;
    private Label cLabel;
    private boolean active = false;

    public WaitingRoom(Frame f) {
        setLayout(null);

        parentFrame = f;

        username = new TextField("unnammed");
        username.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (active) {
                    sendName();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (active) {
                    sendName();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (active) {
                    sendName();
                }
            }
        });
        add(username);

        backToHostList = new Button("Back");
        backToHostList.addActionListener(e -> {
            parentFrame.hostManager().sendEvent(new ClientEvent("quit", "quit"), true);
            parentFrame.showPanel("Join Menu");
        });
        add(backToHostList);

        mainMenu = new Button("Main Menu");
        mainMenu.addActionListener(e -> parentFrame.showPanel("Main Menu"));
        add(mainMenu);

        playerList = new List<>(new ArrayList<>(), e -> {
            return e.isEmpty() ? username.getText() + " (you)" : e.get().username();
        });
        s = new ScrollPane(playerList);
        add(s);

        cLabel = new Label("Players:");
        add(cLabel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (active) {
                    arrangeComponents();
                }
            }
        });
    }

    private void arrangeComponents() {
        int cX = getWidth() / 2;
        int cY = getHeight() / 2;

        username.setBounds(cX - 425, cY - 200, 400, 100);
        mainMenu.setBounds(cX - 425, cY - 50, 400, 100);
        backToHostList.setBounds(cX - 425, cY + 100, 400, 100);

        s.setBounds(cX + 25, cY - 200, 400, 400);
        cLabel.setBounds(cX + 25, cY - 250, 400, 50);
    }

    @Override
    public void setActive(boolean a) {
        active = a;
        if (a) {
            arrangeComponents();
            if(parentFrame.hostManager() != null) {
                parentFrame.hostManager().stop();
            }
            parentFrame.setHostManager(new HostManager(parentFrame.host(), playerList, ()->parentFrame.showPanel("Game")));
            parentFrame.hostManager().addMeeee();
            playerList.setList(parentFrame.hostManager().players());
        }
    }

    private void sendName() {
        if (username.getText().length() >= 1 && active) {
            parentFrame.setNameText(username.getText());
            parentFrame.hostManager().sendEvent(new ClientEvent("setname", username.getText()), true);
            playerList.update();
        }
    }

    public void setNameText(String text) {
        username.setText(text);
    }
}