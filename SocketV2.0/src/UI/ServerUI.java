package UI;


import Server.TCPServer;
import Util.GetIPAddress;
import Util.GetOnlineNum;
import Util.GetOnlinePeo;

import javax.swing.*;
import java.net.UnknownHostException;

public class ServerUI {

    private final JFrame frame = new JFrame("Server"); // 窗口
    private final JLabel label1 = new JLabel();  // 主机ip文本
    private final JLabel label2 = new JLabel();  // 在线人员
    private final JList<String> list = new JList<>();  // 在线人员

    public ServerUI() throws UnknownHostException {
        TCPServer tcpServer = new TCPServer();
        tcpServer.start(); // 启动TCP服务器

        setFrame();  // 设置窗口
        setLabel1();  // 设置label1
        setLabel2();  // 设置label2
        setList();  // 设置list
        GetOnlinePeo.getOnlinePeo(tcpServer, list);  // 获得在线人员的名称
        GetOnlineNum.getOnlineNum(frame, tcpServer);  // 获得在线人数
        frame.setVisible(true);  // 将主窗口设置为可见
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws UnknownHostException {
        new ServerUI();
    }

    /**
     * 设置窗口相关属性
     */
    private void setFrame() {
        frame.setLocationRelativeTo(null);  // 设置窗口左上角在启动时处于屏幕中间
        frame.setSize(350, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 退出、最小化、关闭
        frame.setLayout(null);  // 空白布局
        frame.setResizable(false);  // 不可设置窗口大小
    }

    /**
     * 设置label1的相关属性
     *
     * @throws UnknownHostException
     */
    private void setLabel1() throws UnknownHostException {
        label1.setText("当前服务器IP地址为：" + GetIPAddress.getIPAddress() + "  Socket 监听端口：8888");
        label1.setBounds(0, 0, 350, 20);
        frame.add(label1);
    }

    /**
     * 设置label2的相关属性
     */
    private void setLabel2() {
        label2.setText("当前在线人员：");
        label2.setBounds(0, 60, 350, 20);
        frame.add(label2);
    }

    /**
     * 设置list的相关属性
     */
    private void setList() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 90, 330, 260);
        scrollPane.setViewportView(list);
        frame.getContentPane().add(scrollPane);
    }
}
