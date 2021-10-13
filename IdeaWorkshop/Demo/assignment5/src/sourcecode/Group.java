package sourcecode;

import java.util.ArrayList;

public class Group {
    private int id;//increase from 1 by 1 with total count of groups
    private String piId;// userId of PI, should not in userList, but can access their offices
    public ArrayList<User> userList;// to store all group members
    public ArrayList<Office> officeList;// to store all offices of the group, users in the group can access any office in the list
    private boolean isValid = true;// false if the group is deleted
    private static int count;// total count of groups created

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
                userList.remove(user);
                return "deleteUser succeed";
            }
        }
        return "user not exists";
    }

    public String addOffice(Office office) {
        for (Office o : officeList) {
            if (o.getId() == office.getId()) {
                return "office already exists";
            }
        }
        officeList.add(office);
        return "addOffice succeed";
    }

    public String deleteOffice(int officeId) {
        for (Office office : officeList) {
            if (office.getId() == officeId) {
                officeList.remove(office);
                return "deleteOffice succeed";
            }
        }
        return "office not exists";
    }

    public Group(String piId) {
        userList = new ArrayList<>();
        officeList = new ArrayList<>();
        this.piId = piId;
        this.id = ++count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPiId() {
        return piId;
    }

    public void setPiId(String piId) {
        this.piId = piId;
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
        Group.count = count;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public ArrayList<Office> getOfficeList() {
        return officeList;
    }

    public void setOfficeList(ArrayList<Office> officeList) {
        this.officeList = officeList;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", piId='" + piId + '\'' +
                ", userList=" + userList +
                ", officeList=" + officeList +
                ", isValid=" + isValid +
                ", count=" + count +
                '}';
    }
}
