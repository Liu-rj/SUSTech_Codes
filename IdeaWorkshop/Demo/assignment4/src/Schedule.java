import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Schedule {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        int busNum = sc.nextInt();
        if (busNum <= 0) {
            System.out.print("Bus Number is negative or zero, error!");
            return;
        }
        String list = sc.next();
        String check[] = list.split("_");
        if (check.length == 1) {
            try {
                format.setLenient(false);
                format.parse(check[0]);
            } catch (ParseException e) {
                System.out.print("Time Table error!");
                return;
            }
        }else {
            for (int i = 1; i < check.length; i++) {
                try {
                    format.setLenient(false);
                    format.parse(check[i - 1]);
                    format.parse(check[i]);
                    if (sort(check[i - 1]) > sort(check[i])) {
                        System.out.println("Time Table error!");
                        return;
                    }
                } catch (ParseException e) {
                    System.out.print("Time Table error!");
                    return;
                }
            }
        }
        int[] record = new int[check.length];
        for (int i = 0; i < check.length - 1; i++) {
            record[i] = 1;
            for (int j = i + 1; j < check.length; j++) {
                if (check[i].equals(check[j])) {
                    record[i]++;
                }
            }
        }
        int max = record[0];
        for (int i = 1; i < record.length; i++) {
            if (record[i] > max) {
                max = record[i];
            }
        }
        if (max > busNum) {
            System.out.print("Dispatching failed!");
            return;
        }
        StringBuilder[] schedule = Schedule(list, busNum);
        if (schedule != null) {
            for (StringBuilder s : schedule) {
                System.out.println(s.toString());
            }
        }
    }

    public static StringBuilder[] Schedule(String timelist, int busNum) {
        String list[] = timelist.split("_");
        int minimum;
        if (busNum <= list.length) {
            minimum = busNum;
        } else {
            minimum = list.length;
        }
        StringBuilder[] Schedule = new StringBuilder[minimum];
        int count = 0;
        for (int i = 0; i < list.length; i++) {
            if (i < busNum) {
                Schedule[count] = new StringBuilder(list[i]);
                count = (count + 1) % minimum;
            } else {
                Schedule[count].append("_" + list[i]);
                count = (count + 1) % minimum;
            }
        }
        return Schedule;
    }

    private static int sort(String s) {
        int time = 0;
        int i1 = Integer.parseInt(s.substring(0, 2));
        int i2 = Integer.parseInt(s.substring(3, 5));
        time = i1 * 60 + i2;
        return time;
    }
}
