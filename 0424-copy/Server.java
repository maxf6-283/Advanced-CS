import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.net.Socket;
import java.io.PrintWriter;
import java.util.Scanner;

public class Server {
    private static volatile ArrayList<PrintWriter> sockets = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1024);
            while(!serverSocket.isClosed()) {
                Thread thread = new Thread(new ClientManager(serverSocket.accept()));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientManager implements Runnable {
        private Socket s;

        public ClientManager(Socket s) {
            this.s = s;
            try {
                sockets.add(new PrintWriter(s.getOutputStream(), true));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                Scanner sc = new Scanner(s.getInputStream());

                while(true) {
                    String msg = sc.nextLine();
                    if(msg.toLowerCase().contains("bye")) {
                        sc.close();
                        s.close();
                        return;
                    }
                    for(PrintWriter p : sockets) {
                        p.println(msg);
                        
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}