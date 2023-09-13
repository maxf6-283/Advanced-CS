public class Profile {
    private String name;

    public Profile(String myName) {
        name = myName;
    }

    public String saying() {
        return "My name is "+ name;
    }
}