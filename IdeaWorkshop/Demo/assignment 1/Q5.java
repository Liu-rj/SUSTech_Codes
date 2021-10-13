import java.util.Scanner;

public class Q5 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str1 = in.next();
        String str2 = in.next();
        int no1 = Integer.parseInt(str2.substring(0, 4)) - Integer.parseInt(str1.substring(0, 4));
        int no4 = 0;
        int no5 = 0;
        double no6;
        int no7 = 0;
        if (Integer.parseInt(str1.substring(4, 6)) == 1) {
            no4 = Integer.parseInt(str1.substring(6, 8));
        } else if (Integer.parseInt(str1.substring(4, 6)) == 2) {
            no4 = 31 + Integer.parseInt(str1.substring(6, 8));
        } else if (Integer.parseInt(str1.substring(4, 6)) == 3) {
            no4 = 28 + 31 + Integer.parseInt(str1.substring(6, 8));
        } else if (Integer.parseInt(str1.substring(4, 6)) == 4) {
            no4 = 31 + 31 + 28 + Integer.parseInt(str1.substring(6, 8));
        } else if (Integer.parseInt(str1.substring(4, 6)) == 5) {
            no4 = 30 + 31 + 31 + 28 + Integer.parseInt(str1.substring(6, 8));
        } else if (Integer.parseInt(str1.substring(4, 6)) == 6) {
            no4 = 31 + 30 + 31 + 31 + 28 + Integer.parseInt(str1.substring(6, 8));
        } else if (Integer.parseInt(str1.substring(4, 6)) == 7) {
            no4 = 30 + 31 + 30 + 31 + 31 + 28 + Integer.parseInt(str1.substring(6, 8));
        } else if (Integer.parseInt(str1.substring(4, 6)) == 8) {
            no4 = 31 + 30 + 31 + 30 + 31 + 31 + 28 + Integer.parseInt(str1.substring(6, 8));
        } else if (Integer.parseInt(str1.substring(4, 6)) == 9) {
            no4 = 31 + 31 + 30 + 31 + 30 + 31 + 31 + 28 + Integer.parseInt(str1.substring(6, 8));
        } else if (Integer.parseInt(str1.substring(4, 6)) == 10) {
            no4 = 30 + 31 + 31 + 30 + 31 + 30 + 31 + 31 + 28 + Integer.parseInt(str1.substring(6, 8));
        } else if (Integer.parseInt(str1.substring(4, 6)) == 11) {
            no4 = 31 + 30 + 31 + 31 + 30 + 31 + 30 + 31 + 31 + 28 + Integer.parseInt(str1.substring(6, 8));
        } else if (Integer.parseInt(str1.substring(4, 6)) == 12) {
            no4 = 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30 + 31 + 31 + 28 + Integer.parseInt(str1.substring(6, 8));
        }
        no7 = 365 - no4;
        if (((Integer.parseInt(str1.substring(0, 4)) % 4 == 0 & Integer.parseInt(str1.substring(0, 4)) % 100 != 0)
                || Integer.parseInt(str1.substring(0, 4)) % 400 == 0) & Integer.parseInt(str1.substring(4, 6)) >= 3) {
            no4 += 1;
            no7 = 366 - no4;
        }
        if (Integer.parseInt(str2.substring(4, 6)) == 1) {
            no5 = Integer.parseInt(str2.substring(6, 8));
        } else if (Integer.parseInt(str2.substring(4, 6)) == 2) {
            no5 = 31 + Integer.parseInt(str2.substring(6, 8));
        } else if (Integer.parseInt(str2.substring(4, 6)) == 3) {
            no5 = 28 + 31 + Integer.parseInt(str2.substring(6, 8));
        } else if (Integer.parseInt(str2.substring(4, 6)) == 4) {
            no5 = 31 + 31 + 28 + Integer.parseInt(str2.substring(6, 8));
        } else if (Integer.parseInt(str2.substring(4, 6)) == 5) {
            no5 = 30 + 31 + 31 + 28 + Integer.parseInt(str2.substring(6, 8));
        } else if (Integer.parseInt(str2.substring(4, 6)) == 6) {
            no5 = 31 + 30 + 31 + 31 + 28 + Integer.parseInt(str2.substring(6, 8));
        } else if (Integer.parseInt(str2.substring(4, 6)) == 7) {
            no5 = 30 + 31 + 30 + 31 + 31 + 28 + Integer.parseInt(str2.substring(6, 8));
        } else if (Integer.parseInt(str2.substring(4, 6)) == 8) {
            no5 = 31 + 30 + 31 + 30 + 31 + 31 + 28 + Integer.parseInt(str2.substring(6, 8));
        } else if (Integer.parseInt(str2.substring(4, 6)) == 9) {
            no5 = 31 + 31 + 30 + 31 + 30 + 31 + 31 + 28 + Integer.parseInt(str2.substring(6, 8));
        } else if (Integer.parseInt(str2.substring(4, 6)) == 10) {
            no5 = 30 + 31 + 31 + 30 + 31 + 30 + 31 + 31 + 28 + Integer.parseInt(str2.substring(6, 8));
        } else if (Integer.parseInt(str2.substring(4, 6)) == 11) {
            no5 = 31 + 30 + 31 + 31 + 30 + 31 + 30 + 31 + 31 + 28 + Integer.parseInt(str2.substring(6, 8));
        } else if (Integer.parseInt(str2.substring(4, 6)) == 12) {
            no5 = 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30 + 31 + 31 + 28 + Integer.parseInt(str2.substring(6, 8));
        }
        if (((Integer.parseInt(str2.substring(0, 4)) % 4 == 0 & Integer.parseInt(str2.substring(0, 4)) % 100 != 0)
                || Integer.parseInt(str2.substring(0, 4)) % 400 == 0) & Integer.parseInt(str2.substring(4, 6)) >= 3) {
            no5 += 1;
        }
        if (no1 == 0) {
            if (no5 - no4 <= 30) {
                System.out.print("The return is successful!");
            } else if (no5 - no4 > 30 & no5 - no4 <= 60) {
                no6 = (no5 - no4 - 30) * 0.5;
                System.out.printf("You are late, please pay %.1f yuan!", no6);
            } else if (no5 - no4 > 60 & no5 - no4 <= 180) {
                no6 = (no5 - no4 - 60) * 0.7 + 15;
                System.out.printf("You are late, please pay %.1f yuan!", no6);
            } else if (no5 - no4 > 180) {
                no6 = 250.0;
                System.out.printf("You are late, please pay %.1f yuan!", no6);
            }
        } else if (no1 == 1 & no4 > no5) {
            if (no5 + no7 <= 30) {
                System.out.print("The return is successful!");
            } else if (no5 + no7 > 30 & no5 + no7 <= 60) {
                no6 = (no5 + no7 - 30) * 0.5;
                System.out.printf("You are late, please pay %.1f yuan!", no6);
            } else if (no5 + no7 > 60 & no5 + no7 <= 180) {
                no6 = (no5 + no7 - 60) * 0.7 + 15;
                System.out.printf("You are late, please pay %.1f yuan!", no6);
            } else if (no5 + no7 > 180) {
                no6 = 250.0;
                System.out.printf("You are late, please pay %.1f yuan!", no6);
            } else {
                System.out.print("You are late, please pay 250.0 yuan!");
            }
        }
    }
}
