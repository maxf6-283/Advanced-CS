public class TileChangeEvent {
    public TileObject from;
    public TileObject to;
    public DLList<Square> squares = new DLList<>();

    public void undo(HashTable<Square, TileObject> tileMap) {
        for(Square s : squares) {
            if(from == null) {
                if(tileMap.containsKey(s)) {
                    tileMap.remove(s, to);
                }
            } else {
                if(tileMap.containsKey(s)) {
                    if(to == null) {
                        tileMap.put(s, from);
                    } else {
                        tileMap.get(s).set(tileMap.get(s).indexOf(to), from);
                    }
                }
            }
        }
    }

    public void redo(HashTable<Square, TileObject> tileMap) {
        for(Square s : squares) {
            if(to == null) {
                if(tileMap.containsKey(s)) {
                    tileMap.remove(s, from);
                }
            } else {
                if(tileMap.containsKey(s)) {
                    if(to == null) {
                        tileMap.put(s, to);
                    } else {
                        tileMap.get(s).set(tileMap.get(s).indexOf(from), to);
                    }
                }
            }
        }
    }
}
