import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n;
        n = input.nextInt();
        char[][] chessBoard = new char[n][n];
        int[][] result = new int[201][201];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String str = input.next();
                chessBoard[i][j] = str.charAt(0);
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = 0;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (chessBoard[i][j] == '*' && i == 0 && j == 0) {
                    result[i][j] = 9;
                    result[i + 1][j]++;
                    result[i][j + 1]++;
                    result[i+1][j+1]++;
                }
                if (chessBoard[i][j] == '*' && i != 0 && i != n - 1 && j == 0) {
                    result[i][j] = 9;
                    result[i + 1][j]++;
                    result[i][j + 1]++;
                    result[i - 1][j]++;
                    result[i+1][j+1]++;
                    result[i-1][j+1]++;
                }
                if (chessBoard[i][j] == '*' && i == 0 && j != n - 1 && j != 0) {
                    result[i][j] = 9;
                    result[i + 1][j]++;
                    result[i][j + 1]++;
                    result[i][j - 1]++;
                    result[i+1][j+1]++;
                    result[i+1][j-1]++;
                }
                if (chessBoard[i][j] == '*' && i != 0 && i != n - 1 && j != 0 && j != n - 1) {
                    result[i][j] = 9;
                    result[i + 1][j]++;
                    result[i][j + 1]++;
                    result[i - 1][j]++;
                    result[i][j - 1]++;
                    result[i+1][j+1]++;
                    result[i-1][j-1]++;
                    result[i-1][j+1]++;
                    result[i+1][j-1]++;
                }
                if (chessBoard[i][j] == '*' && i == n - 1 && j == n - 1) {
                    result[i][j] = 9;
                    result[i][j - 1]++;
                    result[i - 1][j]++;
                    result[i-1][j-1]++;
                }
                if (chessBoard[i][j] == '*' && i == 0 && j == n - 1) {
                    result[i][j] = 9;
                    result[i + 1][j]++;
                    result[i][j - 1]++;
                    result[i+1][j-1]++;
                }
                if (chessBoard[i][j] == '*' && i == n - 1 && j == 0) {
                    result[i][j] = 9;
                    result[i][j + 1]++;
                    result[i - 1][j]++;
                    result[i-1][j+1]++;
                }
            }
        }
        for (int i = n; i >=0; --i) {
            for (int j = n; j >=0; --j) {
                result[i+1][j+1]=result[i][j];
            }
        }
        char[][] r=new char[201][201];
        for (int i = 1; i <n+1 ; i++) {
            for (int j = 1; j <n+1; j++) {
                if (result[i][j]>8) {
                    r[i][j] = 'F';
                }
                if(result[i][j]<=8){
                    r[i][j]= (char) (result[i][j]+'0');
                }
            }
        }
        for (int i = 1; i < n+1; i++) {
            for (int j = 1; j < n+1; j++) {
                System.out.print(r[i][j]+" ");
            }System.out.print("\n");
        }
    }
}