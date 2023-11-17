import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MyHashMap<K, V> implements Map<K, V> {
    private MyHashSet<Entry<K, V>> pairSet;

    public MyHashMap() {
        pairSet = new MyHashSet<>();
    }

    private class Pair implements Entry<K, V> {
        public K key;
        public V value;

        public Pair(K k, V v) {
            key = k;
            value = v;
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            try {
                return key.equals(((Entry<K, V>) o).getKey());
            } catch (ClassCastException e) {
                return false;
            }
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V toRet = this.value;
            this.value = value;
            return toRet;
        }
    }

    @Override
    public int size() {
        return pairSet.size();
    }

    @Override
    public boolean isEmpty() {
        return pairSet.size() == 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean containsKey(Object key) {
        try {
            return pairSet.contains(new Pair((K) key, null));
        } catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public boolean containsValue(Object value) {
        for (Entry<K, V> p : pairSet) {
            if (p.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(Object key) {
        try {
            return pairSet.get(new Pair((K) key, null)).getValue();
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Override
    public V put(K key, V value) {
        Pair pair = new Pair(key, value);
        Entry<K, V> oldPair = pairSet.replace(pair);
        if (oldPair != null) {
            return oldPair.getValue();
        }

        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        try {
            V toRet = pairSet.replace(new Pair((K) key, null)).getValue();
            pairSet.remove(new Pair((K) key, null));
            return toRet;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        pairSet.clear();
    }

    @Override
    public Set<K> keySet() {
        return new MyKeySet();
    }

    private class MyKeySet implements Set<K> {

        @Override
        public int size() {
            return pairSet.size();
        }

        @Override
        public boolean isEmpty() {
            return pairSet.isEmpty();
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean contains(Object o) {
            try {
                return pairSet.contains(new Pair((K) o, null));
            } catch (ClassCastException e) {
                return false;
            }
        }

        @Override
        public Iterator<K> iterator() {
            return new MyKeyIterator();
        }

        private class MyKeyIterator implements Iterator<K> {
            Iterator<Entry<K, V>> parentIter = pairSet.iterator();

            @Override
            public boolean hasNext() {
                return parentIter.hasNext();
            }

            @Override
            public K next() {
                return parentIter.next().getKey();
            }

        }

        @Override
        public Object[] toArray() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'toArray'");
        }

        @Override
        public <T> T[] toArray(T[] a) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'toArray'");
        }

        @Override
        public boolean add(K e) {
            throw new UnsupportedOperationException("Unimplemented method 'add'");
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean remove(Object o) {
            try {
                return pairSet.remove(new Pair((K) o, null));
            } catch (ClassCastException e) {
                return false;
            }
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
        }

        @Override
        public boolean addAll(Collection<? extends K> c) {
            throw new UnsupportedOperationException("Unimplemented method 'addAll'");
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
        }

        @Override
        public void clear() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'clear'");
        }

    }

    @Override
    public Collection<V> values() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'values'");
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return pairSet;
    }

    @Override
    public String toString() {
        String toRet = "";
        for(Entry<K,V> p : pairSet) {
            toRet += (p.getKey() + ": " + p.getValue()) + "\n";
        }
        return toRet.substring(0, toRet.length() - 1);
    }
}
