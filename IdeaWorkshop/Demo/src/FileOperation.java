import java.util.Scanner;

public class FileOperation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = new String();
        while (!scanner.hasNext(";")) {
            str += scanner.nextLine();
            str += " ";
        }
        //String out = str.replaceAll("\n", "a");
        System.out.print(str);
    }
}
