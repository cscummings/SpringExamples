package com.cscummings.batch.transform;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;

/**
 * This is a field extractor for a NDNHNomads  bean extending BeanWrapperFieldExtractor. 
 * It retrieves the names of the declared fields and sets them as the names for use
 * by the BeanWrapperFieldExtractor code. For some reason PassTrhoughBeanWrapper did not work.
 * 
 * @author Connie Cummings
 * @param <NDNHNomads>
 */
public class NDNHNomadsFieldExtractor<NDNHNomads> extends BeanWrapperFieldExtractor<NDNHNomads> {

	/**
	 * @param build field names from object to be extracted by the {@link #extract(Object)} method.
	 */
	public void buildNames(Class<?> inClass) {
		Field[] fields = inClass.getDeclaredFields();
  		ArrayList<String> names = new ArrayList<String>(fields.length);
  		
	   	for (Field aField: fields){
	   		 names.add(aField.getName());
	   	 }
	   	
	   	setNames(names.toArray(new String[fields.length]));

	}
	/**
	 * @param names field names to be extracted by the {@link #extract(Object)} method.
	 */
	@Override
	public void setNames(String[] names) {
	  super.setNames(names);
        
	}

}
