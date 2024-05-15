package comms;

import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;

public class Client implements Runnable {
    private Socket socket;
    private volatile boolean running = true;
    private ClientManager clientManager;
    private PrintWriter p;
    private String name = "unnamed";

    public Client(Socket socket, ClientManager cm) {
        this.socket = socket;
        clientManager = cm;
        try {
            p = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Scanner s = new Scanner(socket.getInputStream(), HostBroadcaster.charset);
            while (running) {
                ClientEvent e = ClientEvent.parse(s.nextLine());
                if(e.type().equals("quit")) {
                    running = false;
                } else if(e.type().equals("setname")) {
                    name = e.valueString();
                }
                clientManager.recieveEvent(e, this);
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    public void stop() {
        running = false;
    }

    public void sendMessage(HostEvent e) {
        p.println(e.toString());
    }

    @Override
    public String toString() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }
}
