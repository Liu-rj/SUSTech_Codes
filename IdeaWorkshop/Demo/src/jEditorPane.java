import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class jEditorPane {
    public static void main(String[] args) {
        JEditorPane editPane = null;
        try {
            File file = new File("D:\\java文件/Course Project--Halma（跳棋）.PDF");
            String str = file.getAbsolutePath();// 取得文件位置的绝对路径
            str = "file:" + str;// 将绝对路径合成一完整的输入字符串

            /*
             * 利用setPage()方法将字符串中路径所指的文件加载JEditorPane.
             * 在setPage()方法中，输入的数据是String类型的字符串
             * ，其实这样的构造方式等同于利用JEditorPane的另一个构造函数JEditorPane(String
             * str)来构造。因此如果我们将下面两行程序改写如下行： editPane=new JEditorPane(str);
             * 会得到相同的效果，所以我们就不再对此种构造方式再多加说明.
             */
            editPane = new JEditorPane();// 构造一个空的JEditorPane
            editPane.setPage(str);
        } catch (IOException ioe) {
            ioe.printStackTrace(System.err);
            System.exit(0);
        }
        /*
         * 利用setEditable()方法将JEditorPane设为不可编辑.请注意，这行是相当重要的，若是我们将这个方法设为true,我们将会
         * 失去HTML文件本身的特性，如超级链接的功能等等。因此我们在使用下面JEditorPane2的例子时，一般都会将编辑的功能取
         * 消(设置false).目前这个超级链接功能并没有作用，这部份将在JEditorPane的事件处理中介绍.
         */
        editPane.setEditable(false);
        JFrame f = new JFrame("JEditorPane1");
        f.setContentPane(new JScrollPane(editPane));
        f.setSize(200, 200);
        f.show();
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
