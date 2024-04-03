public class Student {
    private String name;
    private int id;

    public Student(String _name, int _id) {
        name = _name;
        id = _id;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Student s) {
            return s.id == id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        return name + ":" + id;
    }
}