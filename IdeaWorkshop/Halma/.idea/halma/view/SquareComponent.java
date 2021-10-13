package halma.view;

import javax.swing.*;
import java.awt.*;

public class SquareComponent extends JPanel {
    private Color color;
    private  Boolean isValid1;


    public void setValid1(Boolean valid1) {
        isValid1 = valid1;
    }

    public SquareComponent(int size, Color color) {
        setLayout(new GridLayout(1, 1)); // Use 1x1 grid layout
        setSize(size, size);
        this.color = color;
    }



    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
        paintSquare(g);

    }

    private void paintSquare(Graphics g) {//if (isValid1){paintSign(g);}
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth(), getHeight());
    }

    public void paintSign(Graphics g){
        g.setColor(new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue()));

        g.drawLine(getWidth() / 2, getHeight() / 4, getWidth() / 2, getHeight() * 3 / 4);
        g.drawLine(getWidth() / 4, getHeight() / 2, getWidth() * 3 / 4, getHeight() / 2);
   }

}

