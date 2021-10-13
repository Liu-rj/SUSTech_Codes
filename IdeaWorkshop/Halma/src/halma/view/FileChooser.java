package halma.view;

import halma.controller.GameController;
import halma.controller.GameLoader;
import halma.model.ChessBoard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class FileChooser {
    private JLabel label = new JLabel("File Path：");
    private JTextField jtf = new JTextField(25);
    private JButton button = new JButton("Look Through");
    private File file;
    private GameLoader gameLoader;
    private JFrame jf;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileChooser(GameLoader gameLoader) {
        jf = new JFrame("File Chooser");
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(jtf);
        panel.add(button);
        jf.add(panel);
        jf.pack();    //自动调整大小
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        button.addActionListener(new MyActionListener());    //监听按钮事件

        this.gameLoader = gameLoader;
    }

    //Action事件处理
    class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            JFileChooser fc = new JFileChooser();
            int val = fc.showOpenDialog(null);    //文件打开对话框
            if (val == JFileChooser.APPROVE_OPTION) {
                //正常选择文件
                jtf.setText(fc.getSelectedFile().toString());
                file = fc.getSelectedFile();
                gameLoader.dispose();
                jf.dispose();
                ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 16);
                ChessBoard chessBoard = null;
                try {
                    chessBoard = new ChessBoard(16, gameLoader.getPlayer());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                GameController controller = null;
                try {
                    controller = new GameController(chessBoardComponent, chessBoard, 1, getFile(), gameLoader.getId());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                assert controller != null;
                GameFrame mainFrame = null;
                mainFrame = new GameFrame(controller , gameLoader.getId());
                mainFrame.add(chessBoardComponent);
                mainFrame.setVisible(true);
            } else {
                //未正常选择文件，如选择取消按钮
                jtf.setText("未选择文件");
            }
        }
    }
}
