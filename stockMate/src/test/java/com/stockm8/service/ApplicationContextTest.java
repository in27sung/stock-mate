package com.stockm8.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class ApplicationContextTest {

	@Autowired
	private ApplicationContext context;
	

	@Test
	public void testApplicationContextLoads() {
		// ApplicationContext가 null이 아니면 정상 작동
		assertNotNull("ApplicationContext가 로드되지 않았습니다.", context);
	}
	

}
