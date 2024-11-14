public class Node <E> {
    private E data;
    private Node<E> nextNode;

    public Node(E d) {
        data = d;
    }

    public Node<E> next() {
        return nextNode;
    }

    public E get() {
        return data;
    }

    public void setNext(Node<E> node) {
        nextNode = node;
    }

    public void set(E d) {
        data = d;
    }
}
