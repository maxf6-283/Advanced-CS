public class Node<E> {
    private Node<E> prev;
    private Node<E> next;
    private E data;

    public Node(E d) {
        data = d;
    }

    public Node<E> next() {
        return next;
    }

    public Node<E> prev() {
        return prev;
    }

    public E get() {
        return data;
    }

    public void set(E d) {
        data = d;
    }

    public void setNext(Node<E> n) {
        next = n;
    }

    public void setPrev(Node<E> n) {
        prev = n;
    }
    
}

