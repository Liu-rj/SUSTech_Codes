package halma.model;

import javax.swing.*;
import java.awt.*;

public class endGame extends JFrame {

    public endGame(String x, ChessBoard chessBoard) {
        super.setTitle("2020 CS102A Project Halma Game");
        setUndecorated(true);
        ImageIcon picture = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/Background.jpg");  //load a picture from computer
        //ImageIcon picture = new ImageIcon("D:\\Halma\\src\\halma\\images/Background.jpg");  //load a picture from computer

        Image image = picture.getImage();  //create an Image to change the size of the picture
        ImageIcon newpicture = new ImageIcon(image.getScaledInstance(700, 500, Image.SCALE_SMOOTH));
        JLabel label = new JLabel(newpicture);  //add the picture to a label
        setContentPane(label);
        setVisible(true);
        setLocation(600, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(null);

        JLabel winner = new JLabel(x);
        winner.setVisible(true);
        winner.setSize(300, 40);
        winner.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
        winner.setHorizontalAlignment(SwingConstants.LEFT);
        winner.setForeground(Color.WHITE);
        winner.setLocation(30, 200);
        add(winner);

        JButton play = new JButton("one more");
        play.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
        play.setLocation(400, 100);
        play.setSize(300, 60);
        play.setForeground(Color.MAGENTA);
        play.setOpaque(false);
        play.setContentAreaFilled(false);
        play.setBorderPainted(false);
        add(play);

        JButton end = new JButton("end the game");
        end.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
        end.setLocation(400, 350);
        end.setSize(300, 60);
        end.setForeground(Color.MAGENTA);
        end.setOpaque(false);
        end.setContentAreaFilled(false);
        end.setBorderPainted(false);
        add(end);

        play.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            chessBoard.placeInitialPieces();
            this.dispose();
        }));
        end.addActionListener(e -> System.exit(-1));
    }
}
