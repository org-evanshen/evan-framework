package com.ancun.core.web.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ancun.core.model.PageResult;
import com.ancun.core.web.webapi.AncunApiResponse;
import com.ancun.core.web.webapi.AncunApiResponseCode;

@Deprecated
public class AncunApiUtils {
	public static ResponseEntity<AncunApiResponse> createResponseEntity(Serializable data) {
		return createResponseEntityInner(data);
	}

	public static ResponseEntity<AncunApiResponse> createResponseEntity(AncunApiResponseCode responseCode) {
		AncunApiResponse aap = AncunApiResponse.create();
		aap.setCode(responseCode.getCode());
		aap.setMsg(responseCode.getMsg());

		return new ResponseEntity<AncunApiResponse>(aap, HttpStatus.OK);
	}

	public static ResponseEntity<AncunApiResponse> createResponseEntity(int code, String msg) {
		AncunApiResponse aap = AncunApiResponse.create();
		aap.setCode(code);
		aap.setMsg(msg);

		return new ResponseEntity<AncunApiResponse>(aap, HttpStatus.OK);
	}

	public static ResponseEntity<AncunApiResponse> createResponseEntity(PageResult<?> pageResult) {
		AncunApiResponse aap = AncunApiResponse.create();
		aap.setPageResult(pageResult);

		return new ResponseEntity<AncunApiResponse>(aap, HttpStatus.OK);
	}

	public static ResponseEntity<AncunApiResponse> createResponseEntity(List<?> list) {
		AncunApiResponse aap = AncunApiResponse.create();
		aap.setList(list);

		return new ResponseEntity<AncunApiResponse>(aap, HttpStatus.OK);
	}

	public static ResponseEntity<AncunApiResponse> createResponseEntity(Map<String, ?> map) {
		return createResponseEntityInner(map);
	}

	private static ResponseEntity<AncunApiResponse> createResponseEntityInner(Object obj0) {
		AncunApiResponse aap = AncunApiResponse.create();

		if (obj0 instanceof List) {
			aap.setList((List) obj0);
		} else if (obj0 instanceof PageResult) {
			aap.setPageResult((PageResult) obj0);
		} else {
			aap.setData(obj0);
		}

		return new ResponseEntity<AncunApiResponse>(aap, HttpStatus.OK);
	}
}
