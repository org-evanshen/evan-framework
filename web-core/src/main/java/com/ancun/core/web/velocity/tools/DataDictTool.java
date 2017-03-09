package com.ancun.core.web.velocity.tools;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.ancun.core.datadict.DataDictionaryService;
import com.ancun.core.model.DataDictionary;

/**
 * velocity app common tool
 * 
 */
public class DataDictTool {
	//private final static Logger log = LoggerFactory.getLogger(DataDictTool.class);

	private WebApplicationContext context;
	private DataDictionaryService dataDictionaryService;

	public void init(Object obj) {
		context = ContextLoaderListener.getCurrentWebApplicationContext();
		dataDictionaryService = context.getBean(DataDictionaryService.class);
	}	

	/**
	 * 获取数据字典
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2010-12-9 下午10:08:47 <br>
	 * 
	 *
	 */
	public List<DataDictionary> getDataDictionarys(String dictGroup) {
		if (StringUtils.isBlank(dictGroup)) {
			return null;
		}

		List<DataDictionary> list = dataDictionaryService.getByGroup(dictGroup);
		//		if (log.isDebugEnabled()) {
		//			log.debug("get [" + list.size() + "] data dictionarys these key is :[" + dictGroup + "]");
		//		}
		return list;
	}

	/**
	 * 根据dictGroup和value获取数据字典中对应的名称
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2011-2-27 下午04:18:26 <br>
	 * 
	 * @param dictGroup
	 * @param value
	 *
	 */
	public String getDictName(HttpServletRequest request, String dictGroup, String value) {
		String returnV = null;
		if (StringUtils.isBlank(value) || StringUtils.isBlank(dictGroup)) {
			returnV = "";
		} else {
			if (value.indexOf(",") == -1) {
				Object name = request.getAttribute("datadict_" + dictGroup + "|" + value);

				if (name == null) {
					DataDictionary o = dataDictionaryService.getByValue(dictGroup, value);
					if (o != null) {				
						if (StringUtils.isBlank(o.getColor())) {
							returnV = o.getText();
						} else {
							returnV = "<span style='color:" + o.getColor() + "'>" + o.getText() + "</span>";
						}
						request.setAttribute("datadict_" + dictGroup + "|" + value, returnV);
					}
				} else {
					returnV = name.toString();
				}
			} else {
				String[] values = value.split(",");
				StringBuilder sb = new StringBuilder();
				for (String v : values) {
					Object name = request.getAttribute("datadict_" + dictGroup + "|" + v);

					if (name == null) {
						DataDictionary o = dataDictionaryService.getByValue(dictGroup, v);
						if (o != null) {
							sb.append("," + o.getText());
							request.setAttribute("datadict_" + dictGroup + "|" + v, o.getText());
						}
					} else {
						sb.append("," + name);
					}
				}
				if (sb.length() > 0) {
					sb.delete(0, 1);
				}
				returnV = sb.toString();
			}
		}

		return returnV;
	}

}
