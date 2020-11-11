package com.cscummings.batch;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.core.io.ClassPathResource;

import com.cscummings.batch.model.NDNH_Data;
import com.cscummings.batch.tokenizer.NDNH_DataFixedLengthTokenizer;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FixedLengthTokenizerTest {
	
	NDNH_DataFixedLengthTokenizer ndflt;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProcess() throws Exception {
		ndflt = new NDNH_DataFixedLengthTokenizer(NDNH_Data.class);
		assertTrue(ndflt.hasNames());
	}

}
