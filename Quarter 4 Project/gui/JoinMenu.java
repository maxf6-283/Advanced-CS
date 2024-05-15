package gui;

import comms.Host;
import comms.HostFinder;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class JoinMenu extends Panel {
    private Frame parentFrame;
    private HostFinder hostFinder;
    private TextField username;
    private Button joinGame;
    private Button mainMenu;
    private List<Host> hostList;
    private ScrollPane s;
    private Label cLabel;
    private boolean active = false;

    public JoinMenu(Frame f) {
        setLayout(null);

        parentFrame = f;

        username = new TextField("unnamed");
        username.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setName();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setName();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setName();
            }

        });
        add(username);

        joinGame = new Button("Join Game");
        joinGame.setEnabled(false);
        joinGame.addActionListener(e -> parentFrame.showPanel("Waiting Room"));
        add(joinGame);

        mainMenu = new Button("Main Menu");
        mainMenu.addActionListener(e -> parentFrame.showPanel("Main Menu"));
        add(mainMenu);

        hostFinder = new HostFinder(e -> hostList.update(), e -> hostList.update(), e -> hostList.update());
        (new Thread(hostFinder)).start();

        hostList = new List<>(hostFinder.hosts());
        hostList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        hostList.addListSelectionListener(e -> {
            joinGame.setEnabled(hostList.getSelectedValue() != null);
        });
        s = new ScrollPane(hostList);
        add(s);

        cLabel = new Label("Hosts:");
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

    private void setName() {
        if (username.getText().length() >= 1 && active) {
            parentFrame.setNameText(username.getText());
        }
    }

    private void arrangeComponents() {
        int cX = getWidth() / 2;
        int cY = getHeight() / 2;

        username.setBounds(cX - 425, cY - 200, 400, 100);
        mainMenu.setBounds(cX - 425, cY - 50, 400, 100);
        joinGame.setBounds(cX - 425, cY + 100, 400, 100);

        s.setBounds(cX + 25, cY - 200, 400, 400);
        cLabel.setBounds(cX + 25, cY - 250, 400, 50);
    }

    public void setActive(boolean a) {
        active = a;
        if (a) {
            arrangeComponents();
        }
    }

    public void setNameText(String text) {
        username.setText(text);
    }

    public Host selectedHost() {
        return hostList.getSelected();
    }
}
