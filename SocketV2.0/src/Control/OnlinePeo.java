package Control;

import Server.TCPServer;

import javax.swing.*;
import java.io.PrintWriter;
import java.util.Map;

public class OnlinePeo extends Thread {

    private final JList<String> list;
    private final Map<String, PrintWriter> storeInfo;

    /**
     * 构造{@link OnlinePeo}类,通过tcp服务器参数获得服务器中的在线人员信息
     * Map,在将Map中的key显示到{@link UI.ServerUI} 类中的在线人员列表中
     *
     * @param tcpServer tcp服务器
     * @param list      在线人员列表
     */
    public OnlinePeo(TCPServer tcpServer, JList<String> list) {
        this.list = list;
        this.storeInfo = tcpServer.getStoreInfo();
    }

    @Override
    public void run() {
        while (true) {
            final DefaultListModel<String> defaultListModel = new DefaultListModel<>();  // jList添加数据的模式
            for (String name : storeInfo.keySet()
            ) {
                defaultListModel.addElement(name);
            }
            list.setModel(defaultListModel);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
