package coinbase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class transactionbalanceSystemWithRollback {
    public class Account {
        String name;
        int bal; // ask if balance should be something other than int? can be float? 

        public Account(String n, int b) {
            this.name = n;
            this.bal = b;
        }

        @Override
        public String toString() {
            return name + " " + bal;
        }
    }

    public class txn {
        String from;
        String to;
        int percent; 

        public txn (String from, String to, int percent) {
            this.from = from;
            this.to = to;
            this.percent = percent;
        }
    }

    Map<String, Account> accounts = new HashMap<>();

    public void addAccount(String name, int balance) {
        if (accounts.containsKey(name)) {
            throw new IllegalArgumentException();
        }
        accounts.put(name, new Account(name, balance));
    }

    public void processTransactions(List<txn> txns) {
        for (txn t: txns) {
            processTransaction(t);
        }
    }

    public void processTransaction(txn t) {
        if (t.percent > 100) {
            throw new RuntimeException("Error cannot be more than 100%");
        }
        String from = t.from;
        String to = t.to;
        Account fromAcc = accounts.get(from);
        Account toAcc = accounts.get(to);
        int transferAmount = (fromAcc.bal * t.percent) / 100;
        fromAcc.bal -= transferAmount;
        toAcc.bal += transferAmount;

        System.out.println("After processing fromAcc: " + fromAcc + " toAcc: " + toAcc);
    }

    public static void main(String[] args) {
        transactionbalanceSystemWithRollback tbswr = new transactionbalanceSystemWithRollback();
        tbswr.addAccount("X", 100);
        tbswr.addAccount("Y", 50);
        tbswr.addAccount("A", 200);
        tbswr.addAccount("W", 20);
        
        tbswr.processTransactions(List.of(
            tbswr.new txn("X", "Y", 10),
            tbswr.new txn("A", "Y", 20),
            tbswr.new txn("Y", "W", 15)
        ));

        for (String a: tbswr.accounts.keySet()) {
            System.out.println(tbswr.accounts.get(a));
        }
    }
}
