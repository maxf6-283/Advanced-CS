import java.util.Scanner;
import java.util.Map.Entry;

public class Runner {
    public static void main(String[] args) {
        MyHashMap<Student, DLList<String>> studentList = new MyHashMap<Student, DLList<String>>();

        DLList<String> johnClasses = new DLList<>();
        johnClasses.add("AP Calculus BC");
        studentList.put(new Student("john", 12345), johnClasses);
        DLList<String> janeClasses = new DLList<>();
        janeClasses.add("Advanced Computer Science");
        studentList.put(new Student("jane", 54321), janeClasses);
        DLList<String> alexClasses = new DLList<>();
        alexClasses.add("AP Computer Science A");
        studentList.put(new Student("alex", 98760), alexClasses);
        Scanner scan = new Scanner(System.in);

        mainloop: while (true) {
            System.out.println(
                    "1. View all students and their ids.\n2. View a student schedule given an id.\n3. Add a class to a student given an id.\n4. Remove a student given an id.\n5. Quit.");
            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {
                case 1 -> {
                    for (Entry<Student, DLList<String>> entry : studentList.entrySet()) {
                        System.out.println(entry.getKey() + " - " + entry.getValue());
                    }
                }
                case 2 -> {
                    int id = scan.nextInt();
                    scan.nextLine();
                    if (studentList.get(new Student(null, id)) != null) {
                        System.out.println(studentList.get(new Student(null, id)));
                    } else {
                        System.out.println("No student by that ID");
                    }
                }
                case 3 -> {
                    int id = scan.nextInt();
                    scan.nextLine();
                    String className = scan.nextLine();
                    studentList.get(new Student(null, id)).add(className);
                }
                case 4 -> {
                    int id = scan.nextInt();
                    scan.nextLine();
                    studentList.remove(new Student(null, id));
                }
                case 5 -> {
                    break mainloop;
                }
            }

        }
        scan.close();
    }
}