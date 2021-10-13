package halma.model;

import java.awt.*;

public class ChessPiece {
    private Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public ChessPiece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
