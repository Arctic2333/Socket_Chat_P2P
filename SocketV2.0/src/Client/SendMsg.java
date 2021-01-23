package Client;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SendMsg {

    private final Socket socket;
    private final JTextArea textArea;

    /**
     * 构造发送线程,将{@link UI.ClientUI}中的发送框中的信息提交到服务器
     *
     * @param socket   客户端socket
     * @param textArea 发生框
     */
    public SendMsg(Socket socket, JTextArea textArea) {
        this.socket = socket;
        this.textArea = textArea;
    }

    /**
     * 发送信息
     */
    public void sendMessage() {
        String msg = textArea.getText();
        try {
            PrintWriter pw = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            pw.println(msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        textArea.setText("");  // 清空发送框中的已发送信息
    }
}
