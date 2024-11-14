import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        BST<Animal> bst = new BST<Animal>();
        bst.add(new Animal("monkey", 5));
        bst.add(new Animal("bear", 7));
        bst.add(new Animal("bear", 8));
        bst.add(new Animal("octopus", 4));

        System.out.println(bst);
        System.out.println();

        System.out.print("Enter an animal: ");
        String name = sc.next();
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        bst.add(new Animal(name, age));

        System.out.println(bst);
        System.out.println();
        
        System.out.print("Enter an animal: ");
        name = sc.next();
        System.out.print("Enter age: ");
        age = sc.nextInt();
        bst.remove(new Animal(name, age));

        System.out.println(bst);
        System.out.println();

        sc.close();
    }
}
