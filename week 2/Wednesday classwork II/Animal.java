public abstract class Animal {
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public abstract String speak();
    public abstract String getColor();
    public String getName() {
        return "I am " + name;
    }
    public void printInfo() {
        System.out.println(getName() + ", " + speak() + ", " + getColor());
    }
}
