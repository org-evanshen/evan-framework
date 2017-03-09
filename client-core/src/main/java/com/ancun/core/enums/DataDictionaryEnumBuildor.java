package com.ancun.core.enums;

import java.util.Map;

/**
 * 枚举作为数据字典的注册接口，各子系统、各模块实现该接口分别加载其枚举
 * 实现方法getEnums(),例如：
 * <pre>
*map.put("sex", EnumSex.values());//性别	
*map.put("isNotarized", EnumIsNotarized.values());	//公证状态
 * </pre>
 * map的key为数据字典的key，注册之后可以在java,velocity和js中直接读取	
 * <p>
 * create at 2014年4月18日 下午5:40:20 
 * @author  <a href="mailto:shenwei@ancun.com">ShenWei</a> 
 * @version %I%, %G%
 *
 */
public interface DataDictionaryEnumBuildor {
	Map<String, DataDictionaryEnum[]> getEnums();
}
