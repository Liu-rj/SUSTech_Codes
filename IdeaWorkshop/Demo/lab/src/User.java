import java.util.Scanner;

public class User {
    private String account;
    private String password;
    private double money;

    public User() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void introduce() {
        System.out.printf("%s's acount has a balance of %.2f dollar\n", account, money);
    }

    public void expense(double value, Scanner in) {
        if (value > money) {
            System.out.printf("Plan to expense %.2f dollar but no sufficient funds\n", value);
        } else {
            System.out.printf("Plan to expense %.2f dollar\nPlease input your password:\n", value);
            String yourPassword = in.next();
            if (yourPassword.equals(password)) {
                money -= value;
                System.out.printf("Expense %.2f dollar and balance %.2f dollar\n", value, money);
            } else {
                System.out.println("Your password is invalid!");
            }
        }
    }

    public void income(double value) {
        money += value;
        System.out.printf("Got %.2f as income, balance is %.2f dollar\n", value, money);
    }
}
