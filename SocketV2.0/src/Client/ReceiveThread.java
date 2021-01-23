package Client;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReceiveThread extends Thread {

    private final Socket socket;
    private final JTextArea textArea;
    private final JList<String> jList;

    /**
     * 构造接收线程，接收的信息添加到{@link UI.ClientUI}的TextArea中，在线人员列表更新到{@link UI.ClientUI}的List中
     *
     * @param clientSocket 客户端socket
     * @param textArea     文本域
     * @param jList        在线列表
     */
    public ReceiveThread(Socket clientSocket, JTextArea textArea, JList<String> jList) {
        this.textArea = textArea;
        socket = clientSocket;
        this.jList = jList;
    }

    /**
     * 监听接收信息
     */
    @Override
    public void run() {
        try {
            Calendar calendar = Calendar.getInstance();  // 获得当前时间
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  // 设置时间的显示格式
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            String msgString;
            while ((msgString = br.readLine()) != null) {
                /*
                   当有"~!!@@##**~"开头的消息表示接下来发送的消息
                   将是在线人员名单,其中在线人员人数（num）在该开头之后的字符串中
                   通过循环num次读取全部在线人员昵称,而后更新list
                 */
                if (msgString.startsWith("~!!@@##**~")) {  // 接收在线人员名单
                    String strNum = msgString.substring(10);
                    int Num = Integer.parseInt(strNum);
                    final DefaultListModel<String> defaultListModel = new DefaultListModel<>();
                    for (int i = 0; i < Num; i++) {
                        msgString = br.readLine();
                        defaultListModel.addElement(msgString);
                    }
                    jList.setModel(defaultListModel);
                    continue;
                }
                /*
                  当有[系统通知]的开头的消息表示有人上线或者下线
                  此时需要向服务器发送#getOP消息获取在线人员列表
                 */
                if (msgString.startsWith("[系统通知]")) {
                    PrintWriter pw = new PrintWriter(
                            new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
                    pw.println("#getOP");
                }
                textArea.append(format.format(calendar.getTime()) + '\n' + msgString + '\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
