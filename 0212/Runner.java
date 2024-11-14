import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph<String> g = new Graph<>();

        g.add("Jose");
        g.add("John");
        g.add("Jane");
        g.addEdge("Jose", "John");
        g.addEdge("Jose", "Jane");
        g.addEdge("Jane", "John");

        mainloop: while (true) {
            System.out.println(
                    "What would you like to do?\n\t1. Add a node\n\t2. Add an adge\n\t3. Remove a node\n\t4. View graph\n\t5. Quit\n");

            int ans = sc.nextInt();
            sc.nextLine();
            switch (ans) {
                case 1 -> {
                    System.out.print("Enter a name: ");
                    String name = sc.nextLine();

                    g.add(name);
                }
                case 2 -> {
                    System.out.print("Enter first node: ");
                    String name1 = sc.nextLine();
                    System.out.print("Enter second node: ");
                    String name2 = sc.nextLine();

                    g.addEdge(name1, name2);
                }
                case 3 -> {
                    System.out.print("Enter a name: ");
                    String name = sc.nextLine();
                    g.remove(name);
                }
                case 4 -> {
                    System.out.println(g);
                }
                case 5 -> {
                    break mainloop;
                }
            }
        }
        sc.close();
    }
}