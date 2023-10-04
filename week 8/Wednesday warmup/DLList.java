public class DLList<E>{
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DLList() {
        head = new Node<E>(null);
        tail = new Node<E>(null);
        head.setNext(tail);
        tail.setPrev(head);
        size = 0;
    }

    private Node<E> getNode(int i) {
        if (i >= size) {
            throw new IndexOutOfBoundsException("Index " + i + " out of range for size " + size);
        }

        if (i < size / 2) {
            Node<E> current = head.next();
            for (int j = 0; j < i; j++) {
                current = current.next();
            }
            return current;
        } else {
            Node<E> current = tail;
            for (int j = size; j > i; j--) {
                current = current.prev();
            }
            return current;
        }
    }

    public boolean add(E element) {
        Node<E> toAdd = new Node<E>(element);

        toAdd.setPrev(tail.prev());
        toAdd.setNext(tail);

        toAdd.prev().setNext(toAdd);
        toAdd.next().setPrev(toAdd);

        size++;

        return true;
    }

    public void add(int i, E e) {
        Node<E> node = getNode(i);

        Node<E> toAdd = new Node<E>(e);

        toAdd.setPrev(node.prev());
        toAdd.setNext(node);

        toAdd.prev().setNext(toAdd);
        toAdd.next().setPrev(toAdd);

        size++;
    }

    public E get(int i) {
        return getNode(i).get();
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        String toReturn = "[";
        for (Node<E> node = head.next(); node != tail; node = node.next()) {
            toReturn += node.get().toString() + ", ";
        }

        return toReturn.substring(0, toReturn.length() - 2) + "]";
    }

    public E remove(int i) {
        Node<E> toRem = getNode(i);
        toRem.prev().setNext(toRem.next());
        toRem.next().setPrev(toRem.prev());
        size--;
        return toRem.get();
    }

    public boolean remove(E element) {
        for(Node<E> node = head.next(); node != tail; node = node.next()) {
            if(element.equals(node.get())) {
                node.prev().setNext(node.next());
                node.next().setPrev(node.prev());
                size--;
                return true;
            }
        }

        return false;
    }

    public E set(int i, E element) {
        Node<E> node = getNode(i);
        E e = node.get();
        node.set(element);
        return e;
    }
}
