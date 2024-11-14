public class Friend {
    private char id;
    private String name;

    public Friend(char id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return id + ": " + name;
    }

    @Override
    public int hashCode() {
        return (int) id;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof Friend f) {
            return f.name.equalsIgnoreCase(name);
        }
        return false; 
    }
}
