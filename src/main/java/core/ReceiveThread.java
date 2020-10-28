package core;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author Susongfeng
 * @Title: ReceiveThread
 * @Package core
 * @Description: ReceiveMsg
 * @date 2020-10-28 15:19
 */
public class ReceiveThread extends Thread {
    private Socket socket;

    public ReceiveThread(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            while (true) {
                String msg = dataInputStream.readUTF();
                System.out.println(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
