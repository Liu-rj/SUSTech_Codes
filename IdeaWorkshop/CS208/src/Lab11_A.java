import java.util.Scanner;

public class Lab11_A {
    static long[][] refer = new long[60][3];

    public static void main(String[] args) {
        refer[0][0] = 1;
        refer[0][1] = 0;
        refer[0][2] = 0;
        for (int i = 1; i < 60; i++) {
            refer[i][0] = refer[i - 1][0] + refer[i - 1][2];
            refer[i][1] = refer[i - 1][1] + refer[i - 1][0];
            refer[i][2] = refer[i - 1][2] + refer[i - 1][1];
        }
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        long len;
        for (int i = 0; i < T; i++) {
            len = in.nextLong();
            long[] result = countLRN(len);
            System.out.println(result[0] + " " + result[1] + " " + result[2]);
        }
        in.close();
    }

    public static long[] countLRN(long len) {
        if (len == 0) {
            return new long[]{0, 0, 0};
        }
        int index = (int) (Math.log(len) / Math.log(2));
        long[] a = refer[index];
        long[] b = countLRN(len - (long) Math.pow(2, index));
        return new long[]{a[0] + b[2], a[1] + b[0], a[2] + b[1]};
    }
}
