import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProcessCapacity {
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
            int psize = fr.nextInt();
            int[] process = new int[psize];
            for (int i = 0; i < psize; i++) {
                process[i] = fr.nextInt();
            }
            int csize = fr.nextInt();
            int[] cap = new int[csize];
            for (int i = 0; i < csize; i++) {
                cap[i] = fr.nextInt();
            }

            solve(psize, csize, process, cap, fw);
        }

        fw.close();
    }

    // Solution method
    private static void solve(int psize, int csize, int[] process, int[] cap, FastWriter fw) throws IOException {
        // TODO: Implement your solution here
        // greed emthod. Sort the two arrays, and allocate a  processor to every process if it's  capacity is bigger than the rpocess size

        Arrays.sort(process);
        Arrays.sort(cap);
        if (process[psize-1] > cap[csize-1]) {
            fw.println(-1);
            return;
        }
        int i = 0; // pointer running over process 
        int j = 0; // pointer running over capacity 
        int time = 0;
        while (i < psize) {
            while (j<csize && cap[j] < process[i]) {
                j++;
            }
            if (j == csize) {
                j = 0;
                time += 2;
            }
            if (cap[j] >= process[i]) {
                i++; j++;
            }
        }
        fw.println(time);
    }    

}
