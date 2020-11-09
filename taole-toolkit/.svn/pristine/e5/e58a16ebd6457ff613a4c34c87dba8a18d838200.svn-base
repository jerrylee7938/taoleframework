package com.taole.toolkit.util.concurrence;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @desc线程管理器（涉及socket操作，例如rpc远程调用）
 * @author ChengJ-Portal
 * 未写完，
 */
@SuppressWarnings("serial")
public class SocketThreadManagerment implements ThreadPoolManager {
	private static Log logger = LogFactory.getLog(SocketThreadManagerment.class);
	private ThreadPool threadPool;
	private ServerSocket serverSocket;
	private static SocketThreadManagerment currentInstance;
	static Lock lock = new ReentrantLock();

	public static SocketThreadManagerment getInstance(int port) throws IOException {
		lock.lock();
		try {
			if (null == currentInstance)
				currentInstance = new SocketThreadManagerment(port);
		} catch (Exception e) {
			logger.error("线程池初始化失败！" + e.getMessage());
		}

		finally {
			lock.unlock();
		}
		return currentInstance;
	}

	private SocketThreadManagerment(int port) throws IOException {
		super();
		serverSocket = new ServerSocket(port);
		// 创建线程池
		threadPool = new ThreadPool(Runtime.getRuntime()
				.availableProcessors() * POOL_SIZE);
		System.out.println("服务器启动！");
	}


	@Override
	public ThreadPool getThreadPool() {
		return threadPool;
	}

	@Override
	public void joinTask(Runnable task) {
		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				threadPool.execute(new Handler2Socket(socket));// 把与客户通讯的任务交给线程
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void joinTask(Runnable... task) {
		for (Runnable runnable : task)
			joinTask(runnable);
	}
}
