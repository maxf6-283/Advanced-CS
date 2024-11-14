public class BST<E extends Comparable<E>> {
    private Node<E> root = null;

    public BST() {

    }

    public void add(E e) {
        if(root == null) {
            root = new Node<E>(e);
        } else {
            add(e, root);
        }
    }
    private void add(E e, Node<E> n) {
        int comp = e.compareTo(n.get());
        if(comp < 0) {
            if(n.getLeft() == null) {
                n.setLeft(new Node<>(e));
            }else {
                add(e, n.getLeft());
            }
        } else {
            if(n.getRight() == null) {
                n.setRight(new Node<>(e));
            }else {
                add(e, n.getRight());
            }
        }
    }

    @Override
    public String toString() {
        return inOrderString(root);
    }
    
    public String inOrderString(Node<E> current) {
        String s = "";
        
        if (current != null) {
            s += inOrderString(current.getLeft());

            s += current.get() + " ";
            
            s += inOrderString(current.getRight());
        }

        return s;
    }

}
