import java.util.Scanner;

public class Lab12_B {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] w = new int[n + 1];
        int[] sum = new int[n + 1];
        int[][] a = new int[n + 1][n + 1];
        a[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            w[i] = in.nextInt();
            sum[i] = sum[i - 1] + w[i];
            a[0][i] = 100000;
            for (int j = 0; j <= n; j++) {
                a[i][j] = 100000;
            }
            a[i][i] = 0;
        }
        for (int len = 1; len <= n - 1; len++) {
            for (int i = 1; i <= n - len; i++) {
                int j = i + len;
                for (int k = i; k < j; k++) {
                    a[i][j] = Math.min(a[i][j], a[i][k] + a[k + 1][j] + sum[j] - sum[i - 1]);
                }
            }
        }
        System.out.println(a[1][n]);
    }
}
