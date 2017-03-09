package com.ancun.core.web.exception;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ancun.core.exception.DataNotFindException;
import com.ancun.core.exception.ServiceException;
import com.ancun.core.sysconfig.SysConfig;
import com.ancun.core.web.utils.AncunApiUtils;
import com.ancun.core.web.webapi.AncunApiResponse;
import com.ancun.core.web.webapi.AncunApiResponseCode;


public class WebApiExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(WebApiExceptionHandler.class);

	@Autowired
	private SysConfig sysConfig;

	// @PostConstruct
	// public void init() {
	// charset = Charset.forName(sysConfig.getWebEncoding());
	// }

	@ExceptionHandler(value = { ServiceException.class, DataNotFindException.class })
	public final ResponseEntity<?> handleServiceException(ServiceException ex, WebRequest request) {
		log.warn("====================>>" + ex.getMessage() + "," + request);

		ResponseEntity<AncunApiResponse> responseEntity = AncunApiUtils.createResponseEntity(ex.getCode(),
				ex.getMessage());

		return responseEntity;
	}

	@ExceptionHandler(value = { IllegalArgumentException.class })
	public final ResponseEntity<?> handleServiceException(IllegalArgumentException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);

		ResponseEntity<AncunApiResponse> responseEntity = AncunApiUtils
				.createResponseEntity(AncunApiResponseCode._1000021);

		return responseEntity;
	}

	@ExceptionHandler(value = Exception.class)
	public final ResponseEntity<?> handleExceptionExtent(Exception ex, WebRequest request) {
		// String body = ex.getMessage();
		// HttpHeaders headers = new HttpHeaders();
		// headers.setContentType(new MediaType("text", "plain", charset));

		log.error(ex.getMessage(), ex);

		ResponseEntity<AncunApiResponse> responseEntity = AncunApiUtils
				.createResponseEntity(AncunApiResponseCode._1000002);

		return responseEntity;
	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		BindingResult bindingResult = ex.getBindingResult();

		StringBuilder sb = new StringBuilder(128);
		for (FieldError error : bindingResult.getFieldErrors()) {
			sb.append("," + error.getField() + error.getDefaultMessage());
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(0);
		}

		AncunApiResponse aar = AncunApiResponse.create();
		aar.setResponseCode(AncunApiResponseCode._1000081, sb);

		return handleExceptionInternal(ex, aar, headers, HttpStatus.OK, request);
	}

	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error(ex.getMessage(), ex);
		AncunApiResponse aar = AncunApiResponse.create(AncunApiResponseCode._1000008);

		return handleExceptionInternal(ex, aar, headers, HttpStatus.OK, request);
	}

	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		List<FieldError> errors = ex.getFieldErrors();

		StringBuilder sb = new StringBuilder(128);
		for (FieldError error : errors) {
			sb.append("," + error.getField() + error.getDefaultMessage());
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(0);
		}

		AncunApiResponse aar = AncunApiResponse.create();
		aar.setResponseCode(AncunApiResponseCode._1000081, sb);

		return handleExceptionInternal(ex, aar, headers, HttpStatus.OK, request);
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	public final ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest request) {
		Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();

		StringBuilder sb = new StringBuilder(128);
		for (ConstraintViolation<?> error : errors) {
			sb.append("," + error.getPropertyPath() + error.getMessage());
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(0);
		}

		ResponseEntity<AncunApiResponse> responseEntity = AncunApiUtils.createResponseEntity(
				AncunApiResponseCode._1000081.getCode(), sb.toString());

		return responseEntity;
	}

	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
		if (!CollectionUtils.isEmpty(mediaTypes)) {
			headers.setAccept(mediaTypes);
		}

		return handleExceptionInternal(ex, null, headers, status, request);
	}
}
