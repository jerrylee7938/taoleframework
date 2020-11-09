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

/**
 * Class <code>NodeManager</code> is ...
 * 
 * @author Administrator
 * @version 0.1, Sep 12, 2008
 */
public abstract class NodeManager<T extends Node<T>> {

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
		getNodeDao().deleteNodeById(id);
	}

	protected T getNodeById(String id) {
		return getNodeDao().getNode(id);
	}

	/**
	 * 移动Object位置
	 * @param id
	 * @param parentId
	 * @param index
	 * @throws BusinessException
	 */
	protected void moveNode(String id, String parentId, int index) throws BusinessException {
		getNodeDao().moveNode(id, parentId, index);
	}

	protected T copyNode(String id) throws BusinessException {
		return getNodeDao().copyNode(id);
	}

	/**
	 * @param id
	 * @param offset
	 * @throws BusinessException
	 */
	protected void moveNode(String id, int offset) {
		getNodeDao().moveNode(id, offset);
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
