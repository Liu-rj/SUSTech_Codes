package halma.view;

import javax.swing.*;
import java.awt.*;

public class SquareComponent extends JPanel {
    private boolean possible;
    private Icon icon;

    public boolean isPossible() {
        return possible;
    }

    public void setPossible(boolean possible) {
        this.possible = possible;
    }

    public SquareComponent(int size, Icon icon) {
        setLayout(new GridLayout(1, 1)); // Use 1x1 grid layout
        setSize(size, size);
        this.icon = icon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        icon.paintIcon(this,g,0,0);
//        paintSquare(g);
        if (possible){
            paintSign(g);
        }
    }

//    private void paintSquare(Graphics g) {
//        g.setColor(color);
//        g.fillRect(0, 0, getWidth(), getHeight());
//        g.setColor(Color.BLACK);
//        g.drawRect(0, 0, getWidth(), getHeight());
//    }

    public void paintSign(Graphics g){
        g.setColor(Color.BLACK);
        g.drawLine(getWidth() / 2, getHeight() / 4, getWidth() / 2, getHeight() * 3 / 4);
        g.drawLine(getWidth() / 4, getHeight() / 2, getWidth() * 3 / 4, getHeight() / 2);
    }
}
