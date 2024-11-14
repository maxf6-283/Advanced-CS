public class Student extends People {
    private String school;

    public Student(String name, int age, String school) {
        super(name, age);
        this.school = school;
    }

    public String toString() {
        return super.toString() + "\tschool: " + school;
    }

}