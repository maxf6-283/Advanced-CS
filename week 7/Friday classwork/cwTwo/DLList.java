package cwTwo;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

public class DLList<E> implements Iterable<E> {
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
        for (Node<E> node = head.next(); node != tail; node = node.next()) {
            if (element.equals(node.get())) {
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

    public Iterator<E> iterator() {
        return new MyIter();
    }

    private class MyIter implements Iterator<E> {
        private Node<E> node;

        public MyIter() {
            node = head.next();
        }

        @Override
        public boolean hasNext() {
            return node != tail;
        }

        @Override
        public E next() {
            node = node.next();
            return node.prev().get();
        }

        @Override
        public void remove() {
            node = node.prev();
            node.prev().setNext(node.next());
            node.next().setPrev(node.prev());
            node = node.next();
            size--;
        }
    }

    public void removeIf(Predicate<E> p) {
        MyIter iter = new MyIter();
        while (iter.hasNext()) {
            if (p.test(iter.next())) {
                iter.remove();
            }
        }
    }

    public void sort(Comparator<E> c) {
        for (Node<E> node = head.next(); node != tail; node = node.next()) {
            for (Node<E> current = tail.prev(); current != node; current = current.prev()) {
                if (c.compare(current.get(), current.prev().get()) < 0) {
                    E temp = current.get();
                    current.set(current.prev().get());
                    current.prev().set(temp);
                }
            }
        }
    }
}
