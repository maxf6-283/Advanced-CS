import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Comparator;
import java.lang.Comparable;
import java.lang.reflect.Array;

public class TreeSet<E> implements Set<E> {
    private int size;
    private Comparator<E> comp;
    private Node root;

    @SuppressWarnings("unchecked")
    public TreeSet() {
        comp = (e1, e2) -> {
            return ((Comparable<? super E>) e1).compareTo(e2);
        };
        size = 0;
        root = null;
    }

    public TreeSet(Comparator<E> compar) {
        comp = compar;
        size = 0;
        root = null;
    }

    private class Node {
        private Node left;
        private Node right;
        private E element;
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
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        Node node = root;
        while (node != null) {
            try {
                int comparision = comp.compare((E) o, node.element);
                if (comparision < 0) {
                    node = node.left;
                } else if (comparision > 0) {
                    node = node.right;
                } else {
                    return true;
                }
            } catch (ClassCastException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new TreeSetIterator();
    }

    private class TreeSetIterator implements Iterator<E> {
        Node nextNode;
        int index;
        int depth;

        public TreeSetIterator() {
            nextNode = root;
            index = 0;
            while (nextNode.left != null) {
                nextNode = nextNode.left;
                depth++;
            }
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public E next() {
            Node toReturn = nextNode;
            if (nextNode.right != null) {
                nextNode = nextNode.right;
                index |= 1 << depth;
                depth++;
                while (nextNode.left != null) {
                    nextNode = nextNode.left;
                    index &= ~(1 << depth);
                    depth++;
                }
            } else {
                nextNode = root;
                depth--;
                for (int i = 0; i < depth; i++) {
                    if ((index & (1 << depth)) == 0) {
                        nextNode = nextNode.left;
                    } else {
                        nextNode = nextNode.right;
                    }
                }
            }

            return toReturn.element;
        }
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        Iterator<E> iter = new TreeSetIterator();
        for(int i = 0; i < size; i++) {
            arr[i] = iter.next();
        }

        return arr;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        T[] arr;
        if(a.length > size) {
            arr = a;
        } else {
            arr = (T[])Array.newInstance(a.getClass().componentType(), size);
        }

        return arr;
    }

    @Override
    public boolean add(E e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public boolean remove(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

}
