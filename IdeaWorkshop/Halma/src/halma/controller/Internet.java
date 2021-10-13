package halma.controller;

import com.sun.security.ntlm.Server;
import halma.model.ChessBoard;
import halma.model.ChessBoardLocation;
import halma.model.ChessPiece;
import halma.view.ChessBoardComponent;
import halma.view.ChessComponent;
import halma.view.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

public class Internet extends JFrame {
    private final Icon wait = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/wait.png");
    private final Icon connect = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/connect.png");
    private final JButton plainJButton1 = new JButton(wait);
    private final JButton plainJButton2 = new JButton(connect);
    private int player;
    private int count;
    private GameSetConnection gamesetconnection;
    private GameConnection gameConnection;
    private static Thread thread;
    private static Thread move;
    private static Thread settime;

    public static Thread getSettime() {
        return settime;
    }

    public static void setSettime(Thread settime) {
        Internet.settime = settime;
    }

    private int c;

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    private Boolean gameconnection;
    private Boolean setgame;
    private static ChessBoardLocation chessBoardLocation;
    private static Boolean sender;
    private static Boolean receiver;
    private ChessComponent component = new ChessComponent();
    int port = 9898;
    InetAddress group;
    MulticastSocket socket;
    private ServerSocket server;
    private static Thread heartbeat;
    private static Thread lost;

    public static Thread getLost() {
        return lost;
    }

    public static void setLost(Thread lost) {
        Internet.lost = lost;
    }

    public static Thread getHeartbeat() {
        return heartbeat;
    }

    public static void setHeartbeat(Thread heartbeat) {
        Internet.heartbeat = heartbeat;
    }

    private static Thread redo;
    private static int cou = 1;
    private int id;
    private Socket client1;
    private Socket client2;
    private Socket client3;
    private Socket client;
    private File file;
    private ChessBoard chessboard;
    private GameController gamecontroller;
    private Socket watch;

    public Socket getWatch() {
        return watch;
    }

    public void setWatch(Socket watch) {
        this.watch = watch;
    }

    private Socket[] lostconnect = new Socket[3];


    public Socket[] getLostconnect() {
        return lostconnect;
    }

    public void setLostconnect(Socket[] lostconnect) {
        this.lostconnect = lostconnect;
    }


    Icon iconGreen = new ImageIcon("D:\\Halma\\src\\halma\\images/dog1.png");
    Icon iconRed = new ImageIcon("D:\\Halma\\src\\halma\\images/dog.png");
    Icon iconBlue = new ImageIcon("D:\\Halma\\src\\halma\\images/dog2.png");
    Icon iconMagenta = new ImageIcon("D:\\Halma\\src\\halma\\images/dog3.png");

    public void setIconGreen(Icon iconGreen) {
        this.iconGreen = iconGreen;
    }

    public void setIconRed(Icon iconRed) {
        this.iconRed = iconRed;
    }

    public void setIconBlue(Icon iconBlue) {
        this.iconBlue = iconBlue;
    }

    public void setIconMagenta(Icon iconMagenta) {
        this.iconMagenta = iconMagenta;
    }

    public void setChessboard(ChessBoard chessboard) {
        this.chessboard = chessboard;
    }

