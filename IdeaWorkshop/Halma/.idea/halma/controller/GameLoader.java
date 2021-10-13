package halma.controller;

import halma.model.ChessBoard;
import halma.view.ChessBoardComponent;
import halma.view.GameFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameLoader extends JFrame implements ActionListener {
    private final JButton plainJButton1 = new JButton( "New Game" );
    private final JButton plainJButton2 = new JButton( "Continue Last Game" );
    private int player;
    private int count;

    public GameLoader(int player) {
        super("2020 CS102A Project Halma Game");
        setSize(770, 850);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.player = player;

        JPanel jp = new JPanel();
        jp.setBorder(new EmptyBorder(200,0,50,0));
        jp.setLayout(new FlowLayout(FlowLayout.LEADING,123,0));

        Dimension preferredSize=new Dimension(200, 50);
        plainJButton1.setPreferredSize(preferredSize);
        plainJButton1.setVerticalAlignment(SwingConstants.CENTER);
        plainJButton1.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        plainJButton1.setBackground(Color.PINK);
        jp.add(plainJButton1);

        plainJButton2.setPreferredSize(preferredSize);
        plainJButton2.setVerticalAlignment(SwingConstants.CENTER);
        plainJButton2.setFont(new Font(Font.DIALOG, Font.BOLD, 17));
        plainJButton2.setBackground(Color.PINK);
        jp.add(plainJButton2);

        add(jp,BorderLayout.CENTER);

        JPanel jPanel1 = new JPanel();
        jPanel1.setBorder(new EmptyBorder(50,200,30,200));
        jPanel1.setLayout(new GridLayout(1,1,0,0));
        JButton button = new JButton("click to return");
        button.setPreferredSize(preferredSize);
        button.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        button.addActionListener((e) -> {
            this.dispose();
            GameLauncher launcherFrame = new GameLauncher();
            launcherFrame.setVisible(true);
        });
        jPanel1.add(button);
        add(jPanel1,BorderLayout.SOUTH);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(2,1,200,200));
        JLabel welcome = new JLabel("Welcome To Halma Game !");
        welcome.setHorizontalAlignment(JTextField.CENTER);
        welcome.setFont(new Font (Font.DIALOG, Font.BOLD, 50));
        jPanel.add(welcome);
        JLabel choose = new JLabel("Do you want to start a new game or continue last game?");
        choose.setHorizontalAlignment(JTextField.CENTER);
        choose.setFont(new Font(Font.DIALOG, Font.BOLD,25));
        jPanel.add(choose);

        add(jPanel,BorderLayout.NORTH);

        plainJButton1.addActionListener(this);
        plainJButton2.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == plainJButton1){
            count = 0;
            try {
                begin(player);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            this.dispose();
        } else if (e.getSource() == plainJButton2){
            count = 1;
            try {
                begin(player);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            this.dispose();
        }
    }

    public void begin(int player) throws IOException {
        ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 16);
        ChessBoard chessBoard = new ChessBoard(16,player);
        GameController controller = new GameController(chessBoardComponent, chessBoard, count);
        GameFrame mainFrame = new GameFrame(controller);
        mainFrame.add(chessBoardComponent);
        mainFrame.setVisible(true);
    }
}
