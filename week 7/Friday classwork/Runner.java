import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        DLList<String> list = new DLList<>();
        list.add("Cat");
        list.add("Dog");
        list.add("Bird");
        list.add("Bear");
        list.add("Pig");

        System.out.println(list);

        System.out.println("What animal would you like to add: ");
        String animal = sc.nextLine();

        list.add(animal);
        System.out.println(list);

        System.out.println("What animal would you like to add: ");
        animal = sc.nextLine();

        System.out.println("What location would you like to add it at: ");
        int index = sc.nextInt();

        list.add(index, animal);
        System.out.println(list);

        System.out.println("Enter the location you'd like to access an animal at: ");
        index = sc.nextInt();
        System.out.println(list.get(index));

        sc.close();
    }
}