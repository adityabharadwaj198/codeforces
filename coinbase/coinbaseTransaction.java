package coinbase;
import java.util.*;
import java.util.stream.Collectors;

public class coinbaseTransaction {

    public class transaction {
        String id;
        int size, fee;
        List<transaction> parents; 

        public transaction(String id, int size, int fee) {
            this.id = id;
            this.size = size;
            this.fee = fee;
            this.parents = new ArrayList<>();
        }
    }

    public List<transaction> includeTransactionInBlock(List<transaction> input, int blocksize) {
        List<transaction> answer = new ArrayList<>();
        Collections.sort(input, (transaction t1, transaction t2) -> { 
            return Double.compare((double)t2.fee/t2.size, (double)t1.fee/t1.size);
        });
        int fee =0;
        for (int i=0; i<input.size(); i++) {
            if (input.get(i).size <= blocksize) {
                blocksize -= input.get(i).size;
                answer.add(input.get(i));
                fee += input.get(i).fee;
            }
        }
        return answer;
    }

    public int blockSizeOfGroup(List<transaction> input) {
        return input.stream().mapToInt(t -> t.size).sum();
    }

    public int feeOfGroup(List<transaction> input) {
        return input.stream().mapToInt(t -> t.fee).sum();
    }

    public List<List<transaction>> includeTransactionInBlockForGroups(List<List<transaction>> input, int blocksize) {
        List<List<transaction>> answer = new ArrayList<>();
        Collections.sort(input, (List<transaction> t1, List<transaction> t2) -> { 
            return Double.compare((double)blockSizeOfGroup(t2)/feeOfGroup(t2), (double)blockSizeOfGroup(t1)/feeOfGroup(t1));
        });
        int fee =0;
        for (int i=0; i<input.size(); i++) {
            if (blockSizeOfGroup(input.get(i))<= blocksize) {
                blocksize -= blockSizeOfGroup(input.get(i));
                answer.add(input.get(i));
                fee += feeOfGroup(input.get(i));
            }
        }
        return answer;
    }


    public List<List<transaction>> createGroups(List<transaction> transactions) {
        List<List<transaction>> answer = new ArrayList<>();
        for (int i=0; i<transactions.size(); i++) {
            List<transaction> temp = new ArrayList<>();
            Set<transaction> visited = new HashSet<>();
            dfs(transactions.get(i), temp, visited);
            answer.add(temp);
        }
        // for (int i=0; i<answer.size(); i++) {
        //     for (int j=0; j<answer.get(i).size(); j++) {
        //         System.out.print(answer.get(i).get(j).id);
        //     }
        //     System.out.println("");
        // }
        return answer;
    }

    public void dfs(transaction t, List<transaction> group, Set<transaction> visited) {
        if (visited.contains(t)) 
            return; 

        visited.add(t);
        for (transaction parent: t.parents) {
            dfs(parent, group, visited);
        }
        group.add(t);
    }

    public static void main (String[] args) {

        coinbaseTransaction ct = new coinbaseTransaction();
        //    List<transaction> transactions = new ArrayList<> (Arrays.asList(
        // ct.new transaction("tx1", 30, 60),
        // ct.new transaction("tx2", 50, 100),
        // ct.new transaction("tx3", 20, 30),
        // ct.new transaction("tx4", 70, 130)
// )
//         );
        transaction a = ct.new transaction("a", 500, 300);
        transaction b = ct.new transaction("b", 800, 200);
        transaction c = ct.new transaction("c", 400, 150);
        b.parents = List.of(a);
        c.parents = List.of(a, b);
        List<transaction> transactions1 = List.of(a, b, c);
        System.out.println("==== Test Case 1 ====");
        test(ct, transactions1, 2000);

        // Test Case 2: Independent transactions
        transaction t1 = ct.new transaction("t1", 300, 500);
        transaction t2 = ct.new transaction("t2", 400, 400);
        transaction t3 = ct.new transaction("t3", 500, 200);
        List<transaction> transactions2 = List.of(t1, t2, t3);
        System.out.println("==== Test Case 2 ====");
        test(ct, transactions2, 1000);

        // Test Case 3: Multiple groups, some overlapping
        transaction x = ct.new transaction("x", 300, 300);
        transaction y = ct.new transaction("y", 500, 700);
        transaction z = ct.new transaction("z", 200, 100);
        y.parents = List.of(x);
        z.parents = List.of(y);
        List<transaction> transactions3 = List.of(x, y, z);
        System.out.println("==== Test Case 3 ====");
        test(ct, transactions3, 900);

        // Test Case 4: Parent too big, but child alone is possible
        transaction p1 = ct.new transaction("p1", 1500, 900);
        transaction p2 = ct.new transaction("p2", 400, 100);
        p2.parents = List.of(p1);
        List<transaction> transactions4 = List.of(p1, p2);
        System.out.println("==== Test Case 4 ====");
        test(ct, transactions4, 1000);

        // Test Case 5: No dependencies, greedy fee/size sorting
        transaction n1 = ct.new transaction("n1", 200, 100);
        transaction n2 = ct.new transaction("n2", 400, 350);
        transaction n3 = ct.new transaction("n3", 600, 500);
        List<transaction> transactions5 = List.of(n1, n2, n3);
        System.out.println("==== Test Case 5 ====");
        test(ct, transactions5, 1000);

    }

    public static void test(coinbaseTransaction ct, List<transaction> txs, int blockSize) {
        List<List<transaction>> groups = ct.createGroups(txs);
        List<List<transaction>> blockAnswer = ct.includeTransactionInBlockForGroups(groups, blockSize);

        for (List<transaction> group : blockAnswer) {
            for (transaction t : group) {
                System.out.print(t.id + " ");
            }
            System.out.println();
        }
    }

}

