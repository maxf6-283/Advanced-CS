import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Node n1 = new Node("cat");
        Node n2 = new Node("mouse");
        Node n3 = new Node("turtle");
        Node n4 = new Node("bear");
        Node n5 = new Node("tiger");

        n1.setNext(n2);
        n2.setNext(n3);
        n3.setNext(n4);
        n4.setNext(n5);

        Node start = n1;

        // print out nodes
        for (Node b = start; b != null; b = b.next()) {
            System.out.print(b.get() + ", ");
        }
        System.out.println();

        // search
        System.out.print("Enter an animal to search for: ");
        String animal = sc.nextLine();
        boolean found = false;
        for (Node b = start; b != null; b = b.next()) {
            if (b.get().equalsIgnoreCase(animal)) {
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("Animal found!");
        } else {
            System.out.println("Animal not found!");
        }

        // remove
        System.out.println("Enter an animal to remove");
        animal = sc.nextLine();
        if (start.get().equals(animal)) {
            start = n2;
        } else {
            for (Node b = start; b.next() != null; b = b.next()) {
                if (b.next().get().equalsIgnoreCase(animal)) {
                    b.setNext(b.next().next());
                    break;
                }
            }
        }

        for (Node b = start; b != null; b = b.next()) {
            System.out.print(b.get() + ", ");
        }
        System.out.println();

        // replace by name
        System.out.println("Enter an animal you'd like to replace: ");
        animal = sc.nextLine();
        System.out.println("Enter an animal to replace the old one with: ");
        String newAnimal = sc.nextLine();

        for (Node b = start; b != null; b = b.next()) {
            if (b.get().equalsIgnoreCase(animal)) {
                b.set(newAnimal);
                break;
            }
        }

        for (Node b = start; b != null; b = b.next()) {
            System.out.print(b.get() + ", ");
        }
        System.out.println();

        // replace at index
        System.out.println("Enter an index: ");
        int index = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the animal you'd like to be in that index: ");
        newAnimal = sc.nextLine();

        Node b = start;
        for (int i = 0; i < index; i++) {
            b = b.next();
        }
        b.set(newAnimal);

        for (b = start; b != null; b = b.next()) {
            System.out.print(b.get() + ", ");
        }
        System.out.println();

        // sort alphabetically
        System.out.println("Sorting");

        for (b = start; b != null; b = b.next()) {
            for (Node j = start; j.next() != null; j = j.next()) {
                if (j.get().compareToIgnoreCase(j.next().get()) > 0) {
                    String temp = j.get();
                    j.set(j.next().get());
                    j.next().set(temp);
                }
            }
        }

        for (b = start; b != null; b = b.next()) {
            System.out.print(b.get() + ", ");
        }
        System.out.println();

        System.out.println("Enter a new animal to add: ");
        animal = sc.nextLine();

        for (b = start; b.next() != null; b = b.next()) {
        }
        b.setNext(new Node(animal));

        for (b = start; b != null; b = b.next()) {
            System.out.print(b.get() + ", ");
        }
        System.out.println();

        System.out.println("Enter a new animal to add: ");
        animal = sc.nextLine();
        System.out.println("Where would you like to add it: ");
        index = sc.nextInt();
        sc.close();

        if (index == 0) {
            Node newNode = new Node(animal);
            newNode.setNext(start);
            start = newNode;
        } else {
            b = start;
            for (int i = 1; i < index; i++) {
                b = b.next();
            }
            Node newNode = new Node(animal);
            newNode.setNext(b.next());
            b.setNext(newNode);
        }


        for (b = start; b != null; b = b.next()) {
            System.out.print(b.get() + ", ");
        }
        System.out.println();
    }
}