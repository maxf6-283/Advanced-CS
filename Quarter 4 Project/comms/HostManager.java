package comms;

import java.io.IOException;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Optional;

import game.Player;
import gui.List;
import utils.ArrayList;

public class HostManager implements Runnable {
    private Socket socket;
    private PrintWriter p;
    private ArrayList<Optional<Player>> players;
    private volatile boolean running = true;
    private List<Optional<Player>> ps;
    private Runnable r;
    private Player winner;

    public HostManager(Host h, List<Optional<Player>> pList, Runnable onStartGame) {
        this(h.getAddress(), pList, onStartGame);
    }

    public HostManager(InetSocketAddress i, List<Optional<Player>> pList, Runnable onStartGame) {
        players = new ArrayList<>();
        try {
            socket = new Socket(i.getAddress(), i.getPort());
            p = new PrintWriter(socket.getOutputStream(), false, HostBroadcaster.charset);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.ps = pList;
        r = onStartGame;
    }

    public void sendEvent(ClientEvent e, boolean flush) {
        p.println(e.toString());
        if(flush) {
            p.flush();
        }
    }

    public void flush() {
        p.flush();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        try (Scanner sc = new Scanner(socket.getInputStream())) {
            while (running) {
                HostEvent h = HostEvent.parse(sc.nextLine());
                // System.out.println("Received event " + h.toString().replace(HostBroadcaster.sep, "-"));
                if (h.playerNum() < 0) {
                    // server-driven events
                    if (h.type().equals("startgame")) {
                        r.run();
                    }
                } else if (h.type().equals("create")) {
                    if (h.playerNum() == players.size()) {
                        players.add(Optional.of(new Player(h)));
                    } else if (h.playerNum() == players.size() + 1) {
                        // clearly you don't own an air fryer
                        players.add(Optional.empty());
                        players.add(Optional.of(new Player(h)));
                    } else if (h.playerNum() == players.size() - 1) {
                        // clearly you own too many air fryers
                        if (!players.remove(Optional.empty())) {
                            throw new IllegalArgumentException(
                                    "Player num " + h.playerNum() + " does not match size of array " + players.size());
                        }
                        players.add(Optional.of(new Player(h)));
                        players.add(Optional.empty());
                    } else {
                        throw new IllegalArgumentException(
                                "Player num " + h.playerNum() + " does not match size of array " + players.size());
                    }
                    ps.update();
                } else if (h.type().equals("quit")) {
                    players.remove(h.playerNum());
                    ps.update();
                } else {
                    players.get(h.playerNum()).get().acceptHostEvent(h);
                    if(h.type().equals("WINNER")) {
                        System.out.println("Recieved winner");
                        winner = players.get(h.playerNum()).get();
                    }
                    if (h.type().equals("setname")) {
                        ps.update();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMeeee() {
        players.add(Optional.empty());
        ps.update();
    }

    public ArrayList<Optional<Player>> players() {
        return players;
    }

    public Player winner() {
        return winner;
    }
}
