

import javax.swing.*;
import java.awt.*;

public class DisplayJpg {

    public static void main(String[] args) {
        JFrame window = new JFrame();  //create a Frame
        window.setUndecorated(true);
        window.setLayout(null);
        //window.getContentPane().setVisible(false);
        //window.setBackground(new Color(0,0,0,0));
        window.setLocation(500,350);
        ImageIcon picture = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/Background.jpg");  //load a picture from computer
        Image image = picture.getImage();  //create an Image to change the size of the picture
        ImageIcon newpicture = new ImageIcon(image.getScaledInstance(750, 400, Image.SCALE_SMOOTH));
        JLabel label = new JLabel(newpicture);  //add the picture to a label

        window.setContentPane(label);  //add the label to the frame
        window.setVisible(true);  //Set the window to visible
        //set the size of the window, 30 is the height of the window ribbon at the top
        window.setSize(newpicture.getIconWidth(), newpicture.getIconHeight());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //let the window can be close by click "x"

        JButton jButton  = new JButton();
        jButton.setSize(50,50);
        jButton.setLocation(500,100);
        window.add(jButton);
    }


}
