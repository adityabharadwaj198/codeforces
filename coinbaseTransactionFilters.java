import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class coinbaseTransactionFilters {
    TreeMap<String, coinbaseTransaction> tidIndex;
    TreeMap<Long, List<coinbaseTransaction>> timeIndex;
    TreeMap<String, List<coinbaseTransaction>> uidIndex;
    
    public class coinbaseTransaction {
        String tid; // clarify what the types should be - ask if I can take them all to int for simplicity 
        long time;  
        String uid; 
        int amount; 

        // ask if you should handle things like negative amount or time related exceptions here 
        public coinbaseTransaction(String tid, long time, String uid, int amount) { 
            this.tid = tid;
            this.time = time;
            this.uid = uid;
            this.amount = amount;
        }

        // suggest that I can make different indexes
        // by using a treemap -> why treemap? because it supports operations that will give me 
        // greater than or lower than in o(logn)? 
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

    interface Filter {
        public boolean apply (coinbaseTransaction ct);
    }
    
    public class simpleFilter implements Filter {

        String op; // should I handle like unsupported operations here? 
        String field; // should I handle like unsupported operations here? 
        int value; // check what type of value it should be 

        public simpleFilter (String op, 
        String field, 
        int value) {
            this.op = op;
            this.field = field;
            this.value = value;
        }

        public boolean apply (coinbaseTransaction ct) {
            if (field.equals("tid")) {
                String fieldValue = ct.tid;
                switch (op) {
                    case "==":
                        return fieldValue.equals(Integer.toString(value));
                    case ">":
                        throw new UnsupportedOperationException("> is not implemented for transactionId");
                    case "<":
                        throw new UnsupportedOperationException("< is not implemented for transactionId");
                }
            } else if (field.equals("time")) {
                long fieldValue = ct.time;
                switch(op) {
                    case "==":
                        return fieldValue == value;
                    case ">":
                        return fieldValue > value;
                    case "<":
                        return fieldValue < value;
                }
            } else if (field.equals("uid")) {
                String fieldValue = ct.uid;
                switch (op) {
                    case "==":
                        return fieldValue.equals(Integer.toString(value));
                    case ">":
                        throw new UnsupportedOperationException("> is not implemented for transactionId");
                    case "<":
                        throw new UnsupportedOperationException("< is not implemented for transactionId");
                }
            } else {
                int fieldValue = ct.amount;
                switch(op) {
                    case "==":
                        return fieldValue == value;
                    case ">":
                        return fieldValue > value;
                    case "<":
                        return fieldValue < value;
                }
            }

            return false;
        }
    }

    public class compositeFilter implements Filter {

        String op; // should I handle like unsupported operations here? 
        List<Filter> filters; // should I handle like unsupported operations here? 
 
        public compositeFilter (String op, List<Filter> filters) {
            this.op = op;
            this.filters = filters;
        }

        public boolean apply(coinbaseTransactionFilters.coinbaseTransaction ct) {
            if (this.op == "AND") {
                for (Filter f: filters) {
                    if (!f.apply(ct)) {
                        return false;
                    }
                }
                return true;
            } else if (this.op == "OR") {
                for (Filter f: filters) {
                    if (f.apply(ct)) {
                        return true;
                    }
                }
                return false;
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }

    List<coinbaseTransaction> filterTransactions(List<coinbaseTransaction> txns, Filter filter) {
        List<coinbaseTransaction> result = new ArrayList<>();
        for (coinbaseTransaction tx: txns) {
            if (filter.apply(tx)) {
                result.add(tx);
            }
        }
        return result;
    }
    public static void main (String[] args) {
        coinbaseTransactionFilters ctf = new coinbaseTransactionFilters();
        List<coinbaseTransaction> cts = new ArrayList<>();
        cts.add(ctf.new coinbaseTransaction("1", 0, "1", 100));
        cts.add(ctf.new coinbaseTransaction("2", 0, "1", 200));
        cts.add(ctf.new coinbaseTransaction("3", 0, "1", 400));
        cts.add(ctf.new coinbaseTransaction("4", 0, "2", 100));
        cts.add(ctf.new coinbaseTransaction("5", 0, "3", 100));
        cts.add(ctf.new coinbaseTransaction("6", 0, "2", 1000));
        cts.add(ctf.new coinbaseTransaction("7", 0, "3", 50));
        cts.add(ctf.new coinbaseTransaction("8", 0, "4", 10));

        compositeFilter cf = ctf.new compositeFilter("AND", List.of(
            ctf.new simpleFilter("==", "uid", 1),
            ctf.new simpleFilter(">", "amount", 300)
        ));

        compositeFilter cf2 = ctf.new compositeFilter("OR", List.of(
            ctf.new simpleFilter("==", "uid", 1),
            ctf.new simpleFilter(">", "amount", 300)
        ));

        List<coinbaseTransaction> f1 = ctf.filterTransactions(cts, cf);
        for (coinbaseTransaction txn: f1) {
            System.out.println(txn.tid);
        }
        List<coinbaseTransaction> f2 = ctf.filterTransactions(cts, cf2);
        for (coinbaseTransaction txn: f2) {
            System.out.println(txn.tid);
        }
    }
}
