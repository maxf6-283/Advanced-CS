import java.util.ArrayList;

public class Runner {
    public static void main(String[] args) {
        ArrayList<Pair<String, ToDo>> toDoList = new ArrayList<>();

        toDoList.add(new Pair<>("Jose", new ToDo("Homework", 1)));
        toDoList.add(new Pair<>("Jose", new ToDo("Work", 2)));
        
        toDoList.add(new Pair<>("Maria", new ToDo("Sleep", 1)));
        toDoList.add(new Pair<>("Maria", new ToDo("Study", 2)));

        for(Pair<String, ToDo> pair : toDoList) {
            System.out.println(pair);
        }
    }
}
