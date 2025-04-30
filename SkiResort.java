import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class SkiResort {
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
            int k = fr.nextInt();
            int q = fr.nextInt();
            long[] arr = new long[n];
            for (int i = 0; i < n; i++) {
                arr[i] = fr.nextInt();
            }

            solve(n, k, q, arr, fw);
        }

        fw.close();
    }

    // Solution method
    private static void solve(int n, int k, int q, long[] arr, FastWriter fw) throws IOException {
        // TODO: Implement your solution here
        int l = 0; // left ptr 
        int r = l; // right ptr
        long sum = 0;
        while (r < n) {
            if (arr[r] <= q) {
                // fw.println("if: l: " + l + "r: "+ r + "sum:" + sum);
                if (r == n-1) {
                    int length = r - l + 1;
                    if (length >= k)
                        sum += (long)((long)(length - k + 1) * (long)(length - k + 2))/2;    
                }
                r++;
            } else {
                // fw.println("else: l: " + l + "r: "+ r + "sum:" + sum);
                int length = r - l;
                if (length >= k)
                    sum += (long)((long)(length - k + 1) * (long)(length - k + 2))/2;
                r++;
                l = r;
            }
        }
        fw.println(sum);
        
    }    

}
