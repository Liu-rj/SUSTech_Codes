import java.util.Scanner;

public class GrammarCorrection {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        String[] splitInput = input.split("\\. ");
        splitInput[0] = splitInput[0].replace(splitInput[0].charAt(0), Character.toUpperCase(splitInput[0].charAt(0)));
        for (int i = 1; i < splitInput.length; i++) {
            if (Character.isLowerCase(splitInput[i].charAt(0)) & Character.isLowerCase(splitInput[i-1].charAt(splitInput[i-1].lastIndexOf(" ") + 1))) {
                splitInput[i] = splitInput[i].replace(splitInput[i].charAt(0), Character.toUpperCase(splitInput[i].charAt(0)));
            }
        }
        for (String str : splitInput) {
            System.out.print(str + ". ");
        }
    }
}
