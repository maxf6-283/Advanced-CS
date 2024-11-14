public class Info {
    private String name;
    private String capital;
    private int population;

    public Info(int population, String capital, String name) {
        this.name = name;
        this.population = population;
        this.capital = capital;
    }

    public int population() {
        return population;
    }

    public String capital() {
        return capital;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name + ", Capital: " + capital + ", Population: " + population;
    }
}
