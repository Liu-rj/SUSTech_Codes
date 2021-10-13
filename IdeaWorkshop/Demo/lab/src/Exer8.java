import java.util.Scanner;

public class Exer8 {
    public static void main(String[] args) {
       Scanner input = new Scanner(System.in);
        int height;
        String character;
        System.out.print("Please input the height:" );
        height = input.nextInt();
        System.out.print("Please input the character:");
        character = input.next();
        for(int i=1;i<=height;i++){
            for(int j=1;j<=height-i;j++){
                System.out.print(" ");
            }
            for(int j=1;j<=2*i-1;j++){
                System.out.print(character);
            }
            System.out.println();
        }
    }
}
