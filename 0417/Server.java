import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static final String[] questions = { "Where is the Statue of Liberty", "New York",
            "Where is the Sydney Opera House", "Sydney",
            "Where is the Eifel Tower", "Paris",
            "Where is the Taj Mahal", "Agra",
            "Where is Machu Picchu", "Peru",
            "Where is the Great Wall of China", "China",
            "Where is Mount Rushmore", "South Dakota",
            "Where is Mont-Saint-Michel", "Normandy",
            "Where is the Acropolis", "Athens",
            "Where is the Brandenburg Gate", "Berlin" };

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1024);

            while (true) {
                System.out.println("Waiting for a connection...");

                Socket clientSocket = serverSocket.accept();

                (new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int[] questionIndicies = new int[10];
                        for (int i = 0; i < 10; i++) {
                            questionIndicies[i] = i;
                        }
                        for (int i = 0; i < 10; i++) {
                            int temp = questionIndicies[i];
                            int j = (int)(Math.random() * 10);
                            questionIndicies[i] = questionIndicies[j];
                            questionIndicies[j] = temp;
                        }
                        try {
                            Scanner sc = new Scanner(clientSocket.getInputStream());
                            PrintWriter pr = new PrintWriter(clientSocket.getOutputStream(), true);
                            for(int i = 0; i < 10; i++) {
                                pr.println(questions[questionIndicies[i] * 2]);
                                if(sc.nextLine().equalsIgnoreCase(questions[questionIndicies[i] * 2 + 1])) {
                                    pr.println("Correct!");
                                } else {
                                    pr.println("Incorrect!");
                                    clientSocket.close();
                                    sc.close();
                                    return;
                                }
                            }
                            sc.close();
                            pr.println("Congratulations: you got them all right! (:");
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                })).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}