import java.util.Arrays;
import java.util.Scanner;

public class Lab5_A {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // number of offenders
        int m = in.nextInt(); // number of rooms
        int k = in.nextInt(); // number of people one room can hold
        int t = in.nextInt(); // time to arrest
        int[] people = new int[n];
        int[] house = new int[m];
        for (int i = 0; i < n; i++) {
            people[i] = in.nextInt();
        }
        for (int i = 0; i < m; i++) {
            house[i] = in.nextInt();
        }
        Arrays.sort(people);
        Arrays.sort(house);
        int count = 0, pindex = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < k;) {
                if (pindex == n) {
                    System.out.println(count);
                    System.exit(0);
                } else {
                    if (Math.abs(house[i] - people[pindex]) <= t) {
                        count++;
                        pindex++;
                        j++;
                    } else if (people[pindex] <= house[i]) {
                        pindex++;
                    } else {
                        break;
                    }
                }
            }
        }
        System.out.println(count);
        in.close();
    }
}