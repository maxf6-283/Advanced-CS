public class Animal implements Comparable<Animal> {
    private String name;
    private int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Animal o) {
        int nameDiff = name.compareToIgnoreCase(o.name);
        if (nameDiff == 0) {
            return age - o.age;
        }
        return nameDiff;
    }

    @Override
    public String toString() {
        return name + " - " + age;
    }

}
