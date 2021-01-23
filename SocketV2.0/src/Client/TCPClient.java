package Client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPClient {

    static private Socket clientSocket;
    private final String name;

    /**
     * 构造TCP客户端,接收来自{@link UI.LoginUI}中的服务器IP地址和用户昵称
     *
     * @param serverIP 服务器IP地址
     * @param name     昵称
     * @throws IOException
     */
    public TCPClient(String serverIP, String name) throws IOException {
        clientSocket = new Socket(serverIP, 8888);
        this.name = name;
    }

    /**
     * 启动TCPClient
     */
    public void start() {
        try {
            setName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置用户名字
     *
     * @param name 昵称
     * @throws Exception
     */
    private void setName(String name) throws Exception {
        //创建输出流
        PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8), true);
        //创建输入流
        BufferedReader br = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));

        while (true) {
            if (name.trim().equals("")) {
                System.out.println("昵称不得为空");
            } else {
                pw.println(name);  // 将用户昵称发送给服务器
                String pass = br.readLine();  // 服务器返回的状态码
                if (pass != null && (!pass.equals("OK"))) {
                    System.out.println("昵称已经被占用，请重新输入：");
                } else {
                    System.out.println("昵称“" + name + "”已设置成功，可以开始聊天了");
                    break;
                }
            }
        }
    }

    /**
     * 获取Client
     *
     * @return clientSocket 客户端socket
     */
    public Socket getClientSocket() {
        return clientSocket;
    }

}
