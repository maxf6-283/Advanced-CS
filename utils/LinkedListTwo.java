import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;

public class LinkedListTwo<E> implements List<E> {
    private class Node {
        public Node nextNode;
        public Node prevNode;

        public E element;

        public Node(E e) {
            element = e;
        }

        public Node(E e, Node pNode, Node nNode) {
            element = e;

            prevNode = pNode;
            pNode.nextNode = this;
            nextNode = nNode;
            nNode.prevNode = this;
        }

        public void removeSelf() {
            if (nextNode != null) {
                nextNode.prevNode = prevNode;
            }
            if (prevNode != null) {
                prevNode.nextNode = nextNode;
            }
        }
    }

    private Node baseNode;
    private Node lastNode;
    private int size;
    private LinkedListTwo<E> parentList;

    private void addSize(int sizeToAdd) {
        size += sizeToAdd;
        if (parentList != null) {
            parentList.addSize(sizeToAdd);
        }
    }

    private void replaceBaseNode(Node newNode) {
        if (parentList == null) {
            baseNode = newNode;
        } else {
            if (baseNode == parentList.baseNode) {
                parentList.replaceBaseNode(newNode);
            }
        }
    }

    private void replaceLastNode(Node newNode) {
        if (parentList == null) {
            lastNode = newNode;
        } else {
            if (baseNode == parentList.baseNode) {
                parentList.replaceLastNode(newNode);
            }
        }
    }

    public LinkedListTwo() {
        baseNode = null;
        lastNode = null;
        size = 0;
        parentList = null;
    }

    private LinkedListTwo(LinkedListTwo<E> parent, Node base, Node last, int size) {
        baseNode = base;
        lastNode = last;
        parentList = parent;
        this.size = size;
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
        Node node = baseNode;
        for (int i = 0; i < size; i++) {
            if (o.equals(node.element)) {
                return true;
            }
            node = node.nextNode;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new NodeIterator();
    }

    private class NodeIterator implements Iterator<E> {
        private Node node;

        public NodeIterator() {
            node = baseNode;
        }

        @Override
        public boolean hasNext() {
            return node != null && node != lastNode.nextNode;
        }

        @Override
        public E next() {
            E element = node.element;
            node = node.nextNode;
            return element;
        }

    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        Node node = baseNode;
        for (int i = 0; i < size; i++) {
            arr[i] = node.element;
            node = node.nextNode;
        }
        return arr;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        } else if (a.length > size) {
            a[size] = null;
        }

        Node node = baseNode;
        for (int i = 0; i < size; i++) {
            a[i] = (T) node.element;
            node = node.nextNode;
        }

        return a;
    }

