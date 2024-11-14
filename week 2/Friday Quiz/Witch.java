public class Witch extends Monster {
    public Witch(String name) {
        super(name);
    }

    @Override
    public String speak() {
        return "Broom, broom, broom!";
    }
}
