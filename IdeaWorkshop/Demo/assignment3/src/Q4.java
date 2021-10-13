import java.util.Scanner;

public class Q4 {
    public static int[] flatSubMatrix(int[][] matrix, int x1, int x2, int y1, int y2){
        int[] A = new int[(x2 - x1 + 1)*(y2 - y1 + 1)];
        int count = 0;
        for (int i = x1; i <=x2; i++) {
            for (int j = y1; j <= y2; j++) {
                A[count] = matrix[i][j];
                count++;
            }
        }
        return A;
    }

    public static int[] Sort(int[] array){
        for (int i = 1; i < array.length ; i++) {
            for (int j = 0 ; j < array.length - i; j++) {
                if (array[j] > array[j+1]){
                    int mid = array[j+1];
                    array[j +1] = array[j];
                    array[j] = mid;
                }
            }
        }
        return array;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int m = in.nextInt();
        int n = in.nextInt();
        int[][] matrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j]  = in.nextInt();
            }
        }
        int x1 = in.nextInt();
        int x2 = in.nextInt();
        int y1 = in.nextInt();
        int y2 = in.nextInt();
        in.close();
        int[] A = flatSubMatrix(matrix,x1,x2,y1,y2);
        int[] a = Sort(A);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
            System.out.print(" ");
        }
    }
}
