import java.util.List;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashSet;

public class MyHashTable<K, V> {
    private class Pair {
        public K key;
        public LinkedList<V> list;

        public Pair(K k) {
            key = k;
            list = new LinkedList<>();
        }

        public Pair(K k, V v) {
            key = k;
            list = new LinkedList<>();
            list.add(v);
        }
        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object o) {
            try {
                return ((Pair)o).key.equals(key);
            } catch (ClassCastException e) {
                return false;
            }
        }
    }

    private LinkedList<Pair>[] lists;
    private HashSet<K> keys;

    @SuppressWarnings("unchecked")
    public MyHashTable() {
        lists = new LinkedList[100];
        keys = new HashSet<>();
    }

    public Set<K> keySet() {
        return keys;
    }

    public void put(K key, V value) {
        keys.add(key);
        if (lists[key.hashCode() % lists.length] == null) {
            lists[key.hashCode() % lists.length] = new LinkedList<>();
            lists[key.hashCode() % lists.length].add(new Pair(key, value));
        } else {
            // note that while I do iterate through a list in order to find my key, I still
            // find the list that contains the list using hashcode, and under normal
            // circumstances, there will not be very collisions except when adding a large
            // amount of keys to the hash table, and there will be little performance loss
            for (Pair p : lists[key.hashCode() % lists.length]) {
                if (p.key.equals(key)) {
                    p.list.add(value);
                    return;
                }
            }
            lists[key.hashCode() % lists.length].add(new Pair(key, value));
        }
    }

    public List<V> get(K key) {
        if (!keys.contains(key)) {
            return null;
        }
        for (Pair p : lists[key.hashCode() % lists.length]) {
            if (p.key.equals(key)) {
                return p.list;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String str = "";
        int i = 1;
        for (K k : keys) {
            str += "#" + i + " " + k + ": " + get(k) + "\n";
            i++;
        }
        return str;
    }

    public void remove(K key, V value) {
        if (!keys.contains(key)) {
            return;
        }
        List<V> list = get(key);
        list.remove(value);
        if (list.isEmpty()) {
            remove(key);
        }
    }

    public void remove(K key) {
        if (!keys.contains(key)) {
            return;
        }
        keys.remove(key);
        lists[key.hashCode() % lists.length].remove(new Pair(key));
    }
}