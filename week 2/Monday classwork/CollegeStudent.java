public class CollegeStudent extends Student {
    private String major;

    public CollegeStudent(String name, int age, String school, String major) {
        super(name, age, school);
        this.major = major;
    }

    @Override
    public String toString() {
        return super.toString() + "\tmajor: " + major;
    }

}