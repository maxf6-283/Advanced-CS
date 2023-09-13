public class Company {
    private String name;
    private double stockPrice;

    public Company(String name, double price) {
        this.name = name;
        stockPrice = price;
    }

    public void updateStockPrice(double price) {
        stockPrice = price;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s, $%.2f", name, stockPrice);
    }
}
