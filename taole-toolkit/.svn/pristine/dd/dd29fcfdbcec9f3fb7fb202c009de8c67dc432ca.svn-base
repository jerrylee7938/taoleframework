package com.taole.toolkit.util.mpp;

import com.taole.framework.util.JSONTransformer;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TaskObject implements Serializable {
	private String taskId;// 任务ID
	private String taskCode;// 任务编码
	private String taskName;// 任务名称
	private String owner;// 任务负责人
	private Date startTime;// 任务开始时间
	private Date endTime;// 任务结束时间
	private String fatherId;// 上级任务ID
	private double percentageComplete;// 当前完成比
	private Date earlyStart;// 最早开始日期
	private Date earlyFinish;// 最早结束日期
	private Date lateStart;// 最晚开始日期
	private Date lateFinish;// 最晚结束日期
	List<TaskDependency> dependencyList;// 任务前后置关系
	private Integer level; // 任务级别
	private String description;//任务备注

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getFatherId() {
		return fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}

	public double getPercentageComplete() {
		return percentageComplete;
	}

	public void setPercentageComplete(double percentageComplete) {
		this.percentageComplete = percentageComplete;
	}

	public Date getEarlyStart() {
		return earlyStart;
	}

	public void setEarlyStart(Date earlyStart) {
		this.earlyStart = earlyStart;
	}

	public Date getEarlyFinish() {
		return earlyFinish;
	}

	public void setEarlyFinish(Date earlyFinish) {
		this.earlyFinish = earlyFinish;
	}

	public Date getLateStart() {
		return lateStart;
	}

	public void setLateStart(Date lateStart) {
		this.lateStart = lateStart;
	}

	public Date getLateFinish() {
		return lateFinish;
	}

	public void setLateFinish(Date lateFinish) {
		this.lateFinish = lateFinish;
	}

	public List<TaskDependency> getDependencyList() {
		return dependencyList;
	}

	public void setDependencyList(List<TaskDependency> dependencyList) {
		this.dependencyList = dependencyList;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		String result = null;
		try {
			JSONObject jo = (JSONObject) JSONTransformer.transformPojo2Jso(this);
			result = jo.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
