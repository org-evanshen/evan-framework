package com.ancun.core.web.pagemeta;

import java.util.List;

/**
 * 页面meta集合，用于输出页面meta项
 * <p> 
 * @author  <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2011-4-13 上午10:19:27
 */
public interface PageMetas extends List<PageMeta> {

	final String PAGE_META_ARRAY_KEY = "screen_metaArray";
	final String NAME_DESCRIPTION = "description";
	final String NAME_KEY = "keywords";

	/**
	 * 添加meta项目
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2011-4-13 上午10:19:13 <br>
	 * @param name
	 * @param context
	 *
	 */
	PageMetas add(String name, String context);
}
