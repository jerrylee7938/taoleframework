package com.taole.toolkit.util.concurrence;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 普通线程管理器(一般业务线程)
 * @author ChengJ
 *
 */
@SuppressWarnings("serial")
public class EchoThreadManager implements ThreadPoolManager {
	private static Log LOGGER = LogFactory.getLog(EchoThreadManager.class);
	private ThreadPool threadPool;
	private static EchoThreadManager currentInstance;
	static Lock lock = new ReentrantLock();
	public static EchoThreadManager getInstance() throws IOException {
		lock.lock();
		try {
			if (null == currentInstance)
				currentInstance = new EchoThreadManager();
		} catch (Exception e) {
			LOGGER.error("线程池初始化失败！" + e.getMessage());
		}

		finally {
			lock.unlock();
		}
		return currentInstance;
	}

	private EchoThreadManager() throws IOException {
		super();
		// 创建线程池
		threadPool = new ThreadPool(Runtime.getRuntime()
				.availableProcessors() * POOL_SIZE);
	}

	@Override
	public ThreadPool getThreadPool() {
		return threadPool;
	}

	@Override
	public void joinTask(Runnable task) {
		threadPool.execute(task);
	}

	@Override
	public void joinTask(Runnable... task) {
		for (Runnable runnable : task)
			joinTask(runnable);
	}
}
