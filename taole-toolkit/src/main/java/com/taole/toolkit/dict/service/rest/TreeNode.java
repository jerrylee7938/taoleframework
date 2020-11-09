package com.taole.toolkit.dict.service.rest;

import java.util.ArrayList;
import java.util.List;

import com.taole.framework.bean.Node;

public class TreeNode {

	private String id;
	private String name;
	private String iconCls;
	private String type;
	boolean leaf;
	private List<TreeNode> children;

	public String getType() {
		return type;
	}
	
	public String getIconCls() {
		return iconCls;
	}
	
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public List<TreeNode> getChildren() {
		if (children == null) {
			children = new ArrayList<TreeNode>();
		}
		return children;
	}
	
	@SuppressWarnings("unchecked")
	public TreeNode addChildren(List<Node<?>> children, String type) {
		if (children != null && !children.isEmpty()) {
			for (Node<?> node : children) {
				TreeNode child = new TreeNode();
				child.setId(node.getId());
				child.setLeaf(!node.hasChild());
				child.setName(node.getName());
				child.setType(type);
				if (node.hasChild()) {
					child.addChildren((List<Node<?>>) node.getChildNodes(), type);
				}
				getChildren().add(child);
			}
		}
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public TreeNode addChild(Node<?> child, String type) {
		if (child != null) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(child.getId());
			treeNode.setLeaf(!child.hasChild());
			treeNode.setName(child.getName());
			treeNode.setType(type);
			if (child.hasChild()) {
				treeNode.addChildren((List<Node<?>>) child.getChildNodes(), type);
			}
			getChildren().add(treeNode);
		}
		return this;
	}
	
	public TreeNode addChild(Node<?> child) {
		return addChild(child, null);
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
	public TreeNode addChild(TreeNode child) {
		if (children == null) {
			children = new ArrayList<TreeNode>();
		}
		children.add(child);
		return this;
	}
}
