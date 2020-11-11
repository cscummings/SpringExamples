package com.cscummings.batch.transform;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Formatter;
import java.util.Locale;

import javax.persistence.Column;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.FormatterLineAggregator;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import com.cscummings.batch.common.Constants;
import com.cscummings.batch.model.SNAPW4OutputDetailLayout;
import com.cscummings.batch.model.SNAPW4Record;

/**
 * A {@link LineAggregator} implementation which produces a String by
 * aggregating the provided item via the {@link Formatter} syntax. 
 * Customize implementation for types other than String (i.e. Date, Timestamp and Number formats) and turn them into string
 * format will treat Dates as string (%s)
 * Relies on FieldRange Annotation to build the format ranges
 *  <br>
 * @see com.cscummings.batch.common.FieldRange.java
 * @see Formatter
 * 
 * @author Connie Cummmings 
 */
public class CustomLineAggregator<NDNHNomads> extends FormatterLineAggregator<NDNHNomads> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public void buildFormat(Class<?> inClass) throws Exception {
		Assert.notNull(inClass);
		Field[] fields = inClass.getDeclaredFields();
		StringBuilder formatText = new StringBuilder(fields.length * 10); 
		formatText.append(Constants.fmt_hdr);
		
		for (Field field : fields) {
			int length = field.getAnnotation(Column.class).length();
			logger.debug(field.getName() + " " + length);
			formatText.append(length);
			formatText.append(Constants.fmt_string);
		}
		
		formatText.delete(formatText.length()-3, formatText.length());
		formatText.append(Constants.sChar);
		logger.debug("format string " + formatText);
		setFormat(formatText.toString());
	}
	
	/**
	 * Public setter for the Resource that contains the format information to produce the format string.
	 * @throws JAXBException 
	 * @throws IOException 
	 * @parm Resource  - the Resource that will contain the r
	 * @see Formatter
	 * @deprecated ALPHA 0.1.0
	 */
	public void setFormat(Resource resource) throws JAXBException, IOException {
		Assert.notNull(resource);
		StringBuilder formatText = null;
	  	File file = resource.getFile();
		JAXBContext jaxbContext = JAXBContext.newInstance(SNAPW4Record.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		SNAPW4Record rec = (SNAPW4Record) jaxbUnmarshaller.unmarshal(file);
		formatText = new StringBuilder(rec.getField().size());
		formatText.append(Constants.fmt_hdr);
		
		for (SNAPW4OutputDetailLayout odl: rec.getField()) {
			logger.debug(odl.getName() + " " + odl.getLength());
			int length = odl.getLength();
			formatText.append(length);
			formatText.append(Constants.fmt_string);
		}
		
		formatText.delete(formatText.length()-3, formatText.length());
		formatText.append(Constants.sChar);
		logger.debug("format string " + formatText);
		setFormat(formatText.toString());
	}


}

