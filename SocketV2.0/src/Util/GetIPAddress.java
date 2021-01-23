package Util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetIPAddress {

    /**
     * 获取ip工具类不能被实例化
     */
    private GetIPAddress() {
        throw new UnsupportedOperationException("GetIPAddress Util cant be constructed");
    }

    /**
     * @return 当前主机的IP地址
     * @throws UnknownHostException
     */
    public static String getIPAddress() throws UnknownHostException {
        InetAddress host = InetAddress.getLocalHost();
        return host.getHostAddress();
    }
}
