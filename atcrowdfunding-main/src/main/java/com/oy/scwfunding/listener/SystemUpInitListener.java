package com.oy.scwfunding.listener;

import com.oy.scwfunding.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *  监听application对象创建和销毁
 * @Author OY
 * @Date 2021/1/25
 */
public class SystemUpInitListener implements ServletContextListener {

    Logger log = LoggerFactory.getLogger(SystemUpInitListener.class);

    /**
     *  当application创建时进行初始化方法
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext application = servletContextEvent.getServletContext();
        String contextPath = application.getContextPath();
        log.debug("当前应用上下文路径:{}", contextPath);
        application.setAttribute(Const.PATH, contextPath);
    }


    /**
     * 当application 销毁执行销毁方法
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.debug("SystemUpInitListener");
    }
}
