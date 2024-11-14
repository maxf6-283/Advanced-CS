import java.util.Comparator;
import java.util.List;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.Function;

import javax.swing.AbstractListModel;

/**
 * An implementation of the javax.swing.ListModel which is backed by a collection.
 * 
 * It allows you to define a custom function to apply to elements of the backing list before being shown to the JList
 * It also has a list of filters to filter out unwanted elements of the backing list.
 * 
 * T1 is the type of the backing collection, T2 is the type the JList will want.
 */
public class CustomListModel<T1, T2> extends AbstractListModel<T2> {
    private Collection<T1> backingList;
    private List<T1> activeArray;
    public MyArrayList<Predicate<? super T1>> filters;
    private Function<T1, T2> toT2;
    private Comparator<T1> comparer;

    @SuppressWarnings("unchecked")
    public CustomListModel(List<T1> backingList) {
        this(backingList, e -> ((T2)e));
    }

    public CustomListModel(Collection<T1> backingList, Function<T1, T2> toT2) {
        this(backingList, toT2, (e1, e2) -> 0);
    }

    public CustomListModel(Collection<T1> backingList, Function<T1, T2> toT2, Comparator<T1> sorter) {
        this.backingList = backingList;
        this.toT2 = toT2;

        filters = new MyArrayList<>();
        activeArray = new MyArrayList<>(backingList);;

        comparer = sorter;
    }

    @Override
    public int getSize() {
        return activeArray.size();
    }

    @Override
    public T2 getElementAt(int index) {
        return toT2.apply(activeArray.get(index));
    }

    public T1 getBackingElementAt(int index) {
        if(index < 0 || index >= activeArray.size()) {
            return null;
        }
        return activeArray.get(index);
    }

    public void update() {
        int oldSize = activeArray.size();

        activeArray = new MyArrayList<>(backingList);
        
        for (Predicate<? super T1> pred : filters) {
            activeArray = new MyArrayList<>(activeArray.stream().filter(pred).toList());
        }

        activeArray.sort(comparer);

        if (activeArray.size() > oldSize) {
            fireIntervalAdded(this, 0, activeArray.size() - oldSize);
        } else if (activeArray.size() < oldSize) {
            fireIntervalRemoved(this, 0, oldSize - activeArray.size());
        }
        fireContentsChanged(this, 0, activeArray.size() - 1);
    }
}