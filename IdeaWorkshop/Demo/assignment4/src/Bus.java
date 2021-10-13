import java.util.ArrayList;

public class Bus {
    private String licensePlate;
    private int seats;
    private ArrayList<VehicleRun> vrs;
    private int currentVrIndex;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public ArrayList<VehicleRun> getVrs() {
        return vrs;
    }

    public void setVrs(ArrayList<VehicleRun> vrs) {
        this.vrs = vrs;
    }

    public int getCurrentVrIndex() {
        return currentVrIndex;
    }

    public void setCurrentVrIndex(int currentVrIndex) {
        this.currentVrIndex = currentVrIndex;
    }

    public Bus(String licensePlate, int seats) {
        this.licensePlate = licensePlate;
        this.seats = seats;
    }

    public Bus(String licensePlate, int seats, ArrayList<VehicleRun> vrs) {
        this.licensePlate = licensePlate;
        this.seats = seats;
        this.vrs = vrs;
    }

    public boolean arriveStation(int currentVrIndex, String xstation, ArrayList<Passenger> passengersGetOn, ArrayList<Passenger> passengersGetOff) {
        if (passengersGetOff.size() > vrs.get(currentVrIndex).passenger(xstation)) {
            return false;
        } else if (!vrs.get(currentVrIndex).getBusLine().contains(xstation)) {
            return false;
        } else if (vrs.get(currentVrIndex).getBusLine().indexOf(xstation) == 0 & passengersGetOff.size() != 0) {
            return false;
        } else if (xstation.equals(vrs.get(currentVrIndex).getBusLine().get(vrs.get(currentVrIndex).getBusLine().size() - 1)) && (passengersGetOff.size() != vrs.get(currentVrIndex).passenger(xstation) || passengersGetOn.size() != 0)) {
            return false;
        } else {
            this.currentVrIndex = currentVrIndex;
            for (Passenger on : passengersGetOn) {
                on.getOn(vrs.get(currentVrIndex), xstation);
            }
            for (Passenger off : passengersGetOff) {
                off.getOff(vrs.get(currentVrIndex), xstation);
            }
            vrs.get(currentVrIndex).updateCntOnStation(xstation, passengersGetOn.size(), passengersGetOff.size());
            return true;
        }
    }

    public void endCurrentOperation() {
        if (currentVrIndex != vrs.size() - 1) {
            currentVrIndex += 1;
        } else {
            System.out.print("WellDone,all the operations of Today has been finished,have a rest!!");
        }
    }
}
