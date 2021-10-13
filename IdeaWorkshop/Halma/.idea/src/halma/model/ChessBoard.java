package halma.model;

import halma.listener.GameListener;
import halma.listener.Listenable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChessBoard implements Listenable<GameListener> {
    private List<GameListener> listenerList = new ArrayList<>();
    private Square[][] grid;
    private int dimension;
    private int playerNum;
    private int count;
    private  int many;

    public int getMany() {
        return many;
    }

    public void setMany(int many) {
        this.many = many;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
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

    private int[][] checkdest = new int[50][2];

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

    public ChessBoard(int dimension, int playerNum) {
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
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal halma move");
        }
        setChessPieceAt(dest, removeChessPieceAt(src));
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
                endgame x = new endgame("the green one wins!");
                ifEnd = true;
            }
            if (red) {
                endgame x1 = new endgame("the red one wins!");
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
                endgame x = new endgame("team1 wins!");
                ifEnd = true;
            }
            if (MAGENTA && blue) {
                endgame y = new endgame("team2 wins!");
                ifEnd = true;
            }
        }
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
   public ChessBoardLocation linecheck(ChessBoardLocation chsrc,ChessBoardLocation chde){
        int disrow=chde.getRow()-chsrc.getRow(); int discol=chde.getColumn()-chsrc.getColumn();
        for (int i=1;chsrc.getRow()+i*disrow>=0&&chsrc.getRow()+i*disrow<=dimension-1&&chsrc.getColumn()+i*discol>=0&&chsrc.getColumn()+i*discol<=dimension-1;i++){ChessBoardLocation y=new ChessBoardLocation(chsrc.getRow()+i*disrow,chsrc.getColumn()+i*discol);
        if (getChessPieceAt(y)!=null){if (chsrc.getRow()+i*disrow*2>=0&&chsrc.getRow()+i*disrow*2<=dimension-1&&chsrc.getColumn()+i*discol*2>=0&&chsrc.getColumn()+i*discol*2<=dimension-1){ChessBoardLocation y1=new ChessBoardLocation(chsrc.getRow()+i*disrow*2,chsrc.getColumn()+i*discol*2);return y1; }else return null;}}
 return null;}

    public ChessBoardLocation checkDest12(ChessBoardLocation src, ChessBoardLocation dest, ChessBoardLocation trusrc, ChessBoardLocation forsrc) {
        int srccol = src.getColumn();
        int srcrow = src.getRow();
        ChessBoardLocation[] x = new ChessBoardLocation[8];
        ChessBoardLocation[] y = new ChessBoardLocation[8];
         many = 0;
        int j = 0;
        if (srcrow - 2 >= 0 && srccol - 2 >= 0) {
            ChessBoardLocation x1 = new ChessBoardLocation(srcrow - 1, srccol - 1);
            x[many]=linecheck(src,x1);if (x[many]!=null){if (x[many].equals(trusrc)){j++;}if (!x[many].equals(forsrc)&&getChessPieceAt(x[many])==null&&check(checkdest,x[many])){y[many]=x[many];many++;}}
        }
        if (srccol - 2 >= 0) {
            ChessBoardLocation x2 = new ChessBoardLocation(srcrow, srccol - 1);
            x[many]=linecheck(src,x2);if (x[many]!=null){if (x[many].equals(trusrc)){j++;}if (!x[many].equals(forsrc)&&getChessPieceAt(x[many])==null&&check(checkdest,x[many])){y[many]=x[many];many++;}}}
        if (srcrow + 2 <= dimension - 1 && srccol - 2 >= 0) {
            ChessBoardLocation x3 = new ChessBoardLocation(srcrow + 1, srccol - 1);
            x[many]=linecheck(src,x3);if (x[many]!=null){if (x[many].equals(trusrc)){j++;}if (!x[many].equals(forsrc)&&getChessPieceAt(x[many])==null&&check(checkdest,x[many])){y[many]=x[many];many++;}}}
        if (srcrow - 2 >= 0) {
            ChessBoardLocation x4 = new ChessBoardLocation(srcrow - 1, srccol);
            x[many]=linecheck(src,x4);if (x[many]!=null){if (x[many].equals(trusrc)){j++;}if (!x[many].equals(forsrc)&&getChessPieceAt(x[many])==null&&check(checkdest,x[many])){y[many]=x[many];many++;}}}
        if (srcrow - 2 >= 0 && srccol + 2 <= dimension - 1) {
            ChessBoardLocation x5 = new ChessBoardLocation(srcrow - 1, srccol + 1);
            x[many]=linecheck(src,x5);if (x[many]!=null){if (x[many].equals(trusrc)){j++;}if (!x[many].equals(forsrc)&&getChessPieceAt(x[many])==null&&check(checkdest,x[many])){y[many]=x[many];many++;}}}
        if (srccol + 2 <= dimension - 1) {
            ChessBoardLocation x6 = new ChessBoardLocation(srcrow, srccol + 1);
            x[many]=linecheck(src,x6);if (x[many]!=null){if (x[many].equals(trusrc)){j++;}if (!x[many].equals(forsrc)&&getChessPieceAt(x[many])==null&&check(checkdest,x[many])){y[many]=x[many];many++;}}}
        if (srcrow + 2 <= dimension - 1 && srccol + 2 <= dimension - 1) {
            ChessBoardLocation x7 = new ChessBoardLocation(srcrow + 1, srccol + 1);
            x[many]=linecheck(src,x7);if (x[many]!=null){if (x[many].equals(trusrc)){j++;}if (!x[many].equals(forsrc)&&getChessPieceAt(x[many])==null&&check(checkdest,x[many])){y[many]=x[many];many++;}}}
        if (srcrow + 2 <= dimension - 1) {
            ChessBoardLocation x8 = new ChessBoardLocation(srcrow + 1, srccol);
            x[many]=linecheck(src,x8);if (x[many]!=null){if (x[many].equals(trusrc)){j++;}if (!x[many].equals(forsrc)&&getChessPieceAt(x[many])==null&&check(checkdest,x[many])){y[many]=x[many];many++;}}}
        Random random = new Random();
      if (many == 0 && j == 1) {
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


        if (j == 0 && many == 1 && !dest.equals(y[0]) && !forsrc.equals(trusrc) && !src.equals(trusrc)) {
            checkdest[count][0] = srcrow;
            checkdest[count][1] = srccol;
            count++;
            return null;
        }
        return y[i1];
    }

    public boolean isValidMove(ChessBoardLocation src, ChessBoardLocation dest) {
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

        for (int i=0;i<500;i++){
            ChessBoardLocation checkdes = checkDest12(checksrc, dest, src, forsrc);
            if (checkdes == null) {if (many==0&&checksrc.equals(src)){return false;}
                forsrc = src;
                checksrc = src;
            } else if (checkdes != null) {
                if (checkdes.equals(dest)) {
                    checkdest = new int[50][2];
                    return true;
                }
                forsrc = checksrc;
                checksrc = checkdes;
            }}
       return false; }





    @Override
    public void registerListener(GameListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void unregisterListener(GameListener listener) {
        listenerList.remove(listener);
    }
}


