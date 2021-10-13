package halma.controller;

import halma.model.ChessBoardLocation;
import halma.model.Square;

import java.util.ArrayList;

public interface MinimaxAI {
    void action();

    int estimate(Square square, ChessBoardLocation x);

    int countNumber();

    boolean ultimate();

    ArrayList<Square> chooseUltimateSquare();
}
