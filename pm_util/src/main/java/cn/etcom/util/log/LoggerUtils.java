package cn.etcom.util.log;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

public class LoggerUtils {

	public static final String LOGGER_RETURN = "logger_return";

	private LoggerUtils() {
	}

	/**
	 * 获取客户端ip地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getCliectIp(HttpServletRequest request) {
		String client_ip = request.getHeader("x-forwarded-for");
		if (client_ip == null || client_ip.length() == 0 || "unknown".equalsIgnoreCase(client_ip)) {
			client_ip = request.getHeader("Proxy-Client-IP");
		}
		if (client_ip == null || client_ip.length() == 0 || "unknown".equalsIgnoreCase(client_ip)) {
			client_ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (client_ip == null || client_ip.length() == 0 || "unknown".equalsIgnoreCase(client_ip)) {
			client_ip = request.getRemoteAddr();
			if (client_ip.equals("127.0.0.1") || client_ip.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				client_ip = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (client_ip != null && client_ip.length() > 15) { // "***.***.***.***".length() = 15
			if (client_ip.indexOf(",") > 0) {
				client_ip = client_ip.substring(0, client_ip.indexOf(","));
			}
		}
		return client_ip;
	}

	/**
	 * 判断是否为ajax请求
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestType(HttpServletRequest request) {
		return request.getHeader("X-Requested-With");
	}
}