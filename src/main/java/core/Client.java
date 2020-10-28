package core;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Susongfeng
 * @Title: Client
 * @Package core
 * @Description: Clinet to Chat
 * @date 2020-10-28 15:26
 */
public class Client {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1", 8888);

            //启动发送线程
            new SendThread(s).start();
            //启动接受线程
            new ReceiveThread(s).start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
