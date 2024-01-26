public class BinaryTree<E extends Comparable<E>> {
    private Node<E> root;

    public BinaryTree() {
        root = null;
    }

    public void add(E data) {
        if(root == null) {
            root = new Node<>(data);
        } else {
            add(root, data);
        }
    }

    private void add(Node<E> n, E data) {
        int comp = data.compareTo(n.get());
        if(comp < 0) {
            if(n.getLeft() == null) {
                n.setLeft(new Node<>(data));
            } else {
                add(n.getLeft(), data);
            }
        } else if (comp > 0) {
            if(n.getRight() == null) {
                n.setRight(new Node<>(data));
            } else {
                add(n.getRight(), data);
            }
        }
        //if comp == 0 its a duplicate
    }

    public boolean contains(E data) {
        return contains(root, data);
    }

    private boolean contains(Node<E> n, E data) {
        int comp = data.compareTo(n.get());
        if(comp < 0) {
            if(n.getLeft() == null) {
                return false;
            } else {
                return contains(n.getLeft(), data);
            }
        } else if (comp > 0) {
            if(n.getRight() == null) {
                return false;
            } else {
                return contains(n.getRight(), data);
            }
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        String s = inOrderString(root);
        return s.substring(0, s.length() - 2);
    }

    private String inOrderString(Node<E> n) {
        return n == null ? "" : inOrderString(n.getLeft()) + n.get() + ", " + inOrderString(n.getRight());
    }
}
