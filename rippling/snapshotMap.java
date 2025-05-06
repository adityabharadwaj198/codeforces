package rippling;
import java.util.*;

import javax.management.openmbean.KeyAlreadyExistsException;
/*
 * Your task is to create a class that functions as a map with additional snapshot functionality. This class should include the following methods:

get(k): This method should return the value v associated with the key k. If the key k does not exist, it should raise a KeyError exception. The key k will be a string and the value v will be an integer.
put(k, v): This method should add a key‑value pair to the map. The key k will be a string and the value v will be an integer.
delete(k): This method should remove the key‑value pair associated with the key k from the map.
In addition to these basic map operations, the class should also have snapshot functionality. This requires the implementation of the following methods:

take_snapshot(): This method should capture the current state of the map and return a unique snapshot ID (snap_id), which will be an integer; snap_id should start from 0 and increases every time when a new snapshot is taken.
get(k, snap_id): This method should return the value v associated with the key k from the snapshot identified by snap_id. If the key k does not exist in the snapshot, it should raise a KeyError exception.
Please consider the following constraints to guide your optimization: • The map could potentially hold millions or even billions of key‑value pairs, but only around ~100 snapshots. • Between any two consecutive snapshots, less than 1% of the key‑value pairs will be updated. • Each method implemented should have a time complexity less than O(N), where N is the total number of keys.


 */
public class snapshotMap {
    public class sMap {
        Map<String, TreeMap<Integer, Integer>> mymap; // map will be Key -> <SnapId, Value> 
        int snapId;
        public sMap() {
            this.mymap = new HashMap<>();
            this.snapId = 0;
        }

        // Integer get(String key) {
        //     if (mymap.containsKey(key)) { // how to get the last? 
        //         return mymap.get(key);
        //     }
        //     throw new RuntimeException("Key doesn't exist");
        // }

        Integer get(String key, Integer snap_id) {
            if (mymap.containsKey(key)) {
                return mymap.get(key).floorEntry(snap_id).getValue();
            }
            throw new RuntimeException("Key doesn't exist");
        }

        void put(String k, Integer v) {
            TreeMap<Integer, Integer> temp;
            if (this.mymap.containsKey(k)) {
                temp = this.mymap.get(k);
            } else {
                temp = new TreeMap<>();
            }
            temp.put(this.snapId, v);
            this.mymap.put(k, temp);
    }

        void takeSnapShot() {
            this.snapId++;
        }

        void delete (String key) {
            if (this.mymap.containsKey(key)) {
                TreeMap<Integer, Integer> temp = this.mymap.get(key);
                temp.put(this.snapId, null);
            } else {
                throw new RuntimeException("Key doesn't exist so can't delete");
            }
        }
    }

    public static void main (String[] args) {
        snapshotMap snapshotMap = new snapshotMap();
        sMap s = snapshotMap.new sMap();
        s.put("Bhardu", 1);
        s.put("Maatu", 2);
        s.put("minki", 3);

        System.out.println(s.get("Bhardu", 0));
        System.out.println(s.get("Maatu", 0));
        System.out.println(s.get("minki", 0));
        s.takeSnapShot();
        s.delete("Bhardu");
        System.out.println(s.get("Bhardu", 1));
        s.takeSnapShot();
        System.out.println(s.get("Bhardu", 1));
        System.out.println(s.get("Maatu", 2));
        System.out.println(s.get("minki", 2));
        s.put("Bhardu", 205);
        s.put("Maatu", 207);
        s.takeSnapShot();
        System.out.println(s.get("Bhardu", 2));
        System.out.println(s.get("Maatu", 2));
        System.out.println(s.get("minki", 2));
    }
}
