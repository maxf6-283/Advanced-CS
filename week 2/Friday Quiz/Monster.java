public abstract class Monster {
    private String name;

    public Monster(String name) {
        this.name = name;
    }

    public abstract String speak();
    public String getName() {
        return "I am " + name;
    }

    public String toString() {
        return getName() + ", " + speak();
    }
}