import java.util.ArrayList;

public class Runner {
    public static void main(String[] args) {
        ArrayList<MyItem<String, Double>> items = new ArrayList<>();
        items.add(new MyItem<>("Napolean", Math.PI));
        items.add(new MyItem<>("Mammoth", Math.sqrt(2)));
        items.add(new MyItem<>("Doffle", Math.E));

        for(MyItem<String, Double> item : items) {
            System.out.println(item);
        }
    }
}
