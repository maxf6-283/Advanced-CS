public class Node<E> {
    private E data;
    private Node<E> left;
    private Node<E> right;

    public Node(E dat) {
        data = dat;
        left = null;
        right = null;
    }

    public E get() {
        return data;
    }

    public Node<E> getLeft() {
        return left;
    }

    public Node<E> getRight() {
        return right;
    }

    public void setLeft(Node<E> node) {
        left = node;
    }

    public void setRight(Node<E> node) {
        right = node;
    }

    public void set(E dat) {
        data = dat;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}