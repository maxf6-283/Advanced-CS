import java.util.InputMismatchException;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        SLList<Student> list = new SLList<>();
        list.add(new Student("Sean", 62));
        list.add(new Student("John", 124));
        list.add(new Student("Kate", 248));
        list.add(new Student("Evan", 496));
        list.add(new Student("Addy", 992));

        Scanner sc = new Scanner(System.in);

        boolean doLoop = true;
        while (doLoop) {
            System.out.println("Choose an option: ");
            System.out.println("1. Display the list");
            System.out.println("2. Add a new student at an index location.");
            System.out.println("3. Remove a student at an index location");
            System.out.println("4. Remove a student by ID");
            System.out.println("5. Sort by student name.");
            System.out.println("6. Scramble the list");
            System.out.println("7. Quit");

            int ans;
            try {
                ans = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input; try again");
                continue;
            }

            switch (ans) {
                case 1 -> {
                    System.out.println(list);
                }
                case 2 -> {
                    System.out.println("Enter the student's name");
                    String name = sc.nextLine();
                    System.out.println("Enter the student's id");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter the index to place the student");
                    int index = sc.nextInt();
                    sc.nextLine();
                    list.add(index, new Student(name, id));
                }
                case 3 -> {
                    System.out.println("Enter the index to remove the student");
                    int index = sc.nextInt();
                    sc.nextLine();
                    list.remove(index);
                }
                case 4 -> {
                    System.out.println("Enter the ID of the student to remove");
                    int id = sc.nextInt();
                    sc.nextLine();
                    list.remove(new Student("", id));
                }
                case 5 -> {
                    list.sort((Student s1, Student s2) -> s1.name().compareTo(s2.name()));
                }
                case 6 -> {
                    list.scramble();
                }
                case 7 -> {
                    doLoop = false;
                }
                default -> {
                    System.out.println("Invalid input; try again");
                }
            }
        }

        sc.close();
    }
}