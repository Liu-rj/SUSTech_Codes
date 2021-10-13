import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] arabic = transform(in);
        int num = arabic[arabic.length - 1];
        if (arabic.length > 1) {
            for (int i = arabic.length - 2; i >= 0; i--) {
                if (arabic[i] >= arabic[i + 1]) {
                    num += arabic[i];
                } else {
                    num -= arabic[i];
                }
            }
        }
        System.out.print(num);
    }

    public static int[] transform(Scanner in) {
        String roman = in.next();
        char[] chars = roman.toCharArray();
        int[] arabic = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
                case 'I':
                    arabic[i] = 1;
                    break;
                case 'V':
                    arabic[i] = 5;
                    break;
                case 'X':
                    arabic[i] = 10;
                    break;
                case 'L':
                    arabic[i] = 50;
                    break;
                case 'C':
                    arabic[i] = 100;
                    break;
                case 'D':
                    arabic[i] = 500;
                    break;
                case 'M':
                    arabic[i] = 1000;
                    break;
            }
        }
        return arabic;
    }
}
