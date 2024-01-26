import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BinaryTree<Profile> tree = new BinaryTree<>();
        tree.add(new Profile("Potter", "Harry"));
        tree.add(new Profile("Potter", "James"));
        tree.add(new Profile("Potter", "Lily"));
        tree.add(new Profile("Malfoy", "Draco"));
        tree.add(new Profile("Malfoy", "Lucius"));
        tree.add(new Profile("Weasley", "Ronald"));

        System.out.println(tree);
        System.out.println("Enter a first name");
        String fName = sc.nextLine();
        System.out.println("Enter a last name");
        System.out.println("The tree " + (tree.contains(new Profile(sc.nextLine(), fName)) ? "does" : "does not")
                + " contain that name");
        sc.close();
    }
}
