package halma.listener;

import halma.model.ChessBoard;
import halma.model.ChessBoardLocation;
import halma.view.ChessBoardComponent;
import halma.view.ChessComponent;
import halma.view.SquareComponent;

import java.awt.*;
import java.io.IOException;

public interface InputListener extends Listener {
    void onPlayerClickSquare(ChessBoardLocation location, SquareComponent component) throws InterruptedException, IOException;

    void onPlayerClickChessPiece(ChessBoardLocation location, ChessComponent component, ChessBoardComponent chessBoardComponent);
}
