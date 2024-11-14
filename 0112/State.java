public class State implements Comparable<State> {
    private String name;

    public State(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(State o) {
        return name.compareTo(o.name);
    }
}