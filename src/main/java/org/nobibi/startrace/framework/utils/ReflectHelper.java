package org.nobibi.startrace.framework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.nobibi.startrace.framework.plugins.log.Log;
import org.slf4j.Logger;

/**
 * 反射方法类，提供方法缓存，属性值获取等. 常用情景：<br>
 * 1.名称判断属性是否存在并获取 getFieldByFieldName <br>
 * 2.名称判断属性是否存在并获取其属性值 getValueByFieldName <br>
 * 3.属性类型判断属性是否存在并获取其值 getValueByFieldType <br>
 * <p>
 * Company: itic
 * </p>
 * 
 * @author: idong
 * @date: 2015年5月8日 上午9:55:40
 * @version: V1.0
 */
public class ReflectHelper {
    private static final Logger                        LOGGER           = Log.get(ReflectHelper.class);
    // 主要用来缓存Method对象，提升反射效率
    private static final Map<MethodDescriptor, Method> methodCachePool  = new HashMap<ReflectHelper.MethodDescriptor, Method>();
    private static final String                        REFLECTEXCEPTION = "Reflect Exception";
    private static final String                        GETMETHOD        = "GET METHOD";
    private static final String                        INVOKEMETHOD     = "Invoke Method";

    private ReflectHelper() {

    }

    /**
     * 获取单例.
     * 
     * @param className
     * @return
     */
    public static Object newInstance(String className) {
        try {
            return ReflectHelper.class.getClassLoader().loadClass(className).newInstance();
        } catch (InstantiationException ex) {
            LOGGER.info(REFLECTEXCEPTION, ex);
        } catch (IllegalAccessException ex) {
            LOGGER.info(REFLECTEXCEPTION, ex);
        } catch (ClassNotFoundException ex) {
            LOGGER.info(REFLECTEXCEPTION, ex);
        }
        return null;
    }

    /**
     * 获取类中的方法，优先缓存中获取，并缓存新方法.
     * 
     * @param clazz
     *            类
     * @param methodName
     *            方法名
     * @param paramTypes
     *            方法参数类型
     * @return Method 方法
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Method getMethod(Class clazz, String methodName, Class[] paramTypes) {
        MethodDescriptor md = new MethodDescriptor(clazz, methodName, paramTypes);
        Method method = getCachedMethod(md);

        if (method != null) {
            return method;
        }
        try {
            method = clazz.getMethod(methodName, paramTypes);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            cacheMethod(md, method);
            return method;
        } catch (ReflectiveOperationException ex) {
            LOGGER.info(GETMETHOD, ex);
        }
        return null;
    }

    /**
     * 获取方法并调用.
     * 
     * @param obj
     *            类
     * @param methodName
     *            方法名
     * @param parameters
     *            参数
     * @param parameterTypes
     *            方法参数类型
     * @return Object 执行结果
     */
    @SuppressWarnings("rawtypes")
    public static Object invokeMethod(Object obj, String methodName, Object[] parameters, Class[] parameterTypes) {
        Method invokeMethod = getMethod(obj.getClass(), methodName, parameterTypes);
        try {
            if (invokeMethod != null) {
                return invokeMethod.invoke(obj, parameters);
            }
        } catch (ReflectiveOperationException ex) {
            LOGGER.info(INVOKEMETHOD, ex);
        }
        return null;
    }

    /**
     * 获取方法并调用.
     * 
     * @param obj
     *            类
     * @param methodName
     *            方法名
     * @param parameters
     *            参数
     * @return Object 执行结果
     */
    @SuppressWarnings("rawtypes")
    public static Object invokeMethod(Object obj, String methodName, Object[] parameters) {
        Class[] parameterTypes = new Class[parameters.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameterTypes[i] = parameters[i].getClass();
        }
        Method invokeMethod = getMethod(obj.getClass(), methodName, parameterTypes);
        try {
            if (invokeMethod != null) {
                return invokeMethod.invoke(obj, parameters);
            }
        } catch (ReflectiveOperationException ex) {
            LOGGER.info(INVOKEMETHOD, ex);
        }
        return null;
    }

    /**
     * 带throw的方法调用.
     * 
     * @param obj
     *            类
     * @param methodName
     *            方法名
     * @param parameters
     *            参数
     * @return Object 执行结果
     */
    @SuppressWarnings("rawtypes")
    public static Object invokeMethodThrowExceptionOnError(Object obj, String methodName, Object[] parameters) {
        Class[] parameterTypes = new Class[parameters.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameterTypes[i] = parameters[i].getClass();
        }
        Method invokeMethod = getMethod(obj.getClass(), methodName, parameterTypes);
        try {
            if (invokeMethod != null) {
                return invokeMethod.invoke(obj, parameters);
            } else {
                throw new NullPointerException(StringHelper.buildString("在类{0}中不存在方法{1}", obj.getClass().getName(),
                        methodName));
            }
        } catch (ReflectiveOperationException ex) {
            LOGGER.info(REFLECTEXCEPTION, ex);
        }
        return null;
    }

