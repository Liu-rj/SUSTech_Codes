import java.io.*;

public class ReadWriteTextFile {
    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new FileReader("TestIn.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("TestOut.txt"));
            int temp;
            while ((temp = in.read()) != -1) {
                if (temp >= 'a' && temp <= 'z') {
                    writer.write(temp - 'a' + 'A');
                } else {
                    writer.write(temp);
                }
            }
            in.close();
            writer.close();
        } catch (IOException e) {
            System.out.println("There is no this file!");
        }
    }
}

