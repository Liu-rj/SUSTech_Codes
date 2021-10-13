package halma.listener;

import halma.model.ChessBoardLocation;
import halma.view.ChessComponent;
import halma.view.SquareComponent;

public interface InputListener extends Listener {
    void onPlayerClickSquare(ChessBoardLocation location, SquareComponent component);

    void onPlayerClickChessPiece(ChessBoardLocation location, ChessComponent component);
}
