import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.lang.reflect.Array;

public class ArrayList<E> implements List<E>, RandomAccess {
    private Object[] elements = new Object[1];
    private int size = 0;

    public ArrayList() {}
    public ArrayList(Collection<? extends E> c) {
        size = c.size();
        elements = new Object[Integer.highestOneBit(size) << 1];
        int i = 0;
        for(E e : c) {
            elements[i] = e;
            i++;
        }
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
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                return true;
            }
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
        for (int i = 0; i < size; i++) {
            arr[i] = elements[i];
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

        for (int i = 0; i < size; i++) {
            a[i] = (T) elements[i];
        }

        return a;
    }

    @Override
    public boolean add(E e) {
        size++;
        if (elements.length < size) {
            Object[] newElements = new Object[elements.length * 2];
            for (int i = 0; i < size - 1; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }

        elements[size - 1] = e;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < elements.length; i++) {
            if (o.equals(elements[i])) {
                size--;
                for (int j = i; j < size; j++) {
                    elements[j] = elements[j + 1];
                }
                return true;
            }
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
        int index = size;
        size += c.size();
        if (elements.length < size) {
            Object[] newElements = new Object[elements.length * 2];
            while (newElements.length < size) {
                newElements = new Object[newElements.length * 2];
            }
            for (int i = 0; i < size - c.size(); i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }

        for (Object o : c) {
            elements[index] = o;
            index++;
        }

        return c.size() > 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        size += c.size();
        if (elements.length < size) {
            Object[] newElements = new Object[elements.length * 2];
            while (newElements.length < size) {
                newElements = new Object[newElements.length * 2];
            }
            for (int i = 0; i < size - c.size(); i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }

        for (int i = size - c.size() - 1; i >= index; i--) {
            elements[i + c.size()] = elements[i];
        }
        for (Object o : c) {
            elements[index] = o;
            index++;
        }

        return c.size() > 0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = false;
        int offset = 0;
        for (int i = 0; i < size; i++) {
            if (c.contains(elements[i])) {
                removed = true;
                offset++;
            } else {
                elements[i - offset] = elements[i];
            }
        }

        size -= offset;

        return removed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean removed = false;
        int offset = 0;
        for (int i = 0; i < size; i++) {
            if (!c.contains(elements[i])) {
                removed = true;
                offset++;
            } else {
                elements[i - offset] = elements[i];
            }
        }

        size -= offset;

        return removed;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < size) {
            return (E) elements[index];
        } else {
            throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        if (index < size) {
            E toReturn = (E) elements[index];
            elements[index] = element;
            return toReturn;
        } else {
            throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void add(int index, E element) {
        size++;
        if (elements.length < size) {
            Object[] newElements = new Object[elements.length * 2];
            for (int i = 0; i < size - 1; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }

        for (int i = index; i < size; i++) {
            Object temp = element;
            element = (E) elements[i];
            elements[i] = temp;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        E toReturn = (E) elements[index];
        size--;
        for (int i = index; i < size; i++) {
            elements[i] = elements[i + 1];
        }
        return toReturn;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (elements[i].equals(o)) {
                return i;
            }
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
        return new SubArrayList(fromIndex, toIndex);
    }

    private class SubArrayList implements List<E> {
        private int startIndex;
        private int endIndex;

        public SubArrayList(int start, int end) {
            startIndex = start;
            endIndex = end;
            if (start < 0 || end > size) {
                throw new ArrayIndexOutOfBoundsException("Sublist out of bounds");
            }
        }

        @Override
        public int size() {
            return endIndex - startIndex;
        }

        @Override
        public boolean isEmpty() {
            return endIndex == startIndex;
        }

        @Override
        public boolean contains(Object o) {
            for (int i = startIndex; i < endIndex; i++) {
                if (elements[i].equals(o)) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public Iterator<E> iterator() {
            return new MyIterator();
        }

        @Override
        public Object[] toArray() {
            Object[] arr = new Object[endIndex - startIndex];
            for (int i = startIndex; i < endIndex; i++) {
                arr[i] = elements[i];
            }

            return arr;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a) {
            if (a.length < size()) {
                a = (T[]) Array.newInstance(a.getClass().getComponentType(), size());
            } else if (a.length > size()) {
                a[size()] = null;
            }

            for (int i = 0; i < size(); i++) {
                a[i] = (T) elements[i + startIndex];
            }

            return a;
        }

        @Override
        public boolean add(E e) {
            ArrayList.this.add(endIndex, e);
            endIndex++;
            return true;
        }

        @Override
        public boolean remove(Object o) {
            for (int i = startIndex; i < endIndex; i++) {
                if (elements[i].equals(o)) {
                    size--;
                    endIndex--;
                    for (int j = i; j < size; j++) {
                        elements[j] = elements[j + 1];
                    }

                    return true;
                }
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
            return ArrayList.this.addAll(endIndex, c);
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            if (index > endIndex) {
                throw new ArrayIndexOutOfBoundsException(
                        "Index " + index + " out of bounds for array length " + size());
            }
            return ArrayList.this.addAll(startIndex + index, c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            boolean removed = false;
            int offset = 0;
            for (int i = startIndex; i < size; i++) {
                if (i < endIndex && c.contains(elements[i])) {
                    removed = true;
                    offset++;
                    size--;
                    endIndex--;
                    elements[i] = elements[i + offset];
                    i--;
                } else {
                    elements[i] = elements[i + offset];
                }

            }

            return removed;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            boolean removed = false;
            int offset = 0;
            for (int i = startIndex; i < size; i++) {
                if (i < endIndex && !c.contains(elements[i])) {
                    removed = true;
                    offset++;
                    size--;
                    endIndex--;
                    elements[i] = elements[i + offset];
                    i--;
                } else {
                    elements[i] = elements[i + offset];
                }

            }

            return removed;
        }

        @Override
        public void clear() {
            size -= size();
            for (int i = startIndex; i < size; i++) {
                elements[i] = elements[i + size()];
            }
            endIndex = startIndex;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E get(int index) {
            return (E) elements[index + startIndex];
        }

        @Override
        public E set(int index, E element) {
            return ArrayList.this.set(index + startIndex, element);
        }

        @Override
        public void add(int index, E element) {
            ArrayList.this.add(index + startIndex, element);
            endIndex++;
        }

        @Override
        public E remove(int index) {
            E el = ArrayList.this.remove(index + startIndex);
            endIndex--;
            return el;
        }

        @Override
        public int indexOf(Object o) {
            for (int i = startIndex; i < endIndex; i++) {
                if (elements[i].equals(o)) {
                    return i;
                }
            }

            return -1;
        }

        @Override
        public int lastIndexOf(Object o) {
            for (int i = endIndex - 1; i >= startIndex; i--) {
                if (elements[i].equals(o)) {
                    return i;
                }
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
            return new SubArrayList(fromIndex, toIndex);
        }

        private class MyIterator implements Iterator<E> {
            int pointer = startIndex;

            @Override
            public boolean hasNext() {
                return pointer < endIndex;
            }

            @Override
            @SuppressWarnings("unchecked")
            public E next() {
                pointer++;
                return (E) elements[pointer - 1];
            }

            @Override
            public void remove() {
                ArrayList.this.remove(pointer);
                endIndex--;
            }
        }

        private class MyListIterator extends ArrayList<E>.MyListIterator {
            public MyListIterator(int index) {
                super(index + startIndex);
            }

            @Override
            public boolean hasNext() {
                return pointer < endIndex - 1;
            }

            @Override
            public boolean hasPrevious() {
                return pointer >= startIndex;
            }

            @Override
            public int nextIndex() {
                return super.nextIndex() - startIndex;
            }

            @Override
            public int previousIndex() {
                return super.previousIndex() - startIndex;
            }
        }

        @Override
        public String toString() {
            String str = "[";
            for (int i = startIndex; i < endIndex - 1; i++) {
                str += elements[i] + ", ";
            }

            return str + elements[endIndex - 1] + "]";
        }

        @Override
        public boolean equals(Object other) {
            if(!(other instanceof List)) {
                return false;
            }
            List<?> list = (List<?>) other;
            if(list.size() != size()) {
                return false;
            }
            for(int i = 0; i < size; i++) {
                if(!elements[i].equals(list.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    private class MyIterator implements Iterator<E> {
        int pointer = 0;

        @Override
        public boolean hasNext() {
            return pointer < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            pointer++;
            return (E) elements[pointer - 1];
        }

        @Override
        public void remove() {
            pointer--;
            ArrayList.this.remove(pointer);
        }
    }

    private class MyListIterator implements ListIterator<E> {
        int pointer;
        int lastIndexUsed;

        public MyListIterator(int index) {
            pointer = index;
        }

        @Override
        public boolean hasNext() {
            return pointer < size - 1;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            lastIndexUsed = pointer;
            pointer++;
            return (E) elements[pointer - 1];
        }

        @Override
        public boolean hasPrevious() {
            return pointer > 0;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E previous() {
            pointer--;
            lastIndexUsed = pointer;
            return (E) elements[pointer];
        }

        @Override
        public int nextIndex() {
            return pointer;
        }

        @Override
        public int previousIndex() {
            return pointer - 1;
        }

        @Override
        public void remove() {
            ArrayList.this.remove(lastIndexUsed);
            size--;
        }

        @Override
        public void set(E e) {
            ArrayList.this.set(lastIndexUsed, e);
        }

        @Override
        public void add(E e) {
            ArrayList.this.add(pointer, e);
            size++;
        }

    }

    @Override
    public String toString() {
        if(size == 0) {
            return "[]";
        }
        String str = "[";
        for (int i = 0; i < size - 1; i++) {
            str += elements[i] + ", ";
        }

        return str + elements[size - 1] + "]";
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof List)) {
            return false;
        }
        List<?> list = (List<?>) other;
        if(list.size() != size()) {
            return false;
        }
        for(int i = 0; i < size; i++) {
            if(!elements[i].equals(list.get(i))) {
                return false;
            }
        }
        return true;
    }
}