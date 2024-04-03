import java.util.Collection;
import java.util.Iterator;

public class MinHeap<E extends Comparable<E>> implements Collection<E> {
    private ArrayList<E> elems;

    public MinHeap() {
        elems = new ArrayList<>();
    }

    public boolean add(E data) {
        elems.add(data);
        int index = elems.size() - 1;
        
        while(index != 0) {
            if(data.compareTo(elems.get((index - 1) / 2)) < 0) {
                elems.set(index, elems.get((index - 1) / 2));
                elems.set((index - 1) / 2, data);
                index = (index - 1) / 2;
            } else {
                break;
            }
        }

        return true;
    }
    
    
    public String toString() {
        if(elems.size() == 0) {
            return "[empty]";
        }

        String s = "";


        for (int i = 0; i < elems.size(); i++) {
            
            s += elems.get(i) + ", ";

            if(i+2 == Integer.highestOneBit(i+2)) {
                s = s.substring(0, s.length() - 2);
                s += " \n";
            }
        }

        return s.substring(0, s.length() - 2);
    }

    public E remove(E data) {
        int index = elems.indexOf(data);
        if(index == -1) {
            return null;
        }
        return remove(index);
    }

    private E remove(int index) {
        if(index == elems.size() - 1) {
            return elems.remove(index);
        }
        if(elems.size() == 0) {
            throw new IndexOutOfBoundsException("Cannot remove from an empty heap");
        }
        E removed = elems.get(index);

        
        elems.set(index, elems.removeLast());
        
        while(elems.size() > index * 2 + 1) {
            if(elems.size() == index * 2 + 2) {
                index = index * 2 + 1;
            } else {
                if(elems.get(index * 2 + 1).compareTo(elems.get(index * 2 + 2)) < 0) {
                    index = index * 2 + 1;
                } else {
                    index = index * 2 + 2;
                }   
            }
            if(elems.get((index - 1)/2).compareTo(elems.get(index)) < 1) {
                return removed;
            }
            E temp = elems.get((index - 1) /2 );
            elems.set((index - 1)/2, elems.get(index));
            elems.set(index, temp);
        }

        return removed;
    }

    public E peek() {
        return elems.get(0);
    }

    public E poll() {
        return remove(0);
    }

    public boolean isEmpty() {
        return elems.isEmpty();
    }

    public int size() {
        return elems.size();
    }

    @Override
    public boolean contains(Object o) {
        return elems.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return elems.iterator();
    }

    @Override
    public Object[] toArray() {
        return elems.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return elems.toArray(a);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object o) {
        try {
            return remove((E)o) != null;
        }
        catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return elems.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for(E e : c) {
            add(e);
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = false;
        for(Object o : c) {
            if(remove(o)) {
                removed = true;
            }
        }

        return removed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean removed = false;
        for(Object o : elems.toArray()) {
            if(!c.contains(o)) {
                removed = remove(o) ? true : removed;
            }
        }

        return removed;
    }

    @Override
    public void clear() {
        elems.clear();
    }
}