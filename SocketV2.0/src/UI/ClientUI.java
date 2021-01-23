package UI;

import Client.SendMsg;
import Client.TCPClient;
import Util.StartReceiveThread;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class ClientUI extends JFrame {

    private final JFrame frame = new JFrame("Client");  // 窗口
    private final JList<String> list = new JList<>();  // 在线人员
    private final JTextArea textArea = new JTextArea();  //信息框
    private final JTextArea textArea1 = new JTextArea();  // 发送框
    private final JButton button = new JButton("发送");  // 发送按钮
    private final JLabel jLabel = new JLabel("当前在线人员:");  // 在线人员标签
    private final Socket socket;

    /**
     * 通过{@link LoginUI}类传的serverIp和name实例化TCPClient类
     *
     * @param serverIP 服务器IP
     * @param name     用户昵称
     * @throws IOException
     */
    public ClientUI(String serverIP, String name) throws IOException {
        TCPClient tcpClient = new TCPClient(serverIP, name);  // TCPClient
        tcpClient.start();
        socket = tcpClient.getClientSocket();  // 客户端对应的socket赋值给当前变量

        setFrame(name); // 设置窗口
        setList();  // 设置列表
        setTextArea();  // 设置文本域
        setTextArea1();  // 设置发送框
        setButton(); // 设置按钮
        setLabel();  // 设置标签
        StartReceiveThread.startReceiveThread(socket, textArea, list);  // 启动监听线程
        frame.setVisible(true);
    }

    /**
     * 设置窗口相关属性
     */
    public void setFrame(String name) {
        frame.setLocationRelativeTo(null);  // 设置窗口左上角在启动时处于屏幕中间
        frame.setSize(700, 720);
        frame.setTitle(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 退出、最小化、关闭
        frame.setLayout(null);  // 空白布局
        frame.setResizable(false);  // 不可设置窗口大小
    }

    /**
     * 设置list的相关属性
     */
    private void setList() {
        JScrollPane scrollPane = new JScrollPane();  // 带滚动条的画布
        scrollPane.setBounds(500, 25, 180, 575);
        scrollPane.setViewportView(list);  // 画布中添加list
        /*
        设置监听事件,当鼠标单机莫一行松开后在发送框添加私聊的格式
         */
        list.addListSelectionListener(e -> {
            if (!list.getValueIsAdjusting()) {  // 判断是否单击松开
                textArea1.append("@" + list.getSelectedValue() + ":");
            }
        });
        frame.getContentPane().add(scrollPane);
    }

    /**
     * 设置test are相关属性
     */
    private void setTextArea() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 5, 480, 600);
        textArea.setLineWrap(true); // 自动换行
        textArea.setWrapStyleWord(true);  // 断行不断字
        textArea.setEditable(false);  // 不允许直接编辑
        scrollPane.setViewportView(textArea);
        frame.getContentPane().add(scrollPane);
    }

    /**
     * 设置test are1相关属性
     */
    private void setTextArea1() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 610, 480, 50);
        textArea.setLineWrap(true); // 自动换行
        textArea.setWrapStyleWord(true);  // 断行不断字
        scrollPane.setViewportView(textArea1);
        frame.getContentPane().add(scrollPane);
    }

    /**
     * 设置button相关属性
     */
    private void setButton() {
        button.setBounds(500, 610, 85, 50);
        button.addActionListener(e -> new SendMsg(socket, textArea1).sendMessage());
        frame.add(button);
    }

    /**
     * 设置标签的相关属性
     */
    private void setLabel() {
        jLabel.setBounds(500, 0, 180, 20);
        frame.add(jLabel);
    }
}
