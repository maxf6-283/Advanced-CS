public class State {
    private String name;
    private String abbreviation;

    public State (String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    @Override
    public int hashCode() {
        return abbreviation.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof State s) {
            return s.abbreviation.equals(abbreviation);
        }
        return false;
    }

    @Override
    public String toString() {
        return abbreviation + " - " + name;
    }
}
