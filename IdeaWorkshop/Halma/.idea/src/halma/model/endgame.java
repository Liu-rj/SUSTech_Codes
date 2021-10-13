package halma.model;

import com.sun.javaws.Launcher;
import halma.Halma;
import halma.controller.GameLauncher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class endgame extends JFrame {
    public endgame(String x) {
        JFrame endgame = new JFrame();
        endgame.setVisible(true);
        endgame.setBounds(500, 500, 500, 500);
        JLabel winner = new JLabel(x);
        winner.setBounds(0, 0, 200, 80);
        winner.setVisible(true);
        winner.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        Container c = endgame.getContentPane();
        c.setLayout(null);
        c.setBounds(500, 500, 500, 500);
        c.setVisible(true);
        c.add(winner);
        JButton play = new JButton("one more");
        play.setBounds(200, 200, 100, 100);
        play.setVisible(true);
        JButton end = new JButton("end the game");
        end.setBounds(50, 200, 100, 100);
        end.setVisible(true);
        c.add(play);
        c.add(end);
        play.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            GameLauncher launcherFrame = new GameLauncher();
            launcherFrame.setVisible(true);
        }));
        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
