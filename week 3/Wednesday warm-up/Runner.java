import java.util.ArrayList;

public class Runner {
    public static void main(String[] args) {
        ArrayList<MyItem<?>> items = new ArrayList<>();
        items.add(new MyItem<String>("A string of some kind"));
        items.add(new MyItem<Integer>(Integer.MAX_VALUE));
        items.add(new MyItem<Double>(Double.MAX_VALUE));

        for(MyItem<?> item : items) {
            System.out.println(item.get());
        }
    }
}
