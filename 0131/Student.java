public class Student implements Comparable<Student> {
    private String firstName, lastName, school;
    private int ID;

    public Student(String firstName, String lastName, int ID, String school) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.school = school;
    }

    public int id() {
        return ID;
    }

    public String school() {
        return school;
    }

    @Override
    public int compareTo(Student o) {
        int lastDiff = lastName.compareToIgnoreCase(o.lastName);
        if(lastDiff == 0) {
            return firstName.compareToIgnoreCase(o.firstName);
        }
        return lastDiff;
    }

    public String toString() {
        return lastName + " " + firstName;
    }
}