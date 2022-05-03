import java.awt.*;

public class RedBall extends Ball{
    public RedBall(Color color, int xSpeed, int ySpeed, int ballSize) {
        super(color, xSpeed, ySpeed, ballSize);
    }

    @Override
    public void update(char keyChar) {
        switch (keyChar) {
            case 'a':
                this.setXSpeed(-random.nextInt(3) - 1);
                break;
            case 'd':
                this.setXSpeed(random.nextInt(3) + 1);
                break;
            case 'w':
                this.setYSpeed(-random.nextInt(3) - 1);
                break;
            case 's':
                this.setYSpeed(random.nextInt(3) + 1);
        }
    }

    @Override
    public void update(Ball ball) {
        this.setVisible(this.isIntersect(ball));
        if (this.isIntersect(ball)) {
            this.setXSpeed(ball.getXSpeed());
            this.setYSpeed(ball.getYSpeed());
        }
    }
}
