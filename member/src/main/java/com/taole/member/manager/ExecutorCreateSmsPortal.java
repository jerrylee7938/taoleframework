package com.taole.member.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.taole.member.utils.RestClientUtil;

public class ExecutorCreateSmsPortal {

	private ExecutorService executor = Executors.newCachedThreadPool();
    
    public void asynTask(final String mobileNum, final String content,
			final String senderId, final String sendTime, final String status, final String type,
			final String mtType, final String description) throws InterruptedException {
        
        executor.submit(new Runnable() {
            @Override
            public void run() {
            	try {
            		RestClientUtil.createSmsForPortal(mobileNum, content, senderId, 
            				sendTime, status, type, mtType, description);
				} catch (Exception e) { 
					e.printStackTrace();
				}
            }
        });
    }
}
