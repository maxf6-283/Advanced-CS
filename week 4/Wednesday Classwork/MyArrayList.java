public class MyArrayList<E> {
    private Object[] elements;
    private int size;

    public MyArrayList() {
        elements = new Object[8];
        size = 0;
    }

    private void doubleLength() {
        Object[] newElements = new Object[elements.length << 1];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    public boolean add(E var) {
        size++;
        if (size > elements.length) {
            doubleLength();
        }
        elements[size - 1] = var;
        
        return true;
    }

    public void add(int index, E var) {
        size++;
        if (size > elements.length) {
            doubleLength();
        }
        
        Object temp = var;
        for(int i = index; i < size; i++) {
            Object subtemp = elements[i];
            elements[i] = temp;
            temp = subtemp;
        }
    }

    @SuppressWarnings("unchecked")
    public E get(int index){
        return (E) elements[index];
    }

    @SuppressWarnings("unchecked")
    public E remove(int index){
        size--;
        
        Object toReturn = elements[index];
        for(int i = index; i < size; i++) {
            elements[i] = elements[i+1];
        }
        
        return (E) toReturn;
    }
    
    public boolean remove(Object var){
        for(int i = 0; i < size; i++) {
            if(elements[i].equals(var)) {
                remove(i);
                return true;
            }
        }

        return false;
    }

    public void set(int index, E var){
        elements[index] = var;
    }
    
    public String toString(){
        String repr = "[";
        for(int i = 0; i < size; i++) {
            repr += elements[i] + ", ";
        }
        repr = repr.substring(0, repr.length() - 2) + "]";
        return repr;
    }
    
    public int size(){
        return size;
    }
}