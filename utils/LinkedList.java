import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.lang.reflect.Array;

/**
 * does not have full functionality
 */
public class LinkedList<E> implements List<E> {
    private Node baseNode = null;
    private Node lastNode = null;
    private int size = 0;

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
        while (node != null) {
            if (node.getElement().equals(o)) {
                return true;
            }
            node = node.nextNode();
        }

        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        Node node = baseNode;
        for (int i = 0; i < size; i++) {
            arr[i] = node.getElement();
            node = node.nextNode();
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
            a[i] = (T) node.getElement();
            node = node.nextNode();
        }

        return a;
    }

    @Override
    public boolean add(E e) {
        if (lastNode != null) {
            lastNode.addElement(e);
            lastNode = lastNode.nextNode();
        } else {
            baseNode = lastNode = new Node(e);
        }

        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node node = baseNode;
        while (node != null) {
            if (node.getElement().equals(o)) {
                node.removeSelf();
                size--;
                if(node == baseNode) {
                    baseNode = node.nextNode();
                }
                if(node == lastNode) {
                    lastNode = node.prevNode();
                }
                return true;
            }

            node = node.nextNode();
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(Collection<? extends E> c) {
        for (Object o : c) {
            lastNode.addElement((E) o);
            lastNode = lastNode.nextNode();
        }

        size += c.size();
        return c.size() != 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(int index, Collection<? extends E> c) {
        Node node = baseNode;
        for (int i = 0; i < index - 1; i++) {
            node = node.nextNode();
        }

        for (Object o : c) {
            node.addElement((E) o);
            node = node.nextNode();
        }

        if (index == size) {
            lastNode = node;
        }

        size += c.size();
        return c.size() != 0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Node node = baseNode;
        boolean removed = false;
        while (node != null) {
            if (c.contains(node.getElement())) {
                node.removeSelf();
                removed = true;
                size--;
                if (node == lastNode) {
                    lastNode = node.prevNode();
                }
            }

            node = node.nextNode();
        }

        return removed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Node node = baseNode;
        boolean removed = false;
        while (node != null) {
            if (!c.contains(node.getElement())) {
                node.removeSelf();
                removed = true;
                size--;
                if (node == lastNode) {
                    lastNode = node.prevNode();
                }
            }
        }

        return removed;
    }

    @Override
    public void clear() {
        baseNode = null;
        lastNode = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for list length " + size);
        }
        Node node = baseNode;
        for (int i = 0; i < index; i++) {
            node = node.nextNode();
        }

        return node.getElement();
    }

    @Override
    public E set(int index, E element) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for list length " + size);
        }
        Node node = baseNode;
        for (int i = 0; i < index; i++) {
            node = node.nextNode();
        }

        E toReturn = node.getElement();
        node.setElement(element);
        return (toReturn);
    }

    @Override
    public void add(int index, E element) {
        if (index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for list length " + size);
        }
        
        if(size == 0) {
            lastNode = baseNode = new Node(element);
            size++;
            return;
        }
        if(index == 0) {
            Node newBaseNode = new Node(element);
            newBaseNode.setNextNode(baseNode);
            baseNode.setPrevNode(newBaseNode);
            baseNode = newBaseNode;
            size++;
            return;
        }
        size++;
        
        Node node = baseNode;
        for (int i = 0; i < index - 1; i++) {
            node = node.nextNode();
        }
        
        node.addElement(element);
        
        if(index == size) {
            lastNode = node;
        }
    }

    @Override
    public E remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for list length " + size);
        }

        Node node = baseNode;
        for (int i = 0; i < index; i++) {
            node = node.nextNode();
        }

        node.removeSelf();
        size--;
        if(node == lastNode) {
            lastNode = node.prevNode();
        }
        if(node == baseNode) {
            baseNode = node.nextNode();
        }
        return node.getElement();
    }

    @Override
    public int indexOf(Object o) {
        Node node = baseNode;
        for (int i = 0; node != null; i++) {
            if (node.equals(o)) {
                return i;
            }
            node = node.nextNode();
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node node = lastNode;
        for (int i = size - 1; node != null; i--) {
            if (node.equals(o)) {
                return i;
            }
            node = node.prevNode();
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

    // /**
    //  * This will only work with specific methods because sadness
    //  */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new SubLinkedList(fromIndex, toIndex);
    }

    private class SubLinkedList implements List<E> {
        private Node subBaseNode;
        private Node subLastNode;
        private int size;

        public SubLinkedList(int startIndex, int endIndex) {
            subBaseNode = baseNode;
            for (int i = 0; i < startIndex; i++) {
                subBaseNode = subBaseNode.nextNode();
            }

            subLastNode = subBaseNode;
            for (int i = startIndex; i < endIndex; i++) {
                subLastNode = subLastNode.nextNode();
            }

            size = endIndex - startIndex;
        }

        private SubLinkedList(Node startingNode, int startIndex, int endIndex) {
            subBaseNode = startingNode;
            for (int i = 0; i < startIndex; i++) {
                subBaseNode = subBaseNode.nextNode();
            }

            subLastNode = subBaseNode;
            for (int i = startIndex; i < endIndex; i++) {
                subLastNode = subLastNode.nextNode();
            }

            size = endIndex - startIndex;
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
            Node node = subBaseNode;
            while (node != subLastNode) {
                if (node.getElement().equals(o)) {
                    return true;
                }
            }

            return subLastNode.getElement().equals(o);
        }

        @Override
        public Iterator<E> iterator() {
            return new MyIterator();
        }

        @Override
        public Object[] toArray() {
            Object[] arr = new Object[size];

            Node node = subBaseNode;
            for (int i = 0; i < size; i++) {
                arr[i] = node.getElement();
                node = node.nextNode();
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

            Node node = subBaseNode;
            for (int i = 0; i < size; i++) {
                a[i] = (T) node.getElement();
                node = node.nextNode();
            }

            return a;
        }

        @Override
        public boolean add(E e) {
            size++;
            LinkedList.this.size++;
            subLastNode.addElement(e);

            return true;
        }

        @Override
        public boolean remove(Object o) {
            Node node = subBaseNode;
            while (node != subLastNode) {
                if (node.getElement().equals(o)) {
                    node.removeSelf();
                    size--;
                    LinkedList.this.size--;
                    return true;
                }

                node = node.nextNode();
            }
            if (subLastNode.getElement().equals(o)) {
                subLastNode.removeSelf();
                subLastNode = subLastNode.prevNode();
            }
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            for (Object o : c) {
                if (!contains(o)) {
                    return false;
                }
            }

            return true;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            int startIndex = 0;
            Node node = baseNode;
            while (node != subBaseNode) {
                node = node.nextNode();
                startIndex++;
            }
            return LinkedList.this.addAll(startIndex + size, c);
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            int startIndex = 0;
            Node node = baseNode;
            while (node != subBaseNode) {
                node = node.nextNode();
                startIndex++;
            }
            return LinkedList.this.addAll(startIndex + index, c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            Node node = subBaseNode;
            boolean removed = false;
            while (node != subLastNode) {
                if (c.contains(node.getElement())) {
                    node.removeSelf();
                    removed = true;
                    size--;
                    LinkedList.this.size--;
                }

                node = node.nextNode();
            }
            if (c.contains(subLastNode.getElement())) {
                subLastNode.removeSelf();
                removed = true;
                size--;
                LinkedList.this.size--;
                subLastNode = subLastNode.prevNode();
            }

            return removed;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            Node node = subBaseNode;
            boolean removed = false;
            while (node != subLastNode) {
                if (!c.contains(node.getElement())) {
                    node.removeSelf();
                    removed = true;
                    size--;
                    LinkedList.this.size--;
                }

                node = node.nextNode();
            }
            if (!c.contains(subLastNode.getElement())) {
                subLastNode.removeSelf();
                removed = true;
                size--;
                LinkedList.this.size--;
                subLastNode = subLastNode.prevNode();
            }

            return removed;
        }

        @Override
        public void clear() {
            LinkedList.this.size -= size;
            size = 0;

            if(subBaseNode == subLastNode && subBaseNode == null) {
                LinkedList.this.clear();
                return;
            }

            if(subBaseNode == null) {
                baseNode = subLastNode;
                subLastNode.setPrevNode(null);
                return;
            }
            subBaseNode = subBaseNode.prevNode();
            if(subBaseNode == null) {
                baseNode = subLastNode;
                subLastNode.setPrevNode(null);
            } else {
                subBaseNode.setNextNode(subLastNode);
                subLastNode.setPrevNode(subBaseNode);
            }
        }

        @Override
        public E get(int index) {
            Node node = subBaseNode;
            for (int i = 0; i < index; i++) {
                node = node.nextNode();
            }

            return node.getElement();
        }

        @Override
        public E set(int index, E element) {
            Node node = subBaseNode;
            for (int i = 0; i < index; i++) {
                node = node.nextNode();
            }

            E replaced = node.getElement();
            node.setElement(element);
            return replaced;
        }

        @Override
        public void add(int index, E element) {
            Node node = subBaseNode;
            for (int i = 0; i < index; i++) {
                node = node.nextNode();
            }

            node.addElement(element);
            if (node == subLastNode) {
                subLastNode = node.nextNode();
            }
            if (node == lastNode) {
                lastNode = node.nextNode();
            }
            LinkedList.this.size++;
            size++;
        }

        @Override
        public E remove(int index) {
            Node node = subBaseNode;
            for (int i = 0; i < index; i++) {
                node = node.nextNode();
            }

            node.removeSelf();
            if (node == subLastNode) {
                subLastNode = node.prevNode();
            }
            if (node == lastNode) {
                lastNode = node.prevNode();
            }

            return node.getElement();
        }

        @Override
        public int indexOf(Object o) {
            Node node = subBaseNode;
            for (int i = 0; i < size; i++) {
                if (node.getElement().equals(o)) {
                    return i;
                }
                node = node.nextNode();
            }
            return -1;
        }

        @Override
        public int lastIndexOf(Object o) {
            Node node = subLastNode;
            for (int i = size - 1; i >= 0; i--) {
                if (node.getElement().equals(o)) {
                    return i;
                }
                node = node.prevNode();
            }
            return -1;
        }

        @Override
        public ListIterator<E> listIterator() {
            return new MyListIterator(0);
        }

        @Override
        public ListIterator<E> listIterator(int index) {
            return new MyListIterator(index);
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            return new SubLinkedList(subBaseNode, fromIndex, toIndex);
        }

        private class MyIterator implements Iterator<E> {
            private Node atNode = subBaseNode;

            @Override
            public boolean hasNext() {
                return atNode.nextNode() != subLastNode.nextNode();
            }

            @Override
            public E next() {
                E next = atNode.element;
                atNode = atNode.nextNode();
                return next;
            }
        }

        private class MyListIterator extends LinkedList<E>.MyListIterator {
            public MyListIterator(int index) {
                super();
                setAtNode(subBaseNode);
                for (int i = 0; i < index; i++) {
                    next();
                }
            }

            @Override
            public boolean hasNext() {
                if (getAtNode() == subLastNode) {
                    return false;
                }
                return super.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                if (getAtNode() == subBaseNode) {
                    return false;
                }
                return super.hasPrevious();
            }

            @Override
            public void add(E e) {
                super.add(e);
                size++;
            }

            @Override
            public void remove() {
                lastNodeUsed().removeSelf();
                if (getAtNode() == lastNodeUsed()) {
                    setAtNode(getAtNode().nextNode());
                }
                if (lastNodeUsed() == lastNode) {
                    lastNode = lastNodeUsed().prevNode();
                }
                if (lastNodeUsed() == subLastNode) {
                    subLastNode = lastNodeUsed().prevNode();
                }

                LinkedList.this.size--;
                size--;
            }
        }

        @Override
        public String toString() {
            String str = "[";

            Node node = subBaseNode;
            while (node != subLastNode) {
                str += node.getElement().toString() + ", ";
                node = node.nextNode();
            }

            return str + subLastNode.getElement().toString() + "]";
        }
    }

    private class Node {
        private E element;
        private Node nextNode;
        private Node prevNode;

        public Node(E el) {
            element = el;
            nextNode = null;
            prevNode = null;
        }

        private Node(E el, Node prev) {
            element = el;
            nextNode = null;
            prevNode = prev;
        }

        public void addElement(E el) {
            Node toNext = nextNode;
            nextNode = new Node(el, this);
            if(toNext != null) {
                nextNode.setNextNode(toNext);
                toNext.setPrevNode(nextNode);
            }
        }

        public E getElement() {
            return element;
        }

        public Node nextNode() {
            return nextNode;
        }

        public Node prevNode() {
            return prevNode;
        }

        public void setNextNode(Node node) {
            nextNode = node;
        }

        public void setPrevNode(Node node) {
            prevNode = node;
        }

        public void removeSelf() {
            if (nextNode != null) {
                nextNode.setPrevNode(prevNode);
            }
            if (prevNode != null) {
                prevNode.setNextNode(nextNode);
            }
        }

        public void setElement(E el) {
            element = el;
        }
    }

    private class MyIterator implements Iterator<E> {
        private Node atNode = baseNode;

        @Override
        public boolean hasNext() {
            return atNode != null;
        }

        @Override
        public E next() {
            E next = atNode.getElement();
            atNode = atNode.nextNode();
            return next;
        }
    }

    private class MyListIterator implements ListIterator<E> {
        private Node atNode;
        private Node lastNodeUsed;
        private int index;

        public void setAtNode(Node node) {
            atNode = node;
        }

        public Node getAtNode() {
            return atNode;
        }

        public Node lastNodeUsed() {
            return lastNodeUsed;
        }

        public MyListIterator() {
            index = 0;
            atNode = baseNode;
        }

        public MyListIterator(int i) {
            index = i;
            atNode = baseNode;
            for (i = 0; i < index; i++) {
                atNode = atNode.nextNode();
            }
        }

        @Override
        public boolean hasNext() {
            return atNode.nextNode() != null;
        }

        @Override
        public E next() {
            E next = atNode.getElement();
            lastNodeUsed = atNode;
            atNode = atNode.nextNode();
            index++;
            return next;
        }

        @Override
        public boolean hasPrevious() {
            return atNode.prevNode() != null;
        }

        @Override
        public E previous() {
            atNode = atNode.prevNode();
            lastNodeUsed = atNode;
            index--;
            return atNode.getElement();
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            lastNodeUsed.removeSelf();
            if (atNode == lastNodeUsed) {
                atNode = atNode.nextNode();
            }
            if (lastNodeUsed == lastNode) {
                lastNode = lastNodeUsed.prevNode;
            }

            size--;
        }

        @Override
        public void set(E e) {
            lastNodeUsed.setElement(e);
        }

        @Override
        public void add(E e) {
            atNode.addElement(e);
            size++;
        }

    }

    @Override
    public String toString() {
        if(size == 0) {
            return "[]";
        }
        String str = "[";

        Node node = baseNode;
        while (node != lastNode) {
            str += node.getElement().toString() + ", ";
            node = node.nextNode();
        }

        return str + lastNode.getElement().toString() + "]";
    }

    public boolean equals(Object other) {
        if(!(other instanceof List)) {
            return false;
        }
        List<?> list = (List<?>) other;
        if(list.size() != size()) {
            return false;
        }
        Node node = baseNode;
        for(int i = 0; i < size; i++) {
            if(node.getElement().equals(list.get(i))) {
                return false;
            }
            node = node.nextNode();
        }
        return true;
    }
}
