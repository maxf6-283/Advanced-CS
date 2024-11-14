import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.MouseAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Screen extends JPanel {
    private Game game;
    private ObjectOutputStream oOut;
    private volatile boolean myTurn = false;
    private char winner = ' ';

    public Screen() {
        game = new Game();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (myTurn && winner == ' ') {
                    int row = e.getY() * 3 / getHeight();
                    int col = e.getX() * 3 / getWidth();

                    game.insertXO(row, col);
                    myTurn = false;
                    try {
                        oOut.reset();
                        oOut.writeObject(game);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    winner = game.winner();
                    repaint();
                }
            }
        });

        setPreferredSize(new Dimension(400, 400));
    }

    public void startServer(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket client = serverSocket.accept();
            serverSocket.close();

            // start by sending the client if they are X or O
            char clientChar = (Math.random() < 0.5) ? 'X' : 'O';
            client.getOutputStream().write(clientChar);

            myTurn = clientChar == 'O';

            Container j = getParent();
            while (!(j instanceof JFrame)) {
                j = j.getParent();
            }
            ((JFrame) j).setTitle("You are " + (myTurn ? 'X' : 'O'));

            oOut = new ObjectOutputStream(client.getOutputStream());

            ObjectInputStream oIn = new ObjectInputStream(client.getInputStream());

            while (true) {
                Object o = oIn.readObject();
                if (o instanceof Game g) {
                    game = g;
                    myTurn = true;
                    winner = g.winner();
                    repaint();
                } else {
                    System.err.println("Non-game object passed!");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void startClient(String ip, int port) {
        try {
            Socket server = new Socket(ip, port);

            myTurn = server.getInputStream().read() == 'X';
            Container j = getParent();
            while (!(j instanceof JFrame)) {
                j = j.getParent();
            }
            ((JFrame) j).setTitle("You are " + (myTurn ? 'X' : 'O'));

            ObjectInputStream oIn = new ObjectInputStream(server.getInputStream());

            oOut = new ObjectOutputStream(server.getOutputStream());

            while (true) {
                Object o = oIn.readObject();
                if (o instanceof Game g) {
                    game = g;
                    myTurn = true;
                    winner = g.winner();
                    repaint();
                } else {
                    System.err.println("Non-game object passed!");
                    server.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        game.drawMe(g, 0, 0, getWidth(), getHeight());
        if(winner != ' ') {
            g.setColor(Color.GRAY);
            g.setFont(new Font("Futura", Font.BOLD, 40));
            g.drawString(winner + " is the winner!", getWidth() / 2 - 150, getHeight() / 2 - 10);
        }
    }
}
