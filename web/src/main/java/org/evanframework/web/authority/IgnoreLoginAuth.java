/**
 * 
 */
package org.evanframework.web.authority;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略登录验证<br>
 * 在Controller方法上加上该注解，表示该方法不需要登录验证<br>
 * 在Controller类上加上该注解，表示该类所有方法不需要登录验证
 * <p>
 * create at 2014年7月30日 下午8:44:52
 * @author shen.wei
 * @version %I%, %G%
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface IgnoreLoginAuth {

}
