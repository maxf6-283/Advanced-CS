public class TileChangeEvent {
    private HashMap<Square, TileObject> replacerMap;
    private HashMap<Square, TileObject> replaceeMap;

    public TileChangeEvent() {
        replaceeMap = new HashMap<>();
        replacerMap = new HashMap<>();
    }

    public synchronized void undo(HashTable<Square, TileObject> tileMap) {
        for (Square s : replacerMap.keySet()) {
            tileMap.remove(s, replacerMap.get(s));
        }
        for (Square s : replaceeMap.keySet()) {
            if (!tileMap.containsKey(s)) {
                tileMap.put(s, replaceeMap.get(s));
            } else if (!tileMap.get(s).contains(replaceeMap.get(s))) {
                if (replaceeMap.get(s).isBackground()) {
                    tileMap.get(s).set(0, replaceeMap.get(s));
                } else {
                    tileMap.put(s, replaceeMap.get(s));
                }
            }
        }
    }

    public synchronized void redo(HashTable<Square, TileObject> tileMap) {
        HashMap<Square, TileObject> temp = replaceeMap;
        replaceeMap = replacerMap;
        replacerMap = temp;
        undo(tileMap);
        replacerMap = replaceeMap;
        replaceeMap = temp;
    }

    public void addReplacement(Square sq, TileObject replacee, TileObject replacer) {
        if (replacee != null) {
            replaceeMap.put(sq, replacee);
        }
        if (replacer != null) {
            replacerMap.put(sq, replacer);
        }
    }
}
