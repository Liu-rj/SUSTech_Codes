public class Exer6 {
    public static void main(String[] args) {
        double pi = Math.PI;
        System.out.printf("%-20s%-20s%-20s\n","radius","perimeter","area");
        System.out.printf("%-20d%-20.4f%-20.4f\n",1,2*pi*1,pi*1*1);
        System.out.printf("%-20d%-20.4f%-20.4f\n",2,2*pi*2,pi*2*2);
        System.out.printf("%-20d%-20.4f%-20.4f\n",3,2*pi*3,pi*3*3);
        System.out.printf("%-20d%-20.4f%-20.4f\n",4,2*pi*4,pi*4*4);
    }
}
