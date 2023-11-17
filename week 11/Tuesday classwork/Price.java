public class Price {
    private double price;

    public Price(double p) {
        price = p;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Price p) {
            return p.price == price;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int)price*100;
    }

    @Override
    public String toString() {
        return String.format("$%.2f", price);
    }
}
