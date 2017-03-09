package com.ancun.core.web.pagemeta;

/**
 * 用于渲染页面的meta <br>
 * meta name="$!meta.name" content="$!meta.content"
 * <p>
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2011-4-13 上午09:47:57
 */
public class PageMeta {


	private String name;
	private String content;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
