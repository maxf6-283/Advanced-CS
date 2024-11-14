package comms;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class HostBroadcaster implements Runnable {
    public static String hostingMsg = "AsteroidGameHosting";
    public static InetAddress group;
    public static int port = 4096;
    public static Charset charset = StandardCharsets.UTF_8;
    public static String sep = new String(new char[]{0});
    
    private MulticastSocket multicastSocket;
    private DatagramPacket messageForClients;
    
    private ServerSocket serverSocket;
    
    private ClientManager c;
    private String name;

    private volatile boolean running = true;

    static {
        try {
            group = InetAddress.getByName("230.1.1.1");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public HostBroadcaster(String name) {
        this.name = name;
        c = new ClientManager();

        try {
            multicastSocket = new MulticastSocket();
            multicastSocket.setBroadcast(true);

            serverSocket = new ServerSocket(0);
            serverSocket.setSoTimeout(100);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void run() {
        // Start accepting clients
        (new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        c.addClient(clientSocket);
                    } catch (SocketTimeoutException e) {
                        // do nothing
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        })).start();

        // broadcast message for clients
        try {
            byte[] bytes = (hostingMsg + sep + InetAddress.getLocalHost().getHostAddress() + sep
                    + serverSocket.getLocalPort()
                    + sep + name).getBytes(charset);
            messageForClients = new DatagramPacket(bytes, bytes.length, group, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        while (running) {
            try {
                Thread.sleep(100);
                multicastSocket.send(messageForClients);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        byte[] bytes;
        try {
            bytes = (hostingMsg + sep + InetAddress.getLocalHost().getHostAddress() + sep
                    + serverSocket.getLocalPort()
                    + sep + name).getBytes(StandardCharsets.UTF_8);
            messageForClients = new DatagramPacket(bytes, bytes.length, group, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void restart() {
        running = true;
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void sendMessage(HostEvent e) {
        c.sendMessage(e);
    }
}