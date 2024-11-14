public class Graph<E> {
    private HashMap<E, HashSet<E>> map;

    public Graph() {
        map = new HashMap<>();
    }

    public void add(E e) {
        map.put(e, new HashSet<>());
    }

    public void addEdge(E a, E b) {
        map.get(a).add(b);
        map.get(b).add(a);
    }

    public void remove(E e) {
        HashSet<E> set = map.get(e);

        for(E each : set) {
            map.get(each).remove(e);
        }

        map.remove(e);
    }

    public String toString() {
        return map.toString();
    }
}