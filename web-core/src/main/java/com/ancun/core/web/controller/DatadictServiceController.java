package com.ancun.core.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ancun.core.datadict.DataDictionaryService;
import com.ancun.core.model.DataDictionary;
import com.ancun.core.web.model.ext.ComboBox;
import com.ancun.core.web.webapi.AncunApiResponse;

/**
 * 数据字典公共Action
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a> created at 2010-11-28
 */
@Controller
public class DatadictServiceController {
	private static final Logger log = LoggerFactory.getLogger(DatadictServiceController.class);

	@Autowired(required = false)
	private DataDictionaryService dataDictionaryService;

	@RequestMapping("datadict")
	@ResponseBody
	public AncunApiResponse getDatadict(@RequestParam("group") String group) {

		if (dataDictionaryService == null) {
			log.warn("[" + DataDictionaryService.class + "] is not exists in spring content ");
			return null;
		}

		List<DataDictionary> dds = dataDictionaryService.getByGroup(group);

		return AncunApiResponse.create(dds);
	}

	/**
	 * 输出resources
	 */
	@RequestMapping("getDatadict")
	@ResponseBody
	public ComboBox getForCombobox(@RequestParam("group") String group) {

		if (dataDictionaryService == null) {
			log.warn("[" + DataDictionaryService.class + "] is not exists in spring content ");
			return null;
		}

		List<DataDictionary> dds = dataDictionaryService.getByGroup(group);

		ComboBox comboBox = new ComboBox();
		comboBox.add("", "[请选择]");

		if (dds != null) {
			for (DataDictionary dd : dds) {
				comboBox.add(dd.getValue(), dd.getText());
			}
		}
		return comboBox;
	}

	/**
	 * 根据dictGroup和parentValue 输出数据字典,输出select
	 * options字符串,前端:jQuery(select).append(options)
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2011-3-14 下午03:57:48 <br>
	 * 
	 * @param dictGroup
	 * @param parentValue
	 */
	@RequestMapping(value = { "/getDictionaryByGroupAndParentValue" })
	@ResponseBody
	public String getDictionaryByGroupAndParentValue(@RequestParam("dictGroup") String dictGroup,
			@RequestParam("parentValue") String parentValue) {
		List<DataDictionary> list = dataDictionaryService.getByGroupAndParentValue(dictGroup, parentValue);

		StringBuilder str = new StringBuilder();

		if (list != null) {
			for (DataDictionary d : list) {
				str.append("<option value=\"" + d.getValue() + "\">" + d.getText() + "</option>");
			}
		}

		return str.toString();
	}

}
