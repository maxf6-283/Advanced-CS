import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.swing.AbstractListModel;

/**
 * An implementation of the javax.swing.ListModel which is backed by a list.
 * 
 * It allows you to define a custom function to apply to elements of the backing
 * list before being shown to the JList
 * It also has a list of filters to filter out unwanted elements of the backing
 * list.
 * 
 * T1 is the type of the backing list, T2 is the type the JList will want.
 * 
 * NOTE: this class had been modified to accept a custom MyArrayList, which does
 * not implement List
 */
class CustomListModel<T1, T2> extends AbstractListModel<T2> {
    private MyArrayList<T1> backingList;
    private MyArrayList<T1> activeArray;
    public MyArrayList<Predicate<? super T1>> filters;
    private Function<T1, T2> toT2;
    public BiFunction<T1, T1, Integer> sorter;

    @SuppressWarnings("unchecked")
    public CustomListModel(MyArrayList<T1> backingList) {
        this(backingList, e -> ((T2) e));
    }

    public CustomListModel(MyArrayList<T1> backingList, Function<T1, T2> toT2) {
        this(backingList, toT2, (e1, e2) -> 0);
    }

    public CustomListModel(MyArrayList<T1> backingList, Function<T1, T2> toT2, BiFunction<T1, T1, Integer> sorter) {
        this.backingList = backingList;
        this.toT2 = toT2;

        filters = new MyArrayList<>();
        activeArray = backingList;
        this.sorter = sorter;

        update();
    }

    @Override
    public int getSize() {
        return activeArray.size();
    }

    @Override
    public T2 getElementAt(int index) {
        return toT2.apply(activeArray.get(index));
    }

    public void update() {
        int oldSize = activeArray.size();

        activeArray = new MyArrayList<T1>();
        for (int i = 0; i < backingList.size(); i++) {
            activeArray.add(backingList.get(i));
        }
        for (int f = 0; f < filters.size(); f++) {
            MyArrayList<T1> toFilter = activeArray;
            activeArray = new MyArrayList<>();
            for (int i = 0; i < toFilter.size(); i++) {
                if (filters.get(f).test(toFilter.get(i))) {
                    activeArray.add(toFilter.get(i));
                }
            }
        }

        sort(activeArray);

        if (activeArray.size() > oldSize) {
            fireIntervalAdded(this, 0, activeArray.size() - oldSize);
        } else if (activeArray.size() < oldSize) {
            fireIntervalRemoved(this, 0, oldSize - activeArray.size());
        }
        fireContentsChanged(this, 0, activeArray.size() - 1);
    }

    private void sort(MyArrayList<T1> arr) {
        for (int i = 0; i < arr.size() - 1; i++) {
            int maxElem = i;
            for (int j = i + 1; j < arr.size(); j++) {
                if (sorter.apply(arr.get(maxElem), arr.get(j)) > 0) {
                    maxElem = j;
                }
            }
            T1 temp = arr.get(i);
            arr.set(i, arr.get(maxElem));
            arr.set(maxElem, temp);
        }
    }
}