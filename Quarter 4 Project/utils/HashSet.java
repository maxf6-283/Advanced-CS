package utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class HashSet<E> implements Set<E> {
    private HashMap<E, Integer> backingMap = new HashMap<>();

    @Override
    public synchronized int size() {
        return backingMap.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return backingMap.isEmpty();
    }

    @Override
    public synchronized boolean contains(Object o) {
        return backingMap.containsKey(o);
    }

    @Override
    public synchronized Iterator<E> iterator() {
        return backingMap.keySet().iterator();
    }

    @Override
    public synchronized Object[] toArray() {
        return backingMap.keySet().toArray();
    }

    @Override
    public synchronized <T> T[] toArray(T[] a) {
        return backingMap.keySet().toArray(a);
    }

    @Override
    public synchronized boolean add(E e) {
        return backingMap.put(e, 1) == null;
    }

    @Override
    public synchronized boolean remove(Object o) {
        return backingMap.remove(o) != null;
    }

    @Override
    public synchronized boolean containsAll(Collection<?> c) {
        return backingMap.keySet().containsAll(c);
    }

    @Override
    public synchronized boolean addAll(Collection<? extends E> c) {
        boolean changed = false;
        for(E e : c) {
            if(backingMap.put(e, 1) == null) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public synchronized boolean retainAll(Collection<?> c) {
        return backingMap.keySet().retainAll(c);
    }

    @Override
    public synchronized boolean removeAll(Collection<?> c) {
        return backingMap.keySet().removeAll(c);
    }

    @Override
    public synchronized void clear() {
        backingMap.clear();
    }
    
    @Override
    public synchronized String toString() {
        if(backingMap.isEmpty()) {
            return "{}";
        }
        String r = "{";
        for(E e : backingMap.keySet()) {
            r += e + ", ";
        }
        return r.substring(0, r.length() - 2) + "}";
    }
}
