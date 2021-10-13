import java.util.Scanner;

public class Q5 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int[][][] total = new int[N][][];
        int[][][] mid = new int[N][][];
        int m = in.nextInt();
        int n = in.nextInt();
        total[0] = new int[m][n];
        for (int j = 0; j < m; j++) {
            for (int k = 0; k < n; k++) {
                total[0][j][k] = in.nextInt();
            }
        }
        int a;
        int b = 0;
        for (int i = 1; i < N; i++) {
            a = in.nextInt();
            b = in.nextInt();
            total[i] = new int[a][b];
            mid[i] = new int[m][b];
            for (int j = 0; j < a; j++) {
                for (int k = 0; k < b; k++) {
                    total[i][j][k] = in.nextInt();
                }
            }
            if (i == 1) {
                for (int j = 0; j < mid[i].length; j++) {
                    for (int k = 0; k < mid[i][j].length; k++) {
                        for (int l = 0; l < total[0][0].length; l++) {
                            mid[i][j][k] += total[0][j][l] * total[i][l][k];
                        }
                    }
                }
            }
            if (i >= 2) {
                for (int j = 0; j < mid[i].length; j++) {
                    for (int k = 0; k < mid[i][j].length; k++) {
                        for (int l = 0; l < mid[i-1][0].length; l++) {
                            mid[i][j][k] += mid[i - 1][j][l] * total[i][l][k];
                        }
                    }
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < b; j++) {
                System.out.print(mid[N - 1][i][j] + " ");
            }
            System.out.println();
        }
    }
}
