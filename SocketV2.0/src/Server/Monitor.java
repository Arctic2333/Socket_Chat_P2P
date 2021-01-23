package Server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class Monitor extends Thread {

    private final TCPServer tcpServer;
    private final ServerSocket serverSocket;
    private final ExecutorService exe;

    /**
     * 构造Monitor类,通过参数{@link TCPServer}类获取TCPServer类的
     * serverSocket和线程池 ExecutorService
     *
     * @param t 服务器类
     */
    public Monitor(TCPServer t) {
        this.tcpServer = t;
        this.serverSocket = t.getServerSocket();
        this.exe = t.getExec();
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("等待客户端连接... ... ");
                Socket socket = serverSocket.accept();

                // 获取客户端的ip地址
                InetAddress address = socket.getInetAddress();
                System.out.println("客户端：“" + address.getHostAddress() + "”连接成功！ ");
                /*
                 * 启动一个线程，由线程来处理客户端的请求，这样可以再次监听
                 * 下一个客户端的连接
                 */
                exe.execute(new Listener(tcpServer, socket));  // 通过线程池来分配线程
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
