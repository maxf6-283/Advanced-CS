public class Node<E> {
    private Node<E> left;
    private Node<E> right;
    private Node<E> parent;
    private E data;

    public Node(E d) {
        data = d;
    }

    public Node<E> getLeft() {
        return left;
    }

    public Node<E> getRight() {
        return right;
    }

    public Node<E> getParent() {
        return parent;
    }

    public void setLeft(Node<E> n) {
        left = n;
        if (n != null) {
            n.parent = this;
        }
    }

    public void setRight(Node<E> n) {
        right = n;
        if (n != null) {
            n.parent = this;
        }
    }

    public E get() {
        return data;
    }

    public void set(E d) {
        data = d;
    }
}