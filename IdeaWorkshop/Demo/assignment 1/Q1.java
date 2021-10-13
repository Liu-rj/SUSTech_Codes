import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String id = in.next();
        double price = in.nextDouble();
        int number = in.nextInt();
        if (id.substring(0, 3).equals("2020")) {
            id = "2020" + id.substring(4, 8);
        }
        double total = (number * price);
        long t = Math.round(total);
        System.out.printf("%-9s%d",id,t);
    }
}
