package Util;

import Client.ReceiveThread;

import javax.swing.*;
import java.net.Socket;

public class StartReceiveThread {

    /**
     * 工具类不能被实例化
     */
    private StartReceiveThread() {
        throw new UnsupportedOperationException("GetOnlinePeo Util cant be constructed");
    }

    /**
     * 启动监听线程
     *
     * @param socket   客户端socket
     * @param textArea 文本域
     * @param jList    在线人员列表
     */
    public static void startReceiveThread(Socket socket, JTextArea textArea, JList<String> jList) {
        new ReceiveThread(socket, textArea, jList).start();
    }
}
