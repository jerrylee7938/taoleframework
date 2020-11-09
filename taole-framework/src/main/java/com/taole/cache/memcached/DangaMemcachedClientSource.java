package com.taole.cache.memcached;

import java.io.IOException;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class DangaMemcachedClientSource {

	private String servers[] = { "127.0.0.1:11211" };
	private boolean failover = true;
	private int initConn = 10;
	private int minConn = 5;
	private int maxConn = 250;
	private long maintSleep = 30;
	private boolean nagle = false;
	private int socketTO = 3000;
	private boolean aliveCheck = true;

	/**
	 * @param failover the failover to set
	 */
	public void setFailover(boolean failover) {
		this.failover = failover;
	}

	/**
	 * @param initConn the initConn to set
	 */
	public void setInitConn(int initConn) {
		this.initConn = initConn;
	}

	/**
	 * @param minConn the minConn to set
	 */
	public void setMinConn(int minConn) {
		this.minConn = minConn;
	}

	/**
	 * @param maxConn the maxConn to set
	 */
	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}

	/**
	 * @param maintSleep the maintSleep to set
	 */
	public void setMaintSleep(long maintSleep) {
		this.maintSleep = maintSleep;
	}

	/**
	 * @param nagle the nagle to set
	 */
	public void setNagle(boolean nagle) {
		this.nagle = nagle;
	}

	/**
	 * @param socketTO the socketTO to set
	 */
	public void setSocketTO(int socketTO) {
		this.socketTO = socketTO;
	}

	/**
	 * @param aliveCheck the aliveCheck to set
	 */
	public void setAliveCheck(boolean aliveCheck) {
		this.aliveCheck = aliveCheck;
	}

	public void setServers(String servers) {
		this.servers = servers.split(",");
	}

	boolean inited = false;

	protected void initPool() {
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(servers);
		pool.setFailover(failover);
		pool.setInitConn(initConn);
		pool.setMinConn(minConn);
		pool.setMaxConn(maxConn);
		pool.setMaintSleep(maintSleep);
		pool.setNagle(nagle);
		pool.setSocketTO(socketTO);
		pool.setAliveCheck(aliveCheck);
		pool.initialize();
	}

	public synchronized MemCachedClient getMemCachedClient() throws IOException {
		if (!inited) {
			initPool();
			inited = true;
		}
		return new MemCachedClient();
	}

}
