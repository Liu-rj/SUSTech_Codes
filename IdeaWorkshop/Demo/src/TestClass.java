import java.awt.*;
import java.awt.event.InputEvent;

public class TestClass {
    public static void main(String[] args) {
        TestClass test=new TestClass();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        for(int i=0;i<40;i++) {
//            test.choose1();
//        }
//        //让程序进行40次的循环
        test.choose1();
        System.out.println("退出");
    }
    public void choose1() {
        try {
            Robot robot = new Robot();
            robot.delay(400);
            for (int i = 0; i < 5; i++) {
                robot.mouseMove(630,1045);
            }
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);

            robot.delay(400);

            long t1 = System.currentTimeMillis();
            while(true) {
                long t2=System.currentTimeMillis();
                System.out.println("进去1");

                for (int i = 0; i < 5; i++) {
                    robot.mouseMove(1790,490);
                }
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                robot.delay(20);

                for (int i = 0; i < 5; i++) {
                    robot.mouseMove(1080,200);
                }
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                robot.delay(20);

                for (int i = 0; i < 5; i++) {
                    robot.mouseMove(1150,200);
                }
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);

                if(t2-t1>60000) {
                    break;
                }
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
