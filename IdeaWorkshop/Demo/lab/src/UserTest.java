import java.util.Scanner;

public class UserTest {
    public static void main(String[] args) {
        User users = new User();
        Scanner in = new Scanner(System.in);
        users.setAccount("Lucy");
        users.setPassword("123456");
        users.setMoney(1000);
        users.introduce();
        users.expense(2000,in);
        users.expense(500,in);
        users.income(1000);
        users.introduce();
        in.close();
    }
}
