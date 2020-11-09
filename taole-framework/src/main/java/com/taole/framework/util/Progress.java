package com.taole.framework.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Progress {

	private String id;
	private int total = 0;
	private int value = 0;
	private Date dateStart = new Date();
	private Date dateEnd;
	private String description;
	private boolean finished = false;
	
	public Progress () {
		
	}

	public Progress(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public void increment() {
		this.value++;
	}

	public void finish() {
		this.value = this.total;
		this.dateEnd = new Date();
		finished = true;
	}
	
	private static Map<String, Progress> pool = new HashMap<String, Progress>();

	public static Progress newProgress(String id) {
		Progress progress = new Progress(id);
		pool.put(id, progress);
		return progress;
	}

	public static Progress newProgress() {
		return newProgress(UUID.generateUUID());
	}

	public static Progress getProgress(String id, boolean autoCreate) {
		if (!pool.containsKey(id) && autoCreate) {
			return newProgress(id);
		} else {
			return pool.get(id);
		}
	}

	@SuppressWarnings("unchecked")
	private static ThreadLocal<Progress> tl = (ThreadLocal<Progress>) ThreadLocalFactory.createThreadLocal();
	
	public static Progress currentProgressInThread() {
		return tl.get();
	}
	
	public static Progress initProgressInThread() {
		Progress progress = newProgress();
		tl.set(progress);
		return progress;
	}
	
	public static Progress initProgressInThread(String id) {
		Progress progress = newProgress(id);
		tl.set(progress);
		return progress;
	}
	

}
