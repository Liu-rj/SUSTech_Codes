public class ThreeDMovie extends Movie{
    private final int GLASS_PRICE = 20;

    @Override
    public double purchase(int arg) {
        if (this.ticketsLeft == 0) {
            return 0;
        } else {
            if (arg == 0) {
                this.income += this.getPrice();
                ticketsLeft --;
                return this.getPrice();
            } else {
                this.income += this.getPrice() + GLASS_PRICE;
                ticketsLeft --;
                return this.getPrice() + GLASS_PRICE;
            }
        }
    }

    public String toString(){
        return super.toString() + " " + "ThreeDMovie";
    }

    public ThreeDMovie(String name, int runtime, int hallNumber, double price, Time startTime) {
        this.setName(name);
        this.setStartTime(startTime);
        this.setPrice(price);
        this.setRuntime(runtime);
        this.setHallNumber(hallNumber);
    }
}
