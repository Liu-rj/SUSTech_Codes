import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int m = in.nextInt();
        int n = in.nextInt();
        int[][] point = new int[m][n];
        int[][] towards = {{1, 0}, {-1, -1}, {0, -1}, {1, 1}};
        int position = -1;
        int max = m * n;
        int x = 0;
        int y = n - 1;
        point[0][n - 1] = 1;
        if (m > 1 & n > 1) {
            for (int count = 2; count <= max; ) {
                position = (position + 1) % 4;
                x += towards[position][0];
                y += towards[position][1];
                for (int j = 0; j <= 4; j++) {
                    if (x < 0 || x >= m || y < 0 || y >= n) {
                        x -= towards[position][0];
                        y -= towards[position][1];
                        position = (position + 1) % 4;
                        x += towards[position][0];
                        y += towards[position][1];
                    } else if (point[x][y] != 0) {
                        x -= towards[position][0];
                        y -= towards[position][1];
                        position = (position + 1) % 4;
                        x += towards[position][0];
                        y += towards[position][1];
                    }
                }
                point[x][y] = count;
                if (count == max) {
                    break;
                }
                count++;
                position = (position + 1) % 4;
                if (towards[position] == towards[1] || towards[position] == towards[3]) {
                    x += towards[position][0];
                    y += towards[position][1];
                    if (x < 0 || x >= m || y < 0 || y >= n) {
                        x -= towards[position][0];
                        y -= towards[position][1];
                        position = (position + 2) % 4;
                        x += towards[position][0];
                        y += towards[position][1];
                    }
                    while (true) {
                        point[x][y] = count;
                        count++;
                        x += towards[position][0];
                        y += towards[position][1];
                        if (x < 0 || x >= m || y < 0 || y >= n) {
                            x -= towards[position][0];
                            y -= towards[position][1];
                            break;
                        }
                    }
                }
            }
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.printf("%3d", point[i][j]);
                }
                System.out.println();
            }
        } else if (m == 1) {
            for (int i = n - 1; i >= 0; i--) {
                point[0][i] = i + 1;
                System.out.printf("%3d", point[0][i]);
            }
        } else if (n == 1) {
            for (int i = 0; i < m; i++) {
                point[i][0] = i + 1;
                System.out.printf("%3d", point[i][0]);
                System.out.println();
            }
        }
        in.close();
    }
}
