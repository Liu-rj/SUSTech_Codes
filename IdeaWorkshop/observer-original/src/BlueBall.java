import java.awt.*;

public class BlueBall extends Ball{
    public BlueBall(Color color, int xSpeed, int ySpeed, int ballSize) {
        super(color, xSpeed, ySpeed, ballSize);
    }

    @Override
    public void update(char keyChar) {
        this.setXSpeed(-1 * this.getXSpeed());
        this.setYSpeed(-1 * this.getYSpeed());
    }

    @Override
    public void update(Ball ball) {
        this.setVisible(this.isIntersect(ball));
        if (this.isIntersect(ball)) {
            this.setXSpeed(-1 * this.getXSpeed());
            this.setYSpeed(-1 * this.getYSpeed());
        }
    }
}
