import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class coinbaseTransactionFilters {
    TreeMap<String, coinbaseTransaction> tidIndex;
    TreeMap<Long, List<coinbaseTransaction>> timeIndex;
    TreeMap<String, List<coinbaseTransaction>> uidIndex;
    
    public class coinbaseTransaction {
        String tid; // clarify what the types should be 
        long time;  
        String uid; 
        int amount; 

        public coinbaseTransaction(String tid, long time, String uid, int amount) {
            this.tid = tid;
            this.time = time;
            this.uid = uid;
            this.amount = amount;
        }

        public boolean addNewTransaction(String tid, long time, String uid, int amount) {
            coinbaseTransaction ct = new coinbaseTransaction(tid, time, uid, amount);
            tidIndex.putIfAbsent(tid, ct);
            getTimeArrayList(time, ct);
            getUidArrayList(uid, ct);
            return true;
        }

        private void getUidArrayList(String uid, coinbaseTransaction ct) {
            List<coinbaseTransaction> uidArrayList;
            if (!uidIndex.containsKey(uid)) {
                uidArrayList = new ArrayList<>();
            } else {
                uidArrayList = uidIndex.get(uid);
            }
            uidArrayList.add(ct);
            uidIndex.put(uid, uidArrayList);
        }

        private void getTimeArrayList(long time, coinbaseTransaction ct) {
            List<coinbaseTransaction> timeArrayList;
            if (!timeIndex.containsKey(time)) {
                timeArrayList = new ArrayList<>();
            } else {
                timeArrayList = timeIndex.get(time);
            }
            timeArrayList.add(ct);
            timeIndex.put(time, timeArrayList);
        }
    }
}
