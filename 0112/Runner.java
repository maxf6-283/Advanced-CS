import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map;

public class Runner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Map<State, Info> m = new TreeMap<State, Info>();
        State st1 = new State("California");
        Info i1 = new Info(39240000, "Sacramento", "California");
        m.put(st1, i1);
        State st2 = new State("Illinois");
        Info i2 = new Info(12670000, "Chicago", "Illinois");
        m.put(st2, i2);
        State st3 = new State("Washington");
        Info i3 = new Info(7739000, "Seattle", "Washington");
        m.put(st3, i3);

        mainloop: while (true) {
            System.out.println(
                    "Select an option:\n\t1. View all states (name only)\n\t2. Add a new state\n\t3. Search for a state\n\t4. Delete a state\n\t5. Quit");
            int ans = sc.nextInt();
            sc.nextLine();
            switch (ans) {
                case 1 -> {
                    for (State state : m.keySet()) {
                        System.out.println(state);
                    }
                }
                case 2 -> {
                    System.out.println("Enter the name of the state.");
                    String name = sc.nextLine();
                    System.out.println("Enter the capital of the state.");
                    String capital = sc.nextLine();
                    System.out.println("Enter the population of the state.");
                    int population = sc.nextInt();
                    sc.nextLine();

                    m.put(new State(name), new Info(population, capital, name));
                }
                case 3 -> {
                    System.out.println("Enter the name of the state.");
                    State state = new State(sc.nextLine());
                    if (m.containsKey(state)) {
                        System.out.println(m.get(state));
                    } else {
                        System.out.println("No state by that name.");
                    }
                }
                case 4 -> {
                    System.out.println("Enter the name of the state.");
                    State state = new State(sc.nextLine());
                    if (m.containsKey(state)) {
                        m.remove(state);
                    } else {
                        System.out.println("No state by that name.");
                    }
                }
                case 5 -> {
                    break mainloop;
                }
                default -> {
                    System.out.println("Not a valid option");
                }
            }
        }
        
        sc.close();
    }
}