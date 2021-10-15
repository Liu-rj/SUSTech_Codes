import java.util.Scanner;

public class Lab0_prac {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T, n, l;
        T = in.nextInt();
        for (int i = 0; i < T; i++) {
            n = in.nextInt();
            l = in.nextInt();
            long[] memo = new long[n];
            System.out.println(climbStairs(0, n, l, memo));
        }
        in.close();
    }

    public static long climbStairs(int i, int n, int l, long[] memo) {
        if (i > n) {
            return 0;
        } else if (i == n) {
            return 1;
        } else if (memo[i] > 0) {
            return memo[i];
        }
        for (int j = 0; j < l; j++) {
            memo[i] += climbStairs(i + j + 1, n, l, memo) % 998244353;
        }
        memo[i] %= 998244353;
        return memo[i];
    }
}
