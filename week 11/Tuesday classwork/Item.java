public class Item {
    private String item;
    private String store;

    public Item(String i, String s) {
        item = i;
        store = s;
    }

    @Override
    public String toString() {
        return item + " - " + store;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Item i) {
            return i.item.equals(item) && i.store.equals(store);
        }
        return false;
    }
}
