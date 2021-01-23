package UI;


import javax.swing.*;
import java.io.IOException;

public class LoginUI extends JFrame {

    private final JFrame frame = new JFrame("Login");  // 窗口
    private final JLabel label1 = new JLabel();  // 服务器IP
    private final JLabel label2 = new JLabel();  // 昵称
    private final JTextField textField1 = new JTextField();  // 服务器IP输入框
    private final JTextField textField2 = new JTextField();  // 昵称输入框
    private final JButton button = new JButton("登入");  // 登入按钮

    /**
     * 构造LoginUI类,通过函数设置窗口、标签等信息
     */
    public LoginUI() {

        setFrame();  // 设置窗口函数
        setLabel1();  // 设置标签1函数
        setTextField1();  // 设置文本1函数
        setLabel2();  // 设置标签2函数
        setTextField2();  // 设置文本2函数
        setButton();  // 设置按钮函数

        frame.setVisible(true);  // 设置窗口的可见性为TRUE
    }

    public static void main(String[] args) {
        new LoginUI();
    }

    /**
     * 设置窗口相关属性
     */
    private void setFrame() {
        frame.setLocationRelativeTo(null);  // 设置窗口左上角在启动时处于屏幕中间
        frame.setSize(400, 280);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 退出、最小化、关闭
        frame.setLayout(null);  // 空白布局
        frame.setResizable(false);  // 不可设置窗口大小
    }

    /**
     * 设置label1相关属性
     */
    private void setLabel1() {
        label1.setText("服务器IP地址：");
        label1.setBounds(50, 75, 100, 20);  // 设置位置和大小
        frame.add(label1);
    }

    /**
     * 设置text1相关属性
     */
    private void setTextField1() {
        textField1.setBounds(140, 75, 200, 25);
        frame.add(textField1);
    }

    /**
     * 设置label2相关属性
     */
    private void setLabel2() {
        label2.setText("用户昵称：");
        label2.setBounds(50, 110, 100, 20);
        frame.add(label2);
    }

    /**
     * 设置text2相关属性
     */
    private void setTextField2() {
        textField2.setBounds(140, 110, 200, 25);
        frame.add(textField2);
    }

    /**
     * 设置button相关属性
     */
    private void setButton() {
        button.setBounds(150, 150, 100, 40);
        /*
        lambda表达式 设置监听事件 实质是实现内部类ActionListener
        重写其中的actionPerformed方法 e（ActionEvent）
         */
        button.addActionListener(
                e -> {
                    if (textField2.getText().equals("") || textField1.getText().equals("")) {
                        JOptionPane.showMessageDialog(frame, "服务器IP和昵称不能为空");  // 弹出信息框
                    } else {
                        try {
                            frame.dispose();
                            new ClientUI(textField1.getText(), textField2.getText());
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
        );
        frame.add(button);
    }
}
