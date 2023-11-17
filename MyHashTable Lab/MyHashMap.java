import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.lang.reflect.Array;

public class MyHashMap<K, V> implements Map<K, V> {
    private Node[] buckets;
    private int size;
    private float loadFactor;

    public MyHashMap() {
        this(16, 0.75f);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public MyHashMap(float loadFactor) {
        this(16, loadFactor);
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity, float loadFactor) {
        buckets = (Node[]) Array.newInstance(Node.class, initialCapacity);
        size = 0;
        this.loadFactor = loadFactor;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int index = key.hashCode() % buckets.length;
        index = index < 0 ? index + buckets.length : index;
        Node node = buckets[index];
        while (node != null) {
            if (node.key.equals(key)) {
                return true;
            }
            node = node.next;
        }

        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < buckets.length; i++) {
            Node node = buckets[i];
            while (node != null) {
                if (node.value.equals(value)) {
                    return true;
                }
                node = node.next;
            }
        }

        return false;
    }

    @Override
    public V get(Object key) {
        if (key == null) {
            Node node = buckets[0];
            while (node != null) {
                if (node.key == null) {
                    return node.value;
                }
                node = node.next;
            }

            return null;
        }
        int index = key.hashCode() % buckets.length;
        index = index < 0 ? index + buckets.length : index;
        Node node = buckets[index];
        while (node != null) {
            if (node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }

        return null;
    }

    @Override
    public V put(K key, V value) {
        if (key == null) {
            if (buckets[0] == null) {
                buckets[0] = new Node(0, key, value);
                return null;
            }
            Node node = buckets[0];
            while (true) {
                if (node.key == null) {
                    V toReturn = node.value;
                    node.value = value;
                    return toReturn;
                }
                if (node.next == null) {
                    node.next = new Node(0, key, value);
                    return null;
                }
                node = node.next;
            }
        }
        size++;
        if (size > buckets.length * loadFactor) {
            doubleBuckets();
        }
        int hash = key.hashCode();
        int index = hash % buckets.length;
        index = index < 0 ? index + buckets.length : index;

        if (buckets[index] == null) {
            buckets[index] = new Node(hash, key, value);
            return null;
        }

        Node node = buckets[index];
        while (true) {
            if (key.equals(node.key)) {
                V toReturn = node.value;
                node.value = value;
                return toReturn;
            }
            if (node.next == null) {
                node.next = new Node(hash, key, value);
                return null;
            }
            node = node.next;
        }
    }

    @SuppressWarnings("unchecked")
    private void doubleBuckets() {
        Node[] newBuckets = (Node[]) Array.newInstance(Node.class, buckets.length * 2);

        for (int i = 0; i < buckets.length; i++) {
            Node node = buckets[i];
            while (node != null) {
                int index = node.hash % newBuckets.length;
                index = index < 0 ? index + newBuckets.length : index;
                
                if (newBuckets[index] == null) {
                    newBuckets[index] = node;
                } else {
                    Node nodeToAddTo = newBuckets[index];
                    while (nodeToAddTo.next != null) {
                        nodeToAddTo = nodeToAddTo.next;
                    }
                    nodeToAddTo.next = node;
                }
                Node prevNode = node;
                node = node.next;
                prevNode.next = null;
            }
        }

        buckets = newBuckets;
    }

    @Override
    public V remove(Object key) {
        int index = key.hashCode() % buckets.length;
        index = index < 0 ? index + buckets.length : index;
        if (buckets[index] == null) {
            return null;
        }

        Node node = buckets[index];
        if (node.key.equals(key)) {
            V value = node.value;
            buckets[index] = node.next;
            size--;
            return value;
        }
        if (node.next == null) {
            return null;
        }
        while (!node.next.key.equals(key)) {
            node = node.next;
            if (node.next == null) {
                return null;
            }
        }

        V value = node.next.value;
        node.next = node.next.next;
        size--;
        return value;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> pair : m.entrySet()) {
            put(pair.getKey(), pair.getValue());
        }
    }

