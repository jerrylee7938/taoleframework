package com.taole.toolkit.util.mpp;

import com.taole.toolkit.util.DateUtil;
import net.sf.mpxj.*;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.mpx.MPXWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("ALL")
public class MpxUtil {

	public static void main(String args[]) {
		try {
			// 创建mpp文件
			// create("d://output1.MPX");
			MpxUtil mu = new MpxUtil();
			List<Task> taskList = mu.getTaskList("d://test.mpp");
			List<TaskObject> taskObjList = mu.getBusObjList(taskList);
			for (TaskObject taskObject : taskObjList) {
				System.out.println(taskObject.toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
	}

	private static void create(String filename) throws Exception {
		ProjectFile file = new ProjectFile();
		Task task1 = file.addTask();
		task1.setName("root");
		task1.setTaskMode(TaskMode.MANUALLY_SCHEDULED);
		Date date1 = new Date();
		task1.setStart(date1);
		task1.setFinish(DateUtil.addHour(date1, 24));

		Task task12 = task1.addTask();
		task12.setName("任务A");
		task12.setTaskMode(TaskMode.AUTO_SCHEDULED);
		Date date2 = new Date();
		task1.setStart(date2);
		task1.setFinish(DateUtil.addHour(date2, 48));

		MPXWriter writer = new MPXWriter();
		// 设置中文
		writer.setLocale(Locale.CHINESE);
		writer.write(file, filename);
	}

	/**
	 * 读取mpp文件中的任务列表
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public List<Task> getTaskList(String filePath) throws Exception {
		File file = new File(filePath);
		MPPReader mppRead = new MPPReader();
		ProjectFile pf = mppRead.read(file);
		return pf.getAllTasks();
	}
	
	/**
	 * 读取mpp文件中的任务列表
	 * 
	 */
	public List<Task> getTaskList(File f) throws Exception {
		MPPReader mppRead = new MPPReader();
		ProjectFile pf = mppRead.read(f);
		return pf.getAllTasks();
	}

	/**
	 * 将mpp任务列表转化为业务任务对象列表
	 * 
	 * @param mpxjTaskList
	 * @return
	 */
	public List<TaskObject> getBusObjList(List<Task> mpxjTaskList) {
		List<TaskObject> busTaskObjectList = new ArrayList<TaskObject>();
		for (Task mpxTask : mpxjTaskList) {
			TaskObject taskObject = toObject(mpxTask);
			busTaskObjectList.add(taskObject);
		}
		return busTaskObjectList;
	}

	/**
	 * 将mpp任务转为任务对象
	 * 
	 * @param mpxTask
	 * @return
	 */
	private TaskObject toObject(Task mpxTask) {
		TaskObject taskObject = new TaskObject();
		taskObject.setTaskCode(Integer.toString(mpxTask.getUniqueID()));// mpx任务ID
		taskObject.setTaskName(mpxTask.getName());// 任务名称

		String resourceName = "";
		List<ResourceAssignment> assignments = mpxTask.getResourceAssignments();
		for (int i = 0; i < assignments.size(); i++) {
			ResourceAssignment assignment = (ResourceAssignment) assignments.get(i);
			Resource resource = assignment.getResource();
			if (resource != null) {
				resourceName = resourceName + resource.getName() + ",";
			}
		}

		taskObject.setOwner(resourceName);// 任务资源
		taskObject.setStartTime(mpxTask.getStart());// 任务起始时间
		taskObject.setEndTime(mpxTask.getFinish());// 任务结束时间
		taskObject.setPercentageComplete(mpxTask.getPercentageComplete().doubleValue());// 任务完成百分比
		if (mpxTask.getParentTask() != null) {
			taskObject.setFatherId(Integer.toString(mpxTask.getParentTask().getUniqueID()));
		}

		taskObject.setEarlyStart(mpxTask.getEarlyStart());// 最早开始时间
		taskObject.setEarlyFinish(mpxTask.getEarlyFinish());// 最早完成时间
		taskObject.setLateStart(mpxTask.getLateStart());// 最晚开始时间
		taskObject.setLateFinish(mpxTask.getLateFinish());// 最晚完成时间
		taskObject.setLevel(getLevel(mpxTask, 1));// 任务级别
		taskObject.setDescription(mpxTask.getNotes());// 备注

		List<Relation> predecessors = mpxTask.getPredecessors();// 任务前后置依赖关系
		if (predecessors != null && predecessors.isEmpty() == false) {
			List<TaskDependency> depList = new ArrayList<TaskDependency>();
			for (Relation relation : predecessors) {
				TaskDependency dep = new TaskDependency();
				dep.setToId(relation.getSourceTask().getUniqueID().toString());
				dep.setFromId(relation.getTargetTask().getUniqueID().toString());
				dep.setType(relation.getType().toString());
				depList.add(dep);
			}
			taskObject.setDependencyList(depList);
		}

		return taskObject;
	}

	private Integer getLevel(Task mpxTask, Integer level) {
		Task parentTask = mpxTask.getParentTask();
		if (parentTask != null) {
			level = level + 1;
			getLevel(parentTask, level);
		}
		return level;
	}
}
