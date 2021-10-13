import java.util.ArrayList;
import java.util.Scanner;

public class softOpen {
    public static ArrayList<Food> generateMenu() {
        Food pizza1 = new Food();
        Food pizza2 = new Food();
        Food Friedrice = new Food();
        Food Noodles = new Food();
        pizza1.setId(1);
        pizza1.setName("pizza");
        pizza1.setType("Seafood");
        pizza1.setSize(11);
        pizza1.setPrice(12);
        pizza2.setId(2);
        pizza2.setName("pizza");
        pizza2.setType("Beef");
        pizza2.setSize(9);
        pizza2.setPrice(10);
        Friedrice.setId(3);
        Friedrice.setName("fried rice");
        Friedrice.setType("Seafood");
        Friedrice.setSize(5);
        Friedrice.setPrice(12);
        Noodles.setId(4);
        Noodles.setName("noodles");
        Noodles.setType("Beef");
        Noodles.setSize(6);
        Noodles.setPrice(14);
        ArrayList<Food> foodlist = new ArrayList<>();
        foodlist.add(pizza1);
        foodlist.add(pizza2);
        foodlist.add(Friedrice);
        foodlist.add(Noodles);
        return foodlist;
    }

    public static void getMenu(ArrayList<Food> foodlist) {
        System.out.println("-------------------welcome,this is Start of the Menu-------------------");
        for (int i = 0; i < foodlist.size(); i++) {
            foodlist.get(i).showInformation();
        }
        System.out.println("-------------------welcome,this is  End  of the Menu-------------------");
    }

    public static User generateUser(Scanner in) {
        User user = new User();
        System.out.print("Generate a user, please input name:");
        user.setAccount(in.next());
        System.out.print("balance($):");
        user.setMoney(in.nextInt());
        return user;
    }

    public static void userConsume(ArrayList<Food> foodlist, User user, Scanner in) {
        int cost = 0;
        getMenu(foodlist);
        System.out.println("please input the foodID and the number you want, to exit input 0 as foodID");
        while (true){
            System.out.print("food id(input 0 to end select):");
            int i = in.nextInt();
            if (i == 0){
                System.out.println("select end");
                break;
            }
            System.out.print("number of this food:");
            int count = in.nextInt();
            cost += foodlist.get(i-1).getPrice() * count;
        }
        user.setPassword("123456");
        user.expense(cost,in);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Food> foodList = generateMenu(); //generate a Menu
        User user = generateUser(in); //generate a user
        user.introduce(); //show the account of the user
        userConsume(foodList, user, in); //user consume
        user.introduce(); //show the account of the user
        in.close();
    }
}
