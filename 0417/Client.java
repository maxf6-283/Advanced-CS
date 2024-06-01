import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1024);

            Scanner inp = new Scanner(socket.getInputStream());
            Scanner sc = new Scanner(System.in);
            PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);

            while(!socket.isClosed()) {
                try {
                    System.out.println(inp.nextLine());
                } catch (NoSuchElementException e) {
                    socket.close();
                    inp.close();
                    sc.close();
                    return;
                }
                pr.println(sc.nextLine());
                try {
                    System.out.println(inp.nextLine());
                } catch (NoSuchElementException e) {
                    socket.close();
                    inp.close();
                    sc.close();
                    return;
                }
            }

            socket.close();
            inp.close();
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
