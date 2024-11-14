import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<Item<?>> items = new ArrayList<>();
        items.add(new Item<Food>(new Food("apple", 2.43)));
        items.add(new Item<Food>(new Food("banana", 1.87)));
        items.add(new Item<String>("Dog"));
        items.add(new Item<String>("Cat"));
        items.add(new Item<Integer>(99));

        boolean loop = true;
        while(loop) {
            System.out.println("Select from the following:");
            System.out.println("1.) Print the list");
            System.out.println("2.) Search for a name in the list");
            System.out.println("3.) Quit");

            int ans = 0;
            try {
                ans = sc.nextInt();
            } catch (InputMismatchException e) {
                sc.nextLine();
                ans = 0;
            }
            switch(ans) {
                case 1 -> {
                    for(Item<?> item : items) {
                        System.out.println(item.get());
                    }
                }
                case 2 -> {
                    System.out.println("Enter a word to search for");
                    String word = sc.next();
                    boolean found = false;
                    for(Item<?> item : items) {
                        if(item.get().toString().toLowerCase().contains(word.toLowerCase())) {
                            System.out.println(item.get());
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        System.out.println("No item found");
                    }
                }
                case 3 -> {
                    loop = false;
                }
                default -> {
                    System.out.println("Invalid option");
                }
            }
        }

        sc.close();
    }
}
