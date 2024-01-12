import java.util.Scanner;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

public class RunnerII {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Set<StateII> set = new TreeSet<StateII>();
        Map<StateII, Info> map = new HashMap<StateII, Info>();
        StateII st1 = new StateII("California", "CA");
        Info i1 = new Info(39240000, "Sacramento", "California");
        map.put(st1, i1);
        set.add(st1);
        StateII st2 = new StateII("Illinois", "IL");
        Info i2 = new Info(12670000, "Chicago", "Illinois");
        map.put(st2, i2);
        set.add(st2);
        StateII st3 = new StateII("Washington", "WA");
        Info i3 = new Info(7739000, "Seattle", "Washington");
        map.put(st3, i3);
        set.add(st3);

        mainloop: while (true) {
            System.out.println(
                    "Select an option:\n\t1. View all States (name only)\n\t2. Add a new State\n\t3. Search for a State\n\t4. Delete a State\n\t5. Quit");
            int ans = sc.nextInt();
            sc.nextLine();
            switch (ans) {
                case 1 -> {
                    for (StateII StateII : set) {
                        System.out.println(StateII);
                    }
                }
                case 2 -> {
                    System.out.println("Enter the name of the State.");
                    String name = sc.nextLine();
                    System.out.println("Enter the abbreviation of the State.");
                    String abbr = sc.nextLine();
                    System.out.println("Enter the capital of the State.");
                    String capital = sc.nextLine();
                    System.out.println("Enter the population of the State.");
                    int population = sc.nextInt();
                    sc.nextLine();

                    StateII state = new StateII(name, abbr);
                    map.put(state, new Info(population, capital, name));
                    set.add(state);
                }
                case 3 -> {
                    System.out.println("Enter the abbreviation of the State.");
                    StateII stateII = new StateII("name", sc.nextLine());
                    if (set.contains(stateII)) {
                        System.out.println(map.get(stateII));
                    } else {
                        System.out.println("No StateII by that name.");
                    }
                }
                case 4 -> {
                    System.out.println("Enter the abbreviation of the State.");
                    StateII stateII = new StateII("name", sc.nextLine());
                    if (set.contains(stateII)) {
                        map.remove(stateII);
                        set.remove(stateII);
                    } else {
                        System.out.println("No State by that name.");
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
