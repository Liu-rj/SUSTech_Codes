package halma.view;

import halma.controller.GameController;
import halma.controller.GameLoader;
import halma.controller.Talking;
import sun.audio.AudioPlayer;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class GameFrame extends JFrame {
    private GameController gameController;
    private JButton button;
    private boolean ifChat = false;
    private boolean visible = false;
    /**
     * add
     */
    private boolean ifIcon = true;
    private int id;
    private Boolean withdraw = true;      //需要改
    private Boolean restart1 = true;
    private AudioClip audioClip;
    private final Icon iconGreen = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/Green.png");
    private final Icon iconRed = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/Red.png");
    private final Icon iconBlue = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/Blue.png");
    private final Icon iconMagenta = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/Magenta.png");

    private final Icon red = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/dog.png");
    private final Icon green = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/dog1.png");
    private final Icon blue = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/dog2.png");
    private final Icon magenta = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/dog3.png");

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    public Boolean getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Boolean withdraw) {
        this.withdraw = withdraw;
    }

    public Boolean getRestart1() {
        return restart1;
    }

    public void setRestart1(Boolean restart1) {
        this.restart1 = restart1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public boolean isIfChat() {
        return ifChat;
    }

    public void setIfChat(boolean ifChat) {
        this.ifChat = ifChat;
    }

    public GameFrame(GameController gameController, int id) {
//        Thread musicThread = new Thread(() -> {
//            File f = new File("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\sound/music1.wav");
//            URI uri = f.toURI();
//            URL url = null;
//            try {
//                url = uri.toURL();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            audioClip = Applet.newAudioClip(url);
//            audioClip.loop();
//        });
//        musicThread.start();

        this.id = id;
        Talking talking = new Talking(id);
        this.gameController = gameController;
        setTitle("2020 CS102A Project Halma Game");
        setSize(1100, 900);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        Color color1 = new Color(178, 34, 34, 1);

        ImageIcon picture = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/halma.jpeg");  //load a picture from computer
        Image image = picture.getImage();  //create an Image to change the size of the picture
        ImageIcon newpicture = new ImageIcon(image.getScaledInstance(1100, 900, Image.SCALE_SMOOTH));
        JLabel label1 = new JLabel(newpicture);  //add the picture to a label
        setContentPane(label1);

        JLabel statusLabel = new JLabel("Current Player:");
        statusLabel.setForeground(Color.WHITE);
        /**add*/
        if (ifIcon) {
            if (gameController.getCurrentPlayer() == Color.RED) {
                statusLabel.setIcon(red);
            } else if (gameController.getCurrentPlayer() == Color.GREEN) {
                statusLabel.setIcon(green);
            } else if (gameController.getCurrentPlayer() == Color.BLUE) {
                statusLabel.setIcon(blue);
            } else if (gameController.getCurrentPlayer() == Color.MAGENTA) {
                statusLabel.setIcon(magenta);
            }
        } else {
            if (gameController.getCurrentPlayer() == Color.RED) {
                statusLabel.setIcon(iconRed);
            } else if (gameController.getCurrentPlayer() == Color.GREEN) {
                statusLabel.setIcon(iconGreen);
            } else if (gameController.getCurrentPlayer() == Color.BLUE) {
                statusLabel.setIcon(iconBlue);
            } else if (gameController.getCurrentPlayer() == Color.MAGENTA) {
                statusLabel.setIcon(iconMagenta);
            }
        }
        statusLabel.setHorizontalTextPosition(JLabel.LEFT);
        statusLabel.setLocation(0, 760);
        statusLabel.setSize(300, 40);
        statusLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
        add(statusLabel);
        gameController.setjLabel(statusLabel);

        int time = 30;
        JLabel label = new JLabel("Time Left:" + time);
        label.setForeground(Color.WHITE);
        label.setVisible(true);
        add(label);
        label.setLocation(75, 810);
        label.setSize(300, 30);
        label.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
        gameController.setLabel(label);

        Icon back = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/return.png");
        JButton withdraw = new JButton(back);
        withdraw.setOpaque(false);
        withdraw.setContentAreaFilled(false);
        withdraw.setBorderPainted(false);
        withdraw.setVisible(false);
        withdraw.addActionListener((e) -> {
            try {
                gameController.withdraw();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        withdraw.setLocation(810, 700);
        withdraw.setSize(70, 48);
        withdraw.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(withdraw);
/**add,这里是新添加了一个button，用于更方便的切换棋子，如果在展示较复杂路径的时候就可以切换成原始的棋子避免卡顿，要注意在成员变量里添加了ifIcon属性*/
        Icon color = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/ChessColor.png");
        JButton chess = new JButton(color);
        chess.setOpaque(false);
        chess.setContentAreaFilled(false);
        chess.setBorderPainted(false);
        chess.setVisible(false);
        chess.addActionListener((e) -> {
            ifIcon = !ifIcon;
            gameController.setChess(ifIcon);
            if (ifIcon) {
                if (gameController.getCurrentPlayer() == Color.RED) {
                    statusLabel.setIcon(red);
                } else if (gameController.getCurrentPlayer() == Color.GREEN) {
                    statusLabel.setIcon(green);
                } else if (gameController.getCurrentPlayer() == Color.BLUE) {
                    statusLabel.setIcon(blue);
                } else if (gameController.getCurrentPlayer() == Color.MAGENTA) {
                    statusLabel.setIcon(magenta);
                }
            } else {
                if (gameController.getCurrentPlayer() == Color.RED) {
                    statusLabel.setIcon(iconRed);
                } else if (gameController.getCurrentPlayer() == Color.GREEN) {
                    statusLabel.setIcon(iconGreen);
                } else if (gameController.getCurrentPlayer() == Color.BLUE) {
                    statusLabel.setIcon(iconBlue);
                } else if (gameController.getCurrentPlayer() == Color.MAGENTA) {
                    statusLabel.setIcon(iconMagenta);
                }
            }
            gameController.setIfIcon(ifIcon);
        });
        chess.setLocation(810, 780);
        chess.setSize(48, 48);
        chess.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(chess);

        Icon background = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/music.png");
        JButton music = new JButton(background);
        music.setOpaque(false);
        music.setContentAreaFilled(false);
        music.setBorderPainted(false);
        music.setVisible(false);
        music.setLocation(950, 780);
        music.setSize(48, 48);
        music.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        music.addActionListener((e) -> audioClip = null);
        add(music);

        Icon restart = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/restart.png");
        JButton reload = new JButton(restart);
        reload.setOpaque(false);
        reload.setContentAreaFilled(false);
        reload.setBorderPainted(false);
        reload.setVisible(false);
        reload.addActionListener((e) -> {
            gameController.restart();
            JOptionPane.showMessageDialog(this, "Restart Successfully!");
        });
        reload.setLocation(950, 600);
        reload.setSize(48, 48);
        reload.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(reload);

        /**
         * add
         */
        Icon exit = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/exit.png");
        JButton backward = new JButton(exit);
        backward.setOpaque(false);
        backward.setContentAreaFilled(false);
        backward.setBorderPainted(false);
        backward.addActionListener((e) -> {
            try {
                gameController.saveGame();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            this.dispose();
            GameLoader gameLoader = new GameLoader(gameController.getPlayerNum());
            setGameController(null);
            gameLoader.setVisible(true);
        });
        backward.setLocation(820, 600);
        backward.setSize(48, 48);
        backward.setVisible(false);
        backward.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(backward);

        Icon talk = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/chat.png");
        JButton chat = new JButton(talk);
        chat.setOpaque(false);
        chat.setContentAreaFilled(false);
        chat.setBorderPainted(false);
        chat.setVisible(false);
        chat.addActionListener((e) -> {
            if (!ifChat) {
                talking.setVisible(true);
                talking.setLocation(770, 10);
                add(talking);
                talking.repaint();
                Thread thread = new Thread(talking::receive);
                thread.start();
                setIfChat(true);
            } else {
                talking.setVisible(false);
                setIfChat(false);
            }
        });
        chat.setLocation(950, 700);
        chat.setSize(48, 48);
        chat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(chat);

        Icon rule = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/rule.png");
        button = new JButton(rule);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addActionListener((e) -> {
            try {
                Desktop.getDesktop().open(new File("C:\\Users\\21548\\IdeaProjects\\Halma", "HalmaRule.pdf"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        button.setLocation(600, 777);
        button.setSize(48, 48);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(button);

        Icon kit = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/kit.png");
        button = new JButton(kit);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addActionListener((e) -> {
            if (!visible) {
                backward.setVisible(true);
                music.setVisible(true);
                reload.setVisible(true);
                chat.setVisible(true);
                music.setVisible(true);
                withdraw.setVisible(true);
                chess.setVisible(true);
                visible = true;
            } else {
                backward.setVisible(false);
                chess.setVisible(false);
                music.setVisible(false);
                reload.setVisible(false);
                chat.setVisible(false);
                music.setVisible(false);
                withdraw.setVisible(false);
                visible = false;
            }
        });
        button.setLocation(700, 777);
        button.setSize(48, 48);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(button);
    }
}
