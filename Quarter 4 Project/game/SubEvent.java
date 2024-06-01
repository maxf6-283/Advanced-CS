package game;

public class SubEvent {
    public static final String sep = new String(new byte[]{17});
    public static final String subSep = new String(new byte[]{18});

    private String type;
    private int laserNum;
    private String value;

    public SubEvent(String t, int l, String v) {
        type = t;
        laserNum = l;
        value = v;
    }

    public SubEvent(String t, int l, double v) {
        type = t;
        laserNum = l;
        value = ""+v;
    }

    public SubEvent(Laser l, int lNum) {
        type = "create";
        laserNum = lNum;
        value = l.x() + subSep + l.y() + subSep + l.r() + subSep + l.xVel() + subSep + l.yVel();
    }

    public SubEvent(Asteroid a) {
        type = "respawn";
        laserNum = -1;
        value = a.x() + subSep + a.y() + subSep + a.r() + subSep + a.xVel() + subSep + a.yVel() + subSep + a.rVel() + subSep + a.size() + subSep + a.health();
    }

    public SubEvent(PowerUp p) {
        type = "create";
        laserNum = -1;
        value = p.x() + subSep + p.y() + subSep + p.type();
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

    public static SubEvent parse(String str) {
        String[] things = str.split(sep);
        if(things.length != 3) {
            throw new IllegalArgumentException("Cannot parse this string to an event: " + str);
        }

        return new SubEvent(things[0], Integer.parseInt(things[1]), things[2]);
    }

    public int valueInt() {
        return Integer.parseInt(value);
    }
}
