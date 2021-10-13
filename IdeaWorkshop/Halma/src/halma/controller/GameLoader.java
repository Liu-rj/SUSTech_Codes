package halma.controller;

import halma.model.ChessBoard;
import halma.view.ChessBoardComponent;
import halma.view.FileChooser;
import halma.view.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;

public class GameLoader extends JFrame implements ActionListener {
    private final Icon newGame = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/new.png");
    private final JButton plainJButton1 = new JButton(newGame);
    private final Icon doc = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/doc.png");
    private final JButton plainJButton2 = new JButton(doc);
    private int player;
    private int count;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public GameLoader(int player) {
        super("2020 CS102A Project Halma Game");
        setUndecorated(true);
        ImageIcon picture = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/Background.jpg");  //load a picture from computer

        Image image = picture.getImage();  //create an Image to change the size of the picture
        ImageIcon newpicture = new ImageIcon(image.getScaledInstance(750, 400, Image.SCALE_SMOOTH));
        JLabel label = new JLabel(newpicture);  //add the picture to a label
        setContentPane(label);
        setSize(750, 400);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.player = player;

        Dimension preferredSize = new Dimension(350, 60);
        plainJButton1.setSize(preferredSize);
        plainJButton1.setVerticalAlignment(SwingConstants.CENTER);
        plainJButton1.setForeground(Color.MAGENTA);
        plainJButton1.setLocation(0,170);
        plainJButton1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        plainJButton1.setVisible(true);
        plainJButton1.setOpaque(false);
        plainJButton1.setContentAreaFilled(false);
        plainJButton1.setBorderPainted(false);
        add(plainJButton1);

        plainJButton2.setSize(preferredSize);
        plainJButton2.setVerticalAlignment(SwingConstants.CENTER);
        plainJButton2.setForeground(Color.MAGENTA);
        plainJButton2.setLocation(380,170);
        plainJButton2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        plainJButton2.setOpaque(false);
        plainJButton2.setContentAreaFilled(false);
        plainJButton2.setBorderPainted(false);
        add(plainJButton2);

        Icon exit = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/exit.png");
        JButton button = new JButton(exit);
        button.setSize(preferredSize);
        button.addActionListener((e) -> {
            this.dispose();
            GameLauncher launcherFrame = new GameLauncher();
            launcherFrame.setVisible(true);
        });
        button.setLocation(170,330);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(button);

        Icon online = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/online.png");
        JButton jButton = new JButton(online);
        jButton.setSize(preferredSize);
        jButton.addActionListener((e) -> {
            Internet internet = new Internet(id);
            internet.setVisible(true);
            this.dispose();
        });
        jButton.setForeground(Color.MAGENTA);
        jButton.setLocation(0,260);
        jButton.setOpaque(false);
        jButton.setContentAreaFilled(false);
        jButton.setBorderPainted(false);
        jButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(jButton);

        Icon computer = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/AI.png");
        JButton AI = new JButton(computer);
        AI.setSize(preferredSize);
        AI.addActionListener((e) -> {
            ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 16);
            ChessBoard chessBoard = null;
            try {
                chessBoard = new ChessBoard(16, player);
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            }
            GameController controller = null;
            try {
                controller = new GameController(chessBoardComponent, chessBoard, 0, null, id);
                controller.setAI(true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            GameFrame mainFrame = null;
            mainFrame = new GameFrame(controller, id);
            mainFrame.add(chessBoardComponent);
            mainFrame.setVisible(true);
            this.dispose();
        });
        AI.setForeground(Color.MAGENTA);
        AI.setLocation(380,260);
        AI.setOpaque(false);
        AI.setContentAreaFilled(false);
        AI.setBorderPainted(false);
        AI.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(AI);

        JLabel welcome = new JLabel("Welcome To Halma Game !");
        welcome.setHorizontalAlignment(JTextField.CENTER);
        welcome.setFont(new Font(Font.DIALOG, Font.BOLD, 50));
        welcome.setForeground(Color.WHITE);
        welcome.setLocation(0,20);
        welcome.setSize(750,60);
        add(welcome);

        JLabel choose = new JLabel("Start a new game or Continue the last game?");
        choose.setHorizontalAlignment(JTextField.CENTER);
        choose.setFont(new Font(Font.DIALOG, Font.BOLD, 35));
        choose.setLocation(0,100);
        choose.setForeground(Color.MAGENTA);
        choose.setSize(750,60);
        add(choose);

        plainJButton1.addActionListener(this);
        plainJButton2.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == plainJButton1) {
            count = 0;
            try {
                begin(player);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            this.dispose();
        } else if (e.getSource() == plainJButton2) {
            FileChooser fileChooser = new FileChooser(this);
        }
    }

    public void begin(int player) throws IOException {
        ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 16);
        ChessBoard chessBoard = new ChessBoard(16, player);
        GameController controller = new GameController(chessBoardComponent, chessBoard, count, null, id);
        GameFrame mainFrame = new GameFrame(controller, id);
        mainFrame.add(chessBoardComponent);
        mainFrame.setVisible(true);
    }
}
