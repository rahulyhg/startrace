/**  
 * @Title: SpringContextHelper.java
 * @Package: com.itic.appbase.framework.utils
 * @Description: 容器访问类
 * @author: idong
 * @date: 2015年6月4日 下午4:27:35
 * @version: V1.0  
 */

package org.nobibi.startrace.common.utils;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Company: itic
 * </p>
 * Appliction Context 容器静态访问类.
 * 
 */
@Service
@Lazy(false)
public class SpringContextHelper
        implements ApplicationContextAware, DisposableBean {
    private static ApplicationContext applicationContext = null;

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        if (null == applicationContext) {
            return null;
        }
        return applicationContext;
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        if (null == applicationContext || null == applicationContext.getBean(name)) {
            return null;
        }
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        if (null == applicationContext || null == applicationContext.getBean(requiredType)) {
            return null;
        }
        return applicationContext.getBean(requiredType);
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public static void clearHolder() {
        applicationContext = null;
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {

        SpringContextHelper.applicationContext = applicationContext;
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     */
    @Override
    public void destroy()
            throws Exception {
        SpringContextHelper.clearHolder();
    }
}
