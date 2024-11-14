public class Admin extends MVHS{

    public Admin(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "admin";
    }
    
}
