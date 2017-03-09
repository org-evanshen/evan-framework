package com.ancun.core.web.exception;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ancun.core.exception.DataNotFindException;
import com.ancun.core.exception.ServiceException;
import com.ancun.core.sysconfig.SysConfig;
import com.ancun.core.utils.BeanValidators;


public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

	@Autowired
	private SysConfig sysConfig;

	private Charset charset;

	@PostConstruct
	public void init() {
		charset = Charset.forName(sysConfig.getWebEncoding());
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	public final ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest request) {
		//String body = BeanValidators.extractPropertyAndMessage(ex.getConstraintViolations()).toString();
		Map<String, String> body = BeanValidators.extractPropertyAndMessage(ex.getConstraintViolations());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "plain", charset));
		//headers.setAcceptCharset(Arrays.asList());
		
		log.warn(ex.getMessage(), ex);
		
		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { ServiceException.class })
	public final ResponseEntity<?> handleServiceException(Exception ex, WebRequest request) {
		String body = ex.getMessage();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "plain", charset));
	
		log.warn(ex.getMessage(), ex);		

		return handleExceptionInternal(ex, body, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(value = { DataNotFindException.class })
	public final ResponseEntity<?> handleDataNotFindException(Exception ex, WebRequest request) {
		String body = ex.getMessage();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "plain", charset));
		
		log.warn(ex.getMessage(), ex);		

		return handleExceptionInternal(ex, body, headers, HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(value = Exception.class)
	public final ResponseEntity<?> handleExceptionExtent(Exception ex, WebRequest request) {
		String body = "系统出错";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "plain", charset));
		//headers.setContentType(MediaType.TEXT_PLAIN);
		
		log.error(ex.getMessage(), ex);		

		return handleExceptionInternal(ex, body, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		headers.setContentType(new MediaType("text", "plain", charset));
		BindingResult bindingResult = ex.getBindingResult();

		Map<String, String> map = new HashMap<String, String>();

		for (FieldError error : bindingResult.getFieldErrors()) {
			map.put(error.getField(), error.getDefaultMessage());
		}

		return handleExceptionInternal(ex, map, headers, status, request);
	}
	
	@PreDestroy
	public void destroy() {
		charset = null;
	}
}
