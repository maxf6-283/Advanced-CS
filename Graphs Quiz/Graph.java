import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph<E> {
    private Map<E, Set<E>> graph;

    public Graph() {
        graph = new HashMap<>();
    }

    public void add(E e) {
        graph.put(e, new HashSet<>());
    }

    public void addEdge(E e1, E e2) {
        graph.get(e1).add(e2);
        graph.get(e2).add(e1);
    }

    public void remove(E e) {
        for(E e1 : graph.get(e)) {
            graph.get(e1).remove(e);
        }
        graph.remove(e);
    }

    @Override
    public String toString() {
        String str = "";
        for(E e1 : graph.keySet()) {
            str += "\n" + e1 + " -> ";
            for(E e2 : graph.get(e1)) {
                str += e2 + ", ";
            }
            str = str.substring(0, str.length() - 2);
        }
        return str.substring(1);
    }
}
