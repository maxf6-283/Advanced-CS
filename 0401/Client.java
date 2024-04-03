import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String ip = "localhost";
        int port = 1024;

        try {
            Socket server = new Socket(ip, port);
            Scanner reader = new Scanner(server.getInputStream());
            PrintWriter writer = new PrintWriter(server.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);
            while(true) {
                String answer = sc.nextLine();
                writer.println(answer);
                String resp = reader.nextLine();
                System.out.println(resp);
                if(resp.equals("Bye-bye!")) {
                    break;
                }
            }

            server.close();
            reader.close();
            sc.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
