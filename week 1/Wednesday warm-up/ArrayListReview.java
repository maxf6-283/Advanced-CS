import java.util.ArrayList;

public class ArrayListReview {
    public static void main(String[] args) {
        ArrayList<Integer> arrList = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            arrList.add((int)(Math.random() * 999 + 1));
        }

        System.out.println(arrList);
    }
}