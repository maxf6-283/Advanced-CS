public class Student {
    private String name;
    private int id;

    public Student(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String name() {
        return name;
    }

    public int id() {
        return id;
    }

    @Override
    public String toString() {
        return name + " - " + id;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Student s) {
            if(s.id == id) {
                return true;
            }
        }
        return false;
    }
}
