package com.ancun.core.web.pagemeta;

import java.util.ArrayList;

public class PageMetasArray extends ArrayList<PageMeta> implements PageMetas {
	private static final long serialVersionUID = 4606559145724019293L;

	public PageMetas add(String name, String content) {
		PageMeta m = new PageMeta();
		m.setName(name);
		m.setContent(content);

		this.add(m);

		return this;
	}
}
