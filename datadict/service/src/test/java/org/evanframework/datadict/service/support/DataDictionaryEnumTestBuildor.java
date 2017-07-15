package org.evanframework.datadict.service.support;


import org.evanframework.datadict.service.dto.DataDictionaryEnum;
import org.evanframework.datadict.service.DataDictionaryEnumBuildor;
import org.evanframework.datadict.service.support.enums.*;
import org.evanframework.datadict.service.support.enums.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 构建 DEMO 中用到的枚举数据字典
 * <p>
 * <p>
 * create at 2014年7月17日 上午10:09:41
 *
 * @author <a href="mailto:277469513@qq.com">ShenWei</a>
 * @version %I%, %G%
 * @see
 */
public class DataDictionaryEnumTestBuildor implements DataDictionaryEnumBuildor {

    @Override
    public Map<String, DataDictionaryEnum[]> getEnums() {
        Map<String, DataDictionaryEnum[]> map = new HashMap<String, DataDictionaryEnum[]>(16);

        map.put("adminUserStatus", EnumAdminUserStatus.values());
        map.put("dataDictStatus", EnumDataDictStatus.values());
        map.put("publishStatus", EnumPublishStatus.values());
        map.put("regionStatus", EnumRegionStatus.values());
        map.put("regionType", EnumRegionType.values());
        map.put("sex", EnumSex.values());

        return map;
    }
}
