public abstract class MVHS {
    private String name;

    public MVHS(String name) {
        this.name = name;
    }

    public abstract String getRole();

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + ": " + getRole();
    }
}