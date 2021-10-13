package sourcecode;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Data {
    private static ArrayList<User> userList;
    private static ArrayList<Office> officeList;
    private static ArrayList<Group> groupList;
    private static ArrayList<Log> logList;

    public Data() {
        userList = new ArrayList<>();
        officeList = new ArrayList<>();
        groupList = new ArrayList<>();
        logList = new ArrayList<>();
    }

    public static ArrayList<User> getUserList() {
        return userList;
    }

    public static void setUserList(ArrayList<User> userList) {
        Data.userList = userList;
    }

    public static ArrayList<Office> getOfficeList() {
        return officeList;
    }

    public static void setOfficeList(ArrayList<Office> officeList) {
        Data.officeList = officeList;
    }

    public static ArrayList<Group> getGroupList() {
        return groupList;
    }

    public static void setGroupList(ArrayList<Group> groupList) {
        Data.groupList = groupList;
    }

    public static ArrayList<Log> getLogList() {
        return logList;
    }

    public static void setLogList(ArrayList<Log> logList) {
        Data.logList = logList;
    }

    public static void initialize() {

        Office office = new Office("Innovation Park", 10, 501, 5.2);
        Data.getOfficeList().add(office);


        User user = new User("30001001", "Teacher1", 1);
        user.setRole(Role.TEACHER);
        user.setDepartment("CSE");
        user.setDistrict("Nanshan");
        user.setDomain("Domain1");
        user.setBuildingNo(2);
        user.setRoomNo(201);
        user.setDateBackToShenzhen(LocalDate.of(2020, 3, 18));
        Data.getUserList().add(user);

        Group group = new Group(user.getId());//
        Data.getGroupList().add(group);
        group.addOffice(office);


        user = new User("11910001", "Student1", 0);
        user.setRole(Role.UNDERGRADUATE);
        user.setResidentialCollege(ResidentialCollege.ZHIREN);
        user.setDepartment("CSE");
        user.setDistrict("SUSTech");
        user.setDomain("Lychee Park");
        user.setBuildingNo(1);
        user.setRoomNo(101);
        Data.getUserList().add(user);
        group.addUser(user);

        user = new User("11910002", "Student2", 1);
        user.setRole(Role.UNDERGRADUATE);
        user.setResidentialCollege(ResidentialCollege.ZHIXIN);
        user.setDepartment("CSE");
        user.setDistrict("SUSTech");
        user.setDomain("Lychee Park");
        user.setBuildingNo(1);
        user.setRoomNo(102);
        Data.getUserList().add(user);
        group.addUser(user);

        user = new User("11922002", "Student4", 0);
        user.setRole(Role.GRADUATE);
        user.setResidentialCollege(ResidentialCollege.SHUDE);
        user.setDepartment("CSE");
        user.setDistrict("SUSTech");
        user.setDomain("Lychee Park");
        user.setBuildingNo(1);
        user.setRoomNo(103);
        Data.getUserList().add(user);
        //addUser(user);
        group.addUser(user);


        office = new Office("Innovation Park", 10, 502, 8.8);
        Data.getOfficeList().add(office);
        group.addOffice(office);

        user = new User("30001002", "Teacher2", 1);
        user.setRole(Role.TEACHER);
        user.setDepartment("CSE");
        user.setDistrict("SUSTech");
        user.setDomain("Teahcer's Apartment");
        user.setBuildingNo(2);
        user.setRoomNo(202);
        user.setDateBackToShenzhen(LocalDate.of(2020, 3, 17));
        Data.getUserList().add(user);

        group = new Group(user.getId());//
        Data.getGroupList().add(group);
        group.addOffice(office);

        user = new User("11910003", "Student3", 1);
        user.setRole(Role.UNDERGRADUATE);
        user.setResidentialCollege(ResidentialCollege.ZHICHENG);
        user.setDepartment("CSE");
        user.setDistrict("SUSTech");
        user.setDomain("Lychee Park");
        user.setBuildingNo(1);
        user.setRoomNo(102);
        Data.getUserList().add(user);

        user = new User("11922003", "Student5", 0);
        user.setRole(Role.GRADUATE);
        user.setResidentialCollege(ResidentialCollege.SHULI);
        user.setDepartment("CSE");
        user.setDistrict("SUSTech");
        user.setDomain("Lychee Park");
        user.setBuildingNo(1);
        user.setRoomNo(103);
        //user.setValid(false);
        Data.getUserList().add(user);

        user = new User("11922004", "Student6", 1);
        user.setRole(Role.GRADUATE);
        user.setResidentialCollege(ResidentialCollege.SHUREN);
        user.setDepartment("CSE");
        user.setDistrict("SUSTech");
        user.setDomain("Lychee Park");
        user.setBuildingNo(1);
        user.setRoomNo(104);
        Data.getUserList().add(user);


        user = new User("30001003", "Staff1", 1);
        user.setRole(Role.STAFF);
        user.setDepartment("CSE");
        user.setDistrict("Futian");
        user.setDomain("Domain2");
        user.setBuildingNo(2);
        user.setRoomNo(203);
        user.setDateBackToShenzhen(LocalDate.of(2020, 3, 20));
        Data.getUserList().add(user);

        user = new User("440301199001011111", "Visitor1", 1);
        user.setRole(Role.VISITOR);
        user.setDepartment("CSE");
        user.setDistrict("Luohu");
        user.setDomain("Domain3");
        user.setBuildingNo(2);
        user.setRoomNo(204);
        user.setDateBackToShenzhen(LocalDate.of(2020, 3, 19));
        Data.getUserList().add(user);

        Log log = new Log("11911001", 1, 36.5, LocalDateTime.of(2020, 4, 1, 8, 0, 0));
        Data.getLogList().add(log);
        log = new Log("11911001", 1, 36.6, LocalDateTime.of(2020, 4, 1, 14, 0, 0));
        Data.getLogList().add(log);
        log = new Log("11911002", 1, 36.7, LocalDateTime.of(2020, 4, 2, 8, 0, 0));
        Data.getLogList().add(log);
        log = new Log("11911003", 2, 36.8, LocalDateTime.of(2020, 4, 2, 8, 0, 1));
        Data.getLogList().add(log);
        log = new Log("11911001", 1, 36.9, LocalDateTime.of(2020, 4, 2, 8, 0, 2));
        Data.getLogList().add(log);
        log = new Log("11922002", 2, 37.1, LocalDateTime.of(2020, 4, 2, 8, 0, 3));
        Data.getLogList().add(log);
        log = new Log("11922004", 2, 37.2, LocalDateTime.of(2020, 4, 2, 8, 0, 4));
        Data.getLogList().add(log);
        log = new Log("11911001", 1, 37.1, LocalDateTime.of(2020, 4, 2, 13, 0, 5));
        Data.getLogList().add(log);
        log = new Log("11911004", 2, 36.9, LocalDateTime.of(2020, 4, 2, 13, 0, 6));
        Data.getLogList().add(log);
        log = new Log("11911001", 1, 36.8, LocalDateTime.of(2020, 4, 3, 8, 0, 0));
        Data.getLogList().add(log);
        log = new Log("11911002", 0, 36.7, LocalDateTime.of(2020, 4, 3, 8, 0, 1));
        Data.getLogList().add(log);
        log = new Log("11911003", 2, 36.6, LocalDateTime.of(2020, 4, 3, 8, 0, 2));
        Data.getLogList().add(log);
        log = new Log("30001002", 2, 36.5, LocalDateTime.of(2020, 4, 3, 8, 0, 3));
        Data.getLogList().add(log);
        log = new Log("440301199001011111", 0, 36.4, LocalDateTime.of(2020, 4, 4, 8, 0, 4));
        Data.getLogList().add(log);
    }

    public String addUser(User user) {
        for (User u : userList) {
            if (u.getId().equals(user.getId())) {
                return "user already exists";
            }
        }
        userList.add(user);
        return "addUser succeed";
    }

    public String deleteUser(String userId) {
        for (User user : userList) {
            if (user.getId().equals(userId)) {
                user.setValid(false);
                return "deleteUser succeed";
            }
        }
        return "user not exists";
    }

    public User getUser(String userId) {
        for (User user : userList) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public String addGroup(Group group) {
        for (Group g : groupList) {
            if (g.getId() == group.getId()) {
                g.setValid(true);
                return "group already exists";
            }
        }
        groupList.add(group);
        return "addGroup succeed";
    }

    public String deleteGroup(int groupId) {
        for (Group group : groupList) {
            if (group.getId() == groupId) {
                group.setValid(false);
                return "deleteGroup succeed";
            }
        }
        return "group not exist";
    }

    public Group getGroup(int groupId) {
        for (Group group : groupList) {
            if (group.getId() == groupId) {
                return group;
            }
        }
        return null;
    }

    public String addOffice(Office office) {
        for (Office o : officeList) {
            if (o.getId() == office.getId()) {
                o.setValid(true);
                return "Office already exists";
            }
        }
        officeList.add(office);
        return "addOffice succeed";
    }

    public String deleteOffice(int officeId) {
        for (Office o : officeList) {
            if (o.getId() == officeId) {
                o.setValid(false);
                return "deleteOffice succeed";
            }
        }
        return "office not exist";
    }

    public Office getOffice(int officeId) {
        for (Office office : officeList) {
            if (office.getId() == officeId) {
                return office;
            }
        }
        return null;
    }

    public boolean canAccessCampus(String userId, LocalDateTime dateTime) {
        for (User user : userList) {
            if (user.getId().equals(userId)) {
                if (user.getDistrict().equals("SUSTech") && user.getBodyTemperature() != 0) {
                    return true;
                } else if (user.isValid() && user.getBodyTemperature() < 37.3 && user.getBodyTemperature() != 0) {
                    LocalDateTime localDateTime1 = LocalDateTime.of(user.getDateBackToShenzhen(), LocalTime.ofSecondOfDay(0));
                    LocalDateTime localDateTime2 = LocalDateTime.of(LocalDate.from(dateTime), LocalTime.ofSecondOfDay(0));
                    if (Duration.between(localDateTime1, localDateTime2).toDays() >= 14) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean canAccessOffice(String userId, int officeId, LocalDateTime dateTime) {
        for (Log log : logList) {
            if (log.getDateTime().getDayOfMonth() == dateTime.getDayOfMonth() && log.getUserId().equals(userId) && log.getOfficeId() == officeId) {
                return true;
            }
        }
        for (User user : userList) {
            if (user.getId().equals(userId) && user.isValid()) {
                for (Office office : officeList) {
                    if (office.getId() == officeId && office.isValid()) {
                        for (Group group : groupList) {
                            if (group.getPiId().equals(user.getId())) {
                                for (Office o : group.officeList) {
                                    if (o.getId() == office.getId()) {
                                        if (user.getBodyTemperature() < 37.3 && user.getBodyTemperature() != 0) {
                                            if ((office.getArea() - 2 * office.getVisitorCountToday()) >= 2) {
                                                return true;
                                            }
                                        }
                                    }
                                }
                            } else {
                                for (User u : group.userList) {
                                    if (u.getId().equals(user.getId())) {
                                        for (Office o : group.officeList) {
                                            if (o.getId() == office.getId()) {
                                                if (user.getBodyTemperature() < 37.3 && user.getBodyTemperature() != 0) {
                                                    if (office.getArea() - 2 * office.getVisitorCountToday() >= 2) {
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void addAccessRecord(Log log) {
        logList.add(log);
    }

    public boolean accessCampus(String userId, LocalDateTime dateTime) {
        double temp = 0;
        if (canAccessCampus(userId, dateTime)) {
            for (User user : userList) {
                if (user.getId().equals(userId)) {
                    temp = user.getBodyTemperature();
                }
            }
            Log log = new Log(userId, 0, temp, dateTime);
            addAccessRecord(log);
            return true;
        }
        return false;
    }

    public boolean accessOffice(String userId, int officeId, LocalDateTime dateTime) {
        double temp = 0;
        if (canAccessOffice(userId, officeId, dateTime)) {
            for (User user : userList) {
                if (user.getId().equals(userId)) {
                    temp = user.getBodyTemperature();
                }
            }
            for (Office office : officeList) {
                if (office.getId() == officeId) {
                    office.setVisitorCountToday(accessOfficeUsers(officeId,dateTime).size());
                }
            }
            Log log = new Log(userId, officeId, temp, dateTime);
            addAccessRecord(log);
            return true;
        }
        return false;
    }

    public int accessCampusUserCount(LocalDateTime date) {
        ArrayList<Log> logs = new ArrayList<>();
        ArrayList<Log> logs1 = new ArrayList<>();
        for (Log log : logList) {
            if (log.getDateTime().getDayOfMonth() == date.getDayOfMonth() && log.getOfficeId() == 0) {
                logs.add(log);
            }
        }
        for (int i = 1; i < logs.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (logs.get(j).getUserId().equals(logs.get(i).getUserId())){
                    logs1.add(logs.get(i));
                    break;
                }
            }
        }
        return logs.size() - logs1.size();
    }

    public ArrayList<User> accessOfficeUsers(int officeId, LocalDateTime date) {
        ArrayList<User> users = new ArrayList<>();
        Loop:
        for (Log log : logList) {
            if (log.getDateTime().getDayOfMonth() == date.getDayOfMonth() && log.getOfficeId() == officeId) {
                for (User user : userList) {
                    if (user.getId().equals(log.getUserId())) {
                        for (User users1 : users) {
                            if (user.getId().equals(users1.getId())){
                                continue Loop;
                            }
                        }
                        users.add(user);
                    }
                }
            }
        }
        return users;
    }

    public ArrayList<Integer> accessOfficeTimes(String userId, int officeId, LocalDateTime startDate, LocalDateTime endDate) {
        ArrayList<Integer> times = new ArrayList<>();
        for (double i = startDate.getDayOfMonth(); i <= endDate.getDayOfMonth(); i++) {
            Integer count = 0;
            for (Log log : logList) {
                if (log.getDateTime().getDayOfMonth() == i && log.getUserId().equals(userId) && log.getOfficeId() == officeId) {
                    count++;
                }
            }
            times.add(count);
        }
        return times;
    }
}

