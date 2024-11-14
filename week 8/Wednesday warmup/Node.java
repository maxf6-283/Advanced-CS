public class Node<E> {
    private E data;
    private Node<E> nextNode;
    private Node<E> prevNode;

    public Node(E data_) {
        data = data_;
    }

    public Node<E> next() {
        return nextNode;
    }

    public Node<E> prev() {
        return prevNode;
    }

    public E get() {
        return data;
    }

    public void setNext(Node<E> node) {
        nextNode = node;
    }

    public void setPrev(Node<E> node) {
        prevNode = node;
    }

    public void set(E data_) {
        data = data_;
    }
}