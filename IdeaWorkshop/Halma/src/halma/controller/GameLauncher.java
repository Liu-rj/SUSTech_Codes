package halma.controller;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLauncher extends JFrame implements ActionListener {
    private final JButton plainJButton1 = new JButton("two—player");
    private final JButton plainJButton2 = new JButton("four—player");
    private int players = 0;
    private int id = 0;
    Icon iconGreen = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/dog1.png");
    Icon iconRed = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/dog.png");
    Icon iconBlue = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/dog2.png");
    Icon iconMagenta = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/dog3.png");
//        Icon iconGreen = new ImageIcon("D:\\Halma\\src\\halma\\images/Green.png");
//        Icon iconRed = new ImageIcon("D:\\Halma\\src\\halma\\images/Red.png");
//        Icon iconBlue = new ImageIcon("D:\\Halma\\src\\halma\\images/Blue.png");
//        Icon iconMagenta = new ImageIcon("D:\\Halma\\src\\halma\\images/Magenta.png");


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GameLauncher() {
        super("2020 CS102A Project Halma Game");
        setUndecorated(true);
        setLocation(600, 300);
        //setLocationRelativeTo(null); // Center the window
        ImageIcon picture = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/Background.jpg");  //load a picture from computer
        //ImageIcon picture = new ImageIcon("D:\\Halma\\src\\halma\\images/Background.jpg");  //load a picture from computer

        Image image = picture.getImage();  //create an Image to change the size of the picture
        ImageIcon newpicture = new ImageIcon(image.getScaledInstance(750, 400, Image.SCALE_SMOOTH));
        JLabel label = new JLabel(newpicture);  //add the picture to a label
        setContentPane(label);
        setVisible(true);  //Set the window to visible
        setSize(newpicture.getIconWidth(), newpicture.getIconHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //let the window can be close by click "x"

        Dimension preferredSize = new Dimension(300, 60);    //设置尺寸
        plainJButton1.setBackground(new Color(0, 0, 0, 0));
        plainJButton1.setForeground(Color.MAGENTA);
        plainJButton1.setVerticalAlignment(SwingConstants.CENTER);   //设置按钮垂直对齐方式
        plainJButton1.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
        plainJButton1.setLocation(15, 300);
        plainJButton1.setSize(preferredSize);
        plainJButton1.setOpaque(false);
        plainJButton1.setContentAreaFilled(false);
        plainJButton1.setBorderPainted(false);
        plainJButton1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(plainJButton1);

        plainJButton2.setSize(preferredSize);
        plainJButton2.setForeground(Color.MAGENTA);
        plainJButton2.setBackground(new Color(0, 0, 0, 0));
        plainJButton2.setVerticalAlignment(SwingConstants.CENTER);    //设置按钮垂直对齐方式
        plainJButton2.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
        plainJButton2.setLocation(420, 300);
        plainJButton2.setOpaque(false);
        plainJButton2.setContentAreaFilled(false);
        plainJButton2.setBorderPainted(false);
        plainJButton2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(plainJButton2);

        //Icon iconTwo = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/2.png");
        //Icon iconTwo = new ImageIcon("D:\\Halma\\src\\halma\\images/2.png");

        JLabel jLabelRed = new JLabel(iconRed);
        jLabelRed.setLocation(90, 230);
        jLabelRed.setSize(100, 100);
        add(jLabelRed);

        //Icon iconFour = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/4.png");
        //Icon iconFour = new ImageIcon("D:\\Halma\\src\\halma\\images/4.png");

        JLabel jLabelGreen = new JLabel(iconGreen);
        jLabelGreen.setLocation(140, 230);
        jLabelGreen.setSize(100, 100);
        add(jLabelGreen);


        JLabel jLabelRed1 = new JLabel(iconRed);
        jLabelRed1.setLocation(350, 130);
        jLabelRed1.setSize(300, 300);
        add(jLabelRed1);

        JLabel jLabelGreen1 = new JLabel(iconGreen);
        jLabelGreen1.setLocation(400, 130);
        jLabelGreen1.setSize(300, 300);
        add(jLabelGreen1);

        JLabel jLabelBlue = new JLabel(iconBlue);
        jLabelBlue.setLocation(450, 130);
        jLabelBlue.setSize(300, 300);
        add(jLabelBlue);

        JLabel jLabelMagenta = new JLabel(iconMagenta);
        jLabelMagenta.setLocation(500, 130);
        jLabelMagenta.setSize(300, 300);
        add(jLabelMagenta);

        JLabel welcome = new JLabel("Welcome To Halma Game !");
        welcome.setHorizontalAlignment(JTextField.CENTER);
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font(Font.DIALOG, Font.BOLD, 50));
        welcome.setLocation(0, 60);
        welcome.setSize(750, 60);
        add(welcome);
/*
        JLabel choose = new JLabel("please choose player number:");
        choose.setHorizontalAlignment(JTextField.CENTER);
        choose.setFont(new Font(Font.DIALOG, Font.BOLD,50));
        choose.setLocation(0,80);
        choose.setSize(750,60);
        window.add(choose);
*/
        plainJButton1.addActionListener(this);
        plainJButton2.addActionListener(this);
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == plainJButton1) {
            players = 2;
            this.dispose();
            GameLoader gameLoader = new GameLoader(players);
            gameLoader.setVisible(true);
            gameLoader.setId(id);
        } else if (e.getSource() == plainJButton2) {
            players = 4;
            this.dispose();
            GameLoader gameLoader = new GameLoader(players);
            gameLoader.setVisible(true);
            gameLoader.setId(id);
        }
    }
}