import java.util.Scanner;

public class Q5 {
    public static boolean checkBlock(int[][][] mat) {
        int[] count = new int[9];
        int multiply = 1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    count[mat[i][j][k] - 1]++;
                }
            }
            for (int j = 0; j < 9; j++) {
                multiply *= count[j];
            }
            if (multiply != 1) {
                System.out.print("No");
                return true;
            }
            count = new int[9];
        }
        return false;
    }

    public static boolean checkRow(int[][][] mat) {
        int[] count = new int[9];
        int multiply = 1;
        for (int i = 0; i < 3; i++) {
            for (int n = 0; n < 3; n++) {
                for (int j = 3 * i; j < 3 * (i + 1); j++) {
                    for (int k = 0; k < 3; k++) {
                        count[mat[j][n][k] - 1]++;
                    }
                }
                for (int j = 0; j < 9; j++) {
                    multiply *= count[j];
                }
                if (multiply != 1) {
                    System.out.print("No");
                    return true;
                }
                count = new int[9];
            }
        }
        return false;
    }

    public static boolean checkColumn(int[][][] mat) {
        int[] count = new int[9];
        int multiply = 1;
        for (int i = 0; i < 3; i++) {
            for (int n = 0; n < 3; n++) {
                for (int j = i; j <= i + 6; j += 3) {
                    for (int l = 0; l < 3; l++) {
                        count[mat[j][l][n] - 1]++;
                    }
                }
                for (int j = 0; j < 9; j++) {
                    multiply *= count[j];
                }
                if (multiply != 1) {
                    System.out.print("No");
                    return true;
                }
                count = new int[9];
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[][][] mat = new int[9][3][3];
        //initialize mat
        for (int i = 0; i < 3; i++) {
            for (int n = 0; n < 3; n++) {
                for (int j = 3 * i; j < 3 * (i + 1); j++) {
                    for (int k = 0; k < 3; k++) {
                        mat[j][n][k] = in.nextInt();
                    }
                }
            }
        }
        //check block
        if (checkBlock(mat)) {
            return;
        }
        //check row
        if (checkRow(mat)) {
            return;
        }
        //check column
        if (checkColumn(mat)) {
            return;
        }
        System.out.print("Yes");
    }
}
/*
1 6 7 4 8 9 3 5 2
4 3 5 1 2 6 9 7 8
9 2 8 7 5 3 4 1 6
7 9 1 6 3 5 8 2 4
8 4 3 2 7 1 6 9 5
2 5 6 9 4 8 7 3 1
5 8 9 3 1 4 2 6 7
3 1 2 8 6 7 5 4 9
6 7 4 5 9 2 1 8 3
Yes
 */