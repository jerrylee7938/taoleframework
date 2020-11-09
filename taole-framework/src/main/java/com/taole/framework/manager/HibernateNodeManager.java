/*
 * @(#)NodeManager.java 0.1 Sep 12, 2008
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.manager;

import java.util.ArrayList;
import java.util.List;

import com.taole.framework.bean.Node;
import com.taole.framework.dao.NodeDao;
import com.taole.framework.exception.BusinessException;
import com.taole.framework.util.UUID;

/**
 * Class <code>NodeManager</code> is ...
 * 
 * @author Administrator
 * @version 0.1, Sep 12, 2008
 */
public abstract class HibernateNodeManager<T extends Node<T>> extends NodeManager<T> {

	protected abstract NodeDao<T> getNodeDao();

	/**
	 * 创建一个Node
	 * 
	 * @param node
	 * @return
	 */
	protected String saveNode(T node) {
		getNodeDao().saveNode(node);
		return node.getId();
	}

	/**
	 * 删除Node
	 * 
	 * @param node
	 */
	protected void deleteNode(T node) {
		getNodeDao().deleteNode(node);
	}

	protected void deleteAllNodes() {
		getNodeDao().deleteAllNodes();
	}

	/**
	 * 删除node根据指定的ID
	 * 
	 * @param id
	 * @return
	 */
	protected void deleteNodeById(String id) throws BusinessException {
		T node = getNodeById(id);
		if (node == null) {
			throw new BusinessException("Node[" + id + "] not exist.");
		}
		deleteNode(node);
	}

	protected T getNodeById(String id) {
		return getNodeDao().getNode(id);
	}

	/**
	 * 移动Object位置
	 * @param nodeId
	 * @param parentId
	 * @param index
	 * @throws BusinessException
	 */
	protected void moveNode(String id, String parentId, int index) throws BusinessException {
		T node = getNodeById(id);
		T newNode = node.clone();
		deleteNode(node);
		newNode.setId(UUID.generateUUID());
		T parentNode = getNodeById(parentId);
		saveNode(newNode);
		if (parentNode != null) {
			parentNode.addChildNode(newNode, index);
			for (T n : parentNode.getChildNodes()) {
				saveNode(n);
			}
		}
	}

	protected T copyNode(String id) throws BusinessException {
		T node = getNodeById(id);
		if (node == null) {
			return null;
		}
		T copy = node.copy();
		saveNode(copy);
		return copy;
	}

	/**
	 * @param nodeId
	 * @param offset 1: 向下移1位,9:移到最低,-1:向上移动一位,-9:移到最顶
	 * @throws BusinessException
	 */
	protected void moveNode(String id, int offset) {
		if (offset == 0) {
			return;
		}
		T object = getNodeById(id);
		if (object == null) {
			return;
		}
		T parentNode = object.parentNode();
		if (parentNode == null) {
			return;
		}
		List<T> brothers = parentNode.getChildNodes();
		int newIndex = object.getIndex() + offset;
		if (newIndex < 0) {
			newIndex = 0;
		} else if (newIndex >= brothers.size()) {
			newIndex = brothers.size() - 1;
		}
		if (object.getIndex() == newIndex) {
			return;
		}
		brothers.remove(object);
		brothers.add(newIndex, object);
		saveNode(object);
	}

	protected List<T> getRootNodes() {
		return getNodeDao().getRootNodes();
	}

	protected List<T> getAllNodes() {
		List<T> list = new ArrayList<T>();
		List<T> roots = getNodeDao().getRootNodes();
		for (T root : roots) {
			list.add(root);
			list.addAll(root.getAllChildNodes());
		}
		return list;
	}

}
