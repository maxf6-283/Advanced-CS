import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class ChatBot {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(1024);
            server.setSoTimeout(0);
            Socket client = server.accept();
            PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
            Scanner reader = new Scanner(client.getInputStream());
            HashMap<String, String> keyWords = new HashMap<>();

            keyWords.put("problem", "Can you elaborate more on this problem?");
            keyWords.put("hi", "Hello there!");
            keyWords.put("hello", "Hello there!");
            keyWords.put("joke", "What do you call a pony with a cough?");
            keyWords.put("little horse", "Right!");

            mainloop: while (true) {
                String resp = reader.nextLine();
                System.out.println(resp);
                if (resp.toLowerCase().contains("bye")) {
                    writer.println("Bye-bye!");
                    break mainloop;
                }
                for (String k : keyWords.keySet()) {
                    if (resp.toLowerCase().contains(k)) {
                        writer.println(keyWords.get(k));
                        System.out.println(keyWords.get(k));
                        continue mainloop;
                    }
                }
                System.out.println("No keywords");
                writer.println("I didn't get that. What do you mean?");

            }

            server.close();
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
