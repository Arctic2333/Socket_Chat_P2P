package core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Susongfeng
 * @Title: ServerUI
 * @Package ui
 * @Description: Server UI
 * @date 2020-10-28 20:31
 */
public class Server {
    private JFrame jFrame = new JFrame("Server");
    private JTextArea jTextArea = new JTextArea(10, 40);
    private JScrollPane scrollPane_1 = new JScrollPane();  // 滚动条
    private JTextField jTextField = new JTextField();
    private JButton jButton = new JButton("发送");
    ActionHandle actionHandle = new ActionHandle();
    private Calendar calendar = Calendar.getInstance(); // get current instance of the calendar
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ServerSocket serverSocket = null;
    private Socket socket = null;

    public Server() {
        jButton.addActionListener(actionHandle);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int sWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int sHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        int winX = sWidth / 2;
        int winY = sHeight / 2;

        jFrame.setBounds(winX - 200, winY - 300, 700, 700);
        jFrame.setLayout(null);
        jFrame.setResizable(false);

        scrollPane_1.setBounds(10, 10, 500, 550);
        scrollPane_1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);  // 默认显示滚动条
        jFrame.getContentPane().add(scrollPane_1);

        jTextArea.setBounds(10, 10, 500, 550);
        jTextArea.setLineWrap(true);        //激活自动换行功能
        jTextArea.setWrapStyleWord(true);            // 激活断行不断字功能
        jTextArea.setEditable(false);  // 不允许直接编辑
        scrollPane_1.setViewportView(jTextArea);  // 向文件域中添加滚动条

        jTextField.setBounds(10, 580, 500, 40);
        jFrame.add(jTextField);

        jButton.setBounds(520, 580, 100, 40);
        jFrame.add(jButton);

        jFrame.setVisible(true);

        //业务逻辑处理
        try {
            serverSocket = new ServerSocket(8888);
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //接收线程
        new Thread(() -> {
            try {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                String msg = dataInputStream.readUTF();
                jTextArea.append(formatter.format(calendar.getTime()) + '\n' + "客户端：" + msg + '\n');
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

    class ActionHandle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jButton) {
                String msg = jTextField.getText();
                try {
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF(msg);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                jTextArea.append(formatter.format(calendar.getTime()) + '\n' + "服务器：" + msg + '\n');
                jTextField.setText("");
            }
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
