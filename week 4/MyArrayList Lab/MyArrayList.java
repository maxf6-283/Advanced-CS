public class MyArrayList<E> {
    private Object[] elements;
    private int size;

    public MyArrayList() {
        elements = new Object[8];
        size = 0;
    }

    private void doubleLength() {
        Object[] newElements = new Object[elements.length << 1];
        for (int i = 0; i < elements.length; i++) {
            newElements[i] = elements[i];
        }

        elements = newElements;
    }

    public boolean add(E e) {
        size++;
        if (size > elements.length) {
            doubleLength();
        }
        elements[size - 1] = e;

        return true;
    }

    public void add(int index, E e) {
        size++;
        if (size > elements.length) {
            doubleLength();
        }
        Object o = e;
        for (int i = index; i < size; i++) {
            Object temp = elements[i];
            elements[i] = o;
            o = temp;
        }
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        return (E)elements[index];
    }

    @SuppressWarnings("unchecked")
    public E remove(int index) {
        E el = (E) elements[index];
        size--;
        for (int i = index; i < size; i++) {
            elements[i] = elements[i + 1];
        }

        return el;
    }

    public void set(int index, E e) {
        elements[index] = e;
    }

    @Override
    public String toString() {
        String toReturn = "[";
        for(int i = 0; i < size; i++) {
            toReturn += elements[i] + ", ";
        }
        return toReturn.substring(0, toReturn.length() - 2) + "]";
    }

    public int size() {
        return size;
    }
}