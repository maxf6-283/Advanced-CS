import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

public class HashTable<K, V> implements Serializable {
    private HashMap<K, List<V>> backingMap;

    public HashTable() {
        backingMap = new HashMap<>();
    }

    public int size() {
        return backingMap.size();
    }

    public boolean isEmpty() {
        return backingMap.isEmpty();
    }

    public boolean containsKey(Object key) {
        return backingMap.containsKey(key);
    }

    public List<V> get(Object key) {
        return backingMap.get(key);
    }

    public void put(K key, V value) {
        if (!containsKey(key)) {
            backingMap.put(key, new DLList<>());
        }
        backingMap.get(key).add(value);
    }

    public List<V> remove(Object key) {
        return backingMap.remove(key);
    }

    public void remove(Object key, Object value) {
        backingMap.get(key).remove(value);
        if (backingMap.get(key).isEmpty()) {
            backingMap.remove(key);
        }
    }

    public V removeLast(Object key) {
        if (backingMap.containsKey(key)) {
            V v = backingMap.get(key).remove(backingMap.get(key).size() - 1);
            if (backingMap.get(key).isEmpty()) {
                backingMap.remove(key);
            }
            return v;
        }
        return null;
    }

    public Set<K> keySet() {
        return backingMap.keySet();
    }

    public Collection<List<V>> values() {
        return backingMap.values();
    }

    public Set<Entry<K, List<V>>> entrySet() {
        return backingMap.entrySet();
    }

}
