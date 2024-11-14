public class Teacher extends MVHS{

    public Teacher(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "teacher";
    }
    
}
