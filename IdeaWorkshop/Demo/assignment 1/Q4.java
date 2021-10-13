import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str1 = in.next();
        String str2 = in.next();
        int a = Integer.parseInt(str1.charAt(0) + "");
        int b = Integer.parseInt(str1.charAt(1) + "");
        int c = Integer.parseInt(str2.charAt(0) + "");
        int d = Integer.parseInt(str2.charAt(1) + "");
        if (a == 0) {
            if (b == 2 & Integer.parseInt(str2) > 0 & Integer.parseInt(str2) <= 28) {
                System.out.print(0);
            } else if ((b == 1 | b == 3 | b == 5 | b == 7 | b == 8) & Integer.parseInt(str2) > 0 & Integer.parseInt(str2) <= 31) {
                System.out.print(0);
            } else if ((b == 4 | b == 6 | b == 9) & Integer.parseInt(str2) > 0 & Integer.parseInt(str2) <= 30) {
                System.out.print(0);
            } else if (b == 2 & (Integer.parseInt(str2) <= 0 | Integer.parseInt(str2) > 28)) {
                System.out.print(1);
            } else if ((b == 1 | b == 3 | b == 5 | b == 7 | b == 8) & (Integer.parseInt(str2) <= 0 | Integer.parseInt(str2) > 31)) {
                System.out.print(1);
            } else if ((b == 4 | b == 6 | b == 9) & (Integer.parseInt(str2) <= 0 | Integer.parseInt(str2) > 30)) {
                System.out.print(1);
            } else if (b <= 0 | b > 9) {
                if (Integer.parseInt(str2) > 0 & Integer.parseInt(str2) <= 31) {
                    System.out.print(1);
                } else {
                    System.out.print(2);
                }
            }
        } else if (a == 1) {
            if ((b == 0 | b == 2) & Integer.parseInt(str2) > 0 & Integer.parseInt(str2) <= 31) {
                System.out.print(0);
            } else if (b == 1 & Integer.parseInt(str2) > 0 & Integer.parseInt(str2) <= 30) {
                System.out.print(0);
            } else if ((b == 0 | b == 2) & (Integer.parseInt(str2) <= 0 | Integer.parseInt(str2) > 31)) {
                System.out.print(1);
            } else if (Integer.parseInt(str2) <= 0 | Integer.parseInt(str2) > 31) {
                System.out.print(2);
            } else {
                System.out.print(1);
            }
        } else {
            if (b == 2 & Integer.parseInt(str2) > 0 & Integer.parseInt(str2) <= 31) {
                System.out.print(1);
            } else if ((b == 1 | b == 3 | b == 5 | b == 7 | b == 8) & Integer.parseInt(str2) > 0 & Integer.parseInt(str2) <= 31) {
                System.out.print(1);
            } else if ((b == 4 | b == 6 | b == 9) & Integer.parseInt(str2) > 0 & Integer.parseInt(str2) <= 30) {
                System.out.print(1);
            } else if ((b == 1 |b==2| b == 3 | b == 5 | b == 7 | b == 8) & (Integer.parseInt(str2) <= 0 | Integer.parseInt(str2) > 31)) {
                System.out.print(2);
            } else if ((b == 4 | b == 6 | b == 9) & (Integer.parseInt(str2) <= 0 | Integer.parseInt(str2) > 30)) {
                System.out.print(2);
            }
        }
    }
}
