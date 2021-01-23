package Server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Listener implements Runnable {
    private final TCPServer tcpServer;
    private final Socket socket;
    private final Map<String, PrintWriter> storeInfo;
    private String name;

    /**
     * 构造Listenr类,监听来自客户端的信息，或者响应客户端的
     * 昵称请求、获取在线列表请求、私聊请求
     *
     * @param t      TCP服务器
     * @param socket 实现连接的客户端Socket
     */
    public Listener(TCPServer t, Socket socket) {
        this.tcpServer = t;
        this.socket = socket;
        this.storeInfo = tcpServer.getStoreInfo();
    }

    /**
     * 获取客户端昵称
     *
     * @return 客户端昵称
     * @throws Exception
     */
    private String getName() throws Exception {
        //服务端的输入流读取客户端发送来的昵称输出流
        BufferedReader bReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        //服务端将昵称验证结果通过自身的输出流发送给客户端
        PrintWriter ipw = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

        //读取客户端发来的昵称
        while (true) {
            String nameString = bReader.readLine();
            /*
             昵称长度为空,或者和在线人员的昵称重复
             返回 FAIL状态码
             否则返回 OK 状态码
             */
            if ((nameString.trim().length() == 0) || storeInfo.containsKey(nameString)) {
                ipw.println("FAIL");
            } else {
                ipw.println("OK");
                return nameString;
            }
        }
    }

    @Override
    public void run() {
        try {
            /*
             * 通过客户端的Socket获取客户端的输出流
             * 用来将消息发送给客户端
             */
            PrintWriter pw = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

            /*
             * 将客户昵称和输出流存入共享集合HashMap中
             */
            name = getName();
            tcpServer.putIn(name, pw);
            Thread.sleep(100);

            // 服务端通知所有客户端，某用户上线
            tcpServer.sendToAll("[系统通知] “" + name + "”已上线");

            /*
             * 通过客户端的Socket获取输入流
             * 读取客户端发送来的信息
             */
            BufferedReader bReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            String msgString;


            while ((msgString = bReader.readLine()) != null) {
                // 检验是否为私聊（格式：@昵称：内容）
                if (msgString.startsWith("@")) {
                    int index = msgString.indexOf(":");
                    if (index >= 0) {
                        String theName = msgString.substring(1, index);  // 获取昵称
                        String info = msgString.substring(index + 1);  // 获取私聊信息
                        info = name + "：" + info;
                        tcpServer.sendToSomeone(theName, info);  // 将私聊信息发送出去
                        continue;
                    }
                }
                /*
                当信息等于"#getOP"为:客户端向服务器请求在线人员列表,服务器首先响应客户端
                发送信息"~!!@@##**~"标记和当前在线人数(num),而后循环num次发送当前在线
                人员昵称给客户端
                 */
                if (msgString.equals("#getOP")) {
                    tcpServer.sendToSomeone(name, "~!!@@##**~" + tcpServer.getOnlineNum());
                    for (String str : tcpServer.getStoreInfo().keySet()
                    ) {
                        tcpServer.sendToSomeone(name, str);
                    }
                    continue;
                }
                // 遍历所有输出流，将该客户端发送的信息转发给所有客户端
                System.out.println(name + "：" + msgString);
                tcpServer.sendToAll(name + "：" + msgString);
            }
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            tcpServer.remove(name);
            // 通知所有客户端，某某客户已经下线
            tcpServer.sendToAll("[系统通知] " + name + "已经下线了。");

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

