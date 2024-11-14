import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Function;

import javax.swing.AbstractListModel;

/**
 * An implementation of the javax.swing.ListModel which is backed by a list.
 * 
 * It allows you to define a custom function to apply to elements of the backing list before being shown to the JList
 * It also has a list of filters to filter out unwanted elements of the backing list.
 * 
 * T1 is the type of the backing list, T2 is the type the JList will want.
 */
class CustomListModel<T1, T2> extends AbstractListModel<T2> {
    private List<T1> backingList;
    private List<T1> activeArray;
    public ArrayList<Predicate<? super T1>> filters;
    private Function<T1, T2> toT2;

    @SuppressWarnings("unchecked")
    public CustomListModel(List<T1> backingList) {
        this(backingList, e -> ((T2)e));
    }

    public CustomListModel(List<T1> backingList, Function<T1, T2> toT2) {
        this.backingList = backingList;
        this.toT2 = toT2;

        filters = new ArrayList<>();
        activeArray = backingList;
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

        activeArray = backingList;
        for (Predicate<? super T1> pred : filters) {
            activeArray = backingList.stream().filter(pred).toList();
        }

        if (activeArray.size() > oldSize) {
            fireIntervalAdded(this, 0, activeArray.size() - oldSize);
        } else if (activeArray.size() < oldSize) {
            fireIntervalRemoved(this, 0, oldSize - activeArray.size());
        }
        fireContentsChanged(this, 0, activeArray.size() - 1);
    }
}