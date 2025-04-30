import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class filesystem {
    public class file {
        String name;
        int size;
        user owner;
        public file(String n, int s) {
            this.name = n;
            this.size = s;
        }

        public file(String n, int s, user owner) {
            this.name = n;
            this.size = s;
            this.owner = owner;
        }
    }

    public class user {
        String uid;
        int capacity;
        List<file> files;
        public user(String u, int c) {
            this.uid = u;
            this.capacity = c;
            this.files = new ArrayList<>();
        }
    }

    Map<String, file> fileNameToFileMap = new HashMap<>();
    PriorityQueue<file> topKFiles = new PriorityQueue<>((file f1, file f2) -> {
        if (f2.size != f1.size) {
            return f2.size - f1.size;
        }
        return f1.name.compareTo(f2.name);
    });
    Map<String, user> uidToUserMap = new HashMap<>();
    Map<user, List<file>> userToFilesMap = new HashMap<>();

    public boolean addFile (String name, int size) {
        if (fileNameToFileMap.containsKey(name)) {
            return false;
        } else {
            file temp = new file(name, size);
            fileNameToFileMap.put(name, temp);
            topKFiles.offer(temp);
            return true;
        }
    }

    public boolean addFile(file temp) {
        if (fileNameToFileMap.containsKey(temp.name)) {
            return false;
        } else {
            fileNameToFileMap.put(temp.name, temp);
            topKFiles.offer(temp);
            return true;
        }
    }

    public int addFileBy(String uid, String fileName, int size) {
        user u = uidToUserMap.get(uid);
        if (u.capacity >= size) {
            file temp = new file(fileName, size);
            if (addFile(temp)) {                
                u.capacity-=size;
                addFilestoUserToFilesMap(u, temp);
                return u.capacity;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public void addFilestoUserToFilesMap(user u, file tempFile) {
        List<file> temp;
        if (userToFilesMap.containsKey(u)) {
            temp = userToFilesMap.get(u);
        } else {
            temp = new ArrayList<>();
        }
        temp.add(tempFile);
        userToFilesMap.put(u, temp);
    }

    public int mergeUser(String uid1, String uid2) {
        if (uid1 == uid2 || !uidToUserMap.containsKey(uid1) || !uidToUserMap.containsKey(uid2)) {
            return -1;
        }
        user u2 = uidToUserMap.get(uid2);
        List<file> uid2Files = userToFilesMap.get(u2);
        user u1 = uidToUserMap.get(uid1);
        List<file> uid1Files = userToFilesMap.get(u1);
        uid1Files.addAll(uid2Files);
        userToFilesMap.put(u1, uid1Files);
        u1.capacity+=u2.capacity;

        userToFilesMap.remove(u2);
        delete_user(uid2);

        return u1.capacity;
    }

    public boolean add_user(String uid, int capacity) {
        if (uidToUserMap.containsKey(uid)) {
            return false;
        }
        uidToUserMap.put(uid, new user(uid, capacity));
        return true;
    }

    public boolean delete_user(String uid) {
        if (!uidToUserMap.containsKey(uid)) {
            return false;
        }
        uidToUserMap.remove(uid);
        return true;
    }

    public int getFileSize(String name) {
        if (!fileNameToFileMap.containsKey(name)) {
            return 0;
        }
        return fileNameToFileMap.get(name).size;
    }

    public boolean deleteFile(String name) {
        if (!fileNameToFileMap.containsKey(name)) {
            return false;
        }
        fileNameToFileMap.remove(name);
        for (file f: topKFiles) {
            if (f.name.equals(name)) {
                topKFiles.remove(f);
            }
        }
        return true;
    }

    public List<String> getNLargest(String prefix, int n) {
        List<String> answer = new ArrayList<>();
        for (file f: topKFiles) {
            if (f.name.startsWith(prefix)) {
                String temp = f.name + " " + Integer.toString(f.size);
                answer.add(temp);
            }
            if (answer.size() == n) {
                break;
            }
        }
        return answer;
    }

    public static void main (String [] args) {
        filesystem fs = new filesystem();
        // System.out.println(fs.addFile("/dir/dir2/file1.txt", 10));
        // System.out.println(fs.addFile("/dir/dir2/file1.txt", 20));
        // System.out.println(fs.getFileSize("/dir/dir2/file1.txt"));
        // // System.out.println(fs.deleteFile("/dir/dir2/file1.txt"));
        // // System.out.println(fs.deleteFile("/dir/dir2/file1.txt"));
        // System.out.println(fs.getFileSize("/dir/dir2/file1.txt"));

        // System.out.println(fs.addFile("/dir/file1.txt", 5));
        // System.out.println(fs.addFile("/dir/file2", 20));
        // System.out.println(fs.addFile("/dir/deeper/file3.mov", 9));
        // System.out.println(fs.getNLargest("/dir", 5));
        // System.out.println(fs.getNLargest("/dir/dir2", 5));
        // System.out.println(fs.addFile("/big_file.mp4", 20));
        // System.out.println(fs.getNLargest("/", 5));

        System.out.println(fs.add_user("user1", 200));
        System.out.println(fs.add_user("user1", 100));
        System.out.println(fs.addFileBy("user1", "/dir1/file.med", 50));
        System.out.println(fs.addFileBy("user1", "/file.big", 140));
        System.out.println(fs.addFileBy("user1", "/dir/file.snall", 20));
        System.out.println(fs.addFile( "/dir/admin_file", 300));   
        System.out.println(fs.add_user("user2", 110));
        System.out.println(fs.addFileBy("user2", "/dir1/file.med", 45));
        System.out.println(fs.addFileBy("user2", "/new_file", 50));
        System.out.println(fs.mergeUser("user1", "user2"));
    }
}
