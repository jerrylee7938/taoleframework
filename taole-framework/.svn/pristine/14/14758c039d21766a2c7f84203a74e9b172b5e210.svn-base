package com.taole.framework.cluster;

import java.io.Serializable;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import javax.annotation.PostConstruct;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;

public class Node implements Serializable {

	private static final long serialVersionUID = 4379376611517664076L;

	private static final String LOCAL_IP_ADDR = "127.0.0.1";

	static Log logger = LogFactory.getLog(Node.class);

	/**
	 * 名称
	 */
	String name;

	/**
	 * 启动时间
	 */
	long startTime = System.currentTimeMillis();

	/**
	 * 最近活动时间
	 */
	long lastTime = System.currentTimeMillis();

	/**
	 * 本地IP
	 */
	String ipAddress;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	@PostConstruct
	public void init() {
		if (ipAddress == null) {
			ipAddress = getLocalIp();
		}
		if (name == null) {
			try {
				Context context = new InitialContext();
				this.name = (String) context.lookup("java:comp/env/cluster/node");
			} catch (Exception e) {
				logger.info("Not found jndi for cluster/node, node name is ip address.");
				name = ipAddress;
			}
		}
	}

	@Scheduled(fixedDelay = 1000)
	public void autoRun() {
		Cluster.getInstance().register(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("name", getName()).append("ip", getIpAddress()).append("startTime", getStartTime()).append("lastTime", getLastTime()).toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(name).append(ipAddress).toHashCode();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Node rhs = (Node) obj;
		return new EqualsBuilder().append(name, rhs.getName()).append(ipAddress, rhs.getIpAddress()).isEquals();
	}

	/**
	 * 获得本机IP
	 * 
	 * @return
	 */
	private static String getLocalIp() {
		String idAddress = LOCAL_IP_ADDR;
		Enumeration<NetworkInterface> netInterfaces = null;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				for (InterfaceAddress interfaceAddress : ni.getInterfaceAddresses()) {
					String addr = interfaceAddress.getAddress().getHostAddress();
					if (interfaceAddress.getAddress().isSiteLocalAddress() && !LOCAL_IP_ADDR.equals(addr)) {
						idAddress = addr;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idAddress;
	}

	public static void main(String[] args) {
		System.out.println(getLocalIp());
	}

}
