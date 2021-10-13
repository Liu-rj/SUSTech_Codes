import java.util.Scanner;

public class PhoneRecommend {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("user's budget is:");
        int budget = in.nextInt();
        System.out.println("Your phone recommend is:");
        PhoneModel[] phoneModels = PhoneModel.values();
        for (PhoneModel phone : phoneModels) {
            if (phone.getPrice() <= budget){
                System.out.println(phone);
            }
        }
    }
}
