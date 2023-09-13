public class MyItem<K, V> {
    private K key;
    private V value;

    public MyItem(K k, V v) {
        key = k;
        value = v;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key.toString() + ": " + value.toString();
    }
}