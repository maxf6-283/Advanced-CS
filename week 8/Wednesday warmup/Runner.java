import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DLList<String> list = new DLList<>();
        list.add("Dog");
        list.add("Cat");
        list.add("Sheep");
        System.out.println(list);
        System.out.println("Random animal: " + list.get((int)(Math.random() * list.size())));
    
        System.out.println("Enter an index: ");
        int index = sc.nextInt();

        System.out.println("Removing index " + index +" - removed " + list.remove(index));
        System.out.println(list);

        System.out.println("Enter an animal name: ");
        sc.nextLine();
        String name = sc.nextLine();

        System.out.println("Removing name " + name + " - removed: " + list.remove(name));
        System.out.println(list);
        sc.close();;    }
}