package com.cscummings.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.cscummings.batch.model.NDNH_Data;

/*
 * Used after read from file, but before written to database. Linemapper has already made transformations
 * and created the NDNH_Data object.
 * 
 */
public class NDNHItemProcessor implements ItemProcessor<NDNH_Data, NDNH_Data> {

		@Override
	    public NDNH_Data process (NDNH_Data ndnhdata) throws Exception {
	        return ndnhdata;
	    }
		
}
