package com.ancun.core.web.exception.resolvers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异常解决器工厂<br>
 * 采用简单工厂模式，根据异常类型，生产具体的异常解决器
 * <p>
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2012-12-20 下午2:13:51
 */

public class ExceptionResolverStrategyFactory {
	private final static Logger log = LoggerFactory.getLogger(ExceptionResolverStrategyFactory.class);

	private ExceptionResolverStrategy defaultResolver;

	//	private ServiceExcepitonResolver serviceExcepitonResolver;
	//
	//	private SystemExceptionResolver systemExcepitonResolver;
	//
	//	private MultipartExceptionResolver multipartExceptionResolver;
	//
	//	private NoLoginExceptionResolver noLoginExceptionResolver;

	private Map<Class<Exception>, ExceptionResolverStrategy> resolversMap = new HashMap<Class<Exception>, ExceptionResolverStrategy>();

	public ExceptionResolverStrategy create(Exception ex) {
		ExceptionResolverStrategy exceptionResolver = null;

		for (Entry<Class<Exception>, ExceptionResolverStrategy> entry : resolversMap.entrySet()) {
			if (entry.getKey().isInstance(ex)) {
				exceptionResolver = entry.getValue();
				break;
			}

			if(ex.getMessage()!=null && ex.getMessage().contains(entry.getKey().getName())){
				exceptionResolver = entry.getValue();
				break;
			}
		}

		if (exceptionResolver == null) {
			exceptionResolver = defaultResolver;
		}

		//		if (MultipartException.class.isInstance(ex)) {
		//			exceptionResolver = multipartExceptionResolver;
		//		} else if (NoLoginException.class.isInstance(ex)) {
		//			exceptionResolver = noLoginExceptionResolver;
		//		} else if (ServiceException.class.isInstance(ex) //
		//				|| IllegalArgumentException.class.isInstance(ex)// 
		//				|| DataNotFindException.class.isInstance(ex)) {
		//			exceptionResolver = serviceExcepitonResolver;
		//		} else {
		//			exceptionResolver = systemExcepitonResolver;
		//		}

		log.info("Exception resolver is [" + exceptionResolver.getClass() + "]");

		return exceptionResolver;
	}

	public void setResolversMap(Map<Class<Exception>, ExceptionResolverStrategy> resolversMap) {
		this.resolversMap = resolversMap;
	}

	public void setDefaultResolver(ExceptionResolverStrategy defaultResolver) {
		this.defaultResolver = defaultResolver;
	}
}
