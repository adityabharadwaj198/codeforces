import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class arrayMerging {
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
            int n = fr.nextInt();
            int[] a = new int[n];
            int[] b = new int[n];
            for (int i=0; i<n; i++) {
                a[i] = fr.nextInt();
            }
            for (int i=0; i<n; i++) {
                b[i] = fr.nextInt();
            }
            
            solve(n, a, b, fw);

        }

        fw.close();
    }

    // Solution method
    private static void solve(int n, int[] a, int[] b, FastWriter fw) throws IOException {
        // TODO: Implement your solution here
        
        // case 1: maximum is already at an - a1, no need to rotate. 
        // case 2: maximum is at a(i-1) - a(i). Why i-1? because you are rotating the array leftwards -> so at any point if ai comes at a(0), then through rotation a(i-1) would've come at a(n-1). 
        // case 3: maximum is at a(x) - a1, meaning a1 is fixed, and you are rotating some subarray from the rest of the array to bring the maximum value at An
        // case 4: maximum is at an - a(x), meaning an is fixed, and you are rotating a subarray from the rest of the array to bring the minimum value at A1

        HashMap<Integer, Integer> mymap = new HashMap<>();
        for (int i=0; i<a.length; i++) {
            mymap.put(a[i], mymap.getOrDefault(a[i], 0) + 1);
        }
        for (int i=0; i<b.length; i++) {
            mymap.put(b[i], mymap.getOrDefault(b[i], 0) + 1);
        }
        int max = Integer.MIN_VALUE;
        for (int i: mymap.keySet()) {
            max = Math.max(mymap.get(i), max);
        }
        fw.println(max);
    }    

}
