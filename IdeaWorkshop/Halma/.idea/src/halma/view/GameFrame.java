package halma.view;

import halma.controller.GameController;
import halma.controller.GameLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

public class GameFrame extends JFrame {
    private GameController gameController;
    private JButton button;
    private int time = 30;

    public GameFrame() throws HeadlessException {
    }

    public GameFrame(GameController gameController) {
        this.gameController = gameController;
        setTitle("2020 CS102A Project Halma Game");
        setSize(770, 900);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel statusLabel = new JLabel("Current Player:" + gameController.toString(gameController.getColor()));
        statusLabel.setLocation(0, 760);
        statusLabel.setSize(500, 30);
        statusLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
        //Icon icon = new ImageIcon("/images/Green.png");
        //statusLabel.setIcon(icon);
        if (gameController.getColor() == null) {
            statusLabel.setForeground(Color.RED);
        } else {
            statusLabel.setForeground(gameController.getColor());
        }
        add(statusLabel);
        gameController.setjLabel(statusLabel);

        JLabel label = new JLabel("Time Left:" + time);
        label.setVisible(true);
        add(label);
        label.setLocation(550, 760);
        label.setSize(300, 30);
        label.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
        gameController.setLabel(label);

        button = new JButton("click to save");
        button.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        button.addActionListener((e) -> {
            try {
                gameController.saveGame();
                JOptionPane.showMessageDialog(this, "Saved Successfully!");
                this.dispose();
                System.exit(0);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        button.setLocation(540, 800);
        button.setSize(200, 40);
        add(button);

        button = new JButton("click to restart");
        button.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        button.addActionListener((e) -> {
            gameController.restart();
            JOptionPane.showMessageDialog(this, "Restart Successfully!");
        });
        button.setLocation(270, 800);
        button.setSize(200, 40);
        add(button);

        button = new JButton("click to return");
        button.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        button.addActionListener((e) -> {
            this.dispose();
            GameLoader gameLoader = new GameLoader(gameController.getPlayerNum());
            gameLoader.setVisible(true);
        });
        button.setLocation(5, 800);
        button.setSize(200, 40);
        add(button);
    }
}
