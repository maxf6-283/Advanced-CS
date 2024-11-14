import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;

public class TestList<Element> implements List<Element> {
    private List<Element> list1;
    private List<Element> list2;

    @SafeVarargs
    public TestList(List<Element>... lists) {
        if (lists.length < 2) {
            throw new IllegalArgumentException();
        } else if (lists.length == 2) {
            list1 = lists[0];
            list2 = lists[1];

            list1.clear();
            list2.clear();
        } else {
            TestList<Element> listBuilder = new TestList<>(lists[0], lists[1]);
            for(int i = 2; i < lists.length - 1; i++) {
                listBuilder = new TestList<>(listBuilder, lists[i]);
            }

            list1 = listBuilder;
            list2 = lists[lists.length - 1];
        }
    }

    public TestList(List<Element> list1, List<Element> list2) {
        this.list1 = list1;
        this.list2 = list2;

        list1.clear();
        list2.clear();
    }

    private void printInfo() {
        System.out.println("List 1: " + list1);
        System.out.println("List 2: " + list2);
    }

    @Override
    public String toString() {
        return "List 1: " + list1 + "\nList 2: " + list2;
    }

    @Override
    public int size() {
        int size = list1.size();
        if (size == list2.size()) {
            return size;
        } else {
            printInfo();
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean isEmpty() {
        boolean empty = list1.isEmpty();
        if (empty == list2.isEmpty()) {
            return empty;
        } else {
            printInfo();
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean contains(Object o) {
        boolean contains = list1.contains(o);
        if (contains == list2.contains(o)) {
            return contains;
        } else {
            printInfo();
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Iterator<Element> iterator() {
        return new TestListIterator();
    }

    private class TestListIterator implements Iterator<Element> {
        private Iterator<Element> iter1;
        private Iterator<Element> iter2;

        public TestListIterator() {
            iter1 = list1.iterator();
            iter2 = list2.iterator();
        }

        @Override
        public boolean hasNext() {
            boolean hasNext = iter1.hasNext();
            if (hasNext == iter2.hasNext()) {
                return hasNext;
            } else {
                printInfo();
                throw new IllegalArgumentException();
            }
        }

        @Override
        public Element next() {
            Element next = iter1.next();
            if (next == iter2.next()) {
                return next;
            } else {
                printInfo();
                throw new IllegalArgumentException();
            }
        }

    }

    @Override
    public Object[] toArray() {
        Object[] arr1 = list1.toArray();
        Object[] arr2 = list2.toArray();

        if (arr1.length != arr2.length) {
            printInfo();
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[1]) {
                printInfo();
                throw new IllegalArgumentException();
            }
        }

        return arr1;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T[] arr1 = list1.toArray(a);
        T[] arr2 = list2.toArray(a);

        if (arr1.length != arr2.length) {
            printInfo();
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[1]) {
                printInfo();
                throw new IllegalArgumentException();
            }
        }

        return arr1;
    }

    @Override
    public boolean add(Element e) {
        boolean add1 = list1.add(e);
        boolean add2 = list2.add(e);

        if (add1 != add2) {
            printInfo();
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < size(); i++) {
            if (list1.get(i) != list2.get(i)) {
                printInfo();
                throw new IllegalArgumentException();
            }
        }

        return add1;
    }

    @Override
    public boolean remove(Object o) {
        boolean rem1 = list1.remove(o);
        boolean rem2 = list2.remove(o);

        if (rem1 != rem2) {
            printInfo();
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < size(); i++) {
            if (list1.get(i) != list2.get(i)) {
                printInfo();
                throw new IllegalArgumentException();
            }
        }

        return rem1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean contAll = list1.containsAll(c);
        if (contAll != list2.containsAll(c)) {
            printInfo();
            throw new IllegalArgumentException();
        }

        return contAll;
    }

    @Override
    public boolean addAll(Collection<? extends Element> c) {
        boolean addAll1 = list1.addAll(c);
        boolean addAll2 = list2.addAll(c);

        if (addAll1 != addAll2) {
            printInfo();
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < size(); i++) {
            if (list1.get(i) != list2.get(i)) {
                printInfo();
                throw new IllegalArgumentException();
            }
        }

        return addAll1;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Element> c) {
        boolean addAll1 = list1.addAll(index, c);
        boolean addAll2 = list2.addAll(index, c);

        if (addAll1 != addAll2) {
            printInfo();
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < size(); i++) {
            if (list1.get(i) != list2.get(i)) {
                printInfo();
                throw new IllegalArgumentException();
            }
        }

        return addAll1;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean remAll1 = list1.removeAll(c);
        boolean remAll2 = list2.removeAll(c);

        if (remAll1 != remAll2) {
            printInfo();
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < size(); i++) {
            if (list1.get(i) != list2.get(i)) {
                printInfo();
                throw new IllegalArgumentException();
            }
        }

        return remAll1;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean retAll1 = list1.retainAll(c);
        boolean retAll2 = list2.retainAll(c);

        if (retAll1 != retAll2) {
            printInfo();
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < size(); i++) {
            if (list1.get(i) != list2.get(i)) {
                printInfo();
                throw new IllegalArgumentException();
            }
        }

        return retAll1;
    }

    @Override
    public void clear() {
        list1.clear();
        list2.clear();
        if (size() != 0) {
            printInfo();
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Element get(int index) {
        Element el1 = list1.get(index);
        Element el2 = list2.get(index);

        if (el1 != el2) {
            printInfo();
            throw new IllegalArgumentException();
        }

        return el1;
    }

    @Override
    public Element set(int index, Element element) {
        Element el1 = list1.set(index, element);
        Element el2 = list2.set(index, element);

        if (el1 != el2) {
            printInfo();
            throw new IllegalArgumentException();
        }

        return el1;
    }

    @Override
    public void add(int index, Element element) {
        list1.add(element);
        list2.add(element);

        for (int i = 0; i < size(); i++) {
            if (list1.get(i) != list2.get(i)) {
                printInfo();
                throw new IllegalArgumentException();
            }
        }
    }

    @Override
    public Element remove(int index) {
        Element el1 = list1.remove(index);
        Element el2 = list2.remove(index);

        if (el1 != el2) {
            printInfo();
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < size(); i++) {
            if (list1.get(i) != list2.get(i)) {
                printInfo();
                throw new IllegalArgumentException();
            }
        }

        return el1;
    }

    @Override
    public int indexOf(Object o) {
        int index1 = list1.indexOf(o);
        int index2 = list2.indexOf(o);

        if (index1 != index2) {
            printInfo();
            throw new IllegalArgumentException();
        }

        return index1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index1 = list1.lastIndexOf(o);
        int index2 = list2.lastIndexOf(o);

        if (index1 != index2) {
            printInfo();
            throw new IllegalArgumentException();
        }

        return index1;
    }

    @Override
    public ListIterator<Element> listIterator() {
        return new TestListListIterator();
    }

    @Override
    public ListIterator<Element> listIterator(int index) {
        return new TestListListIterator(index);
    }

    private class TestListListIterator implements ListIterator<Element> {
        private ListIterator<Element> listIterator1;
        private ListIterator<Element> listIterator2;

        public TestListListIterator() {
            listIterator1 = list1.listIterator();
            listIterator2 = list2.listIterator();
        }

        public TestListListIterator(int index) {
            listIterator1 = list1.listIterator(index);
            listIterator2 = list2.listIterator(index);
        }

        @Override
        public boolean hasNext() {
            boolean next1 = listIterator1.hasNext();
            boolean next2 = listIterator2.hasNext();

            if (next1 != next2) {
                printInfo();
                throw new IllegalArgumentException();
            }

            return next1;
        }

        @Override
        public Element next() {
            Element next1 = listIterator1.next();
            Element next2 = listIterator1.next();

            if (next1 != next2) {
                printInfo();
                throw new IllegalArgumentException();
            }

            return next1;
        }

        @Override
        public boolean hasPrevious() {
            boolean prev1 = listIterator1.hasPrevious();
            boolean prev2 = listIterator2.hasPrevious();

            if (prev1 != prev2) {
                printInfo();
                throw new IllegalArgumentException();
            }

            return prev1;
        }

        @Override
        public Element previous() {
            Element prev1 = listIterator1.previous();
            Element prev2 = listIterator2.previous();

            if (prev1 != prev2) {
                printInfo();
                throw new IllegalArgumentException();
            }

            return prev1;
        }

        @Override
        public int nextIndex() {
            int index1 = listIterator1.nextIndex();
            int index2 = listIterator2.nextIndex();

            if (index1 != index2) {
                printInfo();
                throw new IllegalArgumentException();
            }

            return index1;
        }

        @Override
        public int previousIndex() {
            int index1 = listIterator1.previousIndex();
            int index2 = listIterator2.previousIndex();

            if (index1 != index2) {
                printInfo();
                throw new IllegalArgumentException();
            }

            return index1;
        }

        @Override
        public void remove() {
            listIterator1.remove();
            listIterator2.remove();

            for (int i = 0; i < size(); i++) {
                if (list1.get(i) != list2.get(i)) {
                    printInfo();
                    throw new IllegalArgumentException();
                }
            }
        }

        @Override
        public void set(Element e) {
            listIterator1.set(e);
            listIterator2.set(e);

            for (int i = 0; i < size(); i++) {
                if (list1.get(i) != list2.get(i)) {
                    printInfo();
                    throw new IllegalArgumentException();
                }
            }
        }

        @Override
        public void add(Element e) {
            listIterator1.add(e);
            listIterator2.add(e);

            for (int i = 0; i < size(); i++) {
                if (list1.get(i) != list2.get(i)) {
                    printInfo();
                    throw new IllegalArgumentException();
                }
            }
        }

    }

    @Override
    public List<Element> subList(int fromIndex, int toIndex) {
        return new TestList<Element>(list1.subList(fromIndex, toIndex), list2.subList(fromIndex, toIndex));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof List<?> l) {
            if (l.size() != size()) {
                return false;
            }
            for (int i = 0; i < size(); i++) {
                if (get(i).equals(l.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
