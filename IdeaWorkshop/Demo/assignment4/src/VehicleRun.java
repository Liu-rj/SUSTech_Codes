import java.util.ArrayList;

public class VehicleRun {
    private String departureTime;
    private ArrayList<String> busLine;
    private boolean throughTicket;
    private float fee;
    private float income;
    private int[] pCntOn;
    private int[] pCntOff;

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public ArrayList<String> getBusLine() {
        return busLine;
    }

    public void setBusLine(ArrayList<String> busLine) {
        this.busLine = busLine;
    }

    public boolean isThroughTicket() {
        return throughTicket;
    }

    public void setThroughTicket(boolean throughTicket) {
        this.throughTicket = throughTicket;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public int[] getpCntOn() {
        return pCntOn;
    }

    public void setpCntOn(int[] pCntOn) {
        this.pCntOn = pCntOn;
    }

    public int[] getpCntOff() {
        return pCntOff;
    }

    public void setpCntOff(int[] pCntOff) {
        this.pCntOff = pCntOff;
    }

    public VehicleRun(String departureTime, ArrayList<String> busLine, boolean throughTicket, float fee){
        this.departureTime = departureTime;
        this.busLine = busLine;
        this.throughTicket = throughTicket;
        this.fee = fee;
        this.pCntOn = new int[busLine.size()];
        this.pCntOff = new int[busLine.size()];
    }

    public boolean updateCntOnStation(String xstation, int onNum, int offNum){
        if (busLine.contains(xstation)){
            pCntOn[busLine.indexOf(xstation)] = onNum;
            pCntOff[busLine.indexOf(xstation)] = offNum;
            return true;
        }
        return false;
    }

    public boolean getFare(float fare){
        if (fare >= 0) {
            this.income += fare;
            return true;
        } else {
            return false;
        }
    }

    public int passenger(String xstation){
        int passengers = 0;
        for (int i = 0; i < busLine.indexOf(xstation); i++) {
            passengers += pCntOn[i];
            passengers -= pCntOff[i];
        }
        return passengers;
    }
}
