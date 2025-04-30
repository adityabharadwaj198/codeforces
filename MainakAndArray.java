import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class MainakAndArray {
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
                int[] arr = new int[n];
                for (int i=0; i<n; i++) {
                    arr[i] = fr.nextInt();
                }
                solve(n, arr, fw);

            }
    
            fw.close();
        }
    
        // Solution method
        private static void solve(int n, int[] arr, FastWriter fw) throws IOException {
            // TODO: Implement your solution here
            
            // case 1: maximum is already at an - a1, no need to rotate. 
            // case 2: maximum is at a(i-1) - a(i). Why i-1? because you are rotating the array leftwards -> so at any point if ai comes at a(0), then through rotation a(i-1) would've come at a(n-1). 
            // case 3: maximum is at a(x) - a1, meaning a1 is fixed, and you are rotating some subarray from the rest of the array to bring the maximum value at An
            // case 4: maximum is at an - a(x), meaning an is fixed, and you are rotating a subarray from the rest of the array to bring the minimum value at A1

            int maxValue = arr[n-1] - arr[0];
            for (int i=1; i<n; i++) {
                maxValue = Math.max(arr[(i - 1)] - arr[i], maxValue);
            }

            for (int i=1; i<n; i++) {
                maxValue = Math.max(arr[i] - arr[0], maxValue);
            }

            for (int i=0; i<n-1; i++) {
                maxValue = Math.max(arr[n-1] - arr[i], maxValue);
            }

            fw.println(maxValue);
        }    
}
