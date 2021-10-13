package sourcecode;

public class Office {
    private int id;//primary key, not null, not duplicate; increase by 1 from 1 with total count of officesas default when creating an office object
    private String domain;//eg:”Lychee Park”
    private int buildingNo;//building number, eg: 6
    private int roomNo;//room number, eg: 402
    private double area;// area of the room
    private boolean isValid=true;// false if the office is deleted
    private static int count;// total count of offices created, including deleted ones
    private int visitorCountToday;// set as 0 for everyday, ++1 when a user entered; not consider userget out. A user gets in office several times in a day should only count once.

    public Office(String domain, int buildingNo, int roomNo, double area) {
        this.domain = domain;
        this.buildingNo = buildingNo;
        this.roomNo = roomNo;
        this.area = area;
        this.id = ++count;
    }

    public Office() {
        this.id = ++count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(int buildingNo) {
        this.buildingNo = buildingNo;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Office.count = count;
    }

    public int getVisitorCountToday() {
        return visitorCountToday;
    }

    public void setVisitorCountToday(int visitorCountToday) {
        this.visitorCountToday = visitorCountToday;
    }

    @Override
    public String toString() {
        return "Office{" +
                "id=" + id +
                ", domain='" + domain + '\'' +
                ", buildingNo=" + buildingNo +
                ", roomNo=" + roomNo +
                ", area=" + area +
                ", count=" + count +
                '}';
    }
}
