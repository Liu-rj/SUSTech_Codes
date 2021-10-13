import java.util.Scanner;

public class Lab5exer1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter how many numbers:");
        int length = in.nextInt();
        System.out.println();
        int[] array = new int[length];
        System.out.println("Enter " + length + " numbers:");
        int sum = 0;
        for (int i = 0; i < length; i++) {
            array[i] = in.nextInt();
            sum += array[i];
        }
        System.out.println("average="+(double)sum/length);
        int num = 0;
        for (int i = 0; i < length; i++) {
            for (int j = i +1; j < length; j++) {
                if ((array[i] + array[j]) / (double) 2 > (double)sum / length) {
                    num += 1;
                }
            }
        }
        System.out.println("The number of pairs of integer is " + num);
        long current1 = System.currentTimeMillis();
        /* your algorithm */
        long current2 = System.currentTimeMillis();
        System.out.printf("your program using %.3f second", (current2 - current1) / 1000.0d);
    }
}
