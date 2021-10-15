import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class test {
    public static void main(String[] args) {
        try {
            Robot myRobot = new Robot();
            while (true) {
                // 移动鼠标到坐标（x,y)处，并点击左键
                myRobot.mouseMove(1300, 700);                // 移动鼠标到坐标（x,y）处
                myRobot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);        // 模拟按下鼠标左键
                myRobot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);    // 模拟释放鼠标左键
                // 设置每次输入的延迟为1s
                myRobot.setAutoDelay(1000);

                myRobot.mouseMove(1500, 800);                // 移动鼠标到坐标（x,y）处
                myRobot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);        // 模拟按下鼠标左键
                myRobot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);    // 模拟释放鼠标左键
                myRobot.setAutoDelay(1000);
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}