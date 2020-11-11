package com.cscummings.batch.tokenizer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.batch.item.file.transform.RangeArrayPropertyEditor;
import org.springframework.core.annotation.AnnotationConfigurationException;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import com.cscummings.batch.common.Constants;
import com.cscummings.batch.common.FieldRange;
import com.cscummings.batch.model.SNAPW4OutputDetailLayout;
import com.cscummings.batch.model.SNAPW4Record;

public class NDNH_DataFixedLengthTokenizer extends FixedLengthTokenizer {

	/*
	 * This Custom FixedLengthTokenizer works in concert with the @FieldRange
	 * Annotation it introspects the class provided in the argument. Each field
	 * in the class must be annotated with @Column and at minimum specify
	 * length=; each field must also specify the @FieldRange that specifies the
	 * min and max attributes of the range.
	 * 
	 * The Tokenizer builds the names from the declaredFields, and setsColumns
	 * based on the Ranges that have been built.
	 * 
	 * @param Object
	 * 
	 * @author C. Cummings
	 * 
	 * @see com.cscummings.batch.common.FieldRange
	 * 
	 */

	public NDNH_DataFixedLengthTokenizer(Class<?> inClass) throws Exception {
		Assert.notNull(inClass);
		Field[] fields = inClass.getDeclaredFields();
		ArrayList<String> fieldArray = new ArrayList<String>(fields.length); // # of fields
		StringBuilder rangesText = new StringBuilder(fields.length * 10); // max lth of ranges altogether
		RangeArrayPropertyEditor ranges = new RangeArrayPropertyEditor();
		setStrict(false);

		for (Field field : fields) {
			int len = field.getAnnotation(Column.class).length();
			int start = field.getAnnotation(FieldRange.class).start();
			int end = field.getAnnotation(FieldRange.class).end();

			// verify that end-start is length - otherwise throw Annotation error
			if ((end - start) + 1 != len) {
				throw new AnnotationConfigurationException(inClass + "." + field.getName()
						+ " Annotation error. check length and FieldRange Attributes ");
			} else {
				rangesText.append(String.valueOf(start) + Constants.DASH + String.valueOf(end));
				rangesText.append(Constants.commaChar);
			}

			fieldArray.add(field.getName());
		}

		rangesText.deleteCharAt(rangesText.length() - 1);
		ranges.setAsText(rangesText.toString());

		setNames((String[]) fieldArray.toArray(new String[0]));

		setColumns((Range[]) ranges.getValue());

		return;

	}

	/*
	 * Constructor that read an xml file for range and length information - does
	 * not depend on custom annotation
	 */
	@SuppressWarnings("deprecation")
	public NDNH_DataFixedLengthTokenizer(Resource resource) throws JAXBException, IOException {
		Assert.notNull(resource);
		File file = resource.getFile();
		RangeArrayPropertyEditor ranges = new RangeArrayPropertyEditor();
		setStrict(false);

		JAXBContext jaxbContext = JAXBContext.newInstance(SNAPW4Record.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		SNAPW4Record rec = (SNAPW4Record) jaxbUnmarshaller.unmarshal(file);

		StringBuilder rangesText = new StringBuilder(rec.getField().size());
		ArrayList<String> fieldArray = new ArrayList<String>(rec.getField().size());

		for (SNAPW4OutputDetailLayout odl : rec.getField()) {
			String rangeString = odl.getLocation();
			rangesText.append(rangeString);
			rangesText.append(',');

			fieldArray.add((String) odl.getName());

		}
		rangesText.deleteCharAt(rangesText.length() - 1);
		ranges.setAsText(rangesText.toString());

		setNames((String[]) fieldArray.toArray(new String[0]));

		setColumns((Range[]) ranges.getValue());

	}

}