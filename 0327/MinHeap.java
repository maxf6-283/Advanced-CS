import java.util.ArrayList;

public class MinHeap<E extends Comparable<E>> {
    private ArrayList<E> list;

    public MinHeap() {
        list = new ArrayList<>();
    }

    public void add(E e) {
        list.add(e);

        int index = list.size() - 1;
        while (index != 0) {
            if (e.compareTo(list.get((index - 1) / 2)) < 0) {
                list.set(index, list.get((index - 1) / 2));
                list.set((index - 1) / 2, e);
                index = (index - 1) / 2;
            } else {
                return;
            }
        }
    }

    public String toString() {
        return list.toString();
    }

    public E poll() {
        E e = list.get(0);
        if(list.size() == 1) {
            list.clear();
            return e;
        }
        list.set(0, list.removeLast());

        int index = 0;

        while (true) {
            if (index * 2 + 1 >= list.size()) {
                return e;
            }
            if (index * 2 + 2 == list.size()) {
                index = index * 2 + 1;
            } else if (list.get(index * 2 + 1).compareTo(list.get(index * 2 + 2)) < 0) {
                index = index * 2 + 1;
            } else {
                index = index * 2 + 2;
            }

            if (list.get(index).compareTo(list.get((index - 1) / 2)) < 0) {
                E temp = list.get(index);
                list.set(index, list.get((index - 1) / 2));
                list.set((index - 1) / 2, temp);
            } else {
                return e;
            }
        }
    }

    public E peek() {
        return list.get(0);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}