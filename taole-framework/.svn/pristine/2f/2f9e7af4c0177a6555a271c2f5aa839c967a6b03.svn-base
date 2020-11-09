/*
 * @(#)Node.java 0.1 Sep 12, 2008
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.bean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import com.taole.framework.annotation.NamePosition;
import com.taole.framework.annotation.NonEntityField;
import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.util.UUID;

/**
 * Class <code>Node</code> is ...
 * 
 * @author Administrator
 * @version 0.1, Sep 12, 2008
 */
@MappedSuperclass
public abstract class Node<T extends Node<T>> extends ExtendableObject implements Comparable<T>, DomainObject {

	private static final long serialVersionUID = 7954003150032687970L;
	protected static final String SEPARATOR = "/";

	@NonEntityField
	private T parentNode;
	
	private String parentId;

	private List<T> childNodes = new LinkedList<T>();

	@PrimaryKey
	private String id;

	@NotBlank
	@NamePosition
	private String name;

	@NonEntityField
	private Integer index;

	@Id
	@Column(name = "VC2_CODE", length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "VC2_NAME", length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "VC2_FATHER", length = 36)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	@Column(name = "NUM_ORDER")
	public Integer getIndex() {
		if (index == null) {
			if (parentNode() != null) {
				int i = parentNode().getChildNodes().indexOf(this);
				if (i >= 0) {
					index = i;
				}
			} else if (index != null && index < 0) {
				index = 0;
			} else {
				index = 0;
			}
		}
		return index;
	}

	@Transient
	public T getParentNode() {
		return parentNode;
	}

	public T parentNode() {
		return parentNode;
	}

	public void setParentNode(T parentNode) {
		if (parentNode == null) {
			this.parentId = null;
		} else {
			this.parentId = parentNode.getId();
		}
		this.parentNode = parentNode;
	}

	@SuppressWarnings("unchecked")
	@Transient
	public T getRootNode() {
		return parentNode() == null ? (T) this : parentNode().getRootNode();
	}

	public boolean hasChild() {
		return !childNodes.isEmpty();
	}

	@Transient
	public List<T> getChildNodes() {
		return this.childNodes;
	}

	/**
	 * 取当前节点的所有祖先节点。
	 * 
	 * @return ancestors and empty list if not exist.
	 */
	@Transient
	public List<T> getAncestors() {
		List<T> ancestors = new LinkedList<T>();
		if (this.parentNode != null) {
			ancestors.add(parentNode);
			ancestors.addAll(this.parentNode.getAncestors());
		}
		return ancestors;
	}

	@SuppressWarnings("unchecked")
	public void setChildNodes(List<T> childNodes) {
		List<T> children = new ArrayList<T>();
		for (T item : childNodes) {
			item.setParentNode((T) this);
			children.add(item);
		}
		this.childNodes = childNodes;
	}

	public boolean containsChildNode(T node) {
		return childNodes.contains(node);
	}

	@SuppressWarnings("unchecked")
	public void addChildNode(T obj) {
		if (StringUtils.isBlank(obj.getId())) {
			obj.setId(UUID.generateUUID());
		}
		if (!containsChildNode(obj)) {
			obj.removeFromParentNode();
			obj.setParentNode((T) this);
			childNodes.add(obj);
			obj.setIndex(childNodes.indexOf(obj));
		}
	}

	@SuppressWarnings("unchecked")
	public void addChildNode(T obj, int index) {
		if (index < 0) {
			index = 0;
		} else if (index >= childNodes.size()) {
			index = childNodes.size();
		}
		obj.removeFromParentNode();
		obj.setParentNode((T) this);
		childNodes.add(index, obj);
		obj.setIndex(childNodes.indexOf(obj));
	}

	public void removeChildNode(T obj) {
		if (containsChildNode(obj)) {
			childNodes.remove(obj);
			obj.setParentNode(null);
		}
	}

	@SuppressWarnings("unchecked")
	public void removeFromParentNode() {
		if (parentNode() != null) {
			this.parentNode().removeChildNode((T) this);
		}
	}

	/**
	 * 取当前结点的路径用默认的分隔符 {@value #SEPARATOR}
	 * 
	 * @return
	 */
	@Transient
	public String getPath() {
		return getPath(SEPARATOR);
	}

	/**
	 * 取当前结点的路径，用指定的分隔符。
	 * 
	 * @param separator
	 * @return
	 */
	@Transient
	public String getPath(String separator) {
		String path = separator + this.getName();
		if (parentNode() != null) {
			path = parentNode().getPath(separator) + path;
		}
		return path;
	}
	
