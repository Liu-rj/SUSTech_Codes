import com.sun.corba.se.impl.interceptors.PICurrent;

import java.util.Scanner;

public class MyTriangle {
    public static void introduce() {
        System.out.println("I am a triangle.");
    }

    public static void print(int height) {
        for (int i = 0; i < height; i++) {
            for (int j = height - 1; j > i; j--) {
                System.out.print(" ");
            }
            System.out.print("*");
            for (int j = 0; j < i; j++) {
                System.out.print("**");
            }
            System.out.println();
        }
    }

    public static double area(double a, double b, double c) {
        double q = 0.5 * (a + b + c);
        return Math.sqrt(q * (q - a) * (q - b) * (q - c));
    }

    public static double perimeter(double a, double b, double c) {
        return a + b + c;
    }

    public static boolean isValid(double a, double b, double c) {
        if (a + b > c && a + c > b && b + c > a) {
            return true;
        } else {
            return false;
        }
    }

    public static double area(double bottom, double height){
        return 0.5*bottom*height;
    }

    public static double area(double a, double b, int angleOfAandB){
        return 0.5*a*b*Math.sin(Math.PI * angleOfAandB/180.0);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Please input three numbers:");
            double a = in.nextDouble();
            if (a == -1) {
                System.out.print("Bye~");
                break;
            }
            double b = in.nextDouble();
            double c = in.nextDouble();
            if (isValid(a, b, c)) {
                System.out.printf("The area is %.3f\n", area(a, b, c));
                System.out.printf("The perimeter is %.3f\n", perimeter(a, b, c));
            } else {
                System.out.println("The input is invalid.");
            }
        }
    }
}

