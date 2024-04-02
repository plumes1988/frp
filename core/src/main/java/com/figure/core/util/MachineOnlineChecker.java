package com.figure.core.util;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

public class MachineOnlineChecker {

    public static boolean isMachineOnline(String ipAddress) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            return inetAddress.isReachable(1000); // 设置超时时间为5秒
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isMachineOnline(String ipAddress,Integer timeout) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            return inetAddress.isReachable(timeout); // 设置超时时间为5秒
        } catch (IOException e) {
            return false;
        }
    }




    public static void main(String[] args) {
        String ipAddress = "192.168.100.101"; // 要检查的 IP 地址
        boolean isOnline = isMachineOnline(ipAddress,100);
        if (isOnline) {
            System.out.println("机器在线");
        } else {
            System.out.println("机器不在线");
        }
    }
}