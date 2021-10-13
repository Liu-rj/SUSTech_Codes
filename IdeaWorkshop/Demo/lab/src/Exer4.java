import java.util.Scanner;

public class Exer4 {
    public static void main(String[] args) {
        String name;
        int age;
        double weight;
        char grade;
        Scanner input = new Scanner(System.in);
        name = input.next();
        age = input.nextInt();
        weight = input.nextDouble();
        grade = input.next().charAt(0);
        System.out.printf("name is %s, age is %d, weight is %.3f, grade = %c",name,age,weight,grade);
    }
}
