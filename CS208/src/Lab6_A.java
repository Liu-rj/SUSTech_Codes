import java.util.Scanner;

public class Lab6_A {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        long[] a = new long[n];
        long sum = 0;
        int count = 0;
        long min = Long.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextLong();
            sum += Math.abs(a[i]);
            min = Math.min(Math.abs(a[i]), min);
            if (a[i] > 0) {
                count++;
            }
        }
        if (n == 1) {
            System.out.println(a[0]);
        } else if (count == n || count == 0) {
            System.out.println(sum - 2 * min);
        } else {
            System.out.println(sum);
        }
    }
}
