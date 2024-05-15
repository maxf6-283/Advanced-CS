package comms;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.function.Consumer;

public class Host {
    private Consumer<Host> nameChangeConsumer;
    private String name;
    private InetSocketAddress address;
    private volatile long timeFound;

    public Host(byte[] bytes, Consumer<Host> onNameChange) {
        timeFound = System.currentTimeMillis();
        String[] parts = new String(bytes, HostBroadcaster.charset).trim().split(HostBroadcaster.sep);
        if (!parts[0].equals(HostBroadcaster.hostingMsg)) {
            throw new IllegalArgumentException("Host cannot be created from non-host byte array");
        }
        try {
            address = new InetSocketAddress(InetAddress.getByName(parts[1]), Integer.parseInt(parts[2]));
        } catch (NumberFormatException | UnknownHostException e) {
            e.printStackTrace();
        }
        name = parts[3];
        nameChangeConsumer = onNameChange;
    }

    public long getTimeDetected() {
        return timeFound;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Host h) {
            if (h.address.equals(address)) {
                boolean x = !name.equals(h.name);
                
                if(timeFound < h.timeFound) {
                    name = h.name;
                    timeFound = h.timeFound;
                } else {
                    h.name = name;
                    h.timeFound = timeFound;
                }
                if (x){
                    nameChangeConsumer.accept(this);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (~address.hashCode()) >> 2 ^ (~address.hashCode()) << 14;
    }

    @Override
    public String toString() {
        return name;
    }

    public InetSocketAddress getAddress() {
        return address;
    }
}
