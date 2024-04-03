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

    public void BSF(E start, E goal) {
        HashSet<E> visitedNodes = new HashSet<>();
        HashSet<E> nodesToSearch = new HashSet<>();
        nodesToSearch.add(start);
        while(visitedNodes.size() < map.size()) {
            HashSet<E> nextToSearch = new HashSet<>();
            for(E e : nodesToSearch) {
                System.out.println(e);
                if(e.equals(goal)) {
                    return;
                }
                visitedNodes.add(e);
                for(E n : map.get(e)) {
                    if(!visitedNodes.contains(n) && !nodesToSearch.contains(n)) {
                        nextToSearch.add(n);
                    }
                }
            }
            nodesToSearch = nextToSearch;
        }
    }

    public void DFS(E start, E goal) {
        DFS(new HashSet<>(), start, goal);
    }

    private boolean DFS(HashSet<E> visited, E start, E goal) {
        System.out.println(start);
        visited.add(start);
        if(start.equals(goal)) {
            return true;
        } else {
            for(E e : map.get(start)) {
                if(!visited.contains(e)) {
                    if(DFS(visited, e, goal)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String toString() {
        return map.toString();
    }
}