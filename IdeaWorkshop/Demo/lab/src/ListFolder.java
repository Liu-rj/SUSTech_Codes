import java.io.File;

public class ListFolder {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\");
        String[] fileList = file.list();
        for (String name : fileList){
            System.out.println(name);
        }
    }
}
