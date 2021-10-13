import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long a = in.nextInt();
        long m = in.nextInt();
        int n = 1000000;
        long[][] arr = new long[n][2];
        long code = a;
        for (int i = 0; i < n; i++) {
            arr[i][0] = in.nextLong();
            if (arr[i][0] == -1){
                break;
            }
            arr[i][1] = in.nextLong();
            a = (arr[i][0]*a + arr[i][1])%m;
            code ^= a;
        }
        System.out.println(code);
    }
}
