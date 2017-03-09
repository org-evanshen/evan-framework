package com.ancun.core.sysparameter.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ancun.core.exception.DataNotFindException;
import com.ancun.core.exception.ServiceException;
import com.ancun.core.sysparameter.SysParameterService;
import com.ancun.core.sysparameter.cache.SystemParameterCache;
import com.ancun.core.sysparameter.model.SysParameter;
import com.ancun.core.sysparameter.model.SysParameterQuery;

/**
 * 系统参数业务类
 * <p/>
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2012-12-6 下午2:29:04
 */
public class SysParameterServiceImpl implements SysParameterService {
    private static final Logger logger = LoggerFactory.getLogger(SysParameterServiceImpl.class);

    @Autowired
    private SysParameterDao pubParameterDao;

    @Resource
    private SystemParameterCache systemParameterCache;

    /**
     * 获取列表
     */
    public List<SysParameter> queryForList(SysParameterQuery query) {
        return pubParameterDao.queryForList(query);
    }

    /**
     * 更新
     *
     * @throws ServiceException
     */
    @Transactional
    public void update(SysParameter o) throws ServiceException {
        SysParameter old = pubParameterDao.load(o.getParamKey());

        if (old == null) {
            throw new DataNotFindException();
        }

        if (StringUtils.equals(old.getParamValue(), o.getParamValue())) {
            throw new ServiceException("修改失败,新旧值相同!");
        }

        pubParameterDao.update(o);

        // sysLogService.writeLog(EnumSysLogOpertateObject.SYS_PARAMETER,
        // old.getParamKey(), old.getParamName(),
        // "值[" + old.getParamValue() + "]改为[" + o.getParamValue() + "]");

        systemParameterCache.remove(o.getParamKey());
    }

    public SysParameter getByKey(String key) {
        // 先从缓存中获取
        SysParameter model = systemParameterCache.get(key);
        if (model == null) {
            model = pubParameterDao.load(key);
            if (model != null) {
                systemParameterCache.put(key, model);
            }
        }
        return model;
    }
}
