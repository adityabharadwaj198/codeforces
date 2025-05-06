package rippling;

import java.util.*;

public class musicPlayer {

    class User {
        int userId; 
        String name;

        public User (int uid, String name) {
            this.userId = uid;
            this.name = name;
        }

        @Override
        public String toString() {
            return Integer.toString(userId);
        }

    }

    class Song {
        String name; 
        int id; 
        int plays; 
        Set<User> playedBy; 

        public Song (String name, 
        int id) {
            this.name = name;
            this.id = id;
            this.plays = 0;
            playedBy = new HashSet<>();
        }

        @Override
        public String toString() {
            return name + " " + id + " " + plays + " " + playedBy;
        }
    }

    public Map<Integer, Song> idToSong = new HashMap<>();
    public Map<Integer, User> idToUser = new HashMap<>();
    public Map<Integer, Queue<Song>> userToSongs = new HashMap<>();
    List<Song> songs = new ArrayList<>();

    int idCounter = 0; 
    int uidCounter = 0;

    public int addSong(String songTitle) {
        idCounter++;
        idToSong.put(idCounter, new Song(songTitle, idCounter));
        return idCounter;
    }

    public void addUser(String name) {
        uidCounter++;
        idToUser.put(uidCounter, new User(uidCounter, name));
        userToSongs.put(uidCounter, new LinkedList<>());
    }

    public void playSong(int songId, int userId) {
        if (!idToSong.containsKey(songId)) {
            throw new RuntimeException();
        }
        if (!userToSongs.containsKey(userId)) {
            throw new RuntimeException("This user doesn't exist");
        }
        Song s = idToSong.get(songId);
        s.plays++;
        s.playedBy.add(idToUser.get(userId));

        Queue<Song> q = userToSongs.get(userId);
        if (q.size()>=3) {
            q.poll();
        } 
        q.offer(s);
    }

    public void printMostPlayedSongs() {
        for (int i: idToSong.keySet()) {
            songs.add(idToSong.get(i));
        }
        Collections.sort(songs, (Song s1, Song s2) -> {
            return s2.plays - s1.plays;
        });
        for (Song s: songs) {
            System.out.println(s);
        }
    }

    public void getLastThreeSongs(int userId) {
        if (!userToSongs.containsKey(userId)) {
            throw new RuntimeException();
        }
        Queue<Song> q = userToSongs.get(userId);
        for (Song s : q) {
            System.out.println(s);
        }
    }

    public static void main (String[] args) {
        musicPlayer ms = new musicPlayer();
        ms.addSong("Georgia");
        ms.addSong("Dirty Paws");
        ms.addSong("The moment");
        ms.addSong("Maharani");
        ms.addSong("Bahara X");
        ms.addSong("Dooba Dooba");
        ms.addSong("RazorBlade");
        ms.addSong("Ocean");
        ms.addSong("I ain't worried");
        ms.addSong("Softcore");
        ms.addSong("In your eyes");
        ms.addSong("You right");

        ms.addUser("aditya");
        ms.addUser("minki");

        ms.playSong(2, 1);
        ms.playSong(1, 1);
        ms.playSong(1, 1);
        ms.playSong(1, 1);
        ms.playSong(1, 1);
        ms.playSong(1, 1);
        ms.playSong(1, 1);
        ms.playSong(2, 1);
        ms.playSong(2, 2);
        ms.playSong(3, 1);
        ms.playSong(1, 2);
        ms.playSong(3, 2);
        ms.playSong(4, 1);
        ms.playSong(5, 2);
        ms.playSong(6, 1);
        ms.playSong(7, 2);
        ms.playSong(8, 1);
        ms.playSong(9, 2);
        ms.playSong(10, 1);
    
        ms.printMostPlayedSongs();

        ms.getLastThreeSongs(1);
    }
}
