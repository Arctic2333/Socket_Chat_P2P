package core;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Susongfeng
 * @Title: SendThread
 * @Package core
 * @Description: SendMsg
 * @date 2020-10-28 15:10
 */
public class SendThread extends Thread {
    private final Socket socket;

    public SendThread(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        try {
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            while (true) {
                Scanner scanner = new Scanner(System.in);
                String str = scanner.nextLine();
                dataOutputStream.writeUTF(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
