public class School {
    private String name;
    private int schoolID;

    public School(String n, int id) {
        name = n;
        schoolID = id;
    }

    public int hashCode() {
        return schoolID;
    }

    @Override
    public String toString() {
        return name + ": " + schoolID;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof School s) {
            return s.schoolID == schoolID;
        }
        return false;
    }

    public int getSchoolID() {
        return schoolID;
    };
}
