public class Dog extends Animal {

    public Dog(String name) {
        super(name);
    }

    @Override
    public String speak() {
        return "woof";
    }

    @Override
    public String getColor() {
        return "brown";
    }
    
}
