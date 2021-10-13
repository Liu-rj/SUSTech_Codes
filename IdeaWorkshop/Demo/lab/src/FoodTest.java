import java.util.ArrayList;

public class FoodTest {
    public static void main(String[] args) {
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
        ArrayList<Food> foodlist = new ArrayList<Food>();
        foodlist.add(pizza1);
        foodlist.add(pizza2);
        foodlist.add(Friedrice);
        foodlist.add(Noodles);
        pizza1.showstart();
        for (int i = 0; i < foodlist.size(); i++) {
            foodlist.get(i).showInformation();
        }
        pizza1.showend();
    }
}
