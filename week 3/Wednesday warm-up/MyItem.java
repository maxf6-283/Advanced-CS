public class MyItem<T> {
    private T o;

    public MyItem(T o) {
        this.o = o;
    }

    public T get() {
        return o;
    }
}