package com.cscummings.batch.tokenizer;

import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.batch.item.file.transform.RangeArrayPropertyEditor;

public class NDNH_AuditFixedLengthTokenizer extends FixedLengthTokenizer {

	public NDNH_AuditFixedLengthTokenizer() {
		RangeArrayPropertyEditor ranges = new RangeArrayPropertyEditor();
		setStrict(false);
		String rangesText = "4-6,7-15,16-16,17-26,27-36,37-56,57-76,151-151";
		String[] nameList = { "RECORD_TYPE", "SSN", "VERIF_REQ_CD", "FIRST_NAME", "MID_NM", "LAST_NM",
				"PASSBACK_DATA", "REJECT_CD" };
		setNames(nameList);
		ranges.setAsText(rangesText);
		setColumns((Range[]) ranges.getValue());
	}

}
