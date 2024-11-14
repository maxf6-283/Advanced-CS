import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.net.Socket;
import java.io.PrintWriter;
import java.util.Scanner;

public class Server {
    private static volatile ArrayList<ClientManager> sockets = new ArrayList<>();
    private static volatile ArrayList<ClientManager> players = new ArrayList<>();
    private static volatile int playersReady;
    private static volatile ArrayList<String> cities = new ArrayList<>();
    private static volatile String selectedCity = null;
    private static volatile boolean playing = false;
    private static volatile ClientManager player;

    public static void main(String[] args) {
        cities.add("San Francisco");
        cities.add("Houston");
        cities.add("New York");
        cities.add("New Orleans");
        cities.add("Portland");

        try {
            ServerSocket serverSocket = new ServerSocket(1024);
            while (!serverSocket.isClosed()) {
                Thread thread = new Thread(new ClientManager(serverSocket.accept()));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static class ClientManager implements Runnable {
        private Socket s;
        private PrintWriter pr;
        private String name;
        private boolean ready = false;

        public ClientManager(Socket s) {
            this.s = s;
            sockets.add(this);
        }

        public PrintWriter printWriter() {
            return pr;
        }

        public String name() {
            return name;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            try {
                Scanner sc = new Scanner(s.getInputStream());
                pr = new PrintWriter(s.getOutputStream(), true);

                name = sc.nextLine();

                pr.println("Ready?");

                while (true) {
                    String msg = sc.nextLine();
                    if (msg.toLowerCase().contains("bye")) {
                        sc.close();
                        s.close();
                        return;
                    }
                    if (ready) {
                        if (this == player) {
                            if (msg.toLowerCase().contains(selectedCity.toLowerCase())) {
                                pr.println("You cannot say the name of the city!");
                                continue;
                            }
                        }
                        for (ClientManager p : sockets) {
                            p.printWriter().println(msg);
                        }
                        if (playing) {
                            if (msg.toLowerCase().contains(selectedCity.toLowerCase())) {
                                pr.println("That's right!");
                                for (ClientManager p : sockets) {
                                    p.printWriter().println(name + " guessed the city " + selectedCity + " correctly!");
                                }
                                play();
                            }
                        }
                    }
                    if (!ready && msg.toLowerCase().contains("ready")) {
                        ready = true;
                        playersReady++;
                        if (playersReady == sockets.size()) {
                            playing = true;
                            players = (ArrayList<ClientManager>) sockets.clone();
                            play();
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @SuppressWarnings("unchecked")
        private void play() {
            player = players.get((int) (Math.random() * players.size()));
            selectedCity = cities.get((int) (Math.random() * cities.size()));
            player.printWriter().println("You are trying to get the other players to guess the city " + selectedCity);
            players.remove(player);
            if (players.isEmpty()) {
                players = (ArrayList<ClientManager>) sockets.clone();
            }
            for (ClientManager c : sockets) {
                if (c != player) {
                    c.printWriter()
                            .println("You are trying to guess the city from " + player.name() + "'s hints");
                }
            }
        }

    }
}