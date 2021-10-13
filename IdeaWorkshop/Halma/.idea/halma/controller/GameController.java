package halma.controller;

import halma.listener.InputListener;
import halma.model.ChessBoard;
import halma.model.ChessBoardLocation;
import halma.model.ChessPiece;
import halma.model.Square;
import halma.view.ChessBoardComponent;
import halma.view.ChessComponent;
import halma.view.GameFrame;
import halma.view.SquareComponent;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class GameController implements InputListener {
    private ChessBoardComponent view;
    private ChessBoard model;
    private ChessBoardLocation selectedLocation;
    private Color currentPlayer;
    private Color color;
    private int[][] store = new int[17][16];
    private JLabel jLabel;
    private int playerNum;


    public int getPlayerNum() {
        return playerNum;
    }

    public JLabel getjLabel() {
        return jLabel;
    }

    public void setjLabel(JLabel jLabel) {
        this.jLabel = jLabel;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Color currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int[][] getStore() {
        return store;
    }

    public void setStore(int[][] store) {
        this.store = store;
    }

    private GameFrame gameFrame = new GameFrame(this);

    public GameController(ChessBoardComponent chessBoardComponent, ChessBoard chessBoard, int count) throws IOException {
        this.view = chessBoardComponent;
        this.model = chessBoard;
        playerNum = model.getPlayerNum();
        this.currentPlayer = Color.RED;
        view.registerListener(this);
        model.registerListener(view);
        if (count == 0) {
            model.placeInitialPieces();
            currentPlayer = Color.RED;
        } else if (count == 1){
            readSave();
            currentPlayer = color;
        }
    }

    public ChessBoardLocation getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(ChessBoardLocation location) {
        this.selectedLocation = location;
    }

    public void resetSelectedLocation() {
        setSelectedLocation(null);
    }

    public boolean hasSelectedLocation() {
        return selectedLocation != null;
    }

    public Color nextPlayer() {
        if (model.getPlayerNum() == 2) {
            currentPlayer = currentPlayer == Color.RED ? Color.GREEN : Color.RED;
            jLabel.setText("current player:" + this.toString(currentPlayer));
            jLabel.setForeground(currentPlayer);
            return currentPlayer;
        } else {
            Color[] colors = {Color.RED,Color.BLUE,Color.GREEN,Color.MAGENTA};
            for (int i = 0; i < colors.length; i++) {
                if (currentPlayer == colors[i]){
                    currentPlayer = colors[(i + 1) % 4];
                    jLabel.setText("current player:" + this.toString(currentPlayer));
                    jLabel.setForeground(currentPlayer);
                    return currentPlayer;
                }
            }
        }
        return currentPlayer;
    }

    @Override



    public void onPlayerClickSquare(ChessBoardLocation location, SquareComponent component) {
      if (hasSelectedLocation() && model.isValidMove(getSelectedLocation(), location)){
      //view.getGridComponents()[location.getRow()][location.getColumn()].isValid()) {
            model.moveChessPiece(selectedLocation, location);
            resetSelectedLocation();               //   for (int i=0;i<model.getDimension()-1;i++){for (int j=0;j<model.getDimension()-1;j++){ view.getGridComponents()[i][j].setValid1(false);view.getGridComponents()[i][j].repaint();}}

          nextPlayer();
        } else {
            JOptionPane.showMessageDialog(gameFrame, "Invalid Move!");
        }
    }


    @Override
    public void onPlayerClickChessPiece(ChessBoardLocation location, ChessComponent component) {
        ChessPiece piece = model.getChessPieceAt(location);
        if (piece.getColor() == currentPlayer && (!hasSelectedLocation() || location.equals(getSelectedLocation()))) {
            if (!hasSelectedLocation()) {
                setSelectedLocation(location);//for (int i=0;i<model.getDimension()-1;i++){for (int j=0;j<model.getDimension()-1;j++){ ChessBoardLocation x=new ChessBoardLocation(i,j);if (model.isValidMove(location,x)){view.getGridComponents()[i][j].setValid1(true);view.getGridComponents()[i][j].repaint();}}}
            } else {
                resetSelectedLocation();//for (int i=0;i<model.getDimension()-1;i++){for (int j=0;j<model.getDimension()-1;j++){view.getGridComponents()[i][j].setValid1(false);view.getGridComponents()[i][j].repaint();}}
            }
            component.setSelected(!component.isSelected());
            component.repaint();
        }
    }

    public void saveGame() throws IOException {
        if (model.getPlayerNum() == 2) {
            if (currentPlayer == Color.RED) {
                store[16][0] = 0;
            } else if (currentPlayer == Color.GREEN) {
                store[16][0] = 1;
            }
            for (int i = 0; i < model.getGrid().length; i++) {
                for (int j = 0; j < model.getGrid()[0].length; j++) {
                    try {
                        if (model.getGrid()[i][j].getPiece().getColor() == Color.RED) {
                            store[i][j] = 1;
                        } else if (model.getGrid()[i][j].getPiece().getColor() == Color.GREEN) {
                            store[i][j] = 2;
                        }
                    } catch (NullPointerException e) {
                        store[i][j] = 0;
                    }
                }
            }
            //File f = new File("C:\\Users\\21548\\IdeaProjects\\Halma", "saveFor2.txt");
            File f = new File("D:\\Halma", "saveFor2.txt");
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            for (int[] ints : store) {
                for (int j = 0; j < store[0].length; j++) {
                    fw.write(ints[j] + "\t");
                }
                fw.write("\r\n");
            }
            fw.close();
        } else if (model.getPlayerNum() == 4) {
            if (currentPlayer == Color.RED) {
                store[16][0] = 0;
            } else if (currentPlayer == Color.BLUE) {
                store[16][0] = 1;
            } else if (currentPlayer == Color.GREEN) {
                store[16][0] = 2;
            } else if (currentPlayer == Color.MAGENTA) {
                store[16][0] = 3;
            }
            for (int i = 0; i < model.getGrid().length; i++) {
                for (int j = 0; j < model.getGrid()[0].length; j++) {
                    try {
                        if (model.getGrid()[i][j].getPiece().getColor() == Color.RED) {
                            store[i][j] = 1;
                        } else if (model.getGrid()[i][j].getPiece().getColor() == Color.BLUE) {
                            store[i][j] = 2;
                        } else if (model.getGrid()[i][j].getPiece().getColor() == Color.GREEN) {
                            store[i][j] = 3;
                        } else if (model.getGrid()[i][j].getPiece().getColor() == Color.MAGENTA) {
                            store[i][j] = 4;
                        }
                    } catch (NullPointerException e) {
                        store[i][j] = 0;
                    }
                }
            }
           // File f = new File("C:\\Users\\21548\\IdeaProjects\\Halma", "saveFor4.txt");
            File f = new File("D:\\Halma", "saveFor4.txt");
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            for (int[] ints : store) {
                for (int j = 0; j < store[0].length; j++) {
                    fw.write(ints[j] + "\t");
                }
                fw.write("\r\n");
            }
            fw.close();
        }
        store = new int[16][16];
    }

    public void readSave() throws IOException {
        if (model.getPlayerNum() == 2) {
           // File f = new File("C:\\Users\\21548\\IdeaProjects\\Halma", "saveFor2.txt");
            File f = new File("D:\\Halma", "saveFor2.txt");
            Reader r = new FileReader(f);
            BufferedReader br = new BufferedReader(r);
            int redNum = 0;
            int greenNum = 0;
            if (f.exists()) {
                String s;
                int lineNum = 0;
                try {
                    while ((s = br.readLine()) != null) {
                        String[] split = s.split("\t");
                        if (lineNum == 16) {
                            switch (split[0]) {
                                case "0":
                                    color = Color.RED;
                                    break;
                                case "1":
                                    color = Color.GREEN;
                                    break;
                            }
                            break;
                        } else {
                            for (int i = 0; i < split.length; i++) {
                                if (split[i].equals("1")) {
                                    model.getGrid()[lineNum][i].setPiece(new ChessPiece(Color.RED));
                                    redNum++;
                                } else if (split[i].equals("2")) {
                                    model.getGrid()[lineNum][i].setPiece(new ChessPiece(Color.GREEN));
                                    greenNum++;
                                } else if (!split[i].equals("0")){
                                    model.getListenerList().forEach(listener -> JOptionPane.showMessageDialog(view, "Reload Error: player number mismatch!"));
                                    restart();
                                }
                            }
                        }
                        lineNum++;
                    }
                    model.getListenerList().forEach(listener -> listener.onChessBoardReload(model));
                    br.close();
                } catch (ArrayIndexOutOfBoundsException e){
                    model.getListenerList().forEach(listener -> JOptionPane.showMessageDialog(view, "Reload Error: chess out of Bound!"));
                    restart();
                }
            }
            if (redNum != 19 || greenNum != 19){
                model.getListenerList().forEach(listener -> JOptionPane.showMessageDialog(view, "Reload Error: wrong chess number!"));
                restart();
            } else if (model.isIfEnd()){
                model.getListenerList().forEach(listener -> JOptionPane.showMessageDialog(view, "Reload Error: the game already has a winner!"));
                restart();
            }
        } else if (model.getPlayerNum() == 4) {
            int redNum = 0;
            int greenNum = 0;
            int blueNum = 0;
            int magentaNum = 0;
            //File f = new File("C:\\Users\\21548\\IdeaProjects\\Halma", "saveFor4.txt");
            File f = new File("D:\\Halma", "saveFor4.txt");
            Reader r = new FileReader(f);
            BufferedReader br = new BufferedReader(r);
            if (f.exists()) {
                String s;
                int lineNum = 0;
                try {
                    while ((s = br.readLine()) != null) {
                        String[] split = s.split("\t");
                        if (lineNum == 16) {
                            switch (split[0]) {
                                case "0":
                                    color = Color.RED;
                                    break;
                                case "1":
                                    color = Color.BLUE;
                                    break;
                                case "2":
                                    color = Color.GREEN;
                                    break;
                                case "3":
                                    color = Color.MAGENTA;
                                    break;
                            }
                            break;
                        } else {
                            for (int i = 0; i < split.length; i++) {
                                if(split[i].equals("1")) {
                                    model.getGrid()[lineNum][i].setPiece(new ChessPiece(Color.RED));
                                    redNum++;
                                } else if (split[i].equals("2")) {
                                    model.getGrid()[lineNum][i].setPiece(new ChessPiece(Color.BLUE));
                                    blueNum++;
                                } else if (split[i].equals("3")) {
                                    model.getGrid()[lineNum][i].setPiece(new ChessPiece(Color.GREEN));
                                    greenNum++;
                                } else if (split[i].equals("4")){
                                    model.getGrid()[lineNum][i].setPiece(new ChessPiece(Color.MAGENTA));
                                    magentaNum++;
                                } else if (!split[i].equals("0")){
                                    model.getListenerList().forEach(listener -> JOptionPane.showMessageDialog(view, "Reload Error: player number mismatch!"));
                                    restart();
                                }
                            }
                        }
                        lineNum++;
                    }
                    model.getListenerList().forEach(listener -> listener.onChessBoardReload(model));
                    br.close();
                } catch (ArrayIndexOutOfBoundsException e){
                    model.getListenerList().forEach(listener -> JOptionPane.showMessageDialog(view, "Reload Error: chess out of Bound!"));
                    restart();
                }
            }
            if (redNum != 13 || greenNum != 13 || blueNum != 13 || magentaNum != 13){
                model.getListenerList().forEach(listener -> JOptionPane.showMessageDialog(view, "Reload Error: wrong chess number!"));
                restart();
            } else if (model.isIfEnd()){
                model.getListenerList().forEach(listener -> JOptionPane.showMessageDialog(view, "Reload Error: the game already has a winner!"));
                restart();
            }
        }
    }

    public void restart(){
        model.placeInitialPieces();
        view.onChessBoardReload(model);
        currentPlayer = Color.RED;
    }

    public String toString(Color currentPlayer){
        if (currentPlayer == Color.RED){
            return "RED";
        } else if (currentPlayer == Color.GREEN){
            return "GREEN";
        } else if (currentPlayer == Color.BLUE){
            return "BLUE";
        } else if (currentPlayer == Color.MAGENTA){
            return "MAGENTA";
        }
        return "RED";
    }
}
