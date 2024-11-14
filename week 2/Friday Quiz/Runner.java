import java.util.ArrayList;

public class Runner {
    public static void main(String[] args) {
        ArrayList<Monster> monsters = new ArrayList<>();

        monsters.add(new Mummy("King Ho-Tep III"));
        monsters.add(new Vampire("Dracula"));
        monsters.add(new Witch("Jane"));

        for(Monster monster : monsters) {
            //println automatically calls toString
            System.out.println(monster);
        }
    }
}
