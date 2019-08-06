package org.evanframework.web.argumentresolver;


import org.evanframework.model.query.QueryParam;
import org.evanframework.web.annotation.QueryPage;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SpringMVC 分页参数解析器
 * <p>Spring Boot 中的应用
 * <p>1、定义Bean：
 * <pre>
 *  {@code @Bean}
 *  public QueryPageArgumentResolver getQueryPageArgumentResolver() {
 *      QueryPageArgumentResolver queryPageArgumentResolver = new QueryPageArgumentResolver();
 *      queryPageArgumentResolver.setDefaultPageSize(10);//默认分页
 *      return queryPageArgumentResolver;
 *  }
 * </pre>
 * 2、 Spring Boot MVC 配置中加载：
 * <pre>
 *  {@code @SpringBootApplication}
 *  public class DemoApplication extends WebMvcConfigurerAdapter {
 *      ......
 *      {@code @Override}
 *      public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
 *          queryPageArgumentResolver.setDefaultPageSize(15);//覆盖默认分页
 *          argumentResolvers.add(queryPageArgumentResolver);
 *      }
 *      ......
 *  }
 * </pre>
 * 3、 Contronller中采用@QueryPage标记该请求有分页参数：
 * <pre>
 *  {@code @RequestMapping("list")}
 *  public ApiResponse list(@QueryPage DemoQuery demoQuery) {
 *  }
 * <pre>
 * @author shen.wei
 * @since 1.0
 */
public class QueryPageArgumentResolver extends ServletModelAttributeMethodProcessor implements
        HandlerMethodArgumentResolver {
    //private String PAGE_NO_KEY = "pageNo";

    private static final Logger logger = LoggerFactory.getLogger(QueryPageArgumentResolver.class);

    private static final String PAGE_SIZE_KEY = "pageSize";
    private int defaultPageSize = 20;

    private final ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

    private final Map<Method, QueryPage> queryPageCache = new ConcurrentHashMap<Method, QueryPage>(128);

    public QueryPageArgumentResolver() {
        super(true);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean support = parameter.hasParameterAnnotation(QueryPage.class)//
                && ClassUtils.isAssignable(parameter.getParameterType(), QueryParam.class);

        return support;
    }

    protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
        super.validateIfApplicable(binder, parameter);

        Integer tag = threadLocal.get();

        if (tag != null && tag.equals(1)) {
            QueryParam query = (QueryParam) binder.getTarget();
//			try {
//				//如果是string类型 去掉前后空格
//				BeanHelperUtil.beanAttributeValueTrim(query);
//			} catch (Exception e) {
//				logger.error("string 类型的属性去空格失败", e);
//			}
            if (query.getPageSize() == 0) {
                QueryPage queryPage = getQueryPage(parameter);
                if (queryPage.defaultPageSize() != 0) {
                    query.setPageSize(queryPage.defaultPageSize());
                } else {
                    query.setPageSize(defaultPageSize);
                }
            }
            threadLocal.remove();
        }

        //		QueryPage queryPage = parameter.getParameterAnnotation(QueryPage.class);
        //		if (queryPage != null && queryPage.defaultPageSize() != 0) {
        //			threadLocal.set(queryPage.defaultPageSize());
        //		}
    }

    private QueryPage getQueryPage(MethodParameter parameter) {
        Method method = parameter.getMethod();
        QueryPage queryPage = queryPageCache.get(method);

        if (queryPage == null) {
            queryPage = parameter.getParameterAnnotation(QueryPage.class);
            queryPageCache.put(method, queryPage);
        }

        return queryPage;
    }

    @Override
    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
        super.bindRequestParameters(binder, request);
        String pageSize = request.getParameter(PAGE_SIZE_KEY);
        if (StringUtils.isBlank(pageSize)) {//如果没有分页参数，则初始化分页参数
            threadLocal.set(1);
        }
    }

    //	@Override
    //	protected void createAttribute(name, parameter, binderFactory, request) {
    //
    //	}

    public void setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }
}
