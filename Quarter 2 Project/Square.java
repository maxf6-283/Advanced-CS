import java.io.Serializable;

public class Square implements Serializable{
    private int x;
    private int y;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Square s) {
            return x == s.x && y == s.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return x >= y ? x * x + x + y : x + y * y;
    }
}
