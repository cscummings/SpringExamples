package com.cscummings.batch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.text.ParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cscummings.batch.common.Constants;
import com.cscummings.batch.common.NomadsProperties;

import com.cscummings.batch.TestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=TestConfiguration.class)
@TestPropertySource(locations="classpath:/resources/application-dev.properties")
//@TestPropertySource(properties="nomads.schema=W026DTF2")
public class TestNomadsProps {

	@Autowired
	private NomadsProperties nomadsProps;

	@Before
	public void setUp() throws Exception {
		//all the fancy spring notifications yet I can't build and pass a valid ctx! possibly a package name issue
		nomadsProps = new NomadsProperties();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetSchema() {
		String schemaName = nomadsProps.getSchema();
		assertNotNull(schemaName);
	}
/*
	@Test
	public void testSetSchema() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetStartdate() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSetStartdate() {
		fail("Not yet implemented"); // TODO
	}
*/
	@Test
	public void testGetEnddate() {
		fail("Not yet implemented"); // TODO
		assertNull(nomadsProps.getEnddate());
		if (nomadsProps.getEnddate() == null ) {
			// the user did not override
			// issue "Select SYSTEM_DATE from nomads.getSchema().TWNSYSDATE" into nomads Props RowMapper
			// use nomadsProps.setEnddate();
		}
			
	}

	@Test
	public void testSetEnddate() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public void testMinRecertApplicationDate() {
		String minValue = null;
		try {
			minValue = nomadsProps.minRecertApplicationDate();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(new String("20170401"), minValue);		
	}
	
	@Test
	public void testMaxRecertApplicationDate() {
		String maxValue = null;
		try {
			maxValue = nomadsProps.maxRecertApplicationDate();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// use with Java 1.8 assertThat(maxValue, is(not(Constants.simpleDateString)));
	}

	@Test
	public void testPeriodBeginDate() {
		String beginDate = null;
		try {
			beginDate = nomadsProps.periodBeginDate();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// use with java 1.8 assertThat(beginDate, is(not(Constants.simpleDateString)));
	}
	
	@Test
	public void testNextReDeterminationDate() {
		String nextDate = null;
		try {
			nextDate = nomadsProps.nextReDeterminationDate();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(new String("20170631"), nextDate);		
	}

}
