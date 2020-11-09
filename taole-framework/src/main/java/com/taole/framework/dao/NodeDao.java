/*
 * @(#)NodeDao.java 0.1 Sep 14, 2008
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.dao;

import java.util.List;

import com.taole.framework.bean.Node;
import com.taole.framework.exception.BusinessException;


/**
 * Class <code>NodeDao</code> is ...
 *
 * @author  Administrator
 * @version 0.1, Sep 14, 2008
 */
public interface NodeDao<T extends Node<T>> {

	void saveNode(T node);
	void deleteNode(T node);
	void deleteAllNodes();
	List<T> getRootNodes();
	T getNode(String id);
	
	void deleteNodeById(String id) throws BusinessException;
	void moveNode(String id, String parentId, int index) throws BusinessException;
	T copyNode(String id) throws BusinessException;
	void moveNode(String id, int offset);

}
