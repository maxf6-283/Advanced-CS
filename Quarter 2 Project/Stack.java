public class Stack<E> {
    private DLList<E> list;

    public Stack() {
        list = new DLList<>();
    }

    public void push(E e) {
        synchronized (this) {
            list.add(e);
        }
    }

    public E peek() {
        synchronized (this) {
            return list.getLast();
        }
    }

    public E pop() {
        synchronized (this) {
            return list.removeLast();
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
}
