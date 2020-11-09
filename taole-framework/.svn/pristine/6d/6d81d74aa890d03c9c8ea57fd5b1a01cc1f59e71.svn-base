/**
 * Project:taole-heaframework
 * File:QueueTest.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;

/**
 * @author Rory
 * @date Feb 15, 2012
 * @version $Id: QueueTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class QueueTest {

	@Test
	public void testQueue() throws Exception {
		Queue<Integer> integerQueue = new LinkedBlockingQueue<Integer>(0x100000);
		for (int i = 0 ; i < 50; i ++ ) {
			integerQueue.add(i);
		}
		
		Integer number = integerQueue.poll();
		while (number != null) {
			System.out.println(number);
			number = integerQueue.poll();
		}
	}
}
