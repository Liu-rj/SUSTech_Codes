import java.util.Scanner;

public class Lab10_A {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        long a, b;
        for (int i = 0; i < n; i++) {
            a = in.nextLong();
            b = in.nextLong();
            System.out.println(numberOfL(b) - numberOfL(a - 1));
        }
    }

    public static long numberOfL(long pos) {
        if (pos == 0) {
            return 0;
        }
        if (pos == 1) {
            return 1;
        }
        long len = pos + 1;
        int n = 0;
        while (len > 1) {
            n++;
            len = len >> 1;
        }
        if (Math.pow(2, n) == pos + 1) {
            return (long) Math.pow(2, n - 1);
        } else {
            long nfl = 1; // number of left part L
            for (int i = 0; i < n - 1; i++) {
                nfl = nfl << 1;
            }
            long nf = nfl << 1; // length of left part + mid part
            long interval = pos - nf; // length of short interval
            return nfl + 1 + interval - (numberOfL(nf - 1) - numberOfL(nf - 1 - interval));
        }
    }
}
