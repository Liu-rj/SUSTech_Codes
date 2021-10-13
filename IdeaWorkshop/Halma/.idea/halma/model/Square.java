package halma.model;

import javax.swing.*;
import java.awt.*;

public class Square  {
    private ChessBoardLocation location;
    private ChessPiece piece;


    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }



    public Square(ChessBoardLocation location) {
        this.location = location;
    }

    public ChessBoardLocation getLocation() {
        return location;
    }

    public ChessPiece getPiece() {
        return piece;
    }


   // private void  paintmark(Graphics g){g.drawLine(getWidth() / 2, getHeight() / 4, getWidth() / 2, getHeight() * 3 / 4);g.drawLine(getWidth() / 4, getHeight() / 2, getWidth() * 3 / 4, getHeight() / 2);}


}
