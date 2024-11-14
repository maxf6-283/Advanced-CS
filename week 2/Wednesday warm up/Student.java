public class Student extends Profile{
    public Student(String myName) {
        super(myName);
    }

    public String saying() {
        return super.saying() + " I am a student";
    }
}
