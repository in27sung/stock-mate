package com.stockm8.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stockm8.util.MyThreadManager;

@WebListener
public class ApplicationLifecycleListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationLifecycleListener.class);
	
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 애플리케이션 시작 시 초기화 작업 (필요할 경우)
        logger.info("애플리케이션이 초기화되었습니다.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 애플리케이션 종료 시 스레드 풀 또는 기타 리소스 정리
        MyThreadManager.shutdownAll(); // 스레드 풀 종료
        logger.info("애플리케이션이 종료되었습니다.");
    }
}
