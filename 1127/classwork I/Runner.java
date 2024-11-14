import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DLList<String> toDos = readList();

        mainloop: while (true) {
            System.out.println("Select an option:\n1. View the list of tasks.\n2. Add a task\n3. Quit.");
            int ans = sc.nextInt();
            sc.nextLine();
            switch (ans) {
                case 1 -> {
                    System.out.println(toDos.toString().replaceAll(", ?", "\n").replaceAll("[\\[\\]]", ""));
                }
                case 2 -> {
                    System.out.println("Enter the task.");
                    toDos.add(sc.nextLine());
                    saveList(toDos);
                }
                case 3 -> {
                    break mainloop;
                }
            }
        }

        sc.close();
    }

    private static void saveList(DLList<String> list) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ToDos.dat"));

            out.writeObject(list);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static DLList<String> readList() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("ToDos.dat"));
            DLList<String> toRet = (DLList<String>)in.readObject();
            in.close();
            return toRet;
        } catch (FileNotFoundException e) {
            return new DLList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
