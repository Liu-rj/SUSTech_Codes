package halma;

import halma.controller.GameLauncher;

import javax.swing.*;

public class receive3 {  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        GameLauncher launcherFrame = new GameLauncher();
        launcherFrame.setVisible(true);launcherFrame.setId(4);
    });
}
}
