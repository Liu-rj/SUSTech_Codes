import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String ISBN = in.next();
        int j = 0;
        if (ISBN.charAt(0) < '0' | ISBN.charAt(0) > '9'
                | ISBN.charAt(1) < '0' | ISBN.charAt(1) > '9'
                | ISBN.charAt(2) < '0' | ISBN.charAt(2) > '9'
                | ISBN.charAt(3) < '0' | ISBN.charAt(3) > '9'
                | ISBN.charAt(4) < '0' | ISBN.charAt(4) > '9'
                | ISBN.charAt(5) < '0' | ISBN.charAt(5) > '9'
                | ISBN.charAt(6) < '0' | ISBN.charAt(6) > '9'
                | ISBN.charAt(7) < '0' | ISBN.charAt(7) > '9'
                | ISBN.charAt(8) < '0' | ISBN.charAt(8) > '9'
                | ((ISBN.charAt(9) < '0' | ISBN.charAt(9) > '9') & ISBN.charAt(9) != 'X')) {
            System.out.print("No");
        } else {
            for (int i = 0; i < ISBN.length() - 1; i++) {
                j += (i + 1) * Integer.parseInt(ISBN.charAt(i) + "");
            }
            if (ISBN.charAt(9) == 'X') {
                if (j % 11 == 10) {
                    System.out.print("Yes");
                } else {
                    System.out.print("No");
                }
            } else if ((j % 11) == Integer.parseInt(ISBN.charAt(9) + "")) {
                System.out.print("Yes");
            } else {
                System.out.print("No");
            }
        }
        in.close();
    }
}
