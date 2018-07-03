package com.fanzhuo.framework.util;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NetworkUtil {

    private static Logger log = LoggerFactory.getLogger(NetworkUtil.class);

    // mac、ip
    private static List<String> macList = new ArrayList<String>();
    private static List<String> ipList = new ArrayList<String>();

    /**
     * 获取本机的MAC、IP
     */
    public static void init() {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
//				log.info("{}, {}, {}, {}", ni.getName(), ni.getDisplayName(), ni.isUp(), ni.getHardwareAddress());

                byte[] macBytes = ni.getHardwareAddress();
                if (macBytes != null && macBytes.length > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (byte b : macBytes) {
                        sb.append(StringUtils.leftPad(Integer.toHexString(b & 0xFF), 2, "0")).append("-");
                    }
                    String mac = sb.substring(0, sb.length() - 1).toUpperCase();
                    macList.add(mac);
                }

                // network interface
//				log.info("----ni.getInterfaceAddresses()-----------");
//				List<InterfaceAddress> niaList = ni.getInterfaceAddresses();
//				for (InterfaceAddress nia : niaList) {
//					InetAddress ia = nia.getAddress();
//					log.info("{}, {}, {}", ia, ia.getHostName(), ia.getHostAddress());
//				}

                // address
//				log.info("----ni.getInetAddresses()-----------");
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    InetAddress ia = ias.nextElement();
//					log.info("{}, {}, {}", ia, ia.getHostName(), ia.getHostAddress());
                    ipList.add(ia.getHostAddress());// ipv4, ipv6
                }

                // e.g. virtual interfaces
//				Enumeration<NetworkInterface> nisSub = ni.getSubInterfaces();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 判断本机是否包含指定MAC
     *
     * @param mac
     * @return
     */
    public static boolean containsLocalMac(String mac) {
        if (macList == null || macList.isEmpty()) {
            NetworkUtil.init();
        }
        return macList.contains(mac);
    }

    /**
     * 判断本机是否包含指定IP
     *
     * @param ip
     * @return
     */
    public static boolean containsLocalIp(String ip) {
        if (ipList == null || ipList.isEmpty()) {
            NetworkUtil.init();
        }
        return ipList.contains(ip);
    }

    /**
     * 获取远端的IP(例如接口调用方)，请求可能被代理，优先从请求头部获取
     *
     * @param req
     * @return
     */
    public static String getRemoteAddr(HttpServletRequest req) {
        // nginx custom defined
        String ip = req.getHeader("Remote-Addr");
        // x-forwarded-for
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("x-forwarded-for");
        }
        // Proxy-Client-IP
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        // WL-Proxy-Client-IP
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        // request RemoteAddr
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }

        if (StringUtils.isNotBlank(ip) && ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(","));
        }
        if (StringUtils.isNotBlank(ip) && ip.indexOf(" ") > 0) {
            ip = ip.substring(0, ip.indexOf(" "));
        }
        return ip;
    }

    /**
     * 获取远端的端口(例如接口调用方)，请求可能被代理，优先从请求头部获取
     *
     * @param req
     * @return
     */
    public static int getRemotePort(HttpServletRequest req) {
        // nginx custom defined
        String port = req.getHeader("Remote-Port");
        port = StringUtils.trimToEmpty(port);

        int remotePort = 0;
        if (StringUtils.isBlank(port) || !NumberUtils.isDigits(port)) {
            // request RemotePort
            remotePort = req.getRemotePort();
        } else {
            remotePort = Integer.valueOf(port);
        }
        return remotePort;
    }

    public static void main(String[] args) {
        log.info("Contains MAC 84-4B-F5-8D-3A-FB = {}", NetworkUtil.containsLocalMac("84-4B-F5-8D-3A-FB"));
        log.info("Contains MAC 84-4B-F5-8D-3A-AA = {}", NetworkUtil.containsLocalMac("84-4B-F5-8D-3A-AA"));

        log.info("Contains IP 127.0.0.1 = {}", NetworkUtil.containsLocalIp("127.0.0.1"));
        log.info("Contains IP 172.1.1.189 = {}", NetworkUtil.containsLocalIp("172.1.1.189"));
        log.info("Contains IP 172.1.1.130 = {}", NetworkUtil.containsLocalIp("172.1.1.130"));

//		InetAddress ia = InetAddress.getLocalHost();
//		log.info("{}, {}, {}", ia, ia.getHostName(), ia.getHostAddress());

//		try {
//			InetAddress ia = InetAddress.getLocalHost();
//			byte[] arr = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
//			if (arr != null && arr.length > 0) {
//				StringBuilder sb = new StringBuilder();
//				for (byte b : arr) {
//					sb.append(StringUtils.leftPad(Integer.toHexString(b & 0xFF), 2, "0")).append("-");
//				}
//				log.info(sb.substring(0, sb.length()-1).toUpperCase());
//			} else {
//				log.info("no mac");
//			}
//		} catch (UnknownHostException e) {
//			log.error(e.getMessage(), e);
//		} catch (SocketException e) {
//			log.error(e.getMessage(), e);
//		}
    }

}
