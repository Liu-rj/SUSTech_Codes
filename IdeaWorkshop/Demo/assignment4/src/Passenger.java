public class Passenger {
    private static int cnt;
    private int id;
    private float balance;
    private String boardingStation;
    private VehicleRun vr;
    private String offStation;

    public static int getCnt() {
        return cnt;
    }

    public static void setCnt(int cnt) {
        Passenger.cnt = cnt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getBoardingStation() {
        return boardingStation;
    }

    public void setBoardingStation(String boardingStation) {
        this.boardingStation = boardingStation;
    }

    public VehicleRun getVr() {
        return vr;
    }

    public void setVr(VehicleRun vr) {
        this.vr = vr;
    }

    public String getOffStation() {
        return offStation;
    }

    public void setOffStation(String offStation) {
        this.offStation = offStation;
    }

    public Passenger() {
        this.id = ++cnt;
    }

    public Passenger(float money) {
        this.balance = money;
        this.id = ++cnt;
    }

    public boolean getOn(VehicleRun vr, String station) {
        if (vr.getBusLine().contains(station) & balance >= 0 & (boardingStation == null || boardingStation.isEmpty())) {
            if (vr.isThroughTicket()) {
                balance -= vr.getFee();
                boardingStation = station;
                this.vr = vr;
                return vr.getFare(vr.getFee());
            } else {
                boardingStation = station;
                this.vr = vr;
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean getOff(VehicleRun vr, String station) {
        if (vr.getBusLine().contains(station) & !(boardingStation == null || boardingStation.isEmpty()) &
                vr.getBusLine().indexOf(station) >= vr.getBusLine().indexOf(boardingStation) & this.vr == vr) {
            if (vr.isThroughTicket()) {
                boardingStation = null;
            } else {
                balance -= vr.getFee() * (vr.getBusLine().indexOf(station) - vr.getBusLine().indexOf(boardingStation));
                vr.getFare(vr.getFee() * (vr.getBusLine().indexOf(station) - vr.getBusLine().indexOf(boardingStation)));
                boardingStation = null;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean refill(float money) {
        if (money > 0) {
            balance += money;
            return true;
        } else {
            return false;
        }
    }
}
