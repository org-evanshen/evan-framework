package com.ancun.core.web.model.ext;

import java.io.Serializable;
import java.util.List;

/**
 * ExtJs Tree的节点
 * 
 * @param <T>
 *            <p>
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a> create at 2013-11-17 上午1:00:47
 *
 *
 *
 */
public interface ExtJsTreeNode<T> extends Serializable {

	String getText();

	Serializable getId();

	boolean isLeaf();

	String getIconCls();

	String getCls();

	List<T> getChildren();
}
