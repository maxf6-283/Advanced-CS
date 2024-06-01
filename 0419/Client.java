import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Client extends JFrame implements ActionListener {
    private static String ip = "localhost";
    private static int port = 1024;

    private Socket server;
    private Scanner reader;
    private PrintWriter writer;

    private ImageShower imageShower;
    private JTextField message;

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

        imageShower = new ImageShower();
        imageShower.setBounds(50, 50, 200, 400);
        add(imageShower);

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
                    if(reader.nextLine().equals("victory")) {
                        try {
                            imageShower.image1 = ImageIO.read(new File("images/VIC.jpg"));
                            imageShower.image2 = imageShower.image1;
                            imageShower.image3 = imageShower.image1;
                            repaint();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    try {
                        imageShower.image1 = ImageIO.read(new File("images/" + reader.nextLine()));
                        imageShower.image2 = ImageIO.read(new File("images/" + reader.nextLine()));
                        imageShower.image3 = ImageIO.read(new File("images/" + reader.nextLine()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    repaint();
                    reader.nextLine();
                    if(reader.nextLine().equals("incorrect")) {
                        reader.close();
                        System.exit(0);
                        return;
                    }

                }
            }

        });
        readThread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == message) {
            writer.println(message.getText());
            message.setText("");
        }
    }

    private class ImageShower extends JPanel {
        public BufferedImage image1;
        public BufferedImage image2;
        public BufferedImage image3;

        @Override
        public void paintComponent(Graphics g) {
            if(image1 == null || image2 == null || image3 == null) {
                return;
            }
            g.drawImage(image1, 0, 0, image1.getWidth()*(getHeight()/3)/image1.getHeight(), getHeight()/3, null);
            g.drawImage(image2, 0, getHeight()/3, image2.getWidth()*(getHeight()/3)/image2.getHeight(), getHeight()/3, null);
            g.drawImage(image3, 0, getHeight()/3*2, image3.getWidth()*(getHeight()/3)/image3.getHeight(), getHeight()/3, null);
        }
    }

}