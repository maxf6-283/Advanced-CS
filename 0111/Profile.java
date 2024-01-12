public class Profile implements Comparable<Profile> {
    private String name;
    private int id;

    public Profile(String _name, int _id) {
        name = _name;
        id = _id;
    }

    @Override
    public String toString() {
        return name + ": " + id;
    }

    @Override
    public int compareTo(Profile o) {
        if (name.equals(o.name))
            return (id - o.id);

        return name.compareTo(o.name);
    }

    public int getId() {
        return id;
    }
}