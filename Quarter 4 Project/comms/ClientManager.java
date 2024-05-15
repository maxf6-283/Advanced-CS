package comms;

import utils.ArrayList;

import java.net.Socket;

public class ClientManager {
    private ArrayList<Client> clients;

    public ClientManager() {
        clients = new ArrayList<>();
    }

    public synchronized void addClient(Socket s) {
        System.out.println("ADDING NEW CLIENT!");
        Client cl = new Client(s, this);
        (new Thread(cl)).start();
        
        //send all clients a creation event
        HostEvent creationEvent = new HostEvent("create", clients.size(), "unnamed");
        for(Client c : clients) {
            c.sendMessage(creationEvent);
        }
        
        //send the client all creation events
        for(int i = 0 ; i < clients.size(); i++) {
            HostEvent e = new HostEvent("create", i, clients.get(i).getName());
            cl.sendMessage(e);
        }
        
        clients.add(cl);
    }

    public synchronized void recieveEvent(ClientEvent e, Client sender) {
        HostEvent event = new HostEvent(e.type(), clients.indexOf(sender), e.valueString());
        for(Client c : clients) {
            if(c != sender) {
                c.sendMessage(event);
            }
        }
        if(e.type().equals("quit")) {
            clients.remove(event.playerNum());
        }
    }

    public synchronized void sendMessage(HostEvent e) {
        //just send it to all clientele
        for(Client c : clients) {
            c.sendMessage(e);
        }
    }
}