    public void setGamecontroller(GameController gamecontroller) {
        this.gamecontroller = gamecontroller;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public ServerSocket getServer() {
        return server;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }

    public void setClient1(Socket client1) {
        this.client1 = client1;
    }

    public void setClient2(Socket client2) {
        this.client2 = client2;
    }

    public void setClient3(Socket client3) {
        this.client3 = client3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getCou() {
        return cou;
    }

    public static void setCou(int cou) {
        Internet.cou = cou;
    }

    public static Thread getRedo() {
        return redo;
    }

    public static void setRedo(Thread redo) {
        Internet.redo = redo;
    }


    public static Thread getMove() {
        return move;
    }

    public static void setMove(Thread move) {
        Internet.move = move;
    }

    public ChessComponent getComponent() {
        return component;
    }

    public void setComponent(ChessComponent component) {
        this.component = component;
    }

    public Boolean getSender() {
        return sender;
    }

    public void setSender(Boolean sender) {
        Internet.sender = sender;
    }

    public Boolean getReceiver() {
        return receiver;
    }

    public void setReceiver(Boolean receiver) {
        Internet.receiver = receiver;
    }

    public GameConnection getGameConnection() {
        return gameConnection;
    }

    public void setGameConnection(GameConnection gameConnection) {
        this.gameConnection = gameConnection;
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

    public Internet(int player) throws HeadlessException {
        this.player = player;
        setUndecorated(true);
        ImageIcon picture = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/Background.jpg");  //load a picture from computer
        //ImageIcon picture = new ImageIcon("D:\\Halma\\src\\halma\\images/Background.jpg");  //load a picture from computer

        Image image = picture.getImage();  //create an Image to change the size of the picture
        ImageIcon newpicture = new ImageIcon(image.getScaledInstance(750, 400, Image.SCALE_SMOOTH));
        JLabel label = new JLabel(newpicture);  //add the picture to a label
        setContentPane(label);
        setSize(750, 400);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Dimension preferredSize = new Dimension(500, 70);
        plainJButton1.setSize(preferredSize);
        plainJButton1.setVerticalAlignment(SwingConstants.CENTER);
        plainJButton1.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
        plainJButton1.setForeground(Color.MAGENTA);
        plainJButton1.setLocation(-50, 150);
        plainJButton1.setVisible(true);
        plainJButton1.setOpaque(false);
        plainJButton1.setContentAreaFilled(false);
        plainJButton1.setBorderPainted(false);
        plainJButton1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(plainJButton1);
        /*
        重连
         */
        Icon reconnect = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/reconnect.png");
        JButton plainJButton3 = new JButton(reconnect);
        plainJButton3.setSize(preferredSize);
        plainJButton3.setVerticalAlignment(SwingConstants.CENTER);
        plainJButton3.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
        plainJButton3.setForeground(Color.MAGENTA);
        plainJButton3.setLocation(-50, 260);
        plainJButton3.setVisible(true);
        plainJButton3.setOpaque(false);
        plainJButton3.setContentAreaFilled(false);
        plainJButton3.setBorderPainted(false);
        plainJButton3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(plainJButton3);

        Icon watcher = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/watcher.png");
        JButton plainJButton4 = new JButton(watcher);
        plainJButton4.setSize(500,70);
        plainJButton4.setVerticalAlignment(SwingConstants.CENTER);
        plainJButton4.setBackground(Color.MAGENTA);
        plainJButton4.setLocation(270, 260);
        plainJButton4.setVisible(true);
        plainJButton4.setOpaque(false);
        plainJButton4.setContentAreaFilled(false);
        plainJButton4.setBorderPainted(false);
        plainJButton4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(plainJButton4);

        plainJButton2.setSize(preferredSize);
        plainJButton2.setVerticalAlignment(SwingConstants.CENTER);
        plainJButton2.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
        plainJButton2.setForeground(Color.MAGENTA);
        plainJButton2.setLocation(270, 150);
        //plainJButton2.setBackground(new Color(0,0,0,0));
        plainJButton2.setOpaque(false);
        plainJButton2.setContentAreaFilled(false);
        plainJButton2.setBorderPainted(false);
        plainJButton2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(plainJButton2);

        Icon exit = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/exit.png");
        JButton button = new JButton(exit);
        button.setSize(preferredSize);
        button.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
        button.addActionListener((e) -> {
            GameLoader gameLoader = new GameLoader(player);
            gameLoader.setVisible(true);
            this.dispose();
        });
        button.setForeground(Color.WHITE);
        button.setLocation(120, 330);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(button);

        JLabel welcome = new JLabel("Choose Connection Type");
        welcome.setHorizontalAlignment(JTextField.CENTER);
        welcome.setFont(new Font(Font.DIALOG, Font.BOLD, 50));
        welcome.setForeground(Color.WHITE);
        welcome.setLocation(0, 20);
        welcome.setSize(750, 60);
        add(welcome);

        JLabel wait = new JLabel("waiting for another player to connect in...");
        wait.setHorizontalAlignment(JTextField.CENTER);
        wait.setFont(new Font(Font.DIALOG, Font.BOLD, 35));
        wait.setForeground(Color.MAGENTA);
        wait.setLocation(0, 170);
        wait.setSize(750, 60);

        JLabel in = new JLabel("connecting to other player...");
        in.setHorizontalAlignment(JTextField.CENTER);
        in.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
        in.setForeground(Color.MAGENTA);
        in.setLocation(0, 170);
        in.setSize(750, 60);

        JLabel jLabel = new JLabel("connect successfully!");
        jLabel.setHorizontalAlignment(JTextField.CENTER);
        jLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
        jLabel.setForeground(Color.MAGENTA);
        jLabel.setLocation(0, 170);
        jLabel.setSize(750, 60);
        plainJButton3.addActionListener(e -> {
            try {
                clientconnectagain();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        plainJButton4.addActionListener(e -> {
            try {
                Socket watch = new Socket("127.0.0.1", 1100);
                watchconnect();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });

        plainJButton1.addActionListener(e -> {
            try {
                sender = true;
                receiver = false;
                setgame = true;
                gameconnection = false;
                plainJButton1.setVisible(false);
                plainJButton2.setVisible(false);
                plainJButton3.setVisible(false);
                plainJButton4.setVisible(false);
                button.setVisible(false);
                welcome.setVisible(false);
                add(wait);
                JOptionPane.showMessageDialog(this, "connecting to other player...");
                this.client = new Socket("127.0.0.1", 1100);
                OutputStream playernum = client.getOutputStream();
                String number = String.valueOf(player);
                playernum.write(number.getBytes());
                while (true) {
                    InputStream success = client.getInputStream();
                    byte[] bt = new byte[1024];
                    int len = success.read(bt);
                    String succes = new String(bt, 0, len);
                    if (succes.equals("connect successfully!")) {
                        break;
                    }
                }
                wait.setVisible(false);
                add(jLabel);
                // server.close();
                JOptionPane.showMessageDialog(this, "connect successfully!");
                this.dispose();
                group = InetAddress.getByName("224.255.10.0");
                socket = new MulticastSocket(port);
                socket.joinGroup(group);
            } catch (IOException o) {
                o.printStackTrace();
            }
            gamesetconnection = new GameSetConnection();
            try {
                count = 0;
                this.dispose();
                begin(player);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        plainJButton2.addActionListener(e -> {
            count = 0;
            receiver = true;
            sender = false;
            gameconnection = true;
            setgame = false;
            plainJButton1.setVisible(false);
            plainJButton2.setVisible(false);
            plainJButton3.setVisible(false);
            plainJButton4.setVisible(false);
            welcome.setVisible(false);
            add(in);
            try {
                this.client = new Socket("127.0.0.1", 1100);
                while (true) {
                    InputStream success = client.getInputStream();
                    byte[] bt = new byte[1024];
                    int len = success.read(bt);
                    String succes = new String(bt, 0, len);
                    if (succes.equals("connect successfully!")) {
                        break;
                    }
                }
                in.setVisible(false);
                add(jLabel);
                //  client.close();
                JOptionPane.showMessageDialog(this, "connect successfully!");
                this.dispose();
                gameConnection = new GameConnection();
                begin(player);
            } catch (IOException o) {
                o.printStackTrace();
            }
        });
    }


    public void begin(int player) throws IOException {
        ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 16);
        chessboard = new ChessBoard(16, player);
        gamecontroller = new GameController(chessBoardComponent, chessboard, count, file, id);
        GameFrame mainFrame = new GameFrame(gamecontroller, id);
        mainFrame.add(chessBoardComponent);
        mainFrame.setVisible(true);

        gamecontroller.setCountTime(false);
        /******要加的*/
        lost = new Thread(() -> {
            while (true) {
                if (setgame) {
                    gamesetconnection.receivelost();
                }
                if (gameconnection) {
                    gameConnection.receivelost();
                }
            }

        });
        lost.start();


        thread = new Thread(() -> {
            while (true) {
                if (sender) {
                    gamecontroller.setCanDo(true);
                }
                if (receiver) {
                    gamecontroller.setCanDo(false);
                }
                ChessBoardLocation x = chessBoardComponent.getSelectedlocation();
                ChessBoardLocation y = chessBoardComponent.getSelectedsqure();
                if (receiver) {
                    gamecontroller.setSender(false);
                    if (gameconnection) {
                        gameConnection.receiveChessBoardLocation();
                        if (gameConnection.getSelectedLocation() != null && gameConnection.getSelectedSquare() != null) {
                            gamecontroller.internetMove(gameConnection.getSelectedLocation(), gameConnection.getSelectedSquare());

                            gameConnection.setSelectedLocation(null);
                            gameConnection.setSelectedSquare(null);
                            if (player == 2) {
                                receiver = false;
                                sender = true;
                            }
                            if (player == 4) {
                                cou++;
                                if (cou == id) {
                                    receiver = false;
                                    sender = true;
                                }
                                if (cou == 4) {
                                    cou = 1;
                                }
                            }
                        }
                    }

                    if (setgame) {
                        gamesetconnection.receiveChessBoardLocation();
                        if (gamesetconnection.getSelectedLocation() != null && gamesetconnection.getSelectedSquare() != null) {
                            gamecontroller.internetMove(gamesetconnection.getSelectedLocation(), gamesetconnection.getSelectedSquare());
                            gamesetconnection.setSelectedSquare(null);
                            gamesetconnection.setSelectedLocation(null);
                            if (player == 2) {
                                receiver = false;
                                sender = true;
                            }
                            if (player == 4) {
                                cou++;
                                if (cou == 4) {
                                    cou = 1;
                                    receiver = false;
                                    sender = true;
                                }
                            }
                        }
                    }
                }
                if (sender) {
                    gamecontroller.setSender(true);
                    if (x != null && y != null) {
                        if (chessboard.isValidMove(y, x) || chessboard.isValidMove(x, y)) {
                            if (setgame) {
                                try {
                                    gamesetconnection.movedsend();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (player == 2) {
                                    sender = false;
                                    receiver = true;
                                }
                                if (player == 4) {
                                    sender = false;
                                    receiver = true;
                                }
                                gamesetconnection.setSelectedLocation(x);
                                gamesetconnection.setSelectedSquare(y);
                                gamesetconnection.sendSelectedLocation();
                                chessBoardComponent.setSelectedlocation(null);
                                chessBoardComponent.setSelectedsqure(null);
                            }
                            if (gameconnection) {
                                try {
                                    gameConnection.movedsend();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (player == 2) {
                                    sender = false;
                                    receiver = true;
                                }
                                if (player == 4) {
                                    sender = false;
                                    receiver = true;
                                }
                                gameConnection.setSelectedLocation(x);
                                gameConnection.setSelectedSquare(y);
                                gameConnection.sendSelectedLocation();
                                chessBoardComponent.setSelectedlocation(null);
                                chessBoardComponent.setSelectedsqure(null);
                            }
                        }
                    }
                    if (gamecontroller.getTime() == 0) {
                        while (gamecontroller.getStartChess() == null && gamecontroller.getEndChess() == null) {
                            try {
                                thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (gamecontroller.getStartChess() != null && gamecontroller.getEndChess() != null) {
                            ChessBoardLocation s = gamecontroller.getStartChess();
                            ChessBoardLocation end = gamecontroller.getEndChess();
                            gamecontroller.setEndChess(null);
                            gamecontroller.setStartChess(null);
                            if (setgame) {
                                if (player == 2) {
                                    sender = false;
                                    receiver = true;
                                }
                                if (player == 4) {
                                    sender = false;
                                    receiver = true;
                                }
                                gamesetconnection.setSelectedLocation(s);
                                gamesetconnection.setSelectedSquare(end);
                                gamesetconnection.sendSelectedLocation();
                                chessBoardComponent.setSelectedlocation(null);
                                chessBoardComponent.setSelectedsqure(null);
                            }
                            if (gameconnection) {
                                if (player == 2) {
                                    sender = false;
                                    receiver = true;
                                }
                                if (player == 4) {
                                    sender = false;
                                    receiver = true;
                                }
                                gameConnection.setSelectedLocation(s);
                                gameConnection.setSelectedSquare(end);
                                gameConnection.sendSelectedLocation();
                                chessBoardComponent.setSelectedlocation(null);
                                chessBoardComponent.setSelectedsqure(null);
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        heartbeat = new Thread(() -> {
            while (true) {
                try {
                    heartbeat.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    connectagain();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        redo = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (player == 2) {
                    if (setgame) {
                        mainFrame.setRestart1(false);
                        if (sender) {
                            mainFrame.setWithdraw(false);
                            try {
                                receiveWithdraw(gamecontroller);
                            } catch (IOException e) {

                            }
                        }
                        if (receiver) {
                            mainFrame.setWithdraw(true);
                            try {
                                sendWithdraw(gamecontroller);
                            } catch (IOException e) {

                                //    System.exit(0);  // e.printStackTrace();
                            }

                        }
                    }
                    if (gameconnection) {
                        if (sender) {
                            mainFrame.setWithdraw(false);
                            mainFrame.setRestart1(false);
                            try {
                                receiveWithdraw(gamecontroller);
                            } catch (IOException e) {
                                //      System.exit(0);   //  e.printStackTrace();
                            }
                        }
                        if (receiver) {
                            mainFrame.setWithdraw(true);
                            mainFrame.setRestart1(true);
                            try {
                                sendWithdraw(gamecontroller);
                            } catch (IOException e) {
                                //      System.exit(0);  //   e.printStackTrace();
                            }
                        }
                    }
                }
                if (player == 4) {

                    if (setgame && receiver) {
                        mainFrame.setWithdraw(false);
                        mainFrame.setRestart1(false);
                    }
                    if (sender && setgame) {
                        mainFrame.setWithdraw(true);
                        mainFrame.setRestart1(true);
                        try {
                            sendWithdraw(gamecontroller);
                        } catch (IOException e) {
                            //    System.exit(0); //  e.printStackTrace();
                        }
                    }
                    if (gameconnection) {
                        mainFrame.setRestart1(false);
                        mainFrame.setWithdraw(false);
                    }
                    if (gameconnection && receiver) {
                        try {
                            receiveWithdraw(gamecontroller);
                        } catch (IOException e) {
                            //   System.exit(0);    //  e.printStackTrace();
                        }
                    }
                }
            }
        });
        settime = new Thread(() -> {
            while (true) {
                if (setgame) {
                    gamesetconnection.receiveTime();
                    gamecontroller.setTime(gamesetconnection.getTime());
                }
                if (gameconnection) {
                    gameConnection.receiveTime();
                    gamecontroller.setTime(gameConnection.getTime());
                }
            }

        });
        heartbeat.start();
        // redo.start();
        thread.start();
        settime.start();

    }

    public void receiveWithdraw(GameController controller) throws IOException {
        InputStream inputStream = client.getInputStream();
        byte[] bt = new byte[1024];
        int len = inputStream.read(bt);
        String ifWithdraw = new String(bt, 0, len);
        if (ifWithdraw.equals("true")) {
            for (int i = 0; i < player; i++) {
                controller.withdraw();
            }
            controller.setWithdraw(false);
        }
        if (ifWithdraw.contains("truerestart")) {
            controller.restart();
            controller.setRestart(false);
        }
    }

    public void sendWithdraw(GameController controller) throws IOException {
        if (!controller.getWithdraw() && player == 2) {
            OutputStream out = client.getOutputStream();
            String x = "1";
            out.write(x.getBytes());
        }
        if (!controller.getWithdraw() && player == 4) {
            OutputStream x1 = client1.getOutputStream();
            OutputStream x2 = client2.getOutputStream();
            OutputStream x3 = client3.getOutputStream();
            String x = "1";
            x1.write(x.getBytes());
            x2.write(x.getBytes());
            x3.write(x.getBytes());
        }

        if (controller.getWithdraw() && player == 2) {
            for (int i = 1; i < player; i++) {
                controller.withdraw();
            }
            String withdraw1 = "true";
            OutputStream outputStream = client.getOutputStream();
            outputStream.write(withdraw1.getBytes());
            controller.setWithdraw(false);
        }
        if (controller.getWithdraw() && setgame && player == 4) {
            for (int i = 1; i < player; i++) {
                controller.withdraw();
            }
            controller.setWithdraw(false);
            OutputStream x1 = client1.getOutputStream();
            OutputStream x2 = client2.getOutputStream();
            OutputStream x3 = client3.getOutputStream();
            String x = "true";
            x1.write(x.getBytes());
            x2.write(x.getBytes());
            x3.write(x.getBytes());
        }


        if (controller.getRestart() && gameconnection && player == 2) {
            OutputStream outputStream = client.getOutputStream();
            String restart = "truerestart";
            outputStream.write(restart.getBytes());
            controller.setRestart(false);
        }
        if (controller.getRestart() && setgame && player == 4) {
            OutputStream x1 = client1.getOutputStream();
            OutputStream x2 = client2.getOutputStream();
            OutputStream x3 = client3.getOutputStream();
            String x = "truerestart";
            x1.write(x.getBytes());
            x2.write(x.getBytes());
            x3.write(x.getBytes());
            controller.setRestart(false);
        }
    }

    public void watchconnect() throws IOException {
        watch = new Socket("127.0.0.1", 1100);
        InputStream connect = watch.getInputStream();
        byte[] bt = new byte[1024];
        int len = connect.read(bt);
        String connectagain1 = new String(bt, 0, len);
        String[] c = connectagain1.split(" ");
        int lenth = c.length;
        int currentplayer = Integer.parseInt(c[lenth - 1]);
        ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 16);
        chessboard = new ChessBoard(16, player);
        gamecontroller = new GameController(chessBoardComponent, chessboard, count, file, id);
        gamecontroller.setCountTime(false);
        GameFrame mainFrame = new GameFrame(gamecontroller, id);
        mainFrame.add(chessBoardComponent);
        mainFrame.setVisible(true);
        gamecontroller.setCountTime(false);
        gamecontroller.setCanDo(false);
        this.dispose();
        mainFrame.setVisible(true);
        int k = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                ChessBoardLocation x = new ChessBoardLocation(i, j);
                if (chessboard.getChessPieceAt(x) != null) {
                    chessboard.removeChessPieceAt(x);
                }          //放棋子
                String y = c[k];
                k++;
                int ifchess = Integer.parseInt(y.substring(0, 1));
                if (ifchess == 1) {
                    int color = Integer.parseInt(y.substring(1, 2));
                    if (color == 1) {
                        ChessPiece chessPiece = new ChessPiece(Color.red);
                        chessboard.setChessPieceAt(x, chessPiece);
                    }
                    if (color == 2) {
                        ChessPiece chessPiece = new ChessPiece(Color.green);
                        chessboard.setChessPieceAt(x, chessPiece);
                    }
                    if (color == 3) {
                        ChessPiece chessPiece = new ChessPiece(Color.blue);
                        chessboard.setChessPieceAt(x, chessPiece);
                    }
                    if (color == 4) {
                        ChessPiece chessPiece = new ChessPiece(Color.magenta);
                        chessboard.setChessPieceAt(x, chessPiece);
                    }
                }
            }
        }
        switch (currentplayer) {
            case 5:
                gamecontroller.setCurrentPlayer(Color.red);
                gamecontroller.getjLabel().setIcon(iconRed);
                break;
            case 6:
                gamecontroller.setCurrentPlayer(Color.green);
                gamecontroller.getjLabel().setIcon(iconGreen);
                break;
            case 7:
                gamecontroller.setCurrentPlayer(Color.blue);
                gamecontroller.getjLabel().setIcon(iconBlue);
                break;
            case 8:
                gamecontroller.setCurrentPlayer(Color.magenta);
                gamecontroller.getjLabel().setIcon(iconMagenta);
                break;                                 //目前的玩家
        }
        watcherconnection watcherconnection = new watcherconnection();
        thread = new Thread(() -> {
            while (true) {

                ChessBoardLocation x = chessBoardComponent.getSelectedlocation();
                ChessBoardLocation y = chessBoardComponent.getSelectedsqure();
                watcherconnection.receiveChessBoardLocation();
                if (watcherconnection.getSelectedLocation() != null && watcherconnection.getSelectedSquare() != null) {
                    gamecontroller.internetMove(watcherconnection.getSelectedLocation(), watcherconnection.getSelectedSquare());
                    watcherconnection.setSelectedLocation(null);
                    watcherconnection.setSelectedSquare(null);
                }

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        settime = new Thread(() -> {
            while (true) {
                watcherconnection.receiveTime();
                gamecontroller.setTime(watcherconnection.getTime());
            }

        });
        thread.start();
        settime.start();
    }

    public String connectagain() throws IOException {
        String connect = new String();
        int dimension = chessboard.getDimension();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                ChessBoardLocation x = new ChessBoardLocation(i, j);
                if (chessboard.getChessPieceAt(x) != null) {
                    Color color = chessboard.getChessPieceAt(x).getColor();
                    if (color == Color.red) {
                        connect = connect + "11 ";
                    }
                    if (color == Color.GREEN) {
                        connect = connect + "12 ";
                    }
                    if (color == Color.blue) {
                        connect = connect + "13 ";
                    }
                    if (color == Color.magenta) {
                        connect = connect + "14 ";
                    }
                }
                if (chessboard.getChessPieceAt(x) == null) {
                    connect = connect + "0 ";
                }
            }
        }          //获取棋子信息
        Color currentplayer = gamecontroller.getCurrentPlayer();
        if (player == 2) {
            if (sender) {
                connect = connect + "00 ";
            } else connect = connect + "88 ";
        }
        if (player == 4) {
            connect = connect + cou + " ";
            connect = connect + id + " ";
        }                                      //发送有关sender的信息

        if (currentplayer == Color.red) {
            connect = connect + "5";
        }
        if (currentplayer == Color.green) {
            connect = connect + "6";
        }
        if (currentplayer == Color.blue) {
            connect = connect + "7";
        }
        if (currentplayer == Color.magenta) {
            connect = connect + "8";
        }
        OutputStream loca = client.getOutputStream();
        loca.write(connect.getBytes());              //发送当前玩家
        return connect;

    }

    public void clientconnectagain() throws IOException {
        if (id != 1) {
            gameConnection = new GameConnection();
            gameconnection = true;
            setgame = false;
        }
        if (id == 1) {
            gamesetconnection = new GameSetConnection();
            setgame = true;
            gameconnection = false;
        }


        this.client = new Socket("127.0.0.1", 1100);
        InputStream connect = client.getInputStream();
        byte[] bt = new byte[1024];
        int len = connect.read(bt);
        String connectagain1 = new String(bt, 0, len);
        String[] c = connectagain1.split(" ");
        int lenth = c.length;
        int currentplayer = Integer.parseInt(c[lenth - 1]);
        ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 16);
        chessboard = new ChessBoard(16, player);
        gamecontroller = new GameController(chessBoardComponent, chessboard, count, file, id);
        gamecontroller.setCountTime(false);
        GameFrame mainFrame = new GameFrame(gamecontroller, id);
        mainFrame.add(chessBoardComponent);
        mainFrame.setVisible(true);


        if (player == 2) {
            if (c[lenth - 2].equals("00")) {
                sender = false;
                receiver = true;
            } else if (c[lenth - 2].equals("88")) {
                sender = true;
                receiver = false;
            }
        }
        if (player == 4) {
            cou = Integer.parseInt(c[lenth - 3]);
            if (cou == id) {
                receiver = false;
                sender = true;
            }
            if (cou != id) {
                receiver = true;
                sender = false;
            }
        }     //sender receiver 信息
        int k = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                ChessBoardLocation x = new ChessBoardLocation(i, j);
                if (chessboard.getChessPieceAt(x) != null) {
                    chessboard.removeChessPieceAt(x);
                }          //放棋子
                String y = c[k];
                k++;
                int ifchess = Integer.parseInt(y.substring(0, 1));
                if (ifchess == 1) {
                    int color = Integer.parseInt(y.substring(1, 2));
                    if (color == 1) {
                        ChessPiece chessPiece = new ChessPiece(Color.red);
                        chessboard.setChessPieceAt(x, chessPiece);
                    }
                    if (color == 2) {
                        ChessPiece chessPiece = new ChessPiece(Color.green);
                        chessboard.setChessPieceAt(x, chessPiece);
                    }
                    if (color == 3) {
                        ChessPiece chessPiece = new ChessPiece(Color.blue);
                        chessboard.setChessPieceAt(x, chessPiece);
                    }
                    if (color == 4) {
                        ChessPiece chessPiece = new ChessPiece(Color.magenta);
                        chessboard.setChessPieceAt(x, chessPiece);
                    }
                }
            }
        }
        switch (currentplayer) {
            case 5:
                gamecontroller.setCurrentPlayer(Color.red);
                gamecontroller.getjLabel().setIcon(iconRed);
                break;
            case 6:
                gamecontroller.setCurrentPlayer(Color.green);
                gamecontroller.getjLabel().setIcon(iconGreen);
                break;
            case 7:
                gamecontroller.setCurrentPlayer(Color.blue);
                gamecontroller.getjLabel().setIcon(iconBlue);
                break;
            case 8:
                gamecontroller.setCurrentPlayer(Color.magenta);
                gamecontroller.getjLabel().setIcon(iconMagenta);
                break;                                 //目前的玩家
        }
        thread = new Thread(() -> {
            while (true) {
                if (sender) {
                    gamecontroller.setCanDo(true);
                }
                if (receiver) {
                    gamecontroller.setCanDo(false);
                }
                // chessBoard.setXyz(false);
                ChessBoardLocation x = chessBoardComponent.getSelectedlocation();
                ChessBoardLocation y = chessBoardComponent.getSelectedsqure();
                if (receiver) {
                    gamecontroller.setSender(false);
                    if (gameconnection) {
                        gameConnection.receiveChessBoardLocation();
                        if (gameConnection.getSelectedLocation() != null && gameConnection.getSelectedSquare() != null) {
                            gamecontroller.internetMove(gameConnection.getSelectedLocation(), gameConnection.getSelectedSquare());
                            gameConnection.setSelectedLocation(null);
                            gameConnection.setSelectedSquare(null);
                            if (player == 2) {
                                receiver = false;
                                sender = true;
                            }
                            if (player == 4) {
                                cou++;
                                if (cou == id) {
                                    receiver = false;
                                    sender = true;
                                }
                                if (cou == 4) {
                                    cou = 1;
                                }
                            }
                        }
                    }

                    if (setgame) {
                        gamesetconnection.receiveChessBoardLocation();
                        if (gamesetconnection.getSelectedLocation() != null && gamesetconnection.getSelectedSquare() != null) {
                            gamecontroller.internetMove(gamesetconnection.getSelectedLocation(), gamesetconnection.getSelectedSquare());

                            gamesetconnection.setSelectedSquare(null);
                            gamesetconnection.setSelectedLocation(null);
                            if (player == 2) {
                                receiver = false;
                                sender = true;
                            }
                            if (player == 4) {
                                cou++;
                                if (cou == 4) {
                                    cou = 1;
                                    receiver = false;
                                    sender = true;
                                }
                            }
                        }
                    }
                }

                if (sender) {
                    gamecontroller.setSender(true);
                    if (x != null && y != null) {
                        if (chessboard.isValidMove(y, x) || chessboard.isValidMove(x, y)) {
                            if (setgame) {
                                if (player == 2) {
                                    sender = false;
                                    receiver = true;
                                }
                                if (player == 4) {
                                    sender = false;
                                    receiver = true;
                                }
                                gamesetconnection.setSelectedLocation(x);
                                gamesetconnection.setSelectedSquare(y);
                                gamesetconnection.sendSelectedLocation();
                                chessBoardComponent.setSelectedlocation(null);
                                chessBoardComponent.setSelectedsqure(null);
                            }
                            if (gameconnection) {
                                if (player == 2) {
                                    sender = false;
                                    receiver = true;
                                }
                                if (player == 4) {
                                    sender = false;
                                    receiver = true;
                                }
                                gameConnection.setSelectedLocation(x);
                                gameConnection.setSelectedSquare(y);
                                gameConnection.sendSelectedLocation();
                                chessBoardComponent.setSelectedlocation(null);
                                chessBoardComponent.setSelectedsqure(null);
                            }
                        }
                    }
                    if (gamecontroller.getTime() == 0) {
                        while (gamecontroller.getStartChess() == null && gamecontroller.getEndChess() == null) {
                            try {
                                thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (gamecontroller.getStartChess() != null && gamecontroller.getEndChess() != null) {
                            ChessBoardLocation s = gamecontroller.getStartChess();
                            ChessBoardLocation end = gamecontroller.getEndChess();
                            gamecontroller.setEndChess(null);
                            gamecontroller.setStartChess(null);
                            if (setgame) {
                                if (player == 2) {
                                    sender = false;
                                    receiver = true;
                                }
                                if (player == 4) {
                                    sender = false;
                                    receiver = true;
                                }
                                gamesetconnection.setSelectedLocation(s);
                                gamesetconnection.setSelectedSquare(end);
                                gamesetconnection.sendSelectedLocation();
                                chessBoardComponent.setSelectedlocation(null);
                                chessBoardComponent.setSelectedsqure(null);
                            }
                            if (gameconnection) {
                                if (player == 2) {
                                    sender = false;
                                    receiver = true;
                                }
                                if (player == 4) {
                                    sender = false;
                                    receiver = true;
                                }
                                gameConnection.setSelectedLocation(s);
                                gameConnection.setSelectedSquare(end);
                                gameConnection.sendSelectedLocation();
                                chessBoardComponent.setSelectedlocation(null);
                                chessBoardComponent.setSelectedsqure(null);
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        redo = new Thread(() -> {

            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (player == 2) {
                    if (setgame) {
                        mainFrame.setRestart1(false);
                        if (sender) {
                            mainFrame.setWithdraw(false);
                            try {
                                receiveWithdraw(gamecontroller);
                            } catch (IOException e) {

                            }
                        }
                        if (receiver) {
                            mainFrame.setWithdraw(true);
                            try {
                                sendWithdraw(gamecontroller);
                            } catch (IOException e) {

                                //    System.exit(0);  // e.printStackTrace();
                            }

                        }
                    }
                    if (gameconnection) {
                        if (sender) {
                            mainFrame.setWithdraw(false);
                            mainFrame.setRestart1(false);
                            try {
                                receiveWithdraw(gamecontroller);
                            } catch (IOException e) {
                                //      System.exit(0);   //  e.printStackTrace();
                            }
                        }
                        if (receiver) {
                            mainFrame.setWithdraw(true);
                            mainFrame.setRestart1(true);
                            try {
                                sendWithdraw(gamecontroller);
                            } catch (IOException e) {
                                //      System.exit(0);  //   e.printStackTrace();
                            }
                        }
                    }
                }
                if (player == 4) {
                    if (gameconnection) {
                        mainFrame.setRestart1(false);
                        mainFrame.setWithdraw(false);
                    }
                    if (gameconnection && receiver) {
                        try {
                            receiveWithdraw(gamecontroller);
                        } catch (IOException e) {
                            //   System.exit(0);    //  e.printStackTrace();
                        }
                    }
                }
            }
        });
        heartbeat = new Thread(() -> {
            try {
                heartbeat.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                connectagain();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        settime = new Thread(() -> {
            while (true) {
                if (setgame) {
                    gamesetconnection.receiveTime();
                    gamecontroller.setTime(gamesetconnection.getTime());
                }
                if (gameconnection) {
                    gameConnection.receiveTime();
                    gamecontroller.setTime(gameConnection.getTime());
                }
            }

        });
        heartbeat.start();
        redo.start();
        thread.start();
        settime.start();

    }


}















