import java.util.Scanner;

public class Lab13_A {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] a = new int[n + 1];
        int[] b = new int[n];
        int[] opt = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextInt();
        }
        for (int i = 1; i < n; i++) {
            b[i] = in.nextInt();
        }
        opt[0] = 0;
        opt[1] = a[1];
        for (int i = 2; i < n + 1; i++) {
            opt[i] = Math.min(opt[i - 2] + b[i - 1], opt[i - 1] + a[i]);
        }
        System.out.println(opt[n]);
    }
}
