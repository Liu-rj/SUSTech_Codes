package sourcecode;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    private String id;// primary key, not null, not duplicate, eg:11911234; for visitors outside campus,it should be national ID card number
    private String name;
    private int gender;// 0-female, 1-male, 2-other
    private Role role;//enum sourcecode.Role{TEACHER, STAFF, UNDERGRADUATE, GRADUATE,VISITOR}
    private ResidentialCollege residentialCollege;// enum sourcecode.ResidentialCollege{ ZHIREN, SHUREN,ZHICHENG,SHUDE,ZHIXIN,SHULI}
    private String department;//eg: “ComputerScienceEngineering”
    private String district;// “SUSTech” for in campus, other for outside campus
    private String domain;//eg: “Lychee Park”
    private int buildingNo;//building number, eg: 2
    private int roomNo;//room number, eg: 101
    private LocalDate dateBackToShenzhen;// the date back to Shenzhen
    private boolean isValid = true;// false if the user is deleted
    private double bodyTemperature;// temperature today, 0- not checked. A user should only taketemperature once per day.
    private LocalDateTime temperatureTestTime;

    public User(String id, String name, int gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ResidentialCollege getResidentialCollege() {
        return residentialCollege;
    }

    public void setResidentialCollege(ResidentialCollege residentialCollege) {
        this.residentialCollege = residentialCollege;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public LocalDate getDateBackToShenzhen() {
        return dateBackToShenzhen;
    }

    public void setDateBackToShenzhen(LocalDate dateBackToShenzhen) {
        this.dateBackToShenzhen = dateBackToShenzhen;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public double getBodyTemperature() {
        return bodyTemperature;
    }

    public void setBodyTemperature(double bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }

    public LocalDateTime getTemperatureTestTime() {
        return temperatureTestTime;
    }

    public void setTemperatureTestTime(LocalDateTime temperatureTestTime) {
        this.temperatureTestTime = temperatureTestTime;
    }

    //to set bodyTemperature and temperatureTestTime
    public void takeTemperature(double temperature, LocalDateTime dateTime){
        this.bodyTemperature = temperature;
        this.temperatureTestTime = dateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", role=" + role +
                ", residentialCollege=" + residentialCollege +
                ", department='" + department + '\'' +
                ", district='" + district + '\'' +
                ", domain='" + domain + '\'' +
                ", buildingNo=" + buildingNo +
                ", roomNo=" + roomNo +
                ", dateBackToShenzhen=" + dateBackToShenzhen +
                ", isValid=" + isValid +
                ", bodyTemperature=" + bodyTemperature +
                ", temperatureTestTime=" + temperatureTestTime +
                '}';
    }
}
