public class Student extends MVHS{

    public Student(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "student";
    }
    
}
