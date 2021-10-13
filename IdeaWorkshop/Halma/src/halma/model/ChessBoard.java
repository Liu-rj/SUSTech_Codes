package halma.model;

import halma.listener.GameListener;
import halma.listener.Listenable;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChessBoard implements Listenable<GameListener> {
    private List<GameListener> listenerList = new ArrayList<>();
    private Square[][] grid;
    private int dimension;
    private int playerNum;
    private int count;
    private int many;
    private int[][] tobre = new int[500][2];
    private Boolean xyz;//add location to path
    private ArrayList<ChessBoardLocation> lujing;
    /**add*/
    private final File f = new File("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\sound/Move1.wav");
    private final URI uri = f.toURI();
    private final URL url = uri.toURL();
    private final AudioClip audioClip = Applet.newAudioClip(url);

    public void setXyz(Boolean xyz) {
        this.xyz = xyz;
    }

    public Boolean getXyz() {
        return xyz;
    }

    public ArrayList<ChessBoardLocation> getLujing() {
        return lujing;
    }

    public void setLujing(ArrayList<ChessBoardLocation> lujing) {
        this.lujing = lujing;
    }

    public void initillzy() {
        checkdest = new int[500][2];
        for (int i = 0; i < checkdest.length; i++) {
            checkdest[i][0] = checkdest[i][1] = -1;
        }
    }


    public int[][] getTobre() {
        return tobre;
    }

    public boolean isIfEnd() {
        return ifEnd;
    }

    public void setIfEnd(boolean ifEnd) {
        this.ifEnd = ifEnd;
    }

    private boolean ifEnd = false;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int[][] getCheckdest() {
        return checkdest;
    }

    public void setCheckdest(int[][] checkdest) {
        this.checkdest = checkdest;
    }

    private int[][] checkdest = new int[500][2];

    public Square[][] getGrid() {
        return grid;
    }

    public void setGrid(Square[][] grid) {
        this.grid = grid;
    }

    public List<GameListener> getListenerList() {
        return listenerList;
    }

    public void setListenerList(List<GameListener> listenerList) {
        this.listenerList = listenerList;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public ChessBoard(int dimension, int playerNum) throws MalformedURLException {
        this.grid = new Square[dimension][dimension];
        this.dimension = dimension;
        this.playerNum = playerNum;
        initGrid();
    }

    private void initGrid() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                grid[i][j] = new Square(new ChessBoardLocation(i, j));
            }
        }
    }

    public void placeInitialPieces() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                grid[i][j].setPiece(null);
            }
        }
        if (playerNum == 2) {
            for (int i = 0; i < 5; i++) {
                grid[0][i].setPiece(new ChessPiece(Color.RED));
                grid[1][i].setPiece(new ChessPiece(Color.RED));
                grid[dimension - 1][dimension - i - 1].setPiece(new ChessPiece(Color.GREEN));
                grid[dimension - 2][dimension - i - 1].setPiece(new ChessPiece(Color.GREEN));
            }
            for (int i = 0; i < 4; i++) {
                grid[2][i].setPiece(new ChessPiece(Color.RED));
                grid[dimension - 3][dimension - i - 1].setPiece(new ChessPiece(Color.GREEN));
            }
            for (int i = 0; i < 3; i++) {
                grid[3][i].setPiece(new ChessPiece(Color.RED));
                grid[dimension - 4][dimension - i - 1].setPiece(new ChessPiece(Color.GREEN));
            }
            for (int i = 0; i < 2; i++) {
                grid[4][i].setPiece(new ChessPiece(Color.RED));
                grid[dimension - 5][dimension - i - 1].setPiece(new ChessPiece(Color.GREEN));
            }
            listenerList.forEach(listener -> listener.onChessBoardReload(this));
        } else if (playerNum == 4) {
            for (int i = 0; i < 4; i++) {
                grid[0][i].setPiece(new ChessPiece(Color.RED));
                grid[1][i].setPiece(new ChessPiece(Color.RED));
                grid[dimension - 1][dimension - i - 1].setPiece(new ChessPiece(Color.GREEN));
                grid[dimension - 2][dimension - i - 1].setPiece(new ChessPiece(Color.GREEN));
                grid[0][dimension - i - 1].setPiece(new ChessPiece(Color.BLUE));
                grid[1][dimension - i - 1].setPiece(new ChessPiece(Color.BLUE));
                grid[dimension - 1][i].setPiece(new ChessPiece(Color.MAGENTA));
                grid[dimension - 2][i].setPiece(new ChessPiece(Color.MAGENTA));
            }
            for (int i = 0; i < 3; i++) {
                grid[2][i].setPiece(new ChessPiece(Color.RED));
                grid[dimension - 3][dimension - i - 1].setPiece(new ChessPiece(Color.GREEN));
                grid[2][dimension - i - 1].setPiece(new ChessPiece(Color.BLUE));
                grid[dimension - 3][i].setPiece(new ChessPiece(Color.MAGENTA));
            }
            for (int i = 0; i < 2; i++) {
                grid[3][i].setPiece(new ChessPiece(Color.RED));
                grid[dimension - 4][dimension - i - 1].setPiece(new ChessPiece(Color.GREEN));
                grid[3][dimension - i - 1].setPiece(new ChessPiece(Color.BLUE));
                grid[dimension - 4][i].setPiece(new ChessPiece(Color.MAGENTA));
            }
            listenerList.forEach(listener -> listener.onChessBoardReload(this));
        }
    }

    public Square getGridAt(ChessBoardLocation location) {
        return grid[location.getRow()][location.getColumn()];
    }

    public ChessPiece getChessPieceAt(ChessBoardLocation location) {
        return getGridAt(location).getPiece();
    }

    public void setChessPieceAt(ChessBoardLocation location, ChessPiece piece) {
        getGridAt(location).setPiece(piece);
        listenerList.forEach(listener -> listener.onChessPiecePlace(location, piece));
    }

    public ChessPiece removeChessPieceAt(ChessBoardLocation location) {
        ChessPiece piece = getGridAt(location).getPiece();
        getGridAt(location).setPiece(null);
        listenerList.forEach(listener -> listener.onChessPieceRemove(location));
        return piece;
    }

    public void moveChessPiece(ChessBoardLocation src, ChessBoardLocation dest) {
        setChessPieceAt(dest, removeChessPieceAt(src));
        audioClip.play();
        estimateEnd();
    }

    public int getDimension() {
        return dimension;
    }

    public boolean check(int[][] checkdest, ChessBoardLocation dest) {
        for (int i = 0; i < checkdest.length; i++) {
            if (checkdest[i][0] == dest.getRow() && checkdest[i][1] == dest.getColumn()) {
                return false;
            }
        }
        return true;
    }

    public ChessBoardLocation linecheck(ChessBoardLocation chsrc, ChessBoardLocation chde, ChessBoardLocation trusrc) {                   //行判断
        int disrow = chde.getRow() - chsrc.getRow();
        int discol = chde.getColumn() - chsrc.getColumn();
        for (int i = 1; chsrc.getRow() + i * disrow >= 0 && chsrc.getRow() + i * disrow <= dimension - 1 && chsrc.getColumn() + i * discol >= 0 && chsrc.getColumn() + i * discol <= dimension - 1; i++) {
            ChessBoardLocation y = new ChessBoardLocation(chsrc.getRow() + i * disrow, chsrc.getColumn() + i * discol);
            if (getChessPieceAt(y) != null && !y.equals(trusrc)) {
                if (chsrc.getRow() + i * disrow * 2 >= 0 && chsrc.getRow() + i * disrow * 2 <= dimension - 1 && chsrc.getColumn() + i * discol * 2 >= 0 && chsrc.getColumn() + i * discol * 2 <= dimension - 1) {
                    for (int j = 1; j < i; j++) {
                        ChessBoardLocation z = new ChessBoardLocation(chsrc.getRow() + (i + j) * disrow, chsrc.getColumn() + (i + j) * discol);
                        if (getChessPieceAt(z) != null) {
                            return null;
                        }
                    }
                    ChessBoardLocation y1 = new ChessBoardLocation(chsrc.getRow() + i * disrow * 2, chsrc.getColumn() + i * discol * 2);
                    return y1;
                } else return null;
            }
        }
        return null;
    }

    public ChessBoardLocation checkDest12(ChessBoardLocation src, ChessBoardLocation dest, ChessBoardLocation trusrc, ChessBoardLocation forsrc) {
        int srccol = src.getColumn();
        int srcrow = src.getRow();
        ChessBoardLocation[] x = new ChessBoardLocation[8];
        ChessBoardLocation[] y = new ChessBoardLocation[8];
        many = 0;
        int j = 0;
        if (srcrow - 2 >= 0 && srccol - 2 >= 0) {
            ChessBoardLocation x1 = new ChessBoardLocation(srcrow - 1, srccol - 1);
            x[many] = linecheck(src, x1, trusrc);
            if (x[many] != null) {
                if (x[many].equals(trusrc)) {
                    j++;
                }
                if (!x[many].equals(forsrc) && getChessPieceAt(x[many]) == null && check(checkdest, x[many])) {
                    y[many] = x[many];
                    many++;
                }
            }
        }
        if (srccol - 2 >= 0) {
            ChessBoardLocation x2 = new ChessBoardLocation(srcrow, srccol - 1);
            x[many] = linecheck(src, x2, trusrc);
            if (x[many] != null) {
                if (x[many].equals(trusrc)) {
                    j++;
                }
                if (!x[many].equals(forsrc) && getChessPieceAt(x[many]) == null && check(checkdest, x[many])) {
                    y[many] = x[many];
                    many++;
                }
            }
        }
        if (srcrow + 2 <= dimension - 1 && srccol - 2 >= 0) {
            ChessBoardLocation x3 = new ChessBoardLocation(srcrow + 1, srccol - 1);
            x[many] = linecheck(src, x3, trusrc);
            if (x[many] != null) {
                if (x[many].equals(trusrc)) {
                    j++;
                }
                if (!x[many].equals(forsrc) && getChessPieceAt(x[many]) == null && check(checkdest, x[many])) {
                    y[many] = x[many];
                    many++;
                }
            }
        }
        if (srcrow - 2 >= 0) {
            ChessBoardLocation x4 = new ChessBoardLocation(srcrow - 1, srccol);
            x[many] = linecheck(src, x4, trusrc);
            if (x[many] != null) {
                if (x[many].equals(trusrc)) {
                    j++;
                }
                if (!x[many].equals(forsrc) && getChessPieceAt(x[many]) == null && check(checkdest, x[many])) {
                    y[many] = x[many];
                    many++;
                }
            }
        }
        if (srcrow - 2 >= 0 && srccol + 2 <= dimension - 1) {
            ChessBoardLocation x5 = new ChessBoardLocation(srcrow - 1, srccol + 1);
            x[many] = linecheck(src, x5, trusrc);
            if (x[many] != null) {
                if (x[many].equals(trusrc)) {
                    j++;
                }
                if (!x[many].equals(forsrc) && getChessPieceAt(x[many]) == null && check(checkdest, x[many])) {
                    y[many] = x[many];
                    many++;
                }
            }
        }
        if (srccol + 2 <= dimension - 1) {
            ChessBoardLocation x6 = new ChessBoardLocation(srcrow, srccol + 1);
            x[many] = linecheck(src, x6, trusrc);
            if (x[many] != null) {
                if (x[many].equals(trusrc)) {
                    j++;
                }
                if (!x[many].equals(forsrc) && getChessPieceAt(x[many]) == null && check(checkdest, x[many])) {
                    y[many] = x[many];
                    many++;
                }
            }
        }
        if (srcrow + 2 <= dimension - 1 && srccol + 2 <= dimension - 1) {
            ChessBoardLocation x7 = new ChessBoardLocation(srcrow + 1, srccol + 1);
            x[many] = linecheck(src, x7, trusrc);
            if (x[many] != null) {
                if (x[many].equals(trusrc)) {
                    j++;
                }
                if (!x[many].equals(forsrc) && getChessPieceAt(x[many]) == null && check(checkdest, x[many])) {
                    y[many] = x[many];
                    many++;
                }
            }
        }
        if (srcrow + 2 <= dimension - 1) {
            ChessBoardLocation x8 = new ChessBoardLocation(srcrow + 1, srccol);
            x[many] = linecheck(src, x8, trusrc);
            if (x[many] != null) {
                if (x[many].equals(trusrc)) {
                    j++;
                }
                if (!x[many].equals(forsrc) && getChessPieceAt(x[many]) == null && check(checkdest, x[many])) {
                    y[many] = x[many];
                    many++;
                }
            }
        }
        Random random = new Random();
        if (many == 0) {
            if (lujing.indexOf(src) != -1) {
                return null;
            }
        }
        if (many == 1) {
            if (lujing.indexOf(y[0]) != -1) {
                return null;
            }
        } //路径判断是否应该返回

        if (many == 0) {
            checkdest[count][0] = srcrow;
            checkdest[count][1] = srccol;
            count++;
            return null;
        }
        int i1 = 0;
        if (many > 1) {
            i1 = random.nextInt(many);
            return y[i1];
        }

        return y[i1];
    }

    public boolean isValidMove(ChessBoardLocation src, ChessBoardLocation dest) {
        try {
            if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
                return false;
            }
            int srcRow = src.getRow(), srcCol = src.getColumn(), destRow = dest.getRow(), destCol = dest.getColumn();
            int rowDistance = destRow - srcRow, colDistance = destCol - srcCol;
            if (Math.abs(rowDistance) < 2 && Math.abs(colDistance) < 2) {
                return true;
            }
            if (Math.abs(rowDistance) % 2 != 0 || Math.abs(colDistance) % 2 != 0) {
                return false;
            }
            ChessBoardLocation checksrc = src;
            ChessBoardLocation forsrc = src;
            lujing = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                ChessBoardLocation checkdes = checkDest12(checksrc, dest, src, forsrc);
                if (checkdes == null) {
                    if (many == 0 && checksrc.equals(src)) {
                        count = 0;
                        initillzy();
                        lujing = new ArrayList<>();
                        return false;
                    }
                    lujing = new ArrayList<>();
                    forsrc = src;
                    checksrc = src;
                }
                if (checkdes != null) {
                    if (xyz) {
                        lujing.add(checkdes);
                    }
                    if (checkdes.equals(dest)) {
                        count = 0;
                        initillzy();
                        return true;
                    }
                    forsrc = checksrc;
                    checksrc = checkdes;
                }
            }
            initillzy();
            lujing = new ArrayList<>();
            return false;
        } catch (NullPointerException e){
            return false;
        }
    }

    @Override
    public void registerListener(GameListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void unregisterListener(GameListener listener) {
        listenerList.remove(listener);
    }

    public boolean estimateEnd() {
        if (playerNum == 2) {
            Boolean red = true;
            Boolean green = true;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 5; j++) {
                    ChessBoardLocation x = new ChessBoardLocation(i, j);
                    ChessBoardLocation y = new ChessBoardLocation(dimension - 1 - i, dimension - j - 1);
                    if (getChessPieceAt(x) == null) {
                        green = false;
                    }
                    if (getChessPieceAt(x) != null) {
                        if (!getChessPieceAt(x).getColor().equals(Color.GREEN)) {
                            green = false;
                        }
                    }
                    if (getChessPieceAt(y) == null) {
                        red = false;
                    }
                    if (getChessPieceAt(y) != null) {
                        if (!getChessPieceAt(y).getColor().equals(Color.red)) {
                            red = false;
                        }
                    }
                }
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4 - i; j++) {
                    ChessBoardLocation x = new ChessBoardLocation(i + 2, j);
                    ChessBoardLocation y = new ChessBoardLocation(dimension - 1 - i - 2, dimension - j - 1);
                    if (getChessPieceAt(x) == null) {
                        green = false;
                    }
                    if (getChessPieceAt(x) != null) {
                        if (!getChessPieceAt(x).getColor().equals(Color.GREEN)) {
                            green = false;
                        }
                    }
                    if (getChessPieceAt(y) == null) {
                        red = false;
                    }
                    if (getChessPieceAt(y) != null) {
                        if (!getChessPieceAt(y).getColor().equals(Color.red)) {
                            red = false;
                        }
                    }
                }
            }
            if (green) {
                endGame x = new endGame("the green one wins!", this);
                x.setVisible(true);
                ifEnd = true;
            }
            if (red) {
                endGame x1 = new endGame("the red one wins!", this);
                x1.setVisible(true);
                ifEnd = true;
            }
        }
        if (playerNum == 4) {
            Boolean red = true;
            Boolean blue = true;
            Boolean green = true;
            Boolean MAGENTA = true;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 4; j++) {
                    ChessBoardLocation x = new ChessBoardLocation(i, j);
                    if (getChessPieceAt(x) == null) {
                        green = false;
                    } else if (!getChessPieceAt(x).getColor().equals(Color.green)) {
                        green = false;
                    }
                }
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3 - i; j++) {
                    ChessBoardLocation x = new ChessBoardLocation(i + 2, j);
                    if (getChessPieceAt(x) == null) {
                        green = false;
                    } else if (!getChessPieceAt(x).getColor().equals(Color.green)) {
                        green = false;
                    }
                }
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 4; j++) {
                    ChessBoardLocation x = new ChessBoardLocation(dimension - i - 1, dimension - j - 1);
                    if (getChessPieceAt(x) == null) {
                        red = false;
                    } else if (!getChessPieceAt(x).getColor().equals(Color.red)) {
                        red = false;
                    }
                }
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3 - i; j++) {
                    ChessBoardLocation x = new ChessBoardLocation(dimension - i - 3, dimension - j - 1);
                    if (getChessPieceAt(x) == null) {
                        red = false;
                    } else if (!getChessPieceAt(x).getColor().equals(Color.red)) {
                        red = false;
                    }
                }
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 4; j++) {
                    ChessBoardLocation x = new ChessBoardLocation(dimension - i - 1, j);
                    if (getChessPieceAt(x) == null) {
                        blue = false;
                    } else if (!getChessPieceAt(x).getColor().equals(Color.blue)) {
                        blue = false;
                    }
                }
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3 - i; j++) {
                    ChessBoardLocation x = new ChessBoardLocation(dimension - i - 3, j);
                    if (getChessPieceAt(x) == null) {
                        blue = false;
                    } else if (!getChessPieceAt(x).getColor().equals(Color.blue)) {
                        blue = false;
                    }
                }
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 4; j++) {
                    ChessBoardLocation x = new ChessBoardLocation(i, dimension - j - 1);
                    if (getChessPieceAt(x) == null) {
                        MAGENTA = false;
                    } else if (!getChessPieceAt(x).getColor().equals(Color.magenta)) {
                        MAGENTA = false;
                    }
                }
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3 - i; j++) {
                    ChessBoardLocation x = new ChessBoardLocation(i + 2, dimension - 1 - j);
                    if (getChessPieceAt(x) == null) {
                        MAGENTA = false;
                    } else if (!getChessPieceAt(x).getColor().equals(Color.magenta)) {
                        MAGENTA = false;
                    }
                }
            }
            if (red && green) {
                endGame x = new endGame("team1 wins!", this);
                x.setVisible(true);
                ifEnd = true;
            }
            if (MAGENTA && blue) {
                endGame y = new endGame("team2 wins!", this);
                y.setVisible(true);
                ifEnd = true;
            }
        }
        return ifEnd;
    }
}


