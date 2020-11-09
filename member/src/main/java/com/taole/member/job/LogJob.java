package com.taole.member.job;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.taole.log.manager.LogManager;

@Component
public class LogJob {

	@Autowired
	private LogManager logManager;
	
	@Scheduled(cron = "0 0 2 * * ?")
	public void execute(){
		try {
			logManager.save();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
