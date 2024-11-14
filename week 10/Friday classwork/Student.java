public class Student {
    private String name;
    private int id;

    public Student(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(this.getClass())) {
            return id == ((Student)o).id;
        }
        return false;
    }

    public String getName(){
        return name;
    }

    public String toString(){
        return name + " - " + id;
    }

    
}