package com.ancun.core.web.exception.resolvers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ancun.core.web.webapi.AncunApiResponse;
import com.ancun.core.web.webapi.AncunApiResponseCode;

/**
 * 上传异常解决器
 * <p>
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2012-12-20 下午2:10:09
 */
public class MultipartExceptionResolver implements ExceptionResolverStrategy {

	private final Logger logger = LoggerFactory.getLogger(MultipartExceptionResolver.class);

	private long maxUploadSize;

	private CommonsMultipartResolver multipartResolver;

	@Override
	public void resolver(ModelAndView mv, AncunApiResponse msg, HttpServletRequest request, HttpServletResponse response,
						 Exception ex) {

		logger.error(ex.getMessage(), ex);

		if (maxUploadSize == 0 && multipartResolver != null) {
			maxUploadSize = multipartResolver.getFileUpload().getSizeMax();
		}

		String msgTitle = "上传错误,最大允许上传" + maxUploadSize / 1000000 + "MB";	
		msg.setCodeAndMsg(AncunApiResponseCode._1000022.getCode(), msgTitle);
	}

	public void setMultipartResolver(CommonsMultipartResolver multipartResolver) {
		this.multipartResolver = multipartResolver;
	}

	public void setMaxUploadSize(int maxUploadSize) {
		this.maxUploadSize = maxUploadSize;
	}

	// /**
	// * @param maxUploadSize
	// * the maxUploadSize to set
	// */
	// public void setMaxUploadSize(int maxUploadSize) {
	// this.maxUploadSize = maxUploadSize;
	// }
}
