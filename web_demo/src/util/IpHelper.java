/**
 * IpHelper.java
 * Copyright(c) 2000-2009 HC360.COM, All Rights Reserved.
 */

package util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

/**
 * 获取IP地址类
 * 
 * @author yanliangbing
 * @version 4.0 Jul 29, 2009
 * @since 4.0
 */
public class IpHelper {
	private static final Logger logger = Logger.getLogger(IpHelper.class);

	private static String LOCAL_IP_STAR_STR = "192.168.";

	static {
		String ip = null;
		String hostName = null;
		try {
			hostName = InetAddress.getLocalHost().getHostName();
			InetAddress ipAddr[] = InetAddress.getAllByName(hostName);
			for (int i = 0; i < ipAddr.length; i++) {
				ip = ipAddr[i].getHostAddress();
				if (ip.startsWith(LOCAL_IP_STAR_STR)) {
					break;
				}
			}
			if (ip == null) {
				ip = ipAddr[0].getHostAddress();
			}

		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
		}

		LOCAL_IP = ip;
		HOST_NAME = hostName;

	}

	/** 系统的本地IP地址 */
	public static final String LOCAL_IP;

	public static final String HOST_NAME;

}
