package com.ancun.core.sysparameter;

import java.util.List;

import com.ancun.core.exception.ServiceException;
import com.ancun.core.sysparameter.model.SysParameter;
import com.ancun.core.sysparameter.model.SysParameterQuery;

/**
 * 系统参数业务接口
 * <p/>
 * <p/>
 * create at 2016年3月20日 下午4:51:18
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 */
public interface SysParameterService {
    /**
     * 获取列表
     */
    List<SysParameter> queryForList(SysParameterQuery query);

    /**
     * 更新
     */
    void update(SysParameter o) throws ServiceException;

    /**
     * 根据key读取
     */
    SysParameter getByKey(String key);
}
