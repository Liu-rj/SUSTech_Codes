import java.util.Scanner;

public class Lab8 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s1 = in.nextLine();
        String s2 = in.nextLine();
        int count = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.substring(i, s1.length()).startsWith(s2)) {
                System.out.println("Found at index: " + s1.indexOf(s2, i));
                count++;
            }
        }
        System.out.print("Total occurrences: " + count);
    }
}
/*
abcd  bcde cdef
bc
 */