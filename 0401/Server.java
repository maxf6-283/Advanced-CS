import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(1024);
            server.setSoTimeout(0);
            Socket client = server.accept();
            PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
            Scanner reader = new Scanner(client.getInputStream());

            String[] questions = new String[] { "What is the capital of California?",
                    "What is the capital of Washington?",
                    "What is the capital of New York?",
                    "What is the capital of Maine?",
                    "What is the capital of Texas?" };

            String[] answers = new String[] {"Sacramento", "Olympia", "Albany", "Augusta", "Houston"};

            for(int i = 0; i < questions.length; i++) {
                writer.println(questions[i]);
                System.out.println("Sent question");
                if(reader.nextLine().equalsIgnoreCase(answers[i])) {
                    writer.println("Correct!");
                    System.out.println("Correct");
                } else {
                    writer.println("Incorrect");
                    System.out.println("Incorrect");
                }
            }

            server.close();
            reader.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}