package halma.view;

import halma.model.ChessBoard;
import halma.model.ChessBoardLocation;

import javax.swing.*;
import java.awt.*;

public class ChessComponent extends JLabel {
    private Color color;
    private boolean selected;
    /**add*/
    private boolean ifIcon = true;
    Icon red = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/dog.png");
    Icon green = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/dog1.png");
    Icon blue = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/dog2.png");
    Icon  magenta = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/dog3.png");

    public boolean isIfIcon() {
        return ifIcon;
    }

    public void setIfIcon(boolean ifIcon) {
        this.ifIcon = ifIcon;
    }

    public ChessComponent(Color color, boolean ifIcon) {
        this.color = color;
        this.setIfIcon(ifIcon);
    }

    public ChessComponent() {
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintChess(g);
    }
/**revise*/
    private void paintChess(Graphics g) {
        if (ifIcon){
            setSize(getWidth(), getHeight());
            setOpaque(false);
            if (color == Color.RED){
                this.setIcon(red);
            } else if (color == Color.GREEN){
                this.setIcon(green);
            } else if (color == Color.BLUE){
                this.setIcon(blue);
            } else if (color == Color.MAGENTA){
                this.setIcon(magenta);
            }
            this.repaint();
        } else {
            this.setIcon(null);
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(color);

            int spacing = (int) (getWidth() * 0.05);
            g.fillOval(spacing, spacing, getWidth() - 2 * spacing, getHeight() - 2 * spacing);
            this.repaint();
        }

        if (selected) { // Draw a + sign in the center of the piece
            paintSign(g);
        }
    }

    private void paintSign(Graphics g) {
        g.setColor(new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue()));
        g.drawLine(getWidth() / 2, getHeight() / 4, getWidth() / 2, getHeight() * 3 / 4);
        g.drawLine(getWidth() / 4, getHeight() / 2, getWidth() * 3 / 4, getHeight() / 2);
    }

}
