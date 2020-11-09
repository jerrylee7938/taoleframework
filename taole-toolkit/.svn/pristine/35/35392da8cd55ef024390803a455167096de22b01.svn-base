package com.taole.toolkit.util.concurrence;

import java.io.IOException;

public class Threadtest {
	public static int o = 0;
	public static Object object = new Object();

	public static void main(String[] args) {
		try {

			ThreadPoolManager threadPoolManager = EchoThreadManager
					.getInstance();
			/*
			 * for (int i = 0; i < 1000; i++) {
			 * wdbThreadPoolManager.joinTask(new Runnable() {
			 * 
			 * @Override public void run() { synchronized (object) {
			 * System.out.println(++o); }
			 * 
			 * } }); }
			 */

			/*
			 * WdbThreadPoolManager wdbThreadPoolManager1 = EchoThreadManager
			 * .getInstance(); System.out .println( wdbThreadPoolManager ==
			 * wdbThreadPoolManager1);
			 */

			threadPoolManager.joinTask(new GeneratePdfTask(threadPoolManager));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class GeneratePdfTask extends GeneralThread<ThreadPoolManager> {

		public GeneratePdfTask(ThreadPoolManager target) {
			super(target);
		}

		@Override
		public void callBack(ThreadPoolManager target) {
			System.out.println(target.toString());
		}

	}
}
