package com.ancun.core.web.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ancun.core.model.PageResult;
import com.ancun.core.web.model.ext.ExtJsFormPostResult;
import com.ancun.core.web.model.ext.ExtPageResult;

public class ExtJsUtils {	

	@SuppressWarnings("rawtypes")
	public static ExtPageResult createExtPageResult(PageResult pageResult) {
		ExtPageResult extPageResult = new ExtPageResult();

		extPageResult.setData(pageResult.getData());
		extPageResult.setRecordCount(pageResult.getRecordCount());

		return extPageResult;
	}

	@SuppressWarnings("rawtypes")
	public static ExtPageResult createExtPageResult(List list) {
		ExtPageResult extPageResult = new ExtPageResult();

		extPageResult.setData(list);
		extPageResult.setRecordCount(list.size());

		return extPageResult;
	}

	public static <T> ExtJsFormPostResult<T> createExtFormResponse(T o) {
		ExtJsFormPostResult<T> f = new ExtJsFormPostResult<T>();

		f.setSuccess(true);
		f.setData(o);

		return f;
	}



	public static String getSuccess() {
		return "{success:true}";
	}

	public static String getFail(String dataJsonStr) {
		StringBuilder sb = new StringBuilder(128);
		sb.append("{success:false");
		if (StringUtils.isNotBlank(dataJsonStr)) {
			sb.append("," + dataJsonStr);
		}
		sb.append("}");
		return sb.toString();
	}
}