    /**
     * 带throw的方法调用.
     * 
     * @param obj
     *            类
     * @param methodName
     *            方法名
     * @param parameters
     *            参数
     * @param parameterTypes
     *            参数类型
     * @return Object 执行结果
     */
    @SuppressWarnings("rawtypes")
    public static Object invokeMethodThrowExceptionOnError(Object obj, String methodName, Object[] parameters,
            Class[] parameterTypes) {
        Method invokeMethod = getMethod(obj.getClass(), methodName, parameterTypes);
        try {
            if (invokeMethod != null) {
                return invokeMethod.invoke(obj, parameters);
            } else {
                throw new NullPointerException(StringHelper.buildString("在类{0}中不存在方法{1}", obj.getClass().getName(),
                        methodName));
            }
        } catch (ReflectiveOperationException ex) {
            LOGGER.info(REFLECTEXCEPTION, ex);
        }
        return null;
    }

    /**
     * 获取缓存的方法.
     * 
     * @param md
     * @return Method
     */
    private static Method getCachedMethod(MethodDescriptor md) {
        Method method = methodCachePool.get(md);
        if (method != null) {
            return method;
        }
        return null;
    }

    /**
     * 缓存方法.
     * 
     * @param md
     * @param method
     */
    private static void cacheMethod(MethodDescriptor md, Method method) {
        if (method != null) {
            methodCachePool.put(md, method);
        }
    }

    /**
     * 清除缓存.
     */
    public static void clearMethodCache() {
        methodCachePool.clear();
    }

    /**
     * 内部描述类，描述方法 用于缓存方法.
     * 
     * @author: djp_ivan
     * @date: 2015年5月4日 下午4:53:51
     * @version: V1.0
     * @Update_Comments: by djp_ivan at 2015年5月4日 - description
     */
    private static class MethodDescriptor {
        @SuppressWarnings("rawtypes")
        private Class   clazz;
        private String  methodName;
        @SuppressWarnings("rawtypes")
        private Class[] parameterTypes;

        private int     hashCode;

        @SuppressWarnings("rawtypes")
        public MethodDescriptor(Class clazz, String methodName, Class[] parameterTypes) {
            this.clazz = clazz;
            this.methodName = methodName;
            this.parameterTypes = parameterTypes;
            this.hashCode = methodName.length();
        }

        @Override
        public int hashCode() {
            return this.hashCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof MethodDescriptor)) {
                return false;
            }

            MethodDescriptor md = (MethodDescriptor) obj;
            return methodName.equals(md.methodName) && clazz.equals(md.clazz)
                    && Arrays.equals(parameterTypes, md.parameterTypes);
        }
    }

    /**
     * 获取obj对象中的Field.
     * 
     * @param obj
     *            类
     * @param fieldName
     *            属性名称
     * @returnField 属性
     */
    public static Field getFieldByFieldName(Object obj, String fieldName) {
        if (null == obj || null == fieldName) {
            return null;
        }
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (ReflectiveOperationException e) {
                LOGGER.info(REFLECTEXCEPTION, e);
            }
        }
        return null;
    }

    /**
     * 获取obj对象中的指定名称的属性的值.
     * 
     * @param obj
     *            类
     * @param fieldName
     *            属性名称
     * @return Object 属性值
     */
    public static Object getValueByFieldName(Object obj, String fieldName) {
        Object value = null;
        Field field = getFieldByFieldName(obj, fieldName);
        if (null != field) {
            value = ReflectHelper.getValue(field, obj);
        }
        return value;
    }

    /**
     * 获取obj对象中的指定返回类型的属性的值.
     * 
     * @param obj
     *            类
     * @param fieldType
     *            属性类型
     * @return Object 属性值
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValueByFieldType(Object obj, Class<T> fieldType) {
        Object value = null;
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            Field[] fields = superClass.getDeclaredFields();
            for (Field f : fields) {
                if (f.getType() == fieldType) {
                    value = ReflectHelper.getValue(f, obj);
                }
            }
            if (null != value) {
                break;
            }
        }
        return (T) value;
    }

    /**
     * 设置obj对象fieldName的属性值.
     * 
     * @param obj
     *            类
     * @param fieldName
     *            属性名称
     * @param value
     *            值
     * @return boolean
     */
    public static boolean setValueByFieldName(Object obj, String fieldName, Object value) {
        try {
            Field field = getFieldByFieldName(obj, fieldName);
            ReflectHelper.setValue(field, obj, value);
            return true;
        } catch (ReflectiveOperationException e) {
            LOGGER.info(REFLECTEXCEPTION, e);
        }
        return false;
    }

    /**
     * 获取属性的值
     * 
     * @param f
     * @param obj
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private static Object getValue(Field f, Object obj) {
        Object value = null;
        try {
            if (f.isAccessible()) {
                value = f.get(obj);
            } else {
                f.setAccessible(true);
                value = f.get(obj);
                f.setAccessible(false);
            }
        } catch (ReflectiveOperationException e) {
            LOGGER.info(REFLECTEXCEPTION, e);
        }
        return value;
    }

    /**
     * 设置属性的值
     * 
     * @param field
     * @param obj
     * @param value
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private static void setValue(Field field, Object obj, Object value)
            throws IllegalAccessException {
        if (field.isAccessible()) {
            field.set(obj, value);
        } else {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }
    }
}
