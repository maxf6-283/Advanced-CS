import java.util.Scanner;
public class Runner {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        
        Node<String> head = new Node<String>(null);
        Node<String> tail = new Node<String>(null);

        Node<String> n1 = new Node<String>("cow");
        Node<String> n2 = new Node<String>("cat");
        Node<String> n3 = new Node<String>("dog");
        Node<String> n4 = new Node<String>("bird");
        Node<String> n5 = new Node<String>("bear");

        head.setNext(n1);
        n1.setPrev(head);

        n1.setNext(n2);
        n2.setPrev(n1);

        n2.setNext(n3);
        n3.setPrev(n2);
        
        n3.setNext(n4);
        n4.setPrev(n3);

        n4.setNext(n5);
        n5.setPrev(n4);

        n5.setNext(tail);
        tail.setPrev(n5);

        for(Node<String> current = head.next(); current != tail; current = current.next()) {
            System.out.println(current.get());
        }
        System.out.println();
        for(Node<String> current = tail.prev(); current != head; current = current.prev()) {
            System.out.println(current.get());
        }

        System.out.println("Enter an animal name.");
        Node<String> toAdd = new Node<>(sc.nextLine());

        tail.prev().setNext(toAdd);
        toAdd.setPrev(tail.prev());
        tail.setPrev(toAdd);
        toAdd.setNext(tail);

        sc.close();

        System.out.println();
        for(Node<String> current = head.next(); current != tail; current = current.next()) {
            System.out.println(current.get());
        }
    }
}