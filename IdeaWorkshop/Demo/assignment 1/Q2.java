import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int bc = in.nextInt();
        int sc = in.nextInt();
        if (sc % bc == 0){
            System.out.print("Yes");
        }else{
            System.out.print("No");
        }
        in.close();
    }
}
