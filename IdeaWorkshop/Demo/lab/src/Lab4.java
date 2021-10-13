import java.util.Scanner;

public class Lab4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int total = 0;
        int credit;
        double score = 0;
        double z;
        int i;
        do {
            credit = sc.nextInt();
            z = sc.nextInt();
            if (z >= 90) {
                i = 4;
            } else if (z >= 80) {
                i = 3;
            } else if (z >= 70) {
                i = 2;
            } else if (z >= 60) {
                i = 1;
            } else {
                i = 0;
            }
            score += i * credit;
            total += credit;
        } while (z != -1 & credit != -1);
        double GPA = (score - i * credit) / (total - credit);
        System.out.println("GPA:" + GPA);
    }
}
