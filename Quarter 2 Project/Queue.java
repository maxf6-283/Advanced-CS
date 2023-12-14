public class Queue<E> {
    private DLList<E> list;

    public Queue() {
        list = new DLList<>();
    }

    public void push(E e) {
        lastAdded = e;
        synchronized (this) {
            list.add(e);
        }
    }

    public E peek() {
        synchronized (this) {
            return list.get(0);
        }
    }

    public E pop() {
        synchronized (this) {
            return list.remove(0);
        }
    }

    public boolean isEmpty() {
        synchronized (this) {
            return list.isEmpty();
        }
    }

    public void clear() {
        synchronized (this) {
            list.clear();
        }
    }

    private E lastAdded;

    public E lastAdded() {
        return lastAdded;
    }
}