	/**
	 * 取当前结点的路径，用指定的分隔符。
	 * 
	 * @param separator
	 * @return
	 */
	@Transient
	public String getPathId(String separator) {
		String path = separator + this.getId();
		if (parentNode() != null) {
			path = parentNode().getPathId(separator) + path;
		}
		return path;
	}

	/**
	 * 取得当前节点在树中的深度。
	 * 
	 * @return
	 */
	@Transient
	public int getDepth() {
		if (parentNode() != null) {
			return parentNode().getDepth() + 1;
		} else {
			return 0;
		}
	}

	/**
	 * 得到当前节点下的所有子孙节点
	 * 
	 * @return
	 */
	@Transient
	public List<T> getAllChildNodes() {
		List<T> children = new LinkedList<T>();
		for (T child : childNodes) {
			children.add(child);
			children.addAll(child.getAllChildNodes());
		}
		return children;
	}

	/**
	 * 得到当前节点所在树所有节点的id列表。包括当前节点的id。
	 * 
	 * @return
	 */
	@Transient
	public List<String> getAllNodeIds() {
		List<String> list = new ArrayList<String>();
		list.add(getId());
		for (T child : childNodes) {
			list.addAll(child.getAllNodeIds());
		}
		return list;
	}

	/**
	 * 取当前节点下所有的叶节点。
	 * 
	 * @return
	 */
	@Transient
	public List<T> getLeafChildNodes() {
		List<T> list = new ArrayList<T>();
		for (T child : childNodes) {
			if (child.hasChild()) {
				list.addAll(child.getLeafChildNodes());
			} else {
				list.add(child);
			}
		}
		return list;
	}

	/**
	 * 判断当前节点是不由 {@code o} 派生出来的(当前节点是不是 o 的子孙节点)
	 * 
	 * @param o
	 * @return
	 */
	@Transient
	public boolean isParentFrom(T o) {
		if (o == null) {
			return true;
		} else if (this.getId().equals(o.getId())) {
			return true;
		} else if (this.parentNode == null) {
			return false;
		} else {
			return this.parentNode.isParentFrom(o);
		}
	}

	private T searchSameParent(T o) {
		if (isParentFrom(o)) {
			return o;
		} else if (o.getParentNode() != null) {
			return searchSameParent(o.getParentNode());
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public T searchParentByDept(int dept) {
		if (this.getDepth() == dept) {
			return (T) this;
		} else if (this.parentNode == null || this.getDepth() < dept) {
			return null;
		} else {
			return this.parentNode.searchParentByDept(dept);
		}
	}

	@SuppressWarnings("unchecked")
	public T selectSingleNode(String id) {
		if (id == null) {
			return null;
		}
		if (id.equals(getId())) {
			return (T) this;
		}
		for (T child : childNodes) {
			T node = child.selectSingleNode(id);
			if (node != null) {
				return node;
			}
		}
		return null;
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getId()).append(getName()).toHashCode();
	}

	public boolean equals(T another) {
		if (another == null) {
			return false;
		}
		if (another == this) {
			return true;
		}
		if (another.getClass() != getClass()) {
			return false;
		}
		return new EqualsBuilder().append(getId(), another.getId()).append(getName(), another.getName()).isEquals();
	}

	public int compareTo(T o) {
		if (this.equals(o)) {
			return 0;
		}
		int brotherDept = 0;
		T sameParent = searchSameParent(o); // 查询节点统一父节点
		if (sameParent != null) {
			if (this.equals(sameParent)) {
				return -1;
			} else if (o.equals(sameParent)) {
				return 1;
			}
			brotherDept = sameParent.getDepth() + 1;
		}
		T t0 = this.searchParentByDept(brotherDept);
		T t1 = o.searchParentByDept(brotherDept);
		if (t0 == t1) {
			return 0;
		} else {
			return t0.getIndex() - t1.getIndex();
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#clone()
	 */
	@SuppressWarnings("unchecked")
	public T clone() {
		try {
			T cloneNode = (T) super.clone();
			if (cloneNode.hasChild()) {
				List<T> children = new ArrayList<T>();
				for (T child : cloneNode.getChildNodes()) {
					children.add(child.clone());
				}
				cloneNode.setChildNodes(children);
			}
			cloneNode.setParentNode(null);
			return cloneNode;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(); // Can't happen
		}
	}

	public T copy() {
		T copyNode = clone();
		setId(null);
		for (T child : copyNode.getAllChildNodes()) {
			child.setId(null);
		}
		return copyNode;
	}
}
