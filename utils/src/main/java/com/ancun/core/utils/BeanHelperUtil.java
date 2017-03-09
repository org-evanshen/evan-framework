package com.ancun.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanHelperUtil {

	 /**
     * 去掉bean中所有属性为字符串的前后空格
     * @param bean
     * @throws Exception
     */
    public static void beanAttributeValueTrim(Object bean) throws Exception {
        if(bean!=null){
            //获取所有的字段包括public,private,protected,private
            Field[] fields = bean.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                if (f.getType().getName().equals("java.lang.String")) {
                    String key = f.getName();//获取字段名
                    Object value = getFieldValue(bean, key);
                    if (null==value){
                        continue;
                    }
                    setFieldValue(bean, key, value.toString().trim());
                }
            }
        }
    }

    /**
     * 利用反射通过get方法获取bean中字段fieldName的值
     * @param bean
     * @param fieldName
     *
     * @throws Exception
     */
	private static Object getFieldValue(Object bean, String fieldName)
            throws Exception {
        StringBuffer result = new StringBuffer();
        String methodName = result.append("get")
                .append(fieldName.substring(0, 1).toUpperCase())
                .append(fieldName.substring(1)).toString();
        Method method = bean.getClass().getMethod(methodName, new Class[] {});
        method.setAccessible(true);
        Object rObject = method.invoke(bean, new Object[0]);
        return rObject;
    }

    /**
     * 利用发射调用bean.set方法将value设置到字段
     * @param bean
     * @param fieldName
     * @param value
     * @throws Exception
     */
	private static void setFieldValue(Object bean, String fieldName, Object value)
            throws Exception {
        StringBuffer result = new StringBuffer();
        String methodName = result.append("set")
                .append(fieldName.substring(0, 1).toUpperCase())
                .append(fieldName.substring(1)).toString();

        /**
         * 利用发射调用bean.set方法将value设置到字段
         */
        Method method=bean.getClass().getMethod(methodName,String.class);
        method.setAccessible(true);
        method.invoke(bean,value);
    }
    
	
}
