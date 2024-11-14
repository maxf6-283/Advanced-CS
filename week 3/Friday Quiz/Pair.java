public class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K myKey, V myValue) {
        key = myKey;
        value = myValue;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key.toString() + ", " + value.toString();
    }
}
