package com.figure.core.util;

import com.figure.core.constant.StringConstants;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Spring工具类 方便在非Spring管理环境中获取Bean
 * </p>
 *
 * @author feather
 * @date 2020-11-15 21:19
 */
@Component
public final class SpringUtils implements BeanFactoryPostProcessor, ApplicationContextAware {

    /**
     * Spring可配置列表Bean工厂
     */
    private static ConfigurableListableBeanFactory beanFactory;
    /**
     * Spring应用程序上下文环境
     */
    private static ApplicationContext applicationContext;

    /**
     * 在标准初始化之后修改应用程序上下文的内部bean工厂。
     * 所有的bean定义都将被加载，但是没有bean被实例化。这允许覆盖或添加属性，甚至是对迫切初始化的bean。
     *
     * @param beanFactory 应用程序上下文使用的bean工厂
     * @throws org.springframework.beans.BeansException 万一出现错误
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        SpringUtils.beanFactory = beanFactory;
    }

    /**
     * 通过它Spring容器会自动把上下文环境对象调用ApplicationContextAware接口中的setApplicationContext方法。
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 返回指定bean的一个实例，该实例可以是共享的，也可以是独立的
     *
     * @param name 要检索的bean的名称
     * @return bean的实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) beanFactory.getBean(name);
    }

    /**
     * 返回唯一匹配给定对象类型的bean实例(如果有的话)
     *
     * @param clz 注册对象的类型
     * @return 匹配所需类型的单个bean的实例
     */
    public static <T> T getBean(Class<T> clz) {
        return beanFactory.getBean(clz);
    }

    /**
     * 确定具有给定名称的bean的类型。更具体地说，确定getBean将为给定名称返回的对象类型。
     *
     * @param name 注册对象的名称
     * @return Class 注册对象的类型
     */
    public static Class<?> getType(String name) {
        return beanFactory.getType(name);
    }

    /**
     * 这个bean工厂包含bean定义还是使用给定名称从外部注册的单例实例
     *
     * @param name 要查询对象的名称
     * @return 指定名称的bean是否存在
     */
    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
     * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param name 要查询的bean的名称
     * @return 这个bean是否对应于一个单例实例
     * @throws NoSuchBeanDefinitionException 如果没有给定名称的bean
     */
    public static boolean isSingleton(String name) {
        return beanFactory.isSingleton(name);
    }

    /**
     * 如果有别名，则返回给定bean的别名。在getBean调用中使用时，所有这些别名都指向同一个bean
     *
     * @param name 检查别名的bean名称
     * @return 别名，如果没有则为空数组
     */
    public static String[] getAliases(String name) {
        return beanFactory.getAliases(name);
    }

    /**
     * 尝试返回当前的AOP代理。只有通过AOP调用了调用方法，并且将AOP框架设置为公开代理时，这个方法才可用。否则，该方法将抛出一个IllegalStateException
     *
     * @return 当前的AOP代理(从不返回null)
     * @throws IllegalStateException 如果找不到代理，因为方法是在AOP调用上下文之外调用的，或者因为AOP框架还没有配置为公开代理
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy() {
        return (T) AopContext.currentProxy();
    }

    /**
     * 获取当前的环境配置，无配置返回null
     *
     * @return 当前的环境配置
     */
    public static String[] getActiveProfiles() {
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    /**
     * 获取当前的环境配置，当有多个环境配置时，只获取第一个
     *
     * @return 当前的环境配置
     */
    public static String getActiveProfile() {
        final String[] activeProfiles = getActiveProfiles();
        return StringUtils.isNotEmpty(activeProfiles) ? activeProfiles[0] : StringConstants.EMPTY_STRING;
    }

}
