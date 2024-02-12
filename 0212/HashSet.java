import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class HashSet<E> implements Set<E> {
    private HashMap<E, Integer> backingMap = new HashMap<>();

    @Override
    public int size() {
        return backingMap.size();
    }

    @Override
    public boolean isEmpty() {
        return backingMap.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return backingMap.containsKey(o);
    }

    @Override
    public Iterator<E> iterator() {
        return backingMap.keySet().iterator();
    }

    @Override
    public Object[] toArray() {
        return backingMap.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return backingMap.keySet().toArray(a);
    }

    @Override
    public boolean add(E e) {
        return backingMap.put(e, 1) == null;
    }

    @Override
    public boolean remove(Object o) {
        return backingMap.remove(o) != null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return backingMap.keySet().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;
        for(E e : c) {
            if(backingMap.put(e, 1) == null) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return backingMap.keySet().retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return backingMap.keySet().removeAll(c);
    }

    @Override
    public void clear() {
        backingMap.clear();
    }
    
    @Override
    public String toString() {
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
