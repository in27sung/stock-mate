package com.stockm8.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyThreadManager {
	
	private static final Logger logger = LoggerFactory.getLogger(MyThreadManager.class);

    // 스레드 풀 관리 (예: 고정 크기 스레드 풀)
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    // 스레드 풀 반환
    public static ExecutorService getExecutorService() {
        return executorService;
    }

    // 스레드 풀 종료
    public static void shutdownAll() {
        try {
            logger.info("스레드 풀 종료 중...");
            executorService.shutdown(); // 더 이상 작업을 받지 않음
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
            	logger.info("스레드가 정상적으로 종료되지 않아 강제 종료합니다.");
                executorService.shutdownNow(); // 강제 종료
            }
        } catch (InterruptedException e) {
        	logger.info("스레드 종료 과정에서 인터럽트 발생");
            executorService.shutdownNow(); // 강제 종료
        }
    }
}
