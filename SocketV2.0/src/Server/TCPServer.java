package Server;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {

    // 服务器ServerSocket
    private ServerSocket serverSocket;

    /**
     * 创建线程池来管理客户端的连接线程
     * 避免系统资源过度浪费
     */
    private ExecutorService exec;

    // 存放客户端的名字和相应的输出流
    private Map<String, PrintWriter> storeInfo;

    /**
     * 构造TCPServer类,实例化ServerSocket、Map、ExecutorService
     */
    public TCPServer() {
        try {

            serverSocket = new ServerSocket(8888);
            storeInfo = new HashMap<>();
            exec = Executors.newCachedThreadPool();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将客户端的信息以Map形式存入集合中
     *
     * @param key   客户端昵称
     * @param value 客户端的输出流
     */
    public void putIn(String key, PrintWriter value) {
        synchronized (this) {
            storeInfo.put(key, value);
        }
    }

    /**
     * 将给定的输出流从共享集合中删除
     *
     * @param key 客户端昵称
     */
    public synchronized void remove(String key) {
        storeInfo.remove(key);
        System.out.println("当前在线人数为：" + storeInfo.size());
    }

    /**
     * 将给定的消息转发给所有客户端
     *
     * @param message 客户端的消息
     */
    public synchronized void sendToAll(String message) {
        for (PrintWriter out : storeInfo.values()) {
            out.println(message);
        }
    }

    /**
     * 将给定的消息转发给私聊的客户端
     *
     * @param name    指定用户的昵称
     * @param message 客户端信息
     */
    public synchronized void sendToSomeone(String name, String message) {
        PrintWriter pw = storeInfo.get(name); //将对应客户端的聊天信息取出作为私聊内容发送出去
        if (pw != null) pw.println(message);
    }

    /**
     * 启动Monitor线程
     */
    public void start() {
        new Monitor(this).start();
    }

    /**
     * get storeInfo's size
     *
     * @return map中的元素个数
     */
    public int getOnlineNum() {
        return storeInfo.size();
    }

    /**
     * get Exec pool
     *
     * @return tcp服务器的线程池
     */
    public ExecutorService getExec() {
        return exec;
    }

    /**
     * get ServerSocket
     *
     * @return tcp服务器socket
     */
    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    /**
     * get Map of StoreInfo
     *
     * @return 记录在线人员的map
     */
    public Map<String, PrintWriter> getStoreInfo() {
        return storeInfo;
    }

}
