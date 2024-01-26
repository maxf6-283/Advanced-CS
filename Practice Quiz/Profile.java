public class Profile implements Comparable<Profile> {
    private String lastName;
    private String firstName;

    public Profile (String lName, String fName) {
        lastName = lName;
        firstName = fName;
    }

    @Override
    public int compareTo(Profile o) {
        if(lastName.compareTo(o.lastName) == 0) {
            return firstName.compareTo(o.firstName);
        }
        return lastName.compareTo(o.lastName);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}