package Main;

import UI.ServerUI;

import java.net.UnknownHostException;

/**
 * @author Susongfeng
 */
public class ServerMain {
    public static void main(String[] args) {
        try {
            new ServerUI();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
