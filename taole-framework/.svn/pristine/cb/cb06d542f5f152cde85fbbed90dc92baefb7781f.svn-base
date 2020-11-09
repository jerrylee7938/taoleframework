/*
 * @(#)EventTarget.java 0.1 2009-10-10
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.events;

import java.util.LinkedHashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taole.framework.util.ThreadLocalFactory;

public class EventTarget {

	private static Log logger = LogFactory.getLog(EventTarget.class);

	private Set<EventListener> listeners = new LinkedHashSet<EventListener>();

	private Queue<EventObject> queue = new LinkedBlockingQueue<EventObject>(0xffffff); // 队列长度支持最大 16777216

	// 线程等待的间隔时间
	long interval = 100;

	// 最大的允许的处理线程数
	public static int maxAllowThreads = 128;

	// 当前最大的活动线程数
	private static int currentThreads = 0;

	Thread task = new Thread(new Runnable() {
		public void run() {
			while (running) {
				try {
					if (currentThreads >= maxAllowThreads) {
						Thread.sleep(interval);
					} else {
						EventObject event = pollEventFromQueue();
						if (event != null) {
							fireAsyncEvent(event);
						} else {
							Thread.sleep(interval);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	});

	private void fireAsyncEvent(final EventObject event) {
		new Thread(new Runnable() {
			public void run() {
				try {
					currentThreads++;
					ThreadLocalFactory.cleanAllThreadLocals();
					// ThreadCacheFactory.initCacheFactory(new MapCacheFactoryImpl());
					notifyEvents(event);
				} finally {
					currentThreads--;
				}
			}
		}).start();
	}

	private EventObject pollEventFromQueue() {
		synchronized (queue) {
			return queue.poll();
		}
	}

	private void addEventInQueue(EventObject event) {
		if (currentThreads >= maxAllowThreads) {
			synchronized (queue) {
				queue.add(event);
			}
		} else {
			fireAsyncEvent(event);
		}

	}

	public Queue<EventObject> getQueue() {
		return queue;
	}

	public synchronized void addEventListener(EventListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		listeners.add(listener);
	}

	public synchronized void removeEventListener(EventListener listener) {
		listeners.remove(listener);
	}

	public void fireEvent(String type, Object arg) {
		fireEvent(type, arg, true);
	}

	public void fireEvent(String type, Object arg, boolean ansync) {
		fireEvent(new EventObject(type, arg), ansync);
	}

	public void fireEvent(EventObject event) {
		fireEvent(event, true);
	}

	public void fireEvent(EventObject event, boolean ansync) {
		event.setTarget(this);
		if (!ansync) {
			notifyEvents(event);
		} else {
			addEventInQueue(event);
			if (!running) {
				start();
			}
		}
	}

	/**
	 * 通知所有 listeners
	 * 
	 * @param event
	 */
	public void notifyEvents(EventObject event) {
		EventListener[] arrLocal;
		synchronized (this) {
			arrLocal = (EventListener[]) listeners.toArray(new EventListener[0]);
		}
		for (EventListener listener : arrLocal) {
			try {
				// logger.debug("Start handle event " + event.getType() + " by " + listener.toString());
				listener.handleEvent(event);
			} catch (Exception e1) {
				logger.error("Handle Event Error! listener:" + listener.getClass() + ", message:" + e1.getMessage());
				e1.printStackTrace();
			}
		}
	}

	private boolean running = false;

	public void start() {
		if (!running) {
			running = true;
			task.start();
		}
	}

	public void stop() {
		if (running) {
			running = false;
			task.interrupt();
		}
	}

	/**
	 * @return the currentThreads
	 */
	public static int getCurrentThreads() {
		return currentThreads;
	}

}
