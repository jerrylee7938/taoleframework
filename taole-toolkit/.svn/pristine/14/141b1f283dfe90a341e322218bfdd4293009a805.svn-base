package com.taole.toolkit.util.mpp;

import java.io.Serializable;

public class TaskDependency implements Serializable{
	private String fromId;
	private String toId;

	/**
	 * Finish-to-Start(FS)
	 * 把这个任务的开始日期和前提条件任务的结束日期对齐，一般用于串行的任务安排，前一个任务必须完成后才能启动下一个新任务
	 * Start-to-Start(SS)
	 * 把这个任务的开始日期和前提条件任务的开始日期对齐，一般用于并行任务的安排，也可以一个任务启动后，第二个任务延后或提前数日启动。
	 * Finish-to-Finish(FF)
	 * 把这个任务的结束日期和前提条件任务的结束日期对齐，可以用于协调任务的统一时间完成，这样可以定义好任务的开始时间.
	 * Start-to-Finish(SF)
	 * 把这个任务的结束日期和前提条件任务的开始日期对齐，或者说是前置任务开始的日期决定了后续任务的完成时间.======
	 **/
	private String type;

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
