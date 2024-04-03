import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatClient extends JFrame implements ActionListener {
    private static String ip = "10.210.98.213";
    private static int port = 1024;

    private Socket server;
    private Scanner reader;
    private PrintWriter writer;

    private JTextArea chat;
    private JTextField message;

    public static void main(String[] args) {
        new ChatClient();
    }

    public ChatClient() {
        setLayout(null);

        try {
            server = new Socket(ip, port);
            reader = new Scanner(server.getInputStream());
            writer = new PrintWriter(server.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        chat = new JTextArea("Connected!\n");
        chat.setBounds(50, 50, 200, 400);
        add(chat);

        message = new JTextField();
        message.setBounds(50, 500, 200, 50);
        add(message);
        message.addActionListener(this);

        setSize(300, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    chat.append("Server: " + reader.nextLine() + "\n");
                }
            }

        });
        readThread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == message) {
            writer.println(message.getText());
            chat.append("Client: " + message.getText() + "\n");
            message.setText("");
        }
    }

}