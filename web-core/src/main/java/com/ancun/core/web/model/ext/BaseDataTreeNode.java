package com.ancun.core.web.model.ext;

import java.io.Serializable;

import com.ancun.core.model.DataDictionary;

public class BaseDataTreeNode extends ExtJsAbstractTreeNode<BaseDataTreeNode> implements Serializable,
		ExtJsTreeNode<BaseDataTreeNode> {

	private static final long serialVersionUID = 7526030071960745274L;

	private DataDictionary pubDataDictionary;

	public BaseDataTreeNode(DataDictionary pubDataDictionary) {
		super();
		this.pubDataDictionary = pubDataDictionary;
		this.setLeaf(pubDataDictionary.isLeaf());
	}

	@Override
	public String getText() {
		return pubDataDictionary.getText();
	}

	@Override
	public Serializable getId() {
		return pubDataDictionary.getValue();
	}

}
