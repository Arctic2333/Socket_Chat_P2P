package Control;

import Server.TCPServer;

import javax.swing.*;

public class OnlineNum extends Thread {

    private final JLabel jLabel = new JLabel();
    private final TCPServer tcpServer;

    /**
     * 构造{@link OnlineNum}类，刷新从{@link UI.ServerUI}中传过来的窗体的标签
     *
     * @param f 窗体
     * @param t TCP服务器
     */
    public OnlineNum(JFrame f, TCPServer t) {
        this.tcpServer = t;
        jLabel.setBounds(0, 30, 700, 20);
        f.add(jLabel);
    }

    @Override
    public void run() {
        /*
        不断的刷新在线人数
         */
        while (true) {
            jLabel.setText("当前在线人数：" + tcpServer.getOnlineNum());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
