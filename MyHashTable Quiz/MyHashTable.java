import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MyHashTable<K, V> {
    private class Pair {
        public K key;
        public List<V> values;

        public Pair(K key) {
            this.key = key;
            values = new LinkedList<>();
        }

        public Pair(K key, V value) {
            this(key);
            values.add(value);
        }
    }

    private List<Pair>[] table;
    private HashSet<K> keySet;

    @SuppressWarnings("unchecked")
    public MyHashTable() {
        table = new List[100];
        keySet = new HashSet<>();
    }

    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    public void put(K key, V value) {
        int index = getIndex(key);
        keySet.add(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
            table[index].add(new Pair(key, value));
        } else {
            for (Pair p : table[index]) {
                if (p.key.equals(key)) {
                    p.values.add(value);
                    return;
                }
            }
            table[index].add(new Pair(key, value));
        }
    }

    public List<V> get(K key) {
        int index = getIndex(key);
        for (Pair p : table[index]) {
            if (p.key.equals(key)) {
                return p.values;
            }
        }
        return null;
    }

    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public String toString() {
        String str = "";
        for (K key : keySet) {
            str += String.format("#%d - %s - %s%n", getIndex(key), key.toString(), get(key).toString());
        }
        return str;
    }

    public void remove(K key, V value) {
        int index = getIndex(key);
        for (Pair p : table[index]) {
            if (p.key.equals(key)) {
                p.values.remove(value);
                if (p.values.isEmpty()) {
                    table[index].remove(p);
                    keySet.remove(key);
                }
                if (table[index].isEmpty()) {
                    table[index] = null;
                }
                return;
            }
        }
    }

    public void remove(K key) {
        int index = getIndex(key);
        for (Pair p : table[index]) {
            if (p.key.equals(key)) {
                table[index].remove(key);
                keySet.remove(key);
                if (table[index].isEmpty()) {
                    table[index] = null;
                }
                return;
            }
        }
    }
}