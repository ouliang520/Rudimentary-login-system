package dljm.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoginListener implements ActionListener {
    private static javax.swing.JTextField text_name;
    private static javax.swing.JPasswordField text_password;
    private javax.swing.JFrame login;

    public LoginListener(javax.swing.JFrame login, javax.swing.JTextField text_name, javax.swing.JPasswordField text_password) {//获取登录界面、账号密码输入框对象
        this.login = login;
        this.text_name = text_name;
        this.text_password = text_password;
    }

    int i = 3;//3次登录机会

    public void actionPerformed(ActionEvent e) {
        Dimension dim2 = new Dimension(100, 30);
        Dimension dim3 = new Dimension(300, 30);

        //生成新界面
        javax.swing.JFrame login2 = new javax.swing.JFrame();
        login2.setSize(400, 200);
        login2.setDefaultCloseOperation(3);
        login2.setLocationRelativeTo(null);
        login2.setFont(new Font("宋体", Font.PLAIN, 14));  //宋体，正常风格，14号字体
        //创建组件
        javax.swing.JPanel jp1 = new JPanel();
        javax.swing.JPanel jp2 = new JPanel();
        boolean table = yanzheng();

        if (table) {
            JLabel message = new JLabel("登陆成功！");
            message.setFont(new Font("宋体", Font.PLAIN, 14));  //宋体，正常风格，14号字体
            message.setPreferredSize(dim3);
            jp1.add(message);
            login2.add(jp1, BorderLayout.CENTER);

            login2.setResizable(false);
            login2.setVisible(true);

            //通过我们获取的登录界面对象，用dispose方法关闭它
            login.dispose();
        } else if (i >= 2) {
            JLabel message = new JLabel("账号或密码错误,您今天还有" + (i - 1) + "次机会");
            message.setFont(new Font("宋体", Font.PLAIN, 14));  //宋体，正常风格，14号字体
            message.setPreferredSize(dim3);
            //将textName标签添加到窗体上
            jp1.add(message);
            login2.add(jp1, BorderLayout.CENTER);

            JButton close = new JButton("确定");
            close.setFont(new Font("宋体", Font.PLAIN, 14));
            //设置按键大小
            close.setSize(dim3);
            jp2.add(close);
            login2.add(jp2, BorderLayout.SOUTH);

            i--;//次数减少
            close.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    login2.dispose();
                }
            });

            login2.setResizable(false);
            login2.setVisible(true);
        } else if (i == 1) {
            JLabel message = new JLabel("账号已锁定，请明天再试");
            message.setFont(new Font("宋体", Font.PLAIN, 14));  //宋体，正常风格，14号字体
            message.setPreferredSize(dim3);
            //将textName标签添加到窗体上
            jp1.add(message);
            login2.add(jp1, BorderLayout.CENTER);

            JButton close = new JButton("确定");
            close.setFont(new Font("宋体", Font.PLAIN, 14));
            //设置按键大小
            close.setSize(dim3);
            jp2.add(close);
            login2.add(jp2, BorderLayout.SOUTH);

            close.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    login2.dispose();
                }
            });

            login2.setResizable(false);
            login2.setVisible(true);

            //通过我们获取的登录界面对象，用dispose方法关闭它
            login.dispose();
        }
    }

    //链接数据库,与数据库数据进行匹配
    private static boolean yanzheng() {
        boolean table = false;
        Connection coon = null;
        Statement sta = null;
        //数据库
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            coon = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bjpowernode", "root", "1234");

            sta = coon.createStatement();
            String sql = "select * from t_user where user='" + text_name.getText() + "'and password ='" + text_password.getText() + "' ";
            ResultSet rs = sta.executeQuery(sql);

            if (rs.next()) {
                table = true;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                sta.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                coon.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return table;
    }
}

//    private static Map<String,String> inti() {
//        Scanner in = new Scanner(System.in);
//        System.out.print("请输入用户名:");
//        String user = in.next();
//        System.out.print("请输入密码:");
//        String power = in.next();
//        Map<String,String> map = new HashMap<>();
//        map.put("user",user);
//        map.put("password",power);
//        return map;
//    }
//}

