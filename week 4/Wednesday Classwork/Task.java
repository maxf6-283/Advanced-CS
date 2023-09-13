public class Task{
    private String name;
    private int rank;

    public Task(String name, int rank) {
        this.name = name;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return String.format("%d: %s", rank, name);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Task t) {
            return name.equals(t.name) && rank == t.rank;
        }
        return false;
    }

    public int rank() {
        return rank;
    }

    public String name() {
        return name;
    }
}