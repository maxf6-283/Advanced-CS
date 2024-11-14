public class Bird extends Animal {

    public Bird(String name) {
        super(name);
    }

    @Override
    public String speak() {
        return "tweet";
    }

    @Override
    public String getColor() {
        return "blue";
    }
    
}
