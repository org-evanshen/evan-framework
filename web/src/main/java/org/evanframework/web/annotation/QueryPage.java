package org.evanframework.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryPage {
	/**
	 * 默认每页记录数 可选 10,20,30,50,100,默认20
	 * 
	 *  <p>
	 *         author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
	 *         create at: 2014年4月16日上午3:19:03
	 */
	int defaultPageSize() default 0;
}
