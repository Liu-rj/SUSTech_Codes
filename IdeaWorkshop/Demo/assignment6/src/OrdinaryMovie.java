public class OrdinaryMovie extends Movie {

    @Override
    public double purchase(int arg) {
        if (this.ticketsLeft == 0) {
            return 0;
        } else {
            if (arg <= this.ticketsLeft) {
                this.income += this.getPrice() * arg;
                ticketsLeft -= arg;
                return this.getPrice() * arg;
            } else {
                this.income += this.getPrice() * this.ticketsLeft;
                double money = this.getPrice() * this.ticketsLeft;
                ticketsLeft = 0;
                return money;
            }
        }
    }

    public String toString() {
        return super.toString() + " " + "OrdinaryMovie";
    }

    public OrdinaryMovie(String name, int runtime, int hallNumber, double price, Time startTime) {
        this.setName(name);
        this.setStartTime(startTime);
        this.setPrice(price);
        this.setRuntime(runtime);
        this.setHallNumber(hallNumber);
    }
}
