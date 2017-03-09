package com.ancun.core.utils;

import java.io.Serializable;


public class ObjectUtil {

    public static final Object NULL = new Serializable() {
                                        private static final long serialVersionUID = 7092611880189329093L;

                                        private Object readResolve() {
                                            return NULL;
                                        }
                                    };

    /* ============================================================================ */
    /*  Ĭ��ֵ����                                                                */
    /*                                                                              */
    /*  ������Ϊnullʱ��������ת����ָ����Ĭ�϶���                                */
    /* ============================================================================ */

    /**
     * ������Ϊ<code>null</code>���򷵻�ָ��Ĭ�϶��󣬷��򷵻ض����?
     * <pre>
     * ObjectUtil.defaultIfNull(null, null)      = null
     * ObjectUtil.defaultIfNull(null, "")        = ""
     * ObjectUtil.defaultIfNull(null, "zz")      = "zz"
     * ObjectUtil.defaultIfNull("abc", *)        = "abc"
     * ObjectUtil.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     *
     * @param object Ҫ���ԵĶ���
     * @param defaultValue Ĭ��ֵ
     *
     *  �������Ĭ�϶���
     */
    public static Object defaultIfNull(Object object, Object defaultValue) {
        return (object != null) ? object : defaultValue;
    }

    /* ============================================================================ */
    /*  �ȽϺ���                                                                  */
    /*                                                                              */
    /*  ���·��������Ƚ����������Ƿ���ͬ��                                          */
    /* ============================================================================ */

    /**
     * �Ƚ����������Ƿ���ȫ��ȡ�
     * 
     * <p>
     * �˷���������ȷ�رȽ϶�ά���顣
     * <pre>
     * ObjectUtil.equals(null, null)                  = true
     * ObjectUtil.equals(null, "")                    = false
     * ObjectUtil.equals("", null)                    = false
     * ObjectUtil.equals("", "")                      = true
     * ObjectUtil.equals(Boolean.TRUE, null)          = false
     * ObjectUtil.equals(Boolean.TRUE, "true")        = false
     * ObjectUtil.equals(Boolean.TRUE, Boolean.TRUE)  = true
     * ObjectUtil.equals(Boolean.TRUE, Boolean.FALSE) = false
     * </pre>
     * </p>
     *
     * @param object1 ����1
     * @param object2 ����2
     *
     *  ������, �򷵻�<code>true</code>
     */
    public static boolean equals(Object object1, Object object2) {
        return ArrayUtil.equals(object1, object2);
    }

    /* ============================================================================ */
    /*  Hashcode����                                                              */
    /*                                                                              */
    /*  ���·�������ȡ�ö����hash code��                                           */
    /* ============================================================================ */

    /**
     * ȡ�ö����hashֵ, ������Ϊ<code>null</code>, �򷵻�<code>0</code>��
     * 
     * <p>
     * �˷���������ȷ�ش����ά���顣
     * </p>
     *
     * @param object ����
     *
     *  hashֵ
     */
    public static int hashCode(Object object) {
        return ArrayUtil.hashCode(object);
    }

    /**
     * ȡ�ö����ԭʼ��hashֵ, ������Ϊ<code>null</code>, �򷵻�<code>0</code>��
     * 
     * <p>
     * �÷���ʹ��<code>System.identityHashCode</code>��ȡ��hashֵ����ֵ���ܶ������<code>hashCode</code>������Ӱ�졣
     * </p>
     *
     * @param object ����
     *
     *  hashֵ
     */
    public static int identityHashCode(Object object) {
        return (object == null) ? 0 : System.identityHashCode(object);
    }

    /* ============================================================================ */
    /*  ȡ�ö����identity��                                                        */
    /* ============================================================================ */

    /**
     * ȡ�ö��������identity����ͬ����û�и���<code>toString()</code>����ʱ��<code>Object.toString()</code>��ԭʼ�����
     * <pre>
     * ObjectUtil.identityToString(null)          = null
     * ObjectUtil.identityToString("")            = "java.lang.String@1e23"
     * ObjectUtil.identityToString(Boolean.TRUE)  = "java.lang.Boolean@7fa"
     * ObjectUtil.identityToString(new int[0])    = "int[]@7fa"
     * ObjectUtil.identityToString(new Object[0]) = "java.lang.Object[]@7fa"
     * </pre>
     *
     * @param object ����
     *
     *  �����identity����������<code>null</code>���򷵻�<code>null</code>
     */
    public static String identityToString(Object object) {
        if (object == null) {
            return null;
        }

        return appendIdentityToString(null, object).toString();
    }

