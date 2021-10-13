public class Food {
    private int id;
    private String name;
    private String type;
    private int size;
    private double price;

    public void showstart(){
        System.out.println("-------------------welcome,this is Start of the Menu-------------------");
    }

    public void showInformation(){
        System.out.printf("%-5s%-5d%-7s%-12s%-8s%-15s%-10s%4d%-10s%-6.2f%-2c\n","[id]",id,"[name]",name,"[type]",type,"[size]",size,"(Inches)",price,'$');
    }

    public void showend(){
        System.out.println("-------------------welcome,this is  End  of the Menu-------------------");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
