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

import com.cscummings.batch.model.NDNHNomads;
import com.cscummings.batch.tokenizer.NDNH_DataFixedLengthTokenizer;
import com.cscummings.batch.transform.CustomLineAggregator;
import com.cscummings.batch.transform.NDNHNomadsFieldExtractor;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomLineAggregatorTest {
	
	NDNH_DataFixedLengthTokenizer ndflt;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCustomLineAggregatorClass() throws JAXBException, IOException {
		//same old same old
		NDNHNomadsFieldExtractor<NDNHNomads> mfe = new NDNHNomadsFieldExtractor<NDNHNomads>();
		mfe.buildNames(NDNHNomads.class);
		CustomLineAggregator<NDNHNomads> cla = new CustomLineAggregator<NDNHNomads>();
		cla.setFieldExtractor(mfe);
		cla.setFormat(new ClassPathResource("/XML/snapInputDetail.xml"));
		cla.setMinimumLength(200);
		cla.setMaximumLength(200);
		assertNotNull(cla);
	}

	@Test
	public void testreflectProcess() throws Exception {
		NDNHNomadsFieldExtractor<NDNHNomads> mfe = new NDNHNomadsFieldExtractor<NDNHNomads>();
		mfe.buildNames(NDNHNomads.class);
		CustomLineAggregator<NDNHNomads> cla = new CustomLineAggregator<NDNHNomads>();
		cla.setFieldExtractor(mfe);
		cla.setMinimumLength(200);
		cla.setMaximumLength(200);
		cla.buildFormat(NDNHNomads.class);
		assertNotNull(cla);
	}

}
