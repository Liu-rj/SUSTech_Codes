import java.util.Scanner;

public class Exer9 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("input :");
        String str1 = input.next();
        String str2 = input.next();
        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) <= '9') {
                System.out.print(str1.charAt(i));
            }
        }
        for (int j = 0; j < str2.length(); j++) {
            if (str2.charAt(j) <= '9') {
                System.out.print(str2.charAt(j));
            }
        }
        input.close();
    }
}
