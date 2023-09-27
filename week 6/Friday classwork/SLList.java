import java.util.Comparator;

public class SLList<E> {
    private Node<E> head;
    private int size;

    public SLList() {
        head = null;
        size = 0;
    }

    public E get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(index);
        }

        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next();
        }
        return current.get();
    }

    public boolean add(E element) {
        if(size == 0) {
            head = new Node<E>(element);
            size = 1;
            return true;
        }
        Node<E> current = head;
        while (current.next() != null) {
            current = current.next();
        }

        current.setNext(new Node<E>(element));
        size++;

        return true;
    }

    public void add(int index, E element) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(index);
        }

        if (index == 0) {
            Node<E> newNode = new Node<E>(element);
            newNode.setNext(head);
            head = newNode;
            size++;
            return;
        }

        Node<E> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next();
        }

        Node<E> newNode = new Node<E>(element);
        newNode.setNext(current.next());
        current.setNext(newNode);
        size++;
    }

    public E remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(index);
        }

        if (index == 0) {
            E toReturn = head.get();
            head = head.next();
            size--;
            return toReturn;
        }

        Node<E> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next();
        }

        E toReturn = current.next().get();
        current.setNext(current.next().next());
        size--;
        return toReturn;
    }

    public boolean remove(Object o) {
        if (head.get().equals(o)) {
            head = head.next();
            size--;
            return true;
        }
        for (Node<E> current = head; current.next() != null; current = current.next()) {
            if (current.next().get().equals(o)) {
                current.setNext(current.next().next());
                size--;
                return true;
            }
        }

        return false;
    }

    public E set(int index, E element) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(index);
        }

        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next();
        }

        E toReturn = current.get();
        current.set(element);
        return toReturn;
    }

    @Override
    public String toString() {
        String str = "[";
        for (Node<E> current = head; current != null; current = current.next()) {
            str += current.get().toString() + ", ";
        }

        return str.substring(0, str.length() - 2) + "]";
    }

    public void sort(Comparator<? super E> c) {
        for (int i = size - 1; i >= 0; i--) {
            Node<E> current = head;
            for (int j = 0; j < i; j++) {
                if (c.compare(current.get(), current.next().get()) > 0) {
                    E temp = current.next().get();
                    current.next().set(current.get());
                    current.set(temp);
                }

                current = current.next();
            }
        }
    }

    public void scramble() {
        for (Node<E> current = head; current != null; current = current.next()) {
            int toSwap = (int) (Math.random() * size);
            Node<E> other = head;
            for (int i = 0; i < toSwap; i++) {
                other = other.next();
            }
            E temp = current.get();
            current.set(other.get());
            other.set(temp);
        }
    }
}
