package com.cscummings.batch.tokenizer;

import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.batch.item.file.transform.RangeArrayPropertyEditor;

public class NDNH_TrailerFixedLengthTokenizer extends FixedLengthTokenizer {

	public NDNH_TrailerFixedLengthTokenizer() {
		RangeArrayPropertyEditor ranges = new RangeArrayPropertyEditor();
		setStrict(false);
		String rangesText = "4-6,7-17,40-50,51-61";
		String[] nameList = {"RECORD_TYPE", "W4_RECORDS", "NOTIFY_RECORDS", "TOTAL_RECORDS"};
		setNames(nameList);
		ranges.setAsText(rangesText);
		setColumns((Range[]) ranges.getValue());
    }
    
}
