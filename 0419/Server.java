import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static int port = 1024;

    private ServerSocket serverSocket;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {

        try {
            serverSocket = new ServerSocket(port);
            int clientNum = 0;
            while (true) {
                Socket client = serverSocket.accept();
                (new Thread(new ClientHandler(client, clientNum))).start();
                clientNum++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class ClientHandler implements Runnable {
        private static String[][] locationImages = {{"DC.jpg","DC2.jpg","DC3.jpg"},{"NYC.jpg","NYC2.jpg","NYC3.jpg"}, {"SF.jpg","SF2.jpg","SF3.jpg"}};
        private static String[] locations = {"Washington DC","New York City","San Fransisco"};
        private Socket client;
        private int num;

        public ClientHandler(Socket client, int num) {
            this.client = client;
            this.num = num;
        }

        @Override
        public void run() {
            try {
                PrintWriter pr = new PrintWriter(client.getOutputStream(), true);
                Scanner sc = new Scanner(client.getInputStream());
                
                int[] locIndicies = {0,1,2};
                for(int i = 0; i < locIndicies.length; i++) {
                    int j = (int)(Math.random() * 3);
                    int temp = locIndicies[j];
                    locIndicies[j] = locIndicies[i];
                    locIndicies[i] = temp;
                }

                for(int i : locIndicies) {
                    System.out.println("Sending client " + num + " location " + locations[i]);
                    pr.println("BEGIN IMAGES");
                    for(String s : locationImages[i]) {
                        pr.println(s);
                    }
                    pr.println("END IMAGES");

                    String ans = sc.nextLine();
                    if(ans.equalsIgnoreCase(locations[i])) {
                        System.out.println("Client " + num + " correctly answered " + ans);
                        pr.println("correct");
                    } else {
                        System.out.println("Client " + num + " incorrectly answered " + ans);
                        pr.println("incorrect");
                        return;
                    }
                }

                pr.println("victory");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}