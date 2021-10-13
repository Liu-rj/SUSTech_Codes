package halma.listener;

import halma.model.ChessBoard;
import halma.model.ChessBoardLocation;
import halma.model.ChessPiece;

public interface GameListener extends Listener {
    void onChessPiecePlace(ChessBoardLocation location, ChessPiece piece);

    void onChessPieceRemove(ChessBoardLocation location);

    void onChessBoardReload(ChessBoard board);
}
