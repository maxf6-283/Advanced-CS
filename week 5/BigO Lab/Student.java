public class Student {
    private String firstName;
    private String lastName;
    private int age;

    public Student(String name) {
        firstName = name.split(" ")[0];
        lastName = name.split(" ")[1];
        age = (int) (Math.random() * 5 + 14);
    }

    public String lastName() {
        return lastName;
    }

    public String firstName() {
        return firstName;
    }

    public int age() {
        return age;
    }

    @Override
    public String toString() {
        return lastName + ", " + firstName + " - " + age;
    }
}
