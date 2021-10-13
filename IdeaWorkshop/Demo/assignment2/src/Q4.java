import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        long[] arr = new long[n];
        long result = 0;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = in.nextLong();
        }
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (arr[i]>=arr[j]){
                    result += arr[i]*arr[j];
                }
            }
        }
        System.out.print(result);
    }
}
