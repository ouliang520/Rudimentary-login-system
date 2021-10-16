package brithday;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
//梁茵童生日快乐
public class openhtml {


        public static void openExplorer(String htmlFile) {

            if (java.awt.Desktop.isDesktopSupported()) {

                try {

                    Desktop.getDesktop().open(new File(htmlFile));

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

        }

        public static void main(String[] args) {

            openExplorer("try1\\src\\brithday\\getliang\\index.html");
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            openExplorer("try1\\src\\brithday\\getliang\\index1.html");

        }
    }

