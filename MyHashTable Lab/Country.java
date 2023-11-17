public class Country {
    private String abbr;
    private String name;

    public Country(String a, String n) {
        abbr = a;
        name = n;
    }
    
    @Override
    public int hashCode() {
        return abbr.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Country c) {
            return c.abbr.equals(abbr);
        }
        return false;
    }

    @Override
    public String toString() {
        return abbr + " - " + name;
    }
}
