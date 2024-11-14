package gui;

import comms.ClientEvent;
import comms.HostBroadcaster;
import comms.HostEvent;
import comms.HostManager;
import game.Player;
import utils.ArrayList;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.InetSocketAddress;
import java.util.Optional;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class HostMenu extends Panel {
    private Frame parentFrame;
    private HostBroadcaster hoster;
    private TextField username;
    private Button startGame;
    private Button mainMenu;
    private List<Optional<Player>> clientList;
    private ScrollPane s;
    private Label cLabel;
    private boolean active = false;

    public HostMenu(Frame f) {
        setLayout(null);
        parentFrame = f;

        username = new TextField("unnamed");
        username.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(active) {
                    sendName();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(active) {
                    sendName();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(active) {
                    sendName();
                }
            }
        });
        add(username);

        startGame = new Button("Start Game");
        startGame.addActionListener(e -> {
            hoster.sendMessage(new HostEvent("startgame", -1, "nullius"));
        });
        add(startGame);

        mainMenu = new Button("Main Menu");
        mainMenu.addActionListener(e -> parentFrame.showPanel("Main Menu"));
        add(mainMenu);

        clientList = new List<>(new ArrayList<>(), e -> {
            return e.isEmpty() ? username.getText() + " (you)" : e.get().username();
        });

        s = new ScrollPane(clientList);
        add(s);

        cLabel = new Label("Players:");
        add(cLabel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if(active) {
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
        startGame.setBounds(cX - 425, cY + 100, 400, 100);

        s.setBounds(cX + 25, cY - 200, 400, 400);
        cLabel.setBounds(cX + 25, cY - 250, 400, 50);
    }

    private void cancelHosting() {
        hoster.stop();
    }

    private void startHosting() {
        hoster = new HostBroadcaster(username.getText());
        if(parentFrame.hostManager() != null) {
            parentFrame.hostManager().stop();
        }
        parentFrame.setHostManager(new HostManager(new InetSocketAddress("127.0.0.1", hoster.getPort()), clientList, ()->parentFrame.showPanel("Game")));
        parentFrame.hostManager().addMeeee();
        clientList.setList(parentFrame.hostManager().players());
        (new Thread(hoster)).start();
        sendName();
        arrangeComponents();
    }

    public void setActive(boolean a) {
        active = a;
        if(a == false) {
            cancelHosting();
        } else {
            startHosting();
        }
    }

    private void sendName() {
        if(username.getText().length() >= 1 && active) {
            parentFrame.setNameText(username.getText());
            parentFrame.hostManager().sendEvent(new ClientEvent("setname", username.getText()), true);
            hoster.setName(username.getText());
            clientList.update();
        }
    }

    public void setNameText(String text) {
        username.setText(text);
    }
}
