package comms;

import game.SubEvent;

public class ClientEvent {
    private static final String sep = new String(new char[] { (char) 0 });
    private String type;
    private String value;

    public ClientEvent(String t, String v) {
        type = t;
        value = v;
    }

    public ClientEvent(String t, double v) {
        type = t;
        value = ""+v;
    }

    public String valueString() {
        return value;
    }

    public double valueDouble() {
        return Double.parseDouble(value);
    }

    public SubEvent valueLaser() {
        return SubEvent.parse(value);
    }
    
    public String type() {
        return type;
    }

    @Override
    public String toString() {
        return type + sep + value;
    }

    public static ClientEvent parse(String str) {
        String[] things = str.split(sep);
        if (things.length != 2) {
            throw new IllegalArgumentException(
                    "Cannot parse this string to an event: " + str);

        }

        return new ClientEvent(things[0], things[1]);
    }
}