import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatServer extends JFrame implements ActionListener{
    private static int port = 1024;

    private Socket server;
    private Scanner reader;
    private PrintWriter writer;

    private JTextArea chat;
    private JTextField message;

    public static void main(String[] args) {
        new ChatServer();
    }

    public ChatServer() {
        setLayout(null);

        
        chat = new JTextArea();
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
        
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            server = serverSocket.accept();
            chat.append("Connected!\n");
            serverSocket.close();
            reader = new Scanner(server.getInputStream());
            writer = new PrintWriter(server.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    chat.append("Client: " + reader.nextLine() + "\n");
                }
            }

        });
        readThread.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == message) {
            writer.println(message.getText());
            chat.append("Server: " + message.getText() + "\n");
            message.setText("");
        }
    }

}