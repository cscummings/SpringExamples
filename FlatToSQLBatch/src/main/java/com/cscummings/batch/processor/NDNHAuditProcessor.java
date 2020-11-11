package com.cscummings.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.cscummings.batch.model.NDNH_Audit;

/*
 * Used after read from file, but before written to database. Linemapper has already made transformations
 * and created the NDNH_Data object.
 * 
 */
public class NDNHAuditProcessor implements ItemProcessor<NDNH_Audit, NDNH_Audit> {

		@Override
	    public NDNH_Audit process (NDNH_Audit ndnhaudit) throws Exception {
	        return ndnhaudit;
	    }
		
}