    @Override
    public boolean add(E e) {
        Node newLastNode = new Node(e, lastNode, null);
        replaceLastNode(newLastNode);
        addSize(1);

        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node node = baseNode;

        for (int i = 0; i < size; i++) {
            if (node.element.equals(o)) {
                if (i == 0) {
                    replaceBaseNode(node.nextNode);
                } else if (i == size - 1) {
                    replaceLastNode(node.prevNode);
                }
                node.removeSelf();
                addSize(-1);
                return true;
            }
            node = node.nextNode;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c.isEmpty()) {
            return true;
        }
        if (size == 0) {
            return false;
        }
        java.util.ArrayList<?> colList = new java.util.ArrayList<>(c);
        for (Node node = baseNode; node != lastNode.nextNode; node = node.nextNode) {
            if (colList.remove(node.element)) {
                if (colList.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        Iterator<? extends E> iter = c.iterator();
        Node baseNodeChain = new Node(iter.next());
        Node lastNodeChain = baseNodeChain;
        while (iter.hasNext()) {
            lastNodeChain = new Node(iter.next(), lastNodeChain, null);
        }

        addSize(c.size());
        replaceLastNode(lastNodeChain);

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        Iterator<? extends E> iter = c.iterator();
        Node baseNodeChain = new Node(iter.next());
        Node lastNodeChain = baseNodeChain;
        while (iter.hasNext()) {
            lastNodeChain = new Node(iter.next(), lastNodeChain, null);
        }

        if (index == 0) {
            replaceBaseNode(baseNodeChain);
        }
        if (index == size) {
            replaceLastNode(lastNodeChain);
        }
        addSize(c.size());

        return true;
    }

    @Override
    public boolean removeIf(Predicate<? super E> predicate) {
        if (size == 0) {
            return false;
        }
        boolean removed = false;
        Node node = baseNode;
        for (int i = 0; i < size; i++) {
            if (predicate.test(node.element)) {
                node.removeSelf();
                if (i == 0) {
                    replaceBaseNode(node.nextNode);
                } else if (i == size - 1) {
                    replaceLastNode(node.prevNode);
                }
                addSize(-1);
                i--;
            }

            node = node.nextNode;
        }

        return removed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return removeIf(e -> c.contains(e));
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return removeIf(e -> !c.contains(e));
    }

    @Override
    public void clear() {
        addSize(-size);
        Node node = baseNode;
        replaceBaseNode(lastNode.nextNode);
        replaceLastNode(node.prevNode);
    }

    @Override
    public E get(int index) {
        Node node = baseNode;
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < index; i++) {
            node = node.nextNode;
        }

        return node.element;
    }

    @Override
    public E set(int index, E element) {
        Node node = baseNode;

        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }

        for (int i = 0; i < index; i++) {
            node = node.nextNode;
        }

        E el = node.element;
        node.element = element;
        return el;
    }

    @Override
    public void add(int index, E element) {
        if (index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node node = baseNode;
        for (int i = 0; i < index; i++) {
            node = node.nextNode;
        }

        Node newNode = new Node(element, node.prevNode, node);

        if (index == 0) {
            replaceBaseNode(newNode);
        } else if (index == size) {
            replaceLastNode(newNode);
        }

        addSize(1);
    }

    @Override
    public E remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node node = baseNode;
        for (int i = 0; i < index; i++) {
            node = node.nextNode;
        }

        if (index == 0) {
            replaceBaseNode(node.nextNode);
        }
        if (index == size - 1) {
            replaceLastNode(node.prevNode);
        }

        node.removeSelf();
        addSize(-1);
        return node.element;
    }

    @Override
    public int indexOf(Object o) {
        Node node = baseNode;
        for (int i = 0; i < size; i++) {
            if (node.element.equals(o)) {
                return i;
            }
            node = node.nextNode;
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node node = lastNode;
        for (int i = size - 1; i >= 0; i++) {
            if (node.element.equals(0)) {
                return i;
            }
            node = node.prevNode;
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new MyListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new MyListIterator(index);
    }

    private class MyListIterator implements ListIterator<E> {
        int nextIndex;
        Node nextNode;
        Node prevNode;
        Node recentNode;

        public MyListIterator() {
            nextNode = baseNode;
            nextIndex = 0;
            prevNode = baseNode != null ? baseNode.prevNode : null;
        }

        public MyListIterator(int index) {
            this();
            for(int i = 0; i < index; i++) {
                next();
            }
        }

        @Override
        public boolean hasNext() {
            return nextNode != null && nextNode != lastNode.nextNode;
        }

        @Override
        public E next() {
            nextNode = nextNode.nextNode;
            prevNode = prevNode.nextNode;
            recentNode = prevNode;
            nextIndex++;
            return prevNode.element;
        }

        @Override
        public boolean hasPrevious() {
            return prevNode != null && prevNode != baseNode.prevNode;
        }

        @Override
        public E previous() {
            nextNode = nextNode.prevNode;
            prevNode = prevNode.prevNode;
            recentNode = nextNode;
            nextIndex--;
            return nextNode.element;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            recentNode.removeSelf();
            if(recentNode == prevNode) {
                prevNode = nextNode.prevNode;
                prevNode.nextNode = nextNode;
                nextIndex--;
            }
            if(recentNode == nextNode) {
                nextNode = prevNode.nextNode;
                nextNode.prevNode = prevNode;
            }
            if(recentNode == baseNode) {
                replaceBaseNode(recentNode.nextNode);
            }
            if(recentNode == lastNode) {
                replaceLastNode(recentNode.prevNode);
            }
            addSize(-1);
        }

        @Override
        public void set(E e) {
            recentNode.element = e;
        }

        @Override
        public void add(E e) {
            Node newNode = new Node(e, prevNode, nextNode);
            prevNode = newNode;
            if(nextIndex == 0) {
                replaceBaseNode(newNode);
            } else if(nextIndex == size) {
                replaceLastNode(newNode);
            }
            nextIndex++;
            addSize(1);
        }

    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex > toIndex || toIndex > size) {
            throw new IndexOutOfBoundsException();
        }

        if (toIndex == 0) {
            return new LinkedListTwo<E>(this, baseNode, baseNode.prevNode, 0);
        }

        Node base = baseNode;
        Node last = baseNode;

        for (int i = 0; i < fromIndex; i++) {
            base = base.nextNode;
        }
        for (int i = 0; i < toIndex - 1; i++) {
            last = last.nextNode;
        }

        return new LinkedListTwo<E>(this, base, last, toIndex - fromIndex);
    }

}
