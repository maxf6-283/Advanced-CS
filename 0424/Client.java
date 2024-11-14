import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;

public class Client extends JFrame implements ActionListener {
    private static String ip = "10.210.98.213";
    private static int port = 1024;

    private Socket server;
    private Scanner reader;
    private PrintWriter writer;
    private volatile JTextArea chat;
    private volatile JScrollPane scrollPane;
    private JTextField message;
    private String userName;

    public static void main(String[] args) {
        new Client();
    }

    public Client() {
        setLayout(null);

        try {
            server = new Socket(ip, port);
            reader = new Scanner(server.getInputStream());
            writer = new PrintWriter(server.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        chat = new JTextArea("Enter a username");
        scrollPane = new JScrollPane(chat);
        scrollPane.setBounds(50, 50, 500, 600);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        chat.setFocusable(false);
        add(scrollPane);

        message = new JTextField();
        message.setBounds(50, 700, 500, 50);
        add(message);
        message.addActionListener(this);

        setSize(600, 800);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == message) {
            if (chat.getText().equals("Enter a username")) {
                userName = message.getText();
                message.setText("");
                chat.append("\n" + userName);
                writer.println(userName);
                Thread readThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            chat.append("\n" + reader.nextLine() + " ");
                            chat.update(chat.getGraphics());
                            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
                        }
                    }

                });
                readThread.start();
            } else {
                writer.println(userName + ": " + message.getText());
                message.setText("");
            }
        }
    }

}