    @Override
    public void clear() {
        size = 0;
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
    }

    @Override
    public Set<K> keySet() {
        return new KeySet();
    }

    @Override
    public Collection<V> values() {
        return new ValueCollection();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Map)) {
            return false;
        }
        return(entrySet().equals(((Map<?, ?>)other).entrySet()));
    }

    private class Node {
        public Node(int h, K k, V val) {
            hash = h;
            key = k;
            value = val;
        }

        private int hash;
        private K key;
        private V value;
        private Node next;
    }

    private class KeySet implements Set<K> {
        private class KeySetIterator implements Iterator<K> {
            private int atIndex = 0;
            private Node atNode = null;

            public KeySetIterator() {
                while (atIndex < buckets.length && buckets[atIndex] == null) {
                    atIndex++;
                }
                if (atIndex == buckets.length) {
                    atIndex = -1;
                    atNode = null;
                } else {
                    atNode = buckets[atIndex];
                }
            }

            @Override
            public boolean hasNext() {
                return atNode != null;
            }

            @Override
            public K next() {
                K key = atNode.key;

                if (atNode.next == null) {
                    atIndex++;
                    while (atIndex < buckets.length && buckets[atIndex] == null) {
                        atIndex++;
                    }
                    if (atIndex == buckets.length) {
                        atIndex = -1;
                        atNode = null;
                    } else {
                        atNode = buckets[atIndex];
                    }
                } else {
                    atNode = atNode.next;
                }

                return key;
            }
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public boolean contains(Object o) {
            return MyHashMap.this.containsKey(o);
        }

        @Override
        public Iterator<K> iterator() {
            return new KeySetIterator();
        }

        @Override
        public Object[] toArray() {
            Object[] keys = new Object[size];
            int index = 0;
            for (int i = 0; i < buckets.length; i++) {
                Node node = buckets[i];
                while (node != null) {
                    keys[index] = node.key;
                    index++;
                    node = node.next;
                }
            }

            return keys;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a) {
            if (a.length < size) {
                a = (T[]) Array.newInstance(a.getClass().componentType(), size);
            } else if (a.length > size) {
                a[size] = null;
            }

            int index = 0;
            for (int i = 0; i < buckets.length; i++) {
                Node node = buckets[i];
                while (node != null) {
                    a[index] = (T) node.key;
                    index++;
                    node = node.next;
                }
            }

            return a;
        }

        @Override
        public boolean add(K e) {
            throw new UnsupportedOperationException("Method 'add' not allowed for key Set");
        }

        @Override
        public boolean remove(Object o) {
            int prevSize = size;
            MyHashMap.this.remove(o);
            return size != prevSize;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            for (Object o : c) {
                if (!contains(o)) {
                    return false;
                }
            }

            return true;
        }

        @Override
        public boolean addAll(Collection<? extends K> c) {
            throw new UnsupportedOperationException("Method 'addAll' not allowed for key Set");
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            boolean removed = false;
            for (int i = 0; i < buckets.length; i++) {
                Node node = buckets[i];
                if (node != null) {
                    if (!c.contains(node.key)) {
                        size--;
                        removed = true;
                        buckets[i] = node.next;
                    }

                    while (node.next != null) {
                        if (!c.contains(node.next.key)) {
                            size--;
                            removed = true;
                            node.next = node.next.next;
                        }
                        node = node.next;
                    }
                }
            }

            return removed;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            boolean removed = false;
            for (Object o : c) {
                if (remove(o)) {
                    removed = true;
                }
            }

            return removed;
        }

        @Override
        public void clear() {
            MyHashMap.this.clear();
        }

        @Override
        public boolean equals(Object other) {
            if(!(other instanceof Set)) {
                return false;
            }
            Set<?> set = (Set<?>) other;
            if(set.size() != size()) {
                return false;
            }
            return containsAll(set);
        }
    }

    private class ValueCollection implements Collection<V> {
        private class ValueIterator implements Iterator<V> {
            private int atIndex = 0;
            private Node atNode = null;

            public ValueIterator() {
                while (atIndex < buckets.length && buckets[atIndex] == null) {
                    atIndex++;
                }
                if (atIndex == buckets.length) {
                    atIndex = -1;
                    atNode = null;
                } else {
                    atNode = buckets[atIndex];
                }
            }

            @Override
            public boolean hasNext() {
                return atNode != null;
            }

            @Override
            public V next() {
                V value = atNode.value;

                if (atNode.next == null) {
                    atIndex++;
                    while (atIndex < buckets.length && buckets[atIndex] == null) {
                        atIndex++;
                    }
                    if (atIndex == buckets.length) {
                        atIndex = -1;
                        atNode = null;
                    } else {
                        atNode = buckets[atIndex];
                    }
                } else {
                    atNode = atNode.next;
                }

                return value;
            }

        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public boolean contains(Object o) {
            return MyHashMap.this.containsValue(o);
        }

        @Override
        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        @Override
        public Object[] toArray() {
            Object[] values = new Object[size];
            int index = 0;
            for (int i = 0; i < buckets.length; i++) {
                Node node = buckets[i];
                while (node != null) {
                    values[index] = node.value;
                    index++;
                    node = node.next;
                }
            }

            return values;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a) {
            if (a.length < size) {
                a = (T[]) Array.newInstance(a.getClass().componentType(), size);
            } else if (a.length > size) {
                a[size] = null;
            }

            int index = 0;
            for (int i = 0; i < buckets.length; i++) {
                Node node = buckets[i];
                while (node != null) {
                    a[index] = (T) node.value;
                    index++;
                    node = node.next;
                }
            }

            return a;
        }

        @Override
        public boolean add(V e) {
            throw new UnsupportedOperationException("Method 'add' not allowed for value Collection");
        }

        @Override
        public boolean remove(Object value) {
            int index = value.hashCode() % buckets.length;
            index = index < 0 ? index + buckets.length : index;
            if (buckets[index] == null) {
                return false;
            }

            Node node = buckets[index];
            if (node.value.equals(value)) {
                buckets[index] = node.next;
                size--;
                return true;
            }
            if (node.next == null) {
                return false;
            }
            while (!node.next.value.equals(value)) {
                node = node.next;
                if (node.next == null) {
                    return false;
                }
            }

            node.next = node.next.next;
            size--;
            return true;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            for (Object o : c) {
                if (!contains(o)) {
                    return false;
                }
            }

            return true;
        }

        @Override
        public boolean addAll(Collection<? extends V> c) {
            throw new UnsupportedOperationException("Method 'addAll' not allowed for value Collection");
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            boolean removed = false;
            for (int i = 0; i < buckets.length; i++) {
                Node node = buckets[i];
                if (node != null) {
                    if (!c.contains(node.value)) {
                        size--;
                        removed = true;
                        buckets[i] = node.next;
                    }

                    while (node.next != null) {
                        if (!c.contains(node.next.value)) {
                            size--;
                            removed = true;
                            node.next = node.next.next;
                        }
                        node = node.next;
                    }
                }
            }

            return removed;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            boolean removed = false;
            for (Object o : c) {
                if (remove(o)) {
                    removed = true;
                }
            }

            return removed;
        }

        @Override
        public void clear() {
            MyHashMap.this.clear();
        }

        @Override
        public boolean equals(Object other) {
            if(!(other instanceof Set)) {
                return false;
            }
            Set<?> set = (Set<?>) other;
            if(set.size() != size()) {
                return false;
            }
            return containsAll(set);
        }
    }

    private class EntrySet implements Set<Entry<K, V>> {
        private class MyEntry implements Entry<K, V> {
            private Node node;

            public MyEntry(Node n) {
                node = n;
            }

            @Override
            public K getKey() {
                return node.key;
            }

            @Override
            public V getValue() {
                return node.value;
            }

            @Override
            public V setValue(V value) {
                V v = node.value;
                node.value = value;
                return v;
            }
            
            @Override
            public boolean equals(Object other) {
                if(!(other instanceof Entry)) {
                    return false;
                }
                Entry<?, ?> o = (Entry<?, ?>) other;
                return o.getKey().equals(node.key) && o.getValue().equals(node.value);
            }

            @Override
            public String toString() {
                return node.key + " - " + node.value;
            }
        }
        private class EntrySetIterator implements Iterator<Entry<K, V>> {
            private int atIndex = 0;
            private Node atNode = null;

            public EntrySetIterator() {
                while (atIndex < buckets.length && buckets[atIndex] == null) {
                    atIndex++;
                }
                if (atIndex == buckets.length) {
                    atIndex = -1;
                    atNode = null;
                } else {
                    atNode = buckets[atIndex];
                }
            }

            @Override
            public boolean hasNext() {
                return atNode != null;
            }

            @Override
            public Entry<K, V> next() {
                Entry<K, V> entry = new MyEntry(atNode);

                if (atNode.next == null) {
                    atIndex++;
                    while (atIndex < buckets.length && buckets[atIndex] == null) {
                        atIndex++;
                    }
                    if (atIndex == buckets.length) {
                        atIndex = -1;
                        atNode = null;
                    } else {
                        atNode = buckets[atIndex];
                    }
                } else {
                    atNode = atNode.next;
                }

                return entry;
            }

        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public boolean contains(Object o) {
            if(!(o instanceof Entry)) {
                return false;
            }
            Entry<?, ?> entry = (Entry<?, ?>) o;
            return MyHashMap.this.containsKey(entry.getKey()) && MyHashMap.this.get(entry.getKey()).equals(entry.getValue());
        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new EntrySetIterator();
        }

        @Override
        public Object[] toArray() {
            Object[] entries = new Object[size];
            int index = 0;
            for (int i = 0; i < buckets.length; i++) {
                Node node = buckets[i];
                while (node != null) {
                    entries[index] = new MyEntry(node);
                    index++;
                    node = node.next;
                }
            }

            return entries;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a) {
            if (a.length < size) {
                a = (T[]) Array.newInstance(a.getClass().componentType(), size);
            } else if (a.length > size) {
                a[size] = null;
            }

            int index = 0;
            for (int i = 0; i < buckets.length; i++) {
                Node node = buckets[i];
                while (node != null) {
                    a[index] = (T) new MyEntry(node);
                    index++;
                    node = node.next;
                }
            }

            return a;
        }

        @Override
        public boolean add(Entry<K, V> e) {
            throw new UnsupportedOperationException("Method 'add' not allowed for entry Set");
        }

        @Override
        public boolean remove(Object o) {
            if(contains(o)) {
                MyHashMap.this.remove(((Entry<?, ?>)o).getKey());
                return true;
            }
            return false;
            
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            for (Object o : c) {
                if (!contains(o)) {
                    return false;
                }
            }

            return true;
        }

        @Override
        public boolean addAll(Collection<? extends Entry<K, V>> c) {
            throw new UnsupportedOperationException("Method 'addAll' not allowed for key Set");
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            boolean removed = false;
            for (int i = 0; i < buckets.length; i++) {
                Node node = buckets[i];
                if (node != null) {
                    if (!c.contains(new MyEntry(node))) {
                        size--;
                        removed = true;
                        buckets[i] = node.next;
                    }

                    while (node.next != null) {
                        if (!c.contains(new MyEntry(node.next))) {
                            size--;
                            removed = true;
                            node.next = node.next.next;
                        }
                        node = node.next;
                    }
                }
            }

            return removed;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            boolean removed = false;
            for (Object o : c) {
                if (remove(o)) {
                    removed = true;
                }
            }

            return removed;
        }

        @Override
        public void clear() {
            MyHashMap.this.clear();
        }

        @Override
        public boolean equals(Object other) {
            if(!(other instanceof Set)) {
                return false;
            }
            Set<?> set = (Set<?>) other;
            if(set.size() != size()) {
                return false;
            }
            return containsAll(set);
        }
    }
}