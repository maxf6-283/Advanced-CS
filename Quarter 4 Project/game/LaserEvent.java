package game;

public class LaserEvent {
    public static final String sep = new String(new byte[]{17});
    public static final String subSep = new String(new byte[]{18});

    private String type;
    private int laserNum;
    private String value;

    public LaserEvent(String t, int l, String v) {
        type = t;
        laserNum = l;
        value = v;
    }

    public LaserEvent(String t, int l, double v) {
        type = t;
        laserNum = l;
        value = ""+v;
    }

    public LaserEvent(Laser l, int lNum) {
        type = "create";
        laserNum = lNum;
        value = l.x() + subSep + l.y() + subSep + l.r() + subSep + l.xVel() + subSep + l.yVel();
    }

    public String valueString() {
        return value;
    }

    public double valueDouble() {
        return Double.parseDouble(value);
    }

    public String type() {
        return type;
    }

    public int laserNum() {
        return laserNum;
    }

    @Override
    public String toString() {
        return type + sep + laserNum + sep + value;
    }

    public static LaserEvent parse(String str) {
        String[] things = str.split(sep);
        if(things.length != 3) {
            throw new IllegalArgumentException("Cannot parse this string to an event: " + str);
        }

        return new LaserEvent(things[0], Integer.parseInt(things[1]), things[2]);
    }

    public int valueInt() {
        return Integer.parseInt(value);
    }
}
