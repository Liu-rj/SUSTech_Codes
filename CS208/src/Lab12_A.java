import java.util.Scanner;

public class Lab12_A {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int[] w = new int[n + 1];
        int[] c = new int[n + 1];
        int[] a = new int[m + 1];
        for (int i = 1; i <= n; i++) {
            w[i] = in.nextInt();
        }
        for (int i = 1; i <= n; i++) {
            c[i] = in.nextInt();
        }
        for (int i = 0; i <= m; i++) {
            a[i] = 0;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = m; j > 0; j--) {
                if (c[i] > j) {
                    a[j] = a[j];
                } else {
                    a[j] = Math.max(a[j], w[i] + a[j - c[i]]);
                }
            }
        }
        System.out.println(a[m]);
    }
}
