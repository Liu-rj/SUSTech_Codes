import java.util.Scanner;

public class Lab3_A {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        int s, n, m;
        for (int i = 0; i < T; i++) {
            s = scanner.nextInt();
            n = scanner.nextInt();
            m = scanner.nextInt();
            int temp = Math.max(n , m);
            if (m == temp) {
                m = n;
                n = temp;
            }
            if ((s & 1) == 1 || (s / 2) % gcd(n, m) != 0) {
                System.out.println("impossible");
                continue;
            }
            s /= 2;
            if (m == n) {
                System.out.println(1);
            } else {
                System.out.println(count(s, n, m));
            }
        }
        scanner.close();
    }

    public static int count(int s, int n, int m) {
        int count = 1, sum = n;
        while (sum != s) {
            if (sum - m > 0) {
                sum -= m;
                count += 2;
            } else {
                sum = n - (m - sum);
                count += 4;
            }
        }
        return count;
    }

    public static int gcd(int x, int y) {
        if (x == y) {
            return x;
        } else if (x < y) {
            return gcd(y, x);
        } else {
            if ((x & 1) == 0 && (y & 1) == 0) {
                return gcd(x >> 1, y >> 1) << 1;
            } else if ((x & 1) == 0 && (y & 1) == 1) {
                return gcd(x >> 1, y);
            } else if ((x & 1) == 1 && (y & 1) == 0) {
                return gcd(x, y >> 1);
            } else {
                return gcd(y, x - y);
            }
        }
    }
}
