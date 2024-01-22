public class BST<E extends Comparable<E>> {
    private Node<E> root;

    public BST() {
        root = null;
    }

    public void add(E data) {
        if (root == null) {
            root = new Node<E> (data);
        } else add(data, root);
    }

    public void add(E data, Node<E> curr) {
        if (data.compareTo(curr.get()) < 0) {
            if(curr.getLeft() == null) {
                curr.setLeft(new Node<E>(data));
            } else {
                add(data, curr.getLeft());
            }
        } else {
            if(curr.getRight() == null) {
                curr.setRight(new Node<E>(data));
            } else {
                add(data, curr.getRight());
            }
        }
    }

    @Override
    public String toString() {
        return inOrderString(root);
    }
    
    private String inOrderString(Node<E> node) {
        String r = "";

        if(node != null) {
            r += inOrderString(node.getLeft());

            r += node.get() + " ";

            r += inOrderString(node.getRight());
        }

        return r;
    }

    public void remove(E data) {
        remove(root, data);
    }

    private void remove(Node<E> curr, E data) {
        int comp = data.compareTo(curr.get());
        if (comp < 0) {
            if(curr.getLeft() == null) {
                throw new IllegalArgumentException("Element not found");
            } else {
                remove(curr.getLeft(), data);
            }
        } else if (comp > 0) {
            if(curr.getRight() == null) {
                throw new IllegalArgumentException("Element not found");
            } else {
                remove(curr.getRight(), data);
            }
        } else {
            if(curr.getRight() == null) {
                if(curr.getParent().getRight() == curr) {
                    curr.getParent().setRight(curr.getLeft());
                } else {
                    curr.getParent().setLeft(curr.getLeft());
                }
            }  else {
                Node<E> maxNode = curr.getRight();
                while(maxNode.getLeft() != null) {
                    maxNode = maxNode.getLeft();
                }
                curr.set(maxNode.get());
                remove(maxNode, maxNode.get());
            }
        }
    }
}