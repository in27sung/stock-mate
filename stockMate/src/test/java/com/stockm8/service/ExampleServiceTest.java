package com.stockm8.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class ExampleServiceTest {

	@Autowired
	private ApplicationContext context;

	@After
	public void closeContext() {
		// 컨텍스트를 명시적으로 닫지 않음
		if (context instanceof ConfigurableApplicationContext) {
			((ConfigurableApplicationContext) context).refresh();
		}
	}

	@Test
	public void testExampleService() {
		// 테스트 코드
	}
}
