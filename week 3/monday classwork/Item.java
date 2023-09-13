public class Item<T> {
    T o;

    public Item(T o) {
        this.o = o;
    }

    public T get() {
        return o;
    }
}