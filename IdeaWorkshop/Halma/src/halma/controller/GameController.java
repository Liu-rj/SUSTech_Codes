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
import java.util.ArrayList;

public class GameController implements InputListener, MinimaxAI {
    private final ChessBoardComponent view;
    private final ChessBoard model;
    private ChessBoardLocation selectedLocation;
    private Color currentPlayer;
    private Color color;
    private JLabel jLabel;
    private final int playerNum;
    private JLabel label;
    private int time = 30;
    private ChessComponent chessComponent;
    private boolean moved;
    private final ArrayList<Square> squares = new ArrayList<>();
    private boolean countTime = true;
    private boolean canDo = true;
    private boolean restart = false;
    private boolean withdraw = false;
    private static ChessBoardLocation srclocation;
    private ChessBoardLocation src;
    private ChessBoardLocation dest;
    private boolean AI = false;
    private File file;
    private ChessBoardLocation startChess;
    private ChessBoardLocation endChess;
    private int id;
    private boolean ifIcon = true;
    private boolean sender=true;

    private final Icon iconGreen = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/Green.png");
    private final Icon iconRed = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/Red.png");
    private final Icon iconBlue = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/Blue.png");
    private final Icon iconMagenta = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/Magenta.png");

    private final Icon red = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/dog.png");
    private final Icon green = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/dog1.png");
    private final Icon blue = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/dog2.png");
    private final Icon magenta = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/dog3.png");

    public boolean isSender() {
        return sender;
    }

    public JLabel getjLabel() {
        return jLabel;
    }

    public boolean isCountTime() {
        return countTime;
    }

    public void setCountTime(boolean countTime) {
        this.countTime = countTime;
    }

    public void setIfIcon(boolean ifIcon) {
        this.ifIcon = ifIcon;
    }

    public void setAI(boolean AI) {
        this.AI = AI;
    }

    public Boolean getRestart() {
        return restart;
    }

    public void setRestart(Boolean restart) {
        this.restart = restart;
    }

    public Boolean getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Boolean withdraw) {
        this.withdraw = withdraw;
    }

    public void setCanDo(Boolean canDo) {
        this.canDo = canDo;
    }

    public void setCountTime(Boolean countTime) {
        this.countTime = countTime;
    }

    public static ArrayList<ChessBoardLocation> getChessBoardLocations() {
        return chessBoardLocations;
    }

    private static ArrayList<ChessBoardLocation> chessBoardLocations;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public ChessBoardLocation getEndChess() {
        return endChess;
    }

    public void setEndChess(ChessBoardLocation endChess) {
        this.endChess = endChess;
    }

    public ChessBoardLocation getStartChess() {
        return startChess;
    }

    public void setStartChess(ChessBoardLocation startChess) {
        this.startChess = startChess;
    }

    public void setSender(boolean sender) {
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public int getPlayerNum() {
        return playerNum;
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

    private final GameFrame gameFrame = new GameFrame(this, id);

    public GameController(ChessBoardComponent chessBoardComponent, ChessBoard chessBoard, int count, File file, int id) throws IOException {
        this.id = id;
        this.view = chessBoardComponent;
        this.model = chessBoard;
        playerNum = model.getPlayerNum();
        this.currentPlayer = Color.RED;
        this.file = file;
        view.registerListener(this);
        model.registerListener(view);
        if (count == 0) {
            model.placeInitialPieces();
            currentPlayer = Color.RED;
        } else if (count == 1) {
            readSave();
            currentPlayer = color;
        }
        countDown();
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

    /**
     * add
     */
    public void setChess(boolean ifIcon) {
        for (int row = 0; row < model.getDimension(); row++) {
            for (int col = 0; col < model.getDimension(); col++) {
                ChessBoardLocation location = model.getGrid()[row][col].getLocation();
                if (model.getChessPieceAt(location) != null) {
                    Color color = model.getChessPieceAt(location).getColor();
                    view.setIfIcon(ifIcon);
                    view.removeChessAtGrid(location);
                    view.setChessAtGrid(location, color);
                }
            }
        }
    }

    public void nextPlayer() {
        if (model.getPlayerNum() == 2) {
            currentPlayer = currentPlayer == Color.RED ? Color.GREEN : Color.RED;
            jLabel.setText("Current Player:");
            if (ifIcon) {
                if (currentPlayer == Color.RED) {
                    jLabel.setIcon(red);
                } else if (currentPlayer == Color.GREEN) {
                    jLabel.setIcon(green);
                }
            } else {
                if (currentPlayer == Color.RED) {
                    jLabel.setIcon(iconRed);
                } else if (currentPlayer == Color.GREEN) {
                    jLabel.setIcon(iconGreen);
                }
            }
        } else {
            Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA};
            for (int i = 0; i < colors.length; i++) {
                if (currentPlayer == colors[i]) {
                    currentPlayer = colors[(i + 1) % 4];
                    jLabel.setText("Current Player:");
                    if (ifIcon) {
                        if (currentPlayer == Color.RED) {
                            jLabel.setIcon(red);
                        } else if (currentPlayer == Color.GREEN) {
                            jLabel.setIcon(green);
                        } else if (currentPlayer == Color.BLUE) {
                            jLabel.setIcon(blue);
                        } else if (currentPlayer == Color.MAGENTA) {
                            jLabel.setIcon(magenta);
                        }
                    } else {
                        if (currentPlayer == Color.RED) {
                            jLabel.setIcon(iconRed);
                        } else if (currentPlayer == Color.GREEN) {
                            jLabel.setIcon(iconGreen);
                        } else if (currentPlayer == Color.BLUE) {
                            jLabel.setIcon(iconBlue);
                        } else if (currentPlayer == Color.MAGENTA) {
                            jLabel.setIcon(iconMagenta);
                        }
                    }
                    return;
                }
            }
        }
    }

    public void lastPlayer() {
        if (model.getPlayerNum() == 2) {
            jLabel.setText("Current Player:");
            if (ifIcon){
                if (currentPlayer == Color.RED) {
                    currentPlayer = Color.GREEN;
                    jLabel.setIcon(green);
                } else if (currentPlayer == Color.GREEN) {
                    currentPlayer = Color.RED;
                    jLabel.setIcon(red);
                }
            } else {
                if (currentPlayer == Color.RED) {
                    currentPlayer = Color.GREEN;
                    jLabel.setIcon(iconGreen);
                } else if (currentPlayer == Color.GREEN) {
                    currentPlayer = Color.RED;
                    jLabel.setIcon(iconRed);
                }
            }
        } else {
            jLabel.setText("Current Player:");
            if (ifIcon){
                if (currentPlayer == Color.RED) {
                    currentPlayer = Color.MAGENTA;
                    jLabel.setIcon(magenta);
                } else if (currentPlayer == Color.GREEN) {
                    currentPlayer = Color.BLUE;
                    jLabel.setIcon(blue);
                } else if (currentPlayer == Color.BLUE) {
                    currentPlayer = Color.RED;
                    jLabel.setIcon(red);
                } else if (currentPlayer == Color.MAGENTA) {
                    currentPlayer = Color.GREEN;
                    jLabel.setIcon(green);
                }
            } else {
                if (currentPlayer == Color.RED) {
                    currentPlayer = Color.MAGENTA;
                    jLabel.setIcon(iconMagenta);
                } else if (currentPlayer == Color.GREEN) {
                    currentPlayer = Color.BLUE;
                    jLabel.setIcon(iconBlue);
                } else if (currentPlayer == Color.BLUE) {
                    currentPlayer = Color.RED;
                    jLabel.setIcon(iconRed);
                } else if (currentPlayer == Color.MAGENTA) {
                    currentPlayer = Color.GREEN;
                    jLabel.setIcon(iconGreen);
                }
            }
        }
    }

    @Override
    public void onPlayerClickSquare(ChessBoardLocation location, SquareComponent component) throws IOException {
        model.setXyz(true);
        if (hasSelectedLocation() && model.isValidMove(getSelectedLocation(), location)) {
            for (int i = 0; i < model.getDimension(); i++) {
                for (int j = 0; j < model.getDimension(); j++) {
                    view.getGridComponents()[i][j].setPossible(false);
                    view.getGridComponents()[i][j].repaint();
                }
            }
            model.setXyz(false);//走完后set false
            chessBoardLocations = model.getLujing();
            if (chessBoardLocations == null || chessBoardLocations.size() == 0) {
                model.moveChessPiece(selectedLocation, location);
            }
            srclocation = selectedLocation;
            Thread t = new Thread(() -> {
                for (ChessBoardLocation chessBoardLocation : chessBoardLocations) {
                    model.moveChessPiece(srclocation, chessBoardLocation);
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    srclocation = chessBoardLocation;
                }
                if (AI && currentPlayer == Color.GREEN) {
                    action();
                }
            });
            t.start();
            model.setLujing(new ArrayList<>());
            time = 30;
            squares.add(model.getGridAt(selectedLocation));
            squares.add(model.getGridAt(location));
            resetSelectedLocation();
            nextPlayer();
            moved = true;
            saveGame();
        } else {
            JOptionPane.showMessageDialog(gameFrame, "Invalid Move!");
        }
    }

    public void internetMove(ChessBoardLocation start, ChessBoardLocation end) {
        model.setXyz(true);
        if (model.isValidMove(start, end)) {
            model.setXyz(false);
            chessBoardLocations = model.getLujing();
            if (chessBoardLocations == null || chessBoardLocations.size() == 0) {
                model.moveChessPiece(start, end);
            }
            srclocation = start;
            Thread thread = new Thread(() -> {
                if (getChessBoardLocations() != null) {
                    for (ChessBoardLocation chessBoardLocation : chessBoardLocations) {
                        model.moveChessPiece(srclocation, chessBoardLocation);
                        srclocation = chessBoardLocation;
                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
            model.setLujing(new ArrayList<>());
            time = 30;
            squares.add(model.getGridAt(start));
            squares.add(model.getGridAt(end));
            nextPlayer();
        }
    }

    @Override
    public void onPlayerClickChessPiece(ChessBoardLocation location, ChessComponent component, ChessBoardComponent chessBoardComponent) {
        if (canDo) {
            this.chessComponent = component;
            ChessPiece piece = model.getChessPieceAt(location);
            if (piece.getColor() == currentPlayer && (!hasSelectedLocation() || location.equals(getSelectedLocation()))) {
                if (!hasSelectedLocation()) {
                    setSelectedLocation(location);
                    model.setXyz(true);
                    for (int i = 0; i < model.getDimension(); i++) {
                        for (int j = 0; j < model.getDimension(); j++) {
                            model.setLujing(new ArrayList<>());
                            ChessBoardLocation x = new ChessBoardLocation(i, j);
                            if (model.isValidMove(location, x)) {
                                model.setCheckdest(new int[500][2]);
                                view.getGridComponents()[i][j].setPossible(true);
                                view.getGridComponents()[i][j].repaint();
                            }
                        }
                    }
                    model.setLujing(new ArrayList<>());  //新添加的  预判
                } else {
                    for (int i = 0; i < model.getDimension(); i++) {
                        for (int j = 0; j < model.getDimension(); j++) {
                            view.getGridComponents()[i][j].setPossible(false);
                            view.getGridComponents()[i][j].repaint();
                        }
                    }       //set false 新添加的
                    resetSelectedLocation();
                }
                component.setSelected(!component.isSelected());
                component.repaint();
            }
        }
    }

    public void saveGame() throws IOException {
        int[][] store = new int[17][16];
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
            File f = new File("C:\\Users\\21548\\IdeaProjects\\Halma", "saveFor2.txt");
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
            File f = new File("C:\\Users\\21548\\IdeaProjects\\Halma", "saveFor4.txt");
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
    }

    public void readSave() throws IOException {
        if (model.getPlayerNum() == 2) {
            if (file == null) {
                file = new File("C:\\Users\\21548\\IdeaProjects\\Halma", "saveFor2.txt");
            }
            Reader r = new FileReader(file);
            BufferedReader br = new BufferedReader(r);
            int redNum = 0;
            int greenNum = 0;
            if (file.exists()) {
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
                                } else if (!split[i].equals("0")) {
                                    model.getListenerList().forEach(listener -> JOptionPane.showMessageDialog(view, "Reload Error: player number mismatch!"));
                                    restart();
                                }
                            }
                        }
                        lineNum++;
                    }
                    model.getListenerList().forEach(listener -> listener.onChessBoardReload(model));
                    br.close();
                } catch (ArrayIndexOutOfBoundsException e) {
                    model.getListenerList().forEach(listener -> JOptionPane.showMessageDialog(view, "Reload Error: chess out of Bound!"));
                    restart();
                }
            }
            if (redNum != 19 || greenNum != 19) {
                model.getListenerList().forEach(listener -> JOptionPane.showMessageDialog(view, "Reload Error: wrong chess number!"));
                restart();
            } else if (model.estimateEnd()) {
                model.getListenerList().forEach(listener -> JOptionPane.showMessageDialog(view, "Reload Error: the game already has a winner!"));
                System.exit(0);
            }
        } else if (model.getPlayerNum() == 4) {
            int redNum = 0;
            int greenNum = 0;
            int blueNum = 0;
            int magentaNum = 0;
            if (file == null) {
                file = new File("C:\\Users\\21548\\IdeaProjects\\Halma\\testCase", "saveFor4_correctGame.txt");
            }
            Reader r = new FileReader(file);
            BufferedReader br = new BufferedReader(r);
            if (file.exists()) {
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
                                if (split[i].equals("1")) {
                                    model.getGrid()[lineNum][i].setPiece(new ChessPiece(Color.RED));
                                    redNum++;
                                } else if (split[i].equals("2")) {
                                    model.getGrid()[lineNum][i].setPiece(new ChessPiece(Color.BLUE));
                                    blueNum++;
                                } else if (split[i].equals("3")) {
                                    model.getGrid()[lineNum][i].setPiece(new ChessPiece(Color.GREEN));
                                    greenNum++;
                                } else if (split[i].equals("4")) {
                                    model.getGrid()[lineNum][i].setPiece(new ChessPiece(Color.MAGENTA));
                                    magentaNum++;
                                } else if (!split[i].equals("0")) {
                                    model.getListenerList().forEach(listener -> JOptionPane.showMessageDialog(view, "Reload Error: player number mismatch!"));
                                    restart();
                                }
                            }
                        }
                        lineNum++;
                    }
                    model.getListenerList().forEach(listener -> listener.onChessBoardReload(model));
                    br.close();
                } catch (ArrayIndexOutOfBoundsException e) {
                    model.getListenerList().forEach(listener -> JOptionPane.showMessageDialog(view, "Reload Error: chess out of Bound!"));
                    restart();
                }
            }
            if (redNum != 13 || greenNum != 13 || blueNum != 13 || magentaNum != 13) {
                model.getListenerList().forEach(listener -> JOptionPane.showMessageDialog(view, "Reload Error: wrong chess number!"));
                restart();
            } else if (model.isIfEnd()) {
                model.getListenerList().forEach(listener -> JOptionPane.showMessageDialog(view, "Reload Error: the game already has a winner!"));
                restart();
            }
        }
    }

    public void restart() {
        model.placeInitialPieces();
        view.onChessBoardReload(model);
        currentPlayer = Color.RED;
        this.restart = true;
    }

    public String toString(Color currentPlayer) {
        if (currentPlayer == Color.RED) {
            return "RED";
        } else if (currentPlayer == Color.GREEN) {
            return "GREEN";
        } else if (currentPlayer == Color.BLUE) {
            return "BLUE";
        } else if (currentPlayer == Color.MAGENTA) {
            return "MAGENTA";
        }
        return "RED";
    }

    public void countDown() {
        Timer timer = new Timer(1000, e -> {
            if (time > 0) {
                if (moved) {
                    time = 30;
                    moved = false;
                }
                if (countTime) {
                    time--;
                }
                label.setText("Time Left:" + time);
            } else if (time == 0) {
                resetSelectedLocation();
                /**添加*/
                setCanDo(true);
                if (chessComponent != null) {
                    chessComponent.setSelected(false);
                    chessComponent.repaint();
                    for (int i = 0; i < model.getDimension(); i++) {
                        for (int j = 0; j < model.getDimension(); j++) {
                            view.getGridComponents()[i][j].setPossible(false);
                            view.getGridComponents()[i][j].repaint();
                        }
                    }
                    resetSelectedLocation();
                }
                if (sender) {
                    go();
                }
                time = 30;
                label.setText("Time Left:" + time);
            }
        });
        timer.start();
    }

    public void withdraw() throws IOException {
        try {
            ChessBoardLocation location1 = squares.get(squares.size() - 1).getLocation();
            ChessBoardLocation location2 = squares.get(squares.size() - 2).getLocation();
            model.moveChessPiece(location1, location2);
            squares.remove(squares.get(squares.size() - 1));
            squares.remove(squares.get(squares.size() - 1));
            resetSelectedLocation();
            moved = true;
            lastPlayer();
            saveGame();
            withdraw = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(view, "haven't moved chess!");
        }
    }

    @Override
    public void action() {
        setCanDo(false);
        int estimate = 0;
        if (ultimate()) {
            for (int i = 0; i < 5; i++) {
                if (model.getGrid()[0][i].getPiece() == null) {
                    for (Square square : chooseUltimateSquare()) {
                        if (model.isValidMove(square.getLocation(), model.getGrid()[0][i].getLocation())) {
                            internetMove(square.getLocation(), model.getGrid()[0][i].getLocation());
                            return;
                        }
                    }
                }
                if (model.getGrid()[1][i].getPiece() == null) {
                    for (Square square : chooseUltimateSquare()) {
                        if (model.isValidMove(square.getLocation(), model.getGrid()[0][i].getLocation())) {
                            internetMove(square.getLocation(), model.getGrid()[0][i].getLocation());
                            return;
                        }
                    }
                }
            }
            for (int i = 0; i < 4; i++) {
                if (model.getGrid()[2][i].getPiece() == null) {
                    for (Square square : chooseUltimateSquare()) {
                        if (model.isValidMove(square.getLocation(), model.getGrid()[0][i].getLocation())) {
                            internetMove(square.getLocation(), model.getGrid()[0][i].getLocation());
                            return;
                        }
                    }
                }
            }
            for (int i = 0; i < 3; i++) {
                if (model.getGrid()[3][i].getPiece() == null) {
                    for (Square square : chooseUltimateSquare()) {
                        if (model.isValidMove(square.getLocation(), model.getGrid()[0][i].getLocation())) {
                            internetMove(square.getLocation(), model.getGrid()[0][i].getLocation());
                            return;
                        }
                    }
                }
            }
            for (int i = 0; i < 2; i++) {
                if (model.getGrid()[4][i].getPiece() == null) {
                    for (Square square : chooseUltimateSquare()) {
                        if (model.isValidMove(square.getLocation(), model.getGrid()[0][i].getLocation())) {
                            internetMove(square.getLocation(), model.getGrid()[0][i].getLocation());
                            return;
                        }
                    }
                }
            }
        } else {
            for (Square[] squares : model.getGrid()) {
                for (Square square : squares) {
                    if (square.getPiece() != null) {
                        if (square.getPiece().getColor() == Color.GREEN) {
                            ChessBoardLocation location = square.getLocation();
                            for (int i = 0; i < model.getDimension(); i++) {
                                for (int j = 0; j < model.getDimension(); j++) {
                                    ChessBoardLocation x = new ChessBoardLocation(i, j);
                                    if (model.isValidMove(location, x)) {
                                        if (x.getRow() < location.getRow() && x.getColumn() < location.getColumn()) {
                                            if (estimate(square, x) > estimate) {
                                                estimate = estimate(square, x);
                                                src = location;
                                                dest = x;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            internetMove(src, dest);
            src = null;
            dest = null;
            setCanDo(true);
        }
    }

    @Override
    public int estimate(Square square, ChessBoardLocation x) {
        int value = 0;//权重走棋子的价值
        int distance = 0;
        ChessBoardLocation chessBoardLocation = square.getLocation();
        square.setLocation(x);
        for (Square[] squares : model.getGrid()) {
            for (Square square1 : squares) {
                if (square1.getPiece() != null) {
                    if (square1.getPiece().getColor() == Color.GREEN) {
                        ChessBoardLocation location = square1.getLocation();
                        for (int i = 0; i < model.getDimension(); i++) {
                            for (int j = 0; j < model.getDimension(); j++) {
                                ChessBoardLocation dest = new ChessBoardLocation(i, j);
                                if (model.isValidMove(location, dest)) {
                                    if (((dest.getRow() - location.getRow()) ^ 2 + (dest.getColumn() - location.getColumn()) ^ 2) >= 9) {
                                        value++;
                                    }
                                    if (dest.getRow() < location.getRow() && dest.getColumn() < location.getColumn()) {
                                        value++;
                                    }
                                    if (((dest.getRow() - location.getRow()) ^ 2 + (dest.getColumn() - location.getColumn()) ^ 2) > distance) {
                                        distance = ((dest.getRow() - location.getRow()) ^ 2 + (dest.getColumn() - location.getColumn()) ^ 2);
                                        value++;
                                    }
                                    int beforeNum = countNumber();
                                    square1.setLocation(dest);
                                    if (countNumber() > beforeNum) {
                                        value++;
                                    }
                                    square1.setLocation(location);
                                }
                            }
                        }
                    }
                }
            }
        }
        for (Square[] squares : model.getGrid()) {
            for (Square square1 : squares) {
                if (square1.getPiece() != null) {
                    if (square1.getPiece().getColor() == Color.RED) {
                        ChessBoardLocation location = square1.getLocation();
                        for (int i = 0; i < model.getDimension(); i++) {
                            for (int j = 0; j < model.getDimension(); j++) {
                                ChessBoardLocation dest = new ChessBoardLocation(i, j);
                                if (model.isValidMove(location, dest)) {
                                    if (((dest.getRow() - location.getRow()) ^ 2 + (dest.getColumn() - location.getColumn()) ^ 2) < 9) {
                                        value++;
                                    }
                                    if (dest.getRow() < location.getRow() && dest.getColumn() < location.getColumn()) {
                                        value++;
                                    }
                                    if (((x.getRow() - location.getRow()) ^ 2 + (x.getColumn() - location.getColumn()) ^ 2) < distance) {
                                        distance = ((x.getRow() - location.getRow()) ^ 2 + (x.getColumn() - location.getColumn()) ^ 2);
                                        value++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        square.setLocation(chessBoardLocation);
        return value;
    }

    public int countNumber() {
        int number = 0;
        for (int i = 0; i < 5; i++) {
            if (model.getGrid()[0][i].getPiece() != null && model.getGrid()[0][i].getPiece().getColor() == Color.GREEN) {
                number++;
            }
            if (model.getGrid()[1][i].getPiece() != null && model.getGrid()[1][i].getPiece().getColor() == Color.GREEN) {
                number++;
            }
        }
        for (int i = 0; i < 4; i++) {
            if (model.getGrid()[2][i].getPiece() != null && model.getGrid()[2][i].getPiece().getColor() == Color.GREEN) {
                number++;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (model.getGrid()[3][i].getPiece() != null && model.getGrid()[3][i].getPiece().getColor() == Color.GREEN) {
                number++;
            }
        }
        for (int i = 0; i < 2; i++) {
            if (model.getGrid()[4][i].getPiece() != null && model.getGrid()[4][i].getPiece().getColor() == Color.GREEN) {
                number++;
            }
        }
        return number;
    }

    public boolean ultimate() {
        if (countNumber() >= 15) {
            return true;
        }
        return false;
    }

    public ArrayList<Square> chooseUltimateSquare() {
        ArrayList<Square> squares = new ArrayList<>();
        for (int i = 0; i < model.getDimension(); i++) {
            for (int j = 0; j < model.getDimension(); j++) {
                if (model.getGrid()[i][j].getPiece() != null && model.getGrid()[i][j].getPiece().getColor() == Color.GREEN) {
                    if (i == 0 && j >= 5) {
                        squares.add(model.getGrid()[i][j]);
                    } else if (i == 1 && j >= 5) {
                        squares.add(model.getGrid()[i][j]);
                    } else if (i == 2 && j >= 4) {
                        squares.add(model.getGrid()[i][j]);
                    } else if (i == 3 && j >= 3) {
                        squares.add(model.getGrid()[i][j]);
                    } else if (i == 4 && j >= 3) {
                        squares.add(model.getGrid()[i][j]);
                    } else if (i >= 5) {
                        squares.add(model.getGrid()[i][j]);
                    }
                }
            }
        }
        return squares;
    }

    public void go() {
        int distance = 0;
        for (Square[] squares : model.getGrid()) {
            for (Square square : squares) {
                if (square.getPiece() != null) {
                    if (square.getPiece().getColor() == currentPlayer) {
                        ChessBoardLocation location = square.getLocation();
                        for (int i = 0; i < model.getDimension(); i++) {
                            for (int j = 0; j < model.getDimension(); j++) {
                                ChessBoardLocation x = new ChessBoardLocation(i, j);
                                if (model.isValidMove(location, x)) {
                                    src = location;
                                    dest = x;
                                    if (((x.getRow() - location.getRow()) ^ 2 + (x.getColumn() - location.getColumn()) ^ 2) > distance) {
                                        distance = ((x.getRow() - location.getRow()) ^ 2 + (x.getColumn() - location.getColumn()) ^ 2);
                                        src = location;
                                        dest = x;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        internetMove(src, dest);
        dest = null;
        src = null;
        if (AI && currentPlayer == Color.GREEN) {
            action();
        }
    }
}
