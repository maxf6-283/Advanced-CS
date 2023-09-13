import java.util.ArrayList;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<People> people = new ArrayList<>();
        people.add(new People("Johnathan", 22));
        people.add(new People("Clarice", 23));
        people.add(new Employee("Albert", 35, 24.00));
        people.add(new Employee("Steve", 18, 15.00));
        people.add(new Student("Eve", 15, "MVHS"));
        people.add(new Student("Jordan", 17, "LAHS"));
        people.add(new CollegeStudent("Max", 17, "WPI", "Mechanical Engineering"));
        people.add(new CollegeStudent("Abby", 17, "MIT", "Computer Science"));

        for (People person : people) {
            System.out.println(person);
        }

        System.out.print("\nEnter a name to display: ");
        String name = sc.nextLine();

        boolean found = false;
        for (People person : people) {
            if (name.equals(person.getName())) {
                System.out.println("Person found: " + person);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No person found by that name");
        }

        System.out.print("\nEnter a name to delete: ");
        name = sc.nextLine();

        found = false;
        for (int i = 0; i < people.size(); i++) {
            if (name.equals(people.get(i).getName())) {
                people.remove(i);
                found = true;
                break;
            }
        }

        if(!found) {
            System.out.println("No person found by that name");
        }

        for (People person : people) {
            System.out.println(person);
        }

        sc.close();
    }
}
