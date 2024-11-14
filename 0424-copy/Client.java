import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client extends JFrame implements ActionListener {
    private static String ip = "localhost";
    private static int port = 1024;

    private Socket server;
    private Scanner reader;
    private PrintWriter writer;

    private JTextArea chat;
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
        chat.setBounds(50, 50, 200, 400);
        chat.setFocusable(false);
        add(chat);

        message = new JTextField();
        message.setBounds(50, 500, 200, 50);
        add(message);
        message.addActionListener(this);

        setSize(300, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == message) {
            if(chat.getText().equals("Enter a username")) {
                userName = message.getText();
                message.setText("");
                chat.append("\n" + userName);
                Thread readThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            chat.append("\n" + reader.nextLine());
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