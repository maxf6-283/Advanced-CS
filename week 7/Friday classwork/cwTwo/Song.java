package cwTwo;

public class Song {
    private String name;
    private String artist;

    public Song(String name_, String artist_) {
        name = name_;
        artist = artist_;
    }

    @Override
    public String toString() {
        return name + " - " + artist;
    }

    public String artist() {
        return artist;
    }

    public String name() {
        return name;
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof Song s) {
            return s.name.equals(name) && s.artist.equals(artist);
        }
        return false;
    }
}
