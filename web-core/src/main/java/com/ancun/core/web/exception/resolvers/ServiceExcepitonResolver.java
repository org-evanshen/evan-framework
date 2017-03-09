package com.ancun.core.web.exception.resolvers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.ancun.core.exception.ServiceException;
import com.ancun.core.web.webapi.AncunApiResponse;

/**
 * 业务异常解决器
 * <p/>
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2012-12-20 下午2:11:10
 */

public class ServiceExcepitonResolver implements ExceptionResolverStrategy {
    private static final Log logger = LogFactory.getLog(ServiceExcepitonResolver.class);

    private static final String SERVICE_EXCEPTION = ServiceException.class.getCanonicalName();
    private static final String ILLEGAL_ARGUMENT_EXCEPTION = IllegalArgumentException.class.getCanonicalName();

    @Override
    public void resolver(ModelAndView mv, AncunApiResponse msg, HttpServletRequest request,
                         HttpServletResponse response, Exception ex) {
        logger.warn(ex.getMessage());
        ServiceException ex1 = null;
        if (ServiceException.class.isInstance(ex)) {
            ex1 = (ServiceException) ex;
        } else {
            String message = ex.getMessage();

            if (message != null) {
                int endIndex = message.indexOf("\n");
                int startIndex = 0;
                if (message.startsWith(SERVICE_EXCEPTION)) {
                    startIndex = SERVICE_EXCEPTION.length() + 1;
                } else {
                    startIndex = ILLEGAL_ARGUMENT_EXCEPTION.length() + 1;
                }

                message = message.substring(startIndex, endIndex);
            }

            ex1 = new ServiceException(message);
        }

        msg.setCodeAndMsg(ex1.getCode(), ex1.getMessage());
    }
}
