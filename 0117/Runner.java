public class Runner {
    public static void main(String[] args) {
        Node<Integer> root = new Node<Integer>(1);
        root.setLeft(new Node<>(2));
        root.setRight(new Node<>(3));
        root.getLeft().setLeft(new Node<>(4));
        root.getLeft().setRight(new Node<>(5));
        root.getRight().setLeft(new Node<>(6));
        root.getRight().setRight(new Node<>(7));
        
        printInOrder(root);
        System.out.println();
        printPreOrder(root);
        System.out.println();
        printPostOrder(root);
        System.out.println();
        printRevOrder(root);
        System.out.println();

    }

    public static void printInOrder(Node<Integer> current) {
        if (current != null) {
            printInOrder(current.getLeft());

            System.out.print(current.get() + " ");
            
            printInOrder(current.getRight());
        }
        
    }
    
    public static void printRevOrder(Node<Integer> current) {
        if (current != null) {
            printRevOrder(current.getRight());
            
            System.out.print(current.get() + " ");
            
            printRevOrder(current.getLeft());
        }
    }

    public static void printPreOrder(Node<Integer> current) {
        if(current != null) {
            System.out.print(current.get() + " ");

            printPreOrder(current.getLeft());

            printPreOrder(current.getRight());
        }
    }
    
    public static void printPostOrder(Node<Integer> current) {
        if (current != null) {
            printPostOrder(current.getLeft());
            
            printPostOrder(current.getRight());
            
            System.out.print(current.get() + " ");
        }
    }
}