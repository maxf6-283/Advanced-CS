import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MyHashTable<Price, Item> table = new MyHashTable<>();
        table.put(new Price(1.99), new Item("Pencil", "Target"));
        table.put(new Price(1.99), new Item("Pen", "Walmart"));
        table.put(new Price(899.00), new Item("TV", "BestBuy"));
        table.put(new Price(899.00), new Item("Laptop", "Amazon"));

        mainloop: while (true) {
            System.out.println("""
                    1. Display only the items.
                    2. Display the hashTable.
                    3. Add an item.
                    4. Delete an item.
                    5. Delete by price. Delete all items of that price.
                    6. Quit.
                        """);
            int ans = sc.nextInt();
            sc.nextLine();
            switch (ans) {
                case 1 -> {
                    for (Price p : table.keySet()) {
                        for (Item i : table.get(p)) {
                            System.out.println(p + ": " + i);
                        }
                    }
                }
                case 2 -> {
                    System.out.println(table);
                }
                case 3 -> {
                    System.out.println("Enter the price of the item");
                    Price p = new Price(sc.nextDouble());
                    sc.nextLine();
                    System.out.println("Enter the name of the item");
                    String itemName = sc.nextLine();
                    System.out.println("Enter the store of the item");
                    Item i = new Item(itemName, sc.nextLine());
                    table.put(p, i);
                }
                case 4 -> {
                    System.out.println("Enter the price of the item");
                    Price p = new Price(sc.nextDouble());
                    sc.nextLine();
                    System.out.println("Enter the name of the item");
                    String itemName = sc.nextLine();
                    System.out.println("Enter the store of the item");
                    Item i = new Item(itemName, sc.nextLine());
                    table.remove(p, i);
                }
                case 5 -> {
                    System.out.println("Enter the price of the item");
                    Price p = new Price(sc.nextDouble());
                    sc.nextLine();
                    table.remove(p);
                }
                case 6 -> {
                    break mainloop;
                }
                default -> {
                    System.out.println("invalid option");
                }
            }
        }

        sc.close();
    }
}
