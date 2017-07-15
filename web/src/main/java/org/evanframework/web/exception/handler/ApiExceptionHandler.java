package org.evanframework.web.exception.handler;

import org.evanframework.dto.ApiResponse;
import org.evanframework.dto.OperateCommonResultType;
import org.evanframework.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 接口异常统一处理
 *
 * @author shenwei
 */
public class ApiExceptionHandler
        //extends ResponseEntityExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);


    @ExceptionHandler(value = Exception.class)
    public Object handleExceptionExtent(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);

        ApiResponse res = ApiResponse.create();
        res.setCode(OperateCommonResultType.ERROR.getCode());
        res.setMsg("系统维护中，请稍候再试");

        return handleExceptionInternal(ex, res, null, request);
    }

    /**
     * 业务异常处理
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = ServiceException.class)
    public Object handleServiceException(ServiceException ex, WebRequest request) {
        log.warn(ex.getMessage());
        ApiResponse res = ApiResponse.create();
        res.setCode(ex.getCode());
        res.setMsg(ex.getMessage());

        return handleExceptionInternal(ex, res, null, request);
    }

    /**
     * 参数不正确
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public Object handleExceptionExtent(IllegalArgumentException ex, WebRequest request) {
        log.warn(ex.getMessage());
        ApiResponse res = ApiResponse.create();
        res.setCode(OperateCommonResultType.PARAMETER_INVALID.getCode());
        res.setMsg(ex.getMessage());
        return handleExceptionInternal(ex, res, null, request);
    }

    /**
     * 请求地址不正确
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    protected Object handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest request) {
        log.warn(ex.getMessage());
        ApiResponse res = ApiResponse.create();
        res.setCode(OperateCommonResultType.HTTP_URL_INVALID.getCode());
        res.setMsg("请求的地址[" + ex.getRequestURL() + "]不正确," + ex.getMessage());
        return handleExceptionInternal(ex, res, null, request);
    }

    /**
     * HTTP请求参数不正确
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected Object handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        log.warn(ex.getMessage());

        BindingResult bindingResult = ex.getBindingResult();

        StringBuilder sb = new StringBuilder(128);
        for (FieldError error : bindingResult.getFieldErrors()) {
            sb.append("," + error.getField() + error.getDefaultMessage());
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(0);
        }

        ApiResponse res = ApiResponse.create();
        res.setCode(OperateCommonResultType.PARAMETER_INVALID.getCode());
        res.setMsg(sb.toString());

        return handleExceptionInternal(ex, res, null, request);
    }

    /**
     * HTTP 请求方式不正确
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    protected Object handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn(ex.getMessage(), ex);
        ApiResponse res = ApiResponse.create();
        res.setCode(OperateCommonResultType.HTTP_METHOD_INVALID.getCode());
        res.setMsg("请求的 Http Method [" + ex.getMethod() + "] 不支持, 支持的methods[" + ex.getSupportedHttpMethods() + "]");

        return handleExceptionInternal(ex, res, null, request);
    }

    /**
     * Http请求格式不正确
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    protected Object handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
        if (!CollectionUtils.isEmpty(mediaTypes)) {
            headers.setAccept(mediaTypes);
        }

        log.warn(ex.getMessage(), ex);
        ApiResponse res = ApiResponse.create();
        res.setCode(OperateCommonResultType.HTTP_MEDIA_TYPE_INVALID.getCode());
        res.setMsg("请求的 media [" + ex.getContentType() + "] 不支持, 支持的 MEDIA_TYPE [" + ex.getSupportedMediaTypes() + "]");

        return handleExceptionInternal(ex, res, headers, request);
    }

    /**
     * Http请求参数绑定不正确
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = {BindException.class})
    protected Object handleBindException(BindException ex, WebRequest request) {
        List<FieldError> errors = ex.getFieldErrors();

        StringBuilder sb = new StringBuilder(128);
        for (FieldError error : errors) {
            sb.append("," + error.getField() + "->" + error.getDefaultMessage());
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(0);
        }

        ApiResponse res = ApiResponse.create();
        res.setCode(OperateCommonResultType.PARAMETER_INVALID.getCode());
        res.setMsg("参数不正确, " + sb);

        return handleExceptionInternal(ex, res, null, request);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public Object handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();

        StringBuilder sb = new StringBuilder(128);
        for (ConstraintViolation<?> error : errors) {
            sb.append("," + error.getPropertyPath() + error.getMessage());
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(0);
        }

        ApiResponse res = ApiResponse.create();
        res.setCode(OperateCommonResultType.PARAMETER_INVALID.getCode());
        res.setMsg("参数不正确, " + sb);

        return handleExceptionInternal(ex, res, null, request);
    }

    //    /**
//     * 参数不正确
//     * @param ex
//     * @param request
//     * @return
//     */
//    @ExceptionHandler(value = {ConstraintViolationException.class})
//    public ResponseEntity<?> handleExceptionExtent(ConstraintViolationException ex, WebRequest request) {
//        log.warn(ex.getMessage());
//        ApiResponse res = ApiResponse.create();
//        res.setCode(OperateCommonResultType.PARAMETER_INVALID.getCode());
//        res.setMsg(ex.getConstraintViolations().toString());
//        ResponseEntity<ApiResponse> responseEntity = new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
//        setExceptionToRequest(request, ex);
//        return responseEntity;
//    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public Object handleConstraintViolationException(MethodArgumentTypeMismatchException ex,
                                                     WebRequest request) {
        MethodParameter methodParameter = ex.getParameter();

        ApiResponse res= ApiResponse.create();
        res.setCode(OperateCommonResultType.PARAMETER_INVALID.getCode());
        res.setMsg("参数[" + methodParameter.getParameterName() + "]不正确," + ex.getMessage());
        return handleExceptionInternal(ex, res, null, request);
    }

    /**
     * 异常统一输出
     *
     * @param ex
     * @param body
     * @param headers
     * @param request
     * @return
     */
    protected Object handleExceptionInternal(Exception ex, Object body,
                                             HttpHeaders headers, WebRequest request) {
        setExceptionToRequest(request, ex);
        return new ResponseEntity<Object>(body, headers, HttpStatus.OK);
    }

    protected void setExceptionToRequest(WebRequest request, Exception ex) {
        request.setAttribute("exception", ex, WebRequest.SCOPE_REQUEST);
    }
}
