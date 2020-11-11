package com.cscummings.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.cscummings.batch.model.NDNHNomads;

/*
 * Used after read from database, but before written to flat file
 * 
 */
public class NDNHNomadsItemProcessor implements ItemProcessor<NDNHNomads, NDNHNomads> {

		@Override
	    public NDNHNomads process (NDNHNomads ndnhdata) throws Exception {
			 // do nothing = possibly calculate date values but basically manipulation is not needed
			 // formatting can be done in writer and line aggregator	
			
	        return ndnhdata;
	    }
		
}
