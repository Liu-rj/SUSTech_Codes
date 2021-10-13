import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.next();
        char[] chars = s.toCharArray();
        int[] frequency = new int[10];
        for (int i = 0; i < chars.length; i++) {
            frequency[Integer.parseInt(chars[i]+"")]++;
        }
        for (int i = 0; i < frequency.length; i++) {
            System.out.print(frequency[i]+" ");
        }
    }
}
