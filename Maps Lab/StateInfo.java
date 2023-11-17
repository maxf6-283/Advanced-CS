public class StateInfo {
    private String capital;
    private int population;
    private int sizeInSqMiles;
    private DLList<String> imageUrls;

    public StateInfo(String capital, int population, int sizeInSqMiles) {
        this.capital = capital;
        this.population = population;
        this.sizeInSqMiles = sizeInSqMiles;
        imageUrls = new DLList<>();
    }

    public void addUrl(String url) {
        imageUrls.add(url);
    }

    public DLList<String> URLList() {
        return imageUrls;
    }

    @Override
    public String toString() {
        return "Capital: " + capital + "\nPopulation: " + population + "\nSize in square miles: " + sizeInSqMiles;
    }
}
