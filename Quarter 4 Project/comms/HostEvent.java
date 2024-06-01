package comms;

import game.SubEvent;

public class HostEvent {
    private static final String sep = new String(new char[]{(char)0});
    private String type;
    private int playerNum;
    private String value;

    public HostEvent(String t, int p, String v) {
        type = t;
        playerNum = p;
        value = v;
    }

    public HostEvent(String t, int p, double v) {
        type = t;
        playerNum = p;
        value = ""+v;
    }

    public String valueString() {
        return value;
    }

    public double valueDouble() {
        return Double.parseDouble(value);
    }

    public SubEvent valueSub() {
        return SubEvent.parse(value);
    }

    public String type() {
        return type;
    }

    public int playerNum() {
        return playerNum;
    }

    @Override
    public String toString() {
        return type + sep + playerNum + sep + value;
    }

    public static HostEvent parse(String str) {
        String[] things = str.split(sep);
        if(things.length != 3) {
            throw new IllegalArgumentException("Cannot parse this string to an event: " + str);
        }

        return new HostEvent(things[0], Integer.parseInt(things[1]), things[2]);
    }

    public int valueInt() {
        return Integer.parseInt(value);
    }
}