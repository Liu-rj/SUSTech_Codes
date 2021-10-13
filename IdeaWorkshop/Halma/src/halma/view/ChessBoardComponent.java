package halma.view;

import halma.listener.GameListener;
import halma.listener.InputListener;
import halma.listener.Listenable;
import halma.model.ChessBoard;
import halma.model.ChessBoardLocation;
import halma.model.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChessBoardComponent extends JComponent implements Listenable<InputListener>, GameListener {
    private static final Color BOARD_COLOR_1 = new Color(255, 255, 204);
    private static final Color BOARD_COLOR_2 = new Color(170, 170, 170);
    private static final Color Boarder1 = Color.RED;
    private static final Color Boarder2 = Color.CYAN;
    /**add*/
    private boolean ifIcon = true;
    /**
     * 要改
     */
    private final Icon icon1 = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/board1.png");
    private final Icon icon2 = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/board1.2.png");
    private final Icon icon3 = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/board1.1.png");
    private final Icon icon4 = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/board1.3.png");

    private final List<InputListener> listenerList = new ArrayList<>();
    private final SquareComponent[][] gridComponents;
    private final int dimension;
    private final int gridSize;
    private ChessBoardLocation selectedsqure;
    private ChessBoardLocation selectedlocation;

    public boolean isIfIcon() {
        return ifIcon;
    }

    public void setIfIcon(boolean ifIcon) {
        this.ifIcon = ifIcon;
    }

    public ChessBoardLocation getSelectedlocation() {
        return selectedlocation;
    }

    public void setSelectedlocation(ChessBoardLocation selectedlocation) {
        this.selectedlocation = selectedlocation;
    }

    public ChessBoardLocation getSelectedsqure() {
        return selectedsqure;
    }

    public void setSelectedsqure(ChessBoardLocation selectedsqure) {
        this.selectedsqure = selectedsqure;
    }

    public SquareComponent[][] getGridComponents() {
        return gridComponents;
    }

    public ChessBoardComponent(int size, int dimension) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLayout(null); // Use absolute layout
        setSize(size, size);

        this.gridComponents = new SquareComponent[dimension][dimension];
        this.dimension = dimension;
        this.gridSize = size / dimension;//47
        intiGrid();
    }

    /**
     * 要改
     */
    protected void intiGrid() {
        for (int row = 0; row < dimension; row = row + 2) {
            for (int col = 0; col < dimension; col++) {
                gridComponents[row][col] = new SquareComponent(gridSize, (row + col) % 2 == 0 ? icon1 : icon2);
                gridComponents[row][col].setLocation(col * gridSize, row * gridSize);
                add(gridComponents[row][col]);
            }
        }
        for (int row = 1; row < dimension; row = row + 2) {
            for (int col = 0; col < dimension; col++) {
                gridComponents[row][col] = new SquareComponent(gridSize, (row + col) % 2 == 0 ? icon3 : icon4);
                gridComponents[row][col].setLocation(col * gridSize, row * gridSize);
                add(gridComponents[row][col]);
            }
        }
    }

    public SquareComponent getGridAt(ChessBoardLocation location) {
        return gridComponents[location.getRow()][location.getColumn()];
    }
/**rexise*/
    public void setChessAtGrid(ChessBoardLocation location, Color color) {
        removeChessAtGrid(location);
        getGridAt(location).add(new ChessComponent(color, ifIcon));
    }

    public void removeChessAtGrid(ChessBoardLocation location) {
        // Note: re-validation is required after remove / removeAll
        getGridAt(location).removeAll();
        getGridAt(location).revalidate();
    }

    private ChessBoardLocation getLocationByPosition(int x, int y) {
        return new ChessBoardLocation(y / gridSize, x / gridSize);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            ChessBoardLocation location = getLocationByPosition(e.getX(), e.getY());
            for (InputListener listener : listenerList) {
                if (clickedComponent.getComponentCount() == 0) {
                    selectedsqure = location;
                    try {
                        listener.onPlayerClickSquare(location, (SquareComponent) clickedComponent);
                    } catch (InterruptedException | IOException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                } else {
                    selectedlocation = location;
                    listener.onPlayerClickChessPiece(location, (ChessComponent) clickedComponent.getComponent(0), this);
                }
            }
        }
    }

    @Override
    public void onChessPiecePlace(ChessBoardLocation location, ChessPiece piece) {
        setChessAtGrid(location, piece.getColor());
        repaint();
    }

    @Override
    public void onChessPieceRemove(ChessBoardLocation location) {
        removeChessAtGrid(location);
        repaint();
    }

    @Override
    public void onChessBoardReload(ChessBoard board) {
        for (int row = 0; row < board.getDimension(); row++) {
            for (int col = 0; col < board.getDimension(); col++) {
                ChessBoardLocation location = new ChessBoardLocation(row, col);
                ChessPiece piece = board.getChessPieceAt(location);
                if (piece != null) {
                    setChessAtGrid(location, piece.getColor());
                } else {
                    removeChessAtGrid(location);
                }
            }
        }
        repaint();
    }

    @Override
    public void registerListener(InputListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void unregisterListener(InputListener listener) {
        listenerList.remove(listener);
    }


}
