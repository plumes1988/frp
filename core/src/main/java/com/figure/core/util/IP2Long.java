package com.figure.core.util;

public class IP2Long {

	// 将127.0.0.1形式的IP地址转换成十进制整数，这里没有进行任何错误处理
	public static int ipToLong(String strIp) {
		int[] ip = new int[4];
		// 先找到IP地址字符串中.的位置
		int position1 = strIp.indexOf(".");
		int position2 = strIp.indexOf(".", position1 + 1);
		int position3 = strIp.indexOf(".", position2 + 1);
		// 将每个.之间的字符串转换成整型
		ip[0] = Integer.parseInt(strIp.substring(0, position1));
		ip[1] = Integer.parseInt(strIp.substring(position1 + 1, position2));
		ip[2] = Integer.parseInt(strIp.substring(position2 + 1, position3));
		ip[3] = Integer.parseInt(strIp.substring(position3 + 1));
		return (ip[3] << 24) + (ip[2] << 16) + (ip[1] << 8) + ip[0];
	}

	// 将十进制整数形式转换成127.0.0.1形式的ip地址
	public static String intToIP(int intIp) {
		String sb = "";
		// 直接右移24位
		sb = ".";
		sb += String.valueOf((intIp >>> 24));
		// 将高8位置0，然后右移16位
		sb = ((intIp & 0x00FFFFFF) >>> 16) + sb;
		sb = "." + sb;
		// 将高16位置0，然后右移8位
		sb = ((intIp & 0x0000FFFF) >>> 8) + sb;
		sb = "." + sb;
		// 将高24位置0
		sb = (intIp & 0x000000FF) + sb;
		return sb;
	}

	/** */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String ipStr = "192.168.110.54";
		int ipint=-1062703427;
		String ips=IP2Long.intToIP(1601415360);
		//System.out.println("转换："+ips);
		int intIp = IP2Long.ipToLong(ipStr);
		String ip = IP2Long.intToIP(intIp);
		int i = ip.indexOf(".");
		String j = ip.substring(i+1);
	}
}