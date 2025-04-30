import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class graphDiameter {
    
    public static class pair {
        int node, dist;
        public pair (int n, int d) {
            this.node = n;
            this.dist = d;
        }
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

    // Fast Writer
    static class FastWriter {
        private final BufferedWriter bw;

        public FastWriter() {
            this.bw = new BufferedWriter(new OutputStreamWriter(System.out));
        }

        public void print(Object object) throws IOException {
            bw.append(object.toString());
        }

        public void println(Object object) throws IOException {
            print(object);
            bw.append("\n");
        }

        public void close() throws IOException {
            bw.close();
        }
    }

    // Utility functions
    static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    static long gcd(long a, long b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    static int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
    }

    static long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void reverse(int[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            swap(arr, i, arr.length - 1 - i);
        }
    }

    // Main method
    public static void main(String[] args) throws IOException {
        FastReader fr = new FastReader();
        FastWriter fw = new FastWriter();

        // Read number of test cases
        int t = fr.nextInt();
        
        // Process each test case
        while (t-- > 0) {
            // Read input for current test case
            int g_nodes = fr.nextInt();
            int g_edges = fr.nextInt();
            int[] gFrom = new int[g_edges];
            int[] gTo = new int[g_edges];
            for (int i=0; i<g_edges; i++) {
                gFrom[i] = fr.nextInt();
                gTo[i] = fr.nextInt();
            }
            fw.println(getMaxTime(g_nodes, gFrom, gTo));
        }

        fw.close();
    }

    // Solution method
    // private static void solve(int n, int[] arr, FastWriter fw) throws IOException {
    //     // TODO: Implement your solution here
        
    //     // case 1: maximum is already at an - a1, no need to rotate. 
    //     // case 2: maximum is at a(i-1) - a(i). Why i-1? because you are rotating the array leftwards -> so at any point if ai comes at a(0), then through rotation a(i-1) would've come at a(n-1). 
    //     // case 3: maximum is at a(x) - a1, meaning a1 is fixed, and you are rotating some subarray from the rest of the array to bring the maximum value at An
    //     // case 4: maximum is at an - a(x), meaning an is fixed, and you are rotating a subarray from the rest of the array to bring the minimum value at A1

    // }    


    private static int getMaxTime(int n, int[] gFrom, int[] gTo) {
        List<List<Integer>> graph = new ArrayList<>(n+1);
        for (int i=0; i<n+1; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i=0; i<gFrom.length; i++) {
            System.out.println("gFrom " + gFrom[i] + " gTo " + gTo[i]);
            graph.get(gFrom[i]).add(gTo[i]);
            graph.get(gTo[i]).add(gFrom[i]);
        }

        for (int i=0; i<n+1; i++) {
            System.out.println(graph.get(i));
        }
        
        int leafNode = 0;

        for (int i=0; i<n; i++) {
            if (graph.get(i).size() == 1) { // this means it's a leaf node
                leafNode = i;
            }
        }
        boolean[] visited = new boolean[n+1];
        Arrays.fill(visited, false);
        int maxDist = Integer.MIN_VALUE;
        for (int i=1; i<n; i++) {
            if (visited[i] == false) {
                maxDist = Math.max(maxDist, bfs(graph, i, visited));
            }
        }
        return maxDist;
    }

    static int bfs(List<List<Integer>> graph, int leafNode, boolean[] visited) {
        int maxDist = Integer.MIN_VALUE;
        Queue<pair> myq = new LinkedList<>();
        myq.offer(new pair(leafNode, 0));

        while (!myq.isEmpty()) {
            pair curr = myq.poll();
            visited[curr.node] = true;
            System.out.println("In here with node" + curr.node);
            maxDist = Math.max(maxDist, curr.dist);
            System.out.println("In here with maxDist" + maxDist);
            for (int neighbour: graph.get(curr.node)) {
                System.out.println("In here with neightbour " + neighbour);
                if (visited[neighbour] == false) {
                    myq.offer(new pair(neighbour, curr.dist + 1));
                }
            }
        }
        return maxDist;
    }

}