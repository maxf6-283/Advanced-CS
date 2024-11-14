public class Song {
    private String name, artist, album;

    public Song(String nam, String artis, String albu) {
        name = nam;
        artist = artis;
        album = albu;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setName(String nam) {
        name = nam;
    }

    public void setArtist(String artis) {
        artist = artis;
    }

    public void setAlbum(String albu) {
        album = albu;
    }

    @Override
    public String toString() {
        return artist + ": " + album + " - " + name;
    }
}
