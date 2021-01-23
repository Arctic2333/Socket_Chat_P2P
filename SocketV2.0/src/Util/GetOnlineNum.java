package Util;

import Control.OnlineNum;
import Server.TCPServer;

import javax.swing.*;

public class GetOnlineNum {

    /**
     * 工具类不能被实例化
     */
    private GetOnlineNum() {
        throw new UnsupportedOperationException("GetOnlineNum Util cant be constructed");
    }

    /**
     * 启动获得在线人数的线程
     *
     * @param jFrame    窗口
     * @param tcpServer tcp服务器
     */
    public static void getOnlineNum(JFrame jFrame, TCPServer tcpServer) {
        new OnlineNum(jFrame, tcpServer).start();
    }
}
