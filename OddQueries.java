import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class OddQueries {
        // Fast I/O
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
                int q = fr.nextInt();
                int[] arr = new int[n];
                long[] prefix = new long[n+1];
                for (int i = 1; i <= n; i++) {
                    arr[i-1] = fr.nextInt();
                    prefix[i] = arr[i-1] + prefix[i-1];
                }

                for (int i=0; i<q; i++) {
                    int l = fr.nextInt();
                    int r = fr.nextInt();
                    int k = fr.nextInt();
                    // Solve the problem
                    solve(n, prefix, l, r, k, fw);
                }
    
            }
    
            fw.close();
        }
    
        // Solution method
        private static void solve(int n, long[] prefix, int l, int r, int k, FastWriter fw) throws IOException {
            // TODO: Implement your solution here
            
            // Example: Print the array'
            
            long ans = prefix[n] - (prefix[r] - prefix[l-1]) + (long)((long)k*(long)(r-l+1));
            if (ans % 2 == 1) {
                fw.println("yes");
            } else {
                fw.println("no");
            }
        }    
}
