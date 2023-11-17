import java.util.Set;
import java.util.Map.Entry;

public class MyHashTable<K, V> {
    private MyHashMap<K, DLList<V>> backMap = new MyHashMap<>();

    public void put(K key, V value) {
        if (backMap.containsKey(key)) {
            backMap.get(key).add(value);
        } else {
            DLList<V> l = new DLList<>();
            l.add(value);
            backMap.put(key, l);
        }
    }

    public void put(K key) {
        if (!backMap.containsKey(key)) {
            backMap.put(key, new DLList<>());
        }
    }

    public Set<Entry<K, DLList<V>>> entrySet() {
        return backMap.entrySet();
    }

    public Set<K> keySet() {
        return backMap.keySet();
    }

    public DLList<V> get(K key) {
        return backMap.get(key);
    }
}
