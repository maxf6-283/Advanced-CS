public class StateII implements Comparable<StateII> {
    private String name;
    private String abbr;

    public StateII(String name, String abbr) {
        this.name = name;
        this.abbr = abbr;
    }

    @Override
    public String toString() {
        return abbr + " - " + name;
    }

    @Override
    public int compareTo(StateII o) {
        return abbr.compareTo(o.abbr);
    }

    @Override
    public int hashCode() {
        return abbr.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof StateII s) {
            return s.abbr.equals(abbr);
        }
        return false;
    }
}
