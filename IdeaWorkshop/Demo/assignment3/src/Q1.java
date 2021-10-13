import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s1 = in.nextLine();
        String s2 = in.nextLine();
        String s3 = s1.toUpperCase();
        String s4 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String s5 = s4.toLowerCase();
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();
        char[] chars3 = s3.toCharArray();
        char[] chars4 = s4.toCharArray();
        char[] chars5 = s5.toCharArray();
        for (int i = 0; i < chars2.length; i++) {
            if ((chars2[i] >= 'A' & chars2[i] <= 'Z') || (chars2[i] >= 'a' &chars2[i] <= 'z')) {
                for (int j = 0; j < chars1.length; j++) {
                    if (chars2[i] == chars5[j]) {
                        System.out.print(chars1[j]);
                    } else if (chars2[i] == chars4[j]) {
                        System.out.print(chars3[j]);
                    }
                }
            }else {
                System.out.print(chars2[i]);
            }
        }
    }
}
