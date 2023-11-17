import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet<E> implements Set<E> {
    private Node[] elements;
    private int size;
    private double capacity;

    private class Node {
        public E object;
        public Node next;

        public Node(E e) {
            object = e;
        }
    }

    @SuppressWarnings("unchecked")
    public MyHashSet() {
        elements = (Node[]) Array.newInstance(Node.class, 10);
        size = 0;
        capacity = 0.0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        int index = (o.hashCode() % elements.length + elements.length) % elements.length;
        Node node = elements[index];
        while(node != null) {
            if(node.object.equals(o)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<E> {
        private int index;
        private boolean hasNext;

        private void upToNext() {
            for (; elements[index] == null; index++) {
                if (index >= elements.length - 1) {
                    hasNext = false;
                    return;
                }
            }
            hasNext = true;
        }

        public MyIterator() {
            index = 0;
            upToNext();
        }

        @Override
        public boolean hasNext() {
            return hasNext;
        }

        @Override
        public E next() {
            int i = index;
            index++;
            upToNext();
            return elements[i].object;
        }

    }

    @Override
    public Object[] toArray() {
        Object[] toRet = new Object[size];
        int i = 0;
        for (Object o : this) {
            toRet[i] = o;
            i++;
        }
        return toRet;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        T[] toRet;
        if(a.length >= size) {
            toRet = a;
            if(a.length != size) {
                a[size] = null;
            }
        } else {
            toRet = (T[]) Array.newInstance(a.getClass().componentType(), size);
        }
        int i = 0;
        for (Object o : this) {
            toRet[i] = (T)o;
            i++;
        }
        return toRet;
    }

    @Override
    public boolean add(E e) {
        if ((size + 1.0) / elements.length > capacity) {
            doubleSize();
        }

        int index = (e.hashCode() % elements.length + elements.length) % elements.length;
        Node node = new Node(e);
        if (elements[index] == null) {
            elements[index] = node;
            size++;
            return true;
        } else {
            Node toAddTo = elements[index];
            while (toAddTo.next != null) {
                if (toAddTo.object.equals(e)) {
                    return false;
                }
                toAddTo = toAddTo.next;
            }
            if (toAddTo.object.equals(e)) {
                return false;
            }
            toAddTo.next = node;
            return true;
        }
    }

    public E replace(E e) {
        int index = (e.hashCode() % elements.length + elements.length) % elements.length;
        Node node = new Node(e);
        if (elements[index] == null) {
            elements[index] = node;
            size++;
            return null;
        } else {
            Node toAddTo = elements[index];
            while (toAddTo.next != null) {
                if (toAddTo.object.equals(e)) {
                    E other = toAddTo.object;
                    toAddTo.object = e;
                    return other;
                }
                toAddTo = toAddTo.next;
            }
            if (toAddTo.object.equals(e)) {
                E other = toAddTo.object;
                toAddTo.object = e;
                return other;
            }
            toAddTo.next = node;
            size++;
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private void doubleSize() {
        Node[] newElements = (Node[]) Array.newInstance(Node.class, elements.length * 2);
        // reallocate everything
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != null) {
                int index = (elements[i].object.hashCode() % newElements.length + newElements.length)
                        % newElements.length;
                Node node = elements[i];
                if (newElements[index] == null) {
                    newElements[index] = node;
                } else {
                    Node toAddTo = newElements[index];
                    while (toAddTo.next != null) {
                        toAddTo = toAddTo.next;
                    }
                    toAddTo.next = node;
                }
            }
        }

        elements = newElements;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object o) {
        E e;
        try {
            e = (E) o;
        } catch (ClassCastException er) {
            return false;
        }

        int index = (e.hashCode() % elements.length + elements.length) % elements.length;
        if (elements[index] == null) {
            return false;
        }

        Node node = elements[index];
        if (node.object.equals(o)) {
            elements[index] = node.next;
            size--;
            return true;
        }
        while (node.next != null) {
            if (node.next.object.equals(o)) {
                node.next = node.next.next;
                size--;
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;
        for (E e : c) {
            changed = add(e) || changed;
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != null) {
                if (!c.contains(elements[i].object)) {
                }
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    @Override
    public void clear() {
        size = 0;
        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
        }
    }

    public E get(Object o) {
        int index = (o.hashCode() % elements.length + elements.length) % elements.length;
        if (elements[index] == null) {
            return null;
        }

        Node node = elements[index];
        while (node != null) {
            if (node.object.equals(o)) {
                return node.object;
            }
            node = node.next;
        }
        return null;
    }

}