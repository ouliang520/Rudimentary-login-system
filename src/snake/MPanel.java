package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

//KeyListener键盘监听
public class MPanel extends JPanel implements KeyListener, ActionListener {
    ImageIcon title = new ImageIcon("src\\snake\\title.jpg");
    ImageIcon right = new ImageIcon("src\\snake\\right.png");
    ImageIcon body = new ImageIcon("src\\snake\\body.png");
    ImageIcon up = new ImageIcon("src\\snake\\up.png");
    ImageIcon down = new ImageIcon("src\\snake\\down.png");
    ImageIcon left = new ImageIcon("src\\snake\\left.png");
    ImageIcon food =new ImageIcon("src\\snake\\food.png");
    int[] snakex = new int[750];
    int[] snakey = new int[750];
    int len;
    String fx ="D" ;//"A"左， "W"上， "S"下
    boolean isStarted = false;
    Timer timer = new Timer(100,this);
    int foodx;
    int foody;
    Random rand =new Random();
    boolean over = true;


    public MPanel() {
        intiSnake();
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();//调用这个时钟对象
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        title.paintIcon(this, g, 25, 11);

        g.fillRect(25, 75, 850, 600);
        if (fx.equals("D")){
            right.paintIcon(this, g, snakex[0], snakey[0]);
        }else if (fx.equals("A")){
            left.paintIcon(this, g, snakex[0], snakey[0]);
        }else if (fx.equals("W")){
            up.paintIcon(this, g, snakex[0], snakey[0]);
        }else if(fx.equals("S")){
            down.paintIcon(this, g, snakex[0], snakey[0]);
        }
        //画身体
        for (int i = 1; i < len; i++) {
            body.paintIcon(this, g, snakex[i], snakey[i]);
        }
        food.paintIcon(this,g,foodx,foody);
        //当isStarted为 false的时候，弹出暂停界面
        if (isStarted == false) {
            g.setColor(Color.BLUE);
            g.setFont(new Font("arial", Font.BOLD, 40));
            g.drawString("Press Space to Strat", 300, 300);
        }
        if(over ==false){
            g.setColor(Color.BLUE);
            g.setFont(new Font("arial", Font.BOLD, 60));
            g.drawString("Game Over", 300, 300);
        }


    }
//初始化
    public void intiSnake() {
        len = 3;
        snakex[0] = 100;
        snakey[0] = 100;
        snakex[1] = 75;
        snakey[1] = 100;
        snakex[2] = 50;
        snakey[2] = 100;
        foodx = 25 + 25 *rand.nextInt(34);
        foody = 75 +25*rand.nextInt(24);

    }
//监听键盘
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    //按空格键
    public void keyPressed(KeyEvent e) {
        int KeyCode = e.getKeyCode();
        if (KeyCode == KeyEvent.VK_SPACE) {
            isStarted = !isStarted;//实现按空格后暂停和开始游戏
            repaint();//重新画
        } else if (KeyCode == KeyEvent.VK_DOWN && fx != "W") {//向下
            fx = "S";
        } else if (KeyCode == KeyEvent.VK_UP && fx != "S") {//向上
            fx = "W";
        } else if (KeyCode == KeyEvent.VK_LEFT && fx != "D") {//向左
            fx = "A";
        } else if (KeyCode == KeyEvent.VK_RIGHT && fx != "A") {//向右
            fx = "D";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
//Timer ，接口ActionListener的方法
    //当计时器时间到了，自动执行该方法
    @Override
    public void actionPerformed(ActionEvent e) {
        if (over) {
            //控制暂停
            if (isStarted) {
                //倒着移动，因为头是坐标
                for (int i = len - 1; i > 0; i--) {
                    snakex[i] = snakex[i - 1];
                    snakey[i] = snakey[i - 1];
                }
                //不同方向对应不同的移动
                if (fx.equals("D")) {
                    snakex[0] += 25;
                } else if (fx.equals("A")) {
                    snakex[0] -= 25;
                } else if (fx.equals("W")) {
                    snakey[0] -= 25;
                } else if (fx.equals("S")) {
                    snakey[0] += 25;
                }
//当蛇的位置超出游戏区，做出限制
                if (snakex[0] > 850) {
                    snakex[0] = 25;
                } else if (snakey[0] > 650) {
                    snakey[0] = 100;
                } else if (snakex[0] < 25) {
                    snakex[0] = 850;
                } else if (snakey[0] < 75) {
                    snakey[0] = 625;

                }
                //判断蛇头是否与食物重叠
                if (snakex[0] == foodx && snakey[0] == foody) {
                    len += 1;
                    foodx = 25 + 25 * rand.nextInt(34);
                    foody = 75 + 25 * rand.nextInt(24);
                }
                //判断身体是否重叠
                for (int i = 1; i < len; i++) {
                    if (snakex[0] == snakex[i] && snakey[0] == snakey[i]) {
                        over = false;

                    }
                }
                repaint();
                timer.start();//再次调用时钟
            }
        }
    }
}