    /**
     * ȡ�ö��������identity����ͬ����û�и���<code>toString()</code>����ʱ��<code>Object.toString()</code>��ԭʼ�����
     * <pre>
     * ObjectUtil.identityToString(null, "NULL")            = "NULL"
     * ObjectUtil.identityToString("", "NULL")              = "java.lang.String@1e23"
     * ObjectUtil.identityToString(Boolean.TRUE, "NULL")    = "java.lang.Boolean@7fa"
     * ObjectUtil.identityToString(new int[0], "NULL")      = "int[]@7fa"
     * ObjectUtil.identityToString(new Object[0], "NULL")   = "java.lang.Object[]@7fa"
     * </pre>
     *
     * @param object ����
     * @param nullStr ������Ϊ<code>null</code>���򷵻ظ��ַ�
     *
     *  �����identity����������<code>null</code>���򷵻�ָ���ַ�
     */
    public static String identityToString(Object object, String nullStr) {
        if (object == null) {
            return nullStr;
        }

        return appendIdentityToString(null, object).toString();
    }

    /**
     * �����������identity������ͬ����û�и���<code>toString()</code>����ʱ��<code>Object.toString()</code>��ԭʼ�������׷�ӵ�<code>StringBuffer</code>�С�
     * <pre>
     * ObjectUtil.appendIdentityToString(*, null)            = null
     * ObjectUtil.appendIdentityToString(null, "")           = "java.lang.String@1e23"
     * ObjectUtil.appendIdentityToString(null, Boolean.TRUE) = "java.lang.Boolean@7fa"
     * ObjectUtil.appendIdentityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa")
     * ObjectUtil.appendIdentityToString(buf, new int[0])    = buf.append("int[]@7fa")
     * ObjectUtil.appendIdentityToString(buf, new Object[0]) = buf.append("java.lang.Object[]@7fa")
     * </pre>
     *
     * @param buffer <code>StringBuffer</code>���������<code>null</code>���򴴽��µ�
     * @param object ����
     *
     *  <code>StringBuffer</code>����������Ϊ<code>null</code>���򷵻�<code>null</code>
     */
    public static StringBuffer appendIdentityToString(StringBuffer buffer, Object object) {
        if (object == null) {
            return null;
        }

        if (buffer == null) {
            buffer = new StringBuffer();
        }

        buffer.append(ClassUtil.getClassNameForObject(object));

        return buffer.append('@').append(Integer.toHexString(identityHashCode(object)));
    }

    /* ============================================================================ */
    /*  �Ƚ϶�������͡�                                                            */
    /* ============================================================================ */

    /**
     * ������������Ƿ�������ͬ���͡�<code>null</code>���������������͡�
     *
     * @param object1 ����1
     * @param object2 ����2
     *
     *  ���������������ͬ�����ͣ��򷵻�<code>true</code>
     */
    public static boolean isSameType(Object object1, Object object2) {
        if ((object1 == null) || (object2 == null)) {
            return true;
        }

        return object1.getClass().equals(object2.getClass());
    }

    /* ============================================================================ */
    /*  toString������                                                              */
    /* ============================================================================ */

    /**
     * ȡ�ö����<code>toString()</code>��ֵ��������Ϊ<code>null</code>���򷵻ؿ��ַ�<code>""</code>��
     * <pre>
     * ObjectUtil.toString(null)         = ""
     * ObjectUtil.toString("")           = ""
     * ObjectUtil.toString("bat")        = "bat"
     * ObjectUtil.toString(Boolean.TRUE) = "true"
     * ObjectUtil.toString([1, 2, 3])    = "[1, 2, 3]"
     * </pre>
     *
     * @param object ����
     *
     *  �����<code>toString()</code>�ķ���ֵ������ַ�<code>""</code>
     */
    public static String toString(Object object) {
        return (object == null) ? ""
            : (object.getClass().isArray() ? ArrayUtil.toString(object) : object.toString());
    }

    /**
     * ȡ�ö����<code>toString()</code>��ֵ��������Ϊ<code>null</code>���򷵻�ָ���ַ�
     * <pre>
     * ObjectUtil.toString(null, null)           = null
     * ObjectUtil.toString(null, "null")         = "null"
     * ObjectUtil.toString("", "null")           = ""
     * ObjectUtil.toString("bat", "null")        = "bat"
     * ObjectUtil.toString(Boolean.TRUE, "null") = "true"
     * ObjectUtil.toString([1, 2, 3], "null")    = "[1, 2, 3]"
     * </pre>
     *
     * @param object ����
     * @param nullStr ������Ϊ<code>null</code>���򷵻ظ��ַ�
     *
     *  �����<code>toString()</code>�ķ���ֵ����ָ���ַ�
     */
    public static String toString(Object object, String nullStr) {
        return (object == null) ? nullStr : (object.getClass().isArray() ? ArrayUtil
            .toString(object) : object.toString());
    }
}
