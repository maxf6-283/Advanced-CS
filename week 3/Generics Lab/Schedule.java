import java.util.ArrayList;
import java.util.Comparator;

public class Schedule {
    private ArrayList<Pair<Integer, String>> mySchedule;

    public Schedule() {
        mySchedule = new ArrayList<>();
    }

    public void addClass(int period, String clazz) {
        mySchedule.add(new Pair<Integer,String>(period, clazz));
        mySchedule.sort(new Comparator<Pair<Integer, String>>() {
            @Override
            public int compare(Pair<Integer, String> o1, Pair<Integer, String> o2) {
                return o1.getKey()-o2.getKey();
            }
        });
    }

    @Override
    public String toString() {
        String str = "";
        for(Pair<Integer, String> pair : mySchedule) {
            str += pair + "\n";
        }

        return str.substring(0, str.length()-1);
    }

    public ArrayList<Pair<Integer, String>> mySchedule() {
        return mySchedule;
    }
}
