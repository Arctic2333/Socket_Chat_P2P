package core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Susongfeng
 * @Title: Server
 * @Package core
 * @Description: Chat Server
 * @date 2020-10-28 15:30
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8888);
        System.out.println("监听端口号在："+ss.getLocalPort());
        Socket s = ss.accept();

        new SendThread(s).start();
        new ReceiveThread(s).start();
    }
}
