package halma.controller;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameLauncher extends JFrame implements ActionListener {
    private final JButton plainJButton1 = new JButton( "two—player" );
    private final JButton plainJButton2 = new JButton( "four—player" );
    private int players = 0;

    public GameLauncher() {
        super("2020 CS102A Project Halma Game");
        setSize(770, 850);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jp = new JPanel();
        jp.setBorder(new EmptyBorder(200,0,200,0));
        jp.setLayout(new FlowLayout(FlowLayout.LEADING,123,0));

        Dimension preferredSize=new Dimension(200, 50);    //设置尺寸
        plainJButton1.setPreferredSize(preferredSize);    //设置按钮大小
        plainJButton1.setVerticalAlignment(SwingConstants.CENTER);   //设置按钮垂直对齐方式
        plainJButton1.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        plainJButton1.setBackground(Color.PINK);
        jp.add(plainJButton1);

        plainJButton2.setPreferredSize(preferredSize);    //设置按钮大小
        plainJButton2.setVerticalAlignment(SwingConstants.CENTER);    //设置按钮垂直对齐方式
        plainJButton2.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        plainJButton2.setBackground(Color.PINK);
        jp.add(plainJButton2);

        add(jp,BorderLayout.SOUTH);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(2,1,200,200));
        JLabel welcome = new JLabel("Welcome To Halma Game !");
        welcome.setHorizontalAlignment(JTextField.CENTER);
        welcome.setFont(new Font (Font.DIALOG, Font.BOLD, 50));
        jPanel.add(welcome);
        JLabel choose = new JLabel("please choose player number:");
        choose.setHorizontalAlignment(JTextField.CENTER);
        choose.setFont(new Font(Font.DIALOG, Font.BOLD,50));
        jPanel.add(choose);

        add(jPanel,BorderLayout.NORTH);

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
        if (e.getSource() == plainJButton1){
            players = 2;
            this.dispose();
            GameLoader gameLoader = new GameLoader(players);
            gameLoader.setVisible(true);
        } else if (e.getSource() == plainJButton2){
            players = 4;
            this.dispose();
            GameLoader gameLoader = new GameLoader(players);
            gameLoader.setVisible(true);
        }
    }

}
