package com.ancun.core.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class VelocityUtilTest {
	private String templetePath = "template/1.vm";

	private static final Map<String, Object> data = new HashMap<String, Object>();

	static {
		data.put("a", 1);
		data.put("b", new Date());
	}

	@Test
	public void testMergeTemplateFromClassPath() {
		String str = VelocityUtils.mergeTemplateFromClassPath(templetePath, data, "UTF-8");
		System.out.println(str);
	}

	@Test
	public void testMergeTemplateFromFilePath() {
		String content = VelocityUtils.mergeTemplateFromFilePath("d:/", "2.vm", data, "UTF-8");
		System.out.println(content);
	}
}
