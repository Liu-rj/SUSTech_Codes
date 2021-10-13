package halma;

import halma.controller.GameController;
import halma.controller.GameLauncher;
import halma.model.ChessBoard;
import halma.view.ChessBoardComponent;
import halma.view.GameFrame;

import javax.swing.*;

public class Halma {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameLauncher launcherFrame = new GameLauncher();
            launcherFrame.setVisible(true);
        });
    }
}
