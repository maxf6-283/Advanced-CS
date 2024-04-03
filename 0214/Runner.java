import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph<Friend> graph = new Graph<>();

        Friend f1 = new Friend('A', "John");
        Friend f2 = new Friend('B', "Jen");
        Friend f3 = new Friend('C', "Jose");
        Friend f4 = new Friend('D', "Jay");
        Friend f5 = new Friend('E', "Jason");
        Friend f6 = new Friend('F', "Jackson");
        Friend f7 = new Friend('G', "Jonah");

        graph.add(f1);
        graph.add(f2);
        graph.add(f3);
        graph.add(f4);
        graph.add(f5);
        graph.add(f6);
        graph.add(f7);

        graph.addEdge(f1, f2);
        graph.addEdge(f1, f3);
        graph.addEdge(f2, f3);
        graph.addEdge(f2, f4);
        graph.addEdge(f2, f5);
        graph.addEdge(f3, f6);
        graph.addEdge(f3, f7);


        System.out.println(graph.toString());
        System.out.println("Enter a name: ");
        String name = sc.nextLine();
        sc.close();
        Friend toSearchFor = new Friend((char)0, name);
        graph.BSF(f1, toSearchFor);
    }
}