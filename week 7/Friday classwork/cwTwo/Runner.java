package cwTwo;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        DLList<Song> list = new DLList<>();
        list.add(new Song("Linger", "The Cranberries"));
        list.add(new Song("Dreams", "The Cranberries"));
        list.add(new Song("the 1", "Taylor Swift"));
        list.add(new Song("Green Light", "Lorde"));
        list.add(new Song("Marigold", "Nirvana"));

        boolean doLoop = true;
        while (doLoop) {
            System.out.println(
                    """
                                1. Add a new song.
                                2. Display song List.
                                3. Delete a song given an artist name and song name.
                                4. Delete a song by number on the playlist.
                                5. Delete songs by artist.
                                6. Delete songs by name.
                                7. Sort by artist name.
                                8. Sort by song name.
                                9. Search by artist.
                                10. Quit
                            """);

            int ans = sc.nextInt();
            sc.nextLine();
            switch (ans) {
                case 1 -> {
                    System.out.println("Enter the name of the song to add");
                    String songName = sc.nextLine();
                    System.out.println("Enter the name of the artist");
                    String artistName = sc.nextLine();
                    list.add(new Song(songName, artistName));
                }
                case 2 -> {
                    int i = 0;
                    for (Song song : list) {
                        i++;
                        System.out.println(i + ": " + song);
                    }
                }
                case 3 -> {
                    System.out.println("What song would you like to delete: ");
                    String songName = sc.nextLine();
                    System.out.println("What artist is it by: ");
                    String artistName = sc.nextLine();

                    list.remove(new Song(songName, artistName));

                }
                case 4 -> {
                    System.out.println("Which index would you like to remove a song from: ");
                    int index = sc.nextInt();
                    sc.nextLine();
                    list.remove(index - 1);
                }
                case 5 -> {
                    System.out.println("What artist would you like to remove from the playlist: ");
                    String artistName = sc.nextLine();
                    list.removeIf(song -> song.artist().equals(artistName));
                }
                case 6 -> {
                    System.out.println("What song would you like to delete: ");
                    String songName = sc.nextLine();
                    list.removeIf(song -> song.name().equals(songName));
                }
                case 7 -> {
                    list.sort((s1, s2) -> s1.artist().compareTo(s2.artist()));
                }
                case 8 -> {
                    list.sort((s1, s2) -> s1.name().compareTo(s2.name()));
                }
                case 9 -> {
                    System.out.println("What artist would you like to search for: ");
                    String artistName = sc.nextLine();
                    int i = 0;
                    for (Song song : list) {
                        if (song.artist().equals(artistName)) {
                            i++;
                            System.out.println(i + ". " + song);
                        }
                    }
                    if (i == 0) {
                        System.out.println("No artist found by that name");
                    }
                }
                case 10 -> {
                    doLoop = false;
                }

            }
        }

        sc.close();
    }
}