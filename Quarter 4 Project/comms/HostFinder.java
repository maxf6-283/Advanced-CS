package comms;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.nio.channels.ClosedByInterruptException;
import java.net.SocketException;
import java.util.function.Consumer;

import utils.HashSet;

public class HostFinder implements Runnable {
    private MulticastSocket multicastSocket;
    private HashSet<Host> foundHosts;
    private Consumer<Host> foundHostConsumer;
    private Consumer<Host> lostHostConsumer;
    private Consumer<Host> nameChangeHostConsumer;

    private HashSet<Host> nameChangedHosts;

    private volatile boolean running = true;

    public HostFinder(Consumer<Host> onFindHost, Consumer<Host> onLoseHost, Consumer<Host> onHostNameChange) {
        try {
            multicastSocket = new MulticastSocket(HostBroadcaster.port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        foundHosts = new HashSet<>();
        nameChangedHosts = new HashSet<>();

        foundHostConsumer = onFindHost;
        lostHostConsumer = onLoseHost;
        nameChangeHostConsumer = onHostNameChange;
    }

    @Override
    public void run() {
        try {
            // join all the network interfaces because I hate network interfaces
            NetworkInterface.getNetworkInterfaces().asIterator().forEachRemaining(netInterface -> {
                try {
                    multicastSocket.joinGroup(new InetSocketAddress(HostBroadcaster.group, HostBroadcaster.port),
                            netInterface);
                } catch (IOException e) {
                    // do nothing because netowrk interfaces will complain over anything
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // start removing hosts that haven't existed for too long
        (new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    HashSet<Host> removedHosts = new HashSet<>();
                    foundHosts.removeIf(e -> {
                        if (System.currentTimeMillis() - e.getTimeDetected() > 1000) {
                            removedHosts.add(e);
                            return true;
                        }
                        return false;
                    });

                    removedHosts.forEach(lostHostConsumer);
                    nameChangedHosts.forEach(nameChangeHostConsumer);
                    nameChangedHosts.clear();
                }
            }

        })).start();

        byte[] bytes = new byte[256];
        DatagramPacket p = new DatagramPacket(bytes, bytes.length);
        while (running) {
            try {
                for (int i = 0; i < bytes.length
                        && (bytes[i] != 0 || (i != bytes.length - 1 && bytes[i + 1] != 0)); i++) {
                    bytes[i] = 0;
                }
                multicastSocket.receive(p);
                try {
                    Host newHost = new Host(bytes, e -> nameChangedHosts.add(e));
                    foundHosts.add(newHost);
                    foundHostConsumer.accept(newHost);
                } catch (IllegalArgumentException e) {
                }

            } catch (ClosedByInterruptException | SocketException e) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
        multicastSocket.close();
    }

    public HashSet<Host> hosts() {
        return foundHosts;
    }

    public static void main(String[] args) {
        (new HostFinder(e -> System.out.println(e), e -> System.out.println(e), e -> System.out.println(e))).run();
    }
}
