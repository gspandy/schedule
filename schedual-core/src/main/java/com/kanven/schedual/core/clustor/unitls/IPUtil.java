package com.kanven.schedual.core.clustor.unitls;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IPUtil {

	public static String getIp() {
		try {
			Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
			while (enumeration.hasMoreElements()) {
				NetworkInterface networkInterface = enumeration.nextElement();
				Enumeration<InetAddress> inetAddresss = networkInterface.getInetAddresses();
				while (inetAddresss.hasMoreElements()) {
					InetAddress inetAddress = inetAddresss.nextElement();
					if (inetAddress.isSiteLocalAddress() && !inetAddress.isLoopbackAddress()
							&& inetAddress.getHostAddress().indexOf(":") == -1) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			throw new RuntimeException("获取IP地址信息出现异常！", e);
		}
		throw new RuntimeException("没有获取到IP地址信息！");
	}

}
