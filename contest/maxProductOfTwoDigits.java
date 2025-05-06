package contest;

public class maxProductOfTwoDigits {
    public int maxProduct(int n) {
        String x = Integer.toString(n);
        int max = 0;
        int secondMax = 0;
        for (int i=0; i<x.length(); i++) {
            if (x.charAt(i) - '0' > max) {
                secondMax = max;
                max = x.charAt(i) - '0';
            } else if (x.charAt(i) - '0' > secondMax) {
                secondMax = x.charAt(i) - '0';
            }
        }
        return max*secondMax;
    }

    public static void main(String[] args) {
        maxProductOfTwoDigits m = new maxProductOfTwoDigits();
        System.out.println(m.maxProduct(124));
        System.out.println(m.maxProduct(31));
        System.out.println(m.maxProduct(22));
        System.out.println(m.maxProduct(31));
        System.out.println(m.maxProduct(-112312312));
    }
}
