package com.cscummings.batch;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cscummings.batch.BatchConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={BatchConfiguration.class})
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private BatchConfiguration batchConfiguration;
	
	@Test
	public void contextLoads() {
		System.out.println("We're Here at ContextLoads");
	}

	@Test
	public void verifyConfigBean() {
		assertNotNull(batchConfiguration);
	}
	
}
