package com.ancun.core.web.model.ext;

import java.io.Serializable;
import java.util.List;

/**
 * ExtJs Tree的节点
 * @param <T>
 * <p> 
 * @author  <a href="mailto:shenwei@ancun.com">ShenWei</a> create at 2013-11-17 上午1:01:28
 */
public abstract class ExtJsAbstractTreeNode<T> implements Serializable, ExtJsTreeNode<T> {

	private static final long serialVersionUID = -3819315597816493440L;
	private boolean leaf = true;
	private String iconCls;
	private String icon;
	private String cls;
	private String qtip; 
	private Object data;
	private boolean editable = false;	 
	private List<T> children;
	private String treeNodeType;

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public abstract String getText();

	public abstract Serializable getId();

	public List<T> getChildren() {
		return children;
	}

	public void setChildren(List<T> children) {
		this.children = children;
		if (children != null) {
			setLeaf(children.size() == 0);
		}
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getTreeNodeType() {
		return treeNodeType;
	}

	public void setTreeNodeType(String treeNodeType) {
		this.treeNodeType = treeNodeType;
	}

}
