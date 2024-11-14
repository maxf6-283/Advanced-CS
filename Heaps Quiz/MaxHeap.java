import java.util.ArrayList;

public class MaxHeap<E extends Comparable<E>> {
    private ArrayList<E> myList;

    public MaxHeap() {
        myList = new ArrayList<>();
    }

    public void add(E data) {
        myList.add(data);
        
        int index = myList.size() - 1;

        while(index > 0) {
            if(myList.get(index).compareTo(myList.get((index - 1)/2)) > 0) {
                E temp = myList.get(index);
                myList.set(index, myList.get((index - 1)/2));
                myList.set((index - 1) / 2, temp);
                index = (index - 1)/2;
            } else {
                return;
            }
        }
    }

    public E peek() {
        return myList.get(0);
    }

    public E poll() {
        if(myList.size() == 1) {
            return myList.removeFirst();
        }
        E toRet = myList.remove(0);
        myList.addFirst(myList.removeLast());

        int index = 0;
        while(index * 2 + 1 < myList.size()) {
            if(index * 2 + 2 == myList.size()) {
                index = index * 2 + 1;
            }
            else {
                if(myList.get(index * 2 + 1).compareTo(myList.get(index * 2 + 2)) > 0) {
                    index = index * 2 + 1;
                } else {
                    index = index * 2 + 2;
                }
            }

            if(myList.get(index).compareTo(myList.get((index - 1)/2)) > 0) {
                E temp = myList.get(index);
                myList.set(index, myList.get((index - 1)/2));
                myList.set((index - 1) / 2, temp);
            } else {
                return toRet;
            }
        }

        return toRet;
    }

    public boolean isEmpty() {
        return myList.isEmpty();
    }

    public String toString() {
        String str = "";

        for(E e : myList) {
            str += e.toString() + ", ";
        }

        return str.substring(0, str.length() - 2);
    }
}