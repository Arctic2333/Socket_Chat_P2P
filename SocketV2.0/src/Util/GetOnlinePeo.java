package Util;

import Control.OnlinePeo;
import Server.TCPServer;

import javax.swing.*;

public class GetOnlinePeo {
    /**
     * 工具类不能被实例化
     */
    private GetOnlinePeo() {
        throw new UnsupportedOperationException("GetOnlinePeo Util cant be constructed");
    }

    /**
     * 启动获得在线人员的姓名的线程
     *
     * @param tcpServer tcp服务器
     * @param list      在线人员列表
     */
    public static void getOnlinePeo(TCPServer tcpServer, JList<String> list) {
        new OnlinePeo(tcpServer, list).start();
    }
}
