package com.cscummings.batch.transform;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.NotWritablePropertyException;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;

import com.cscummings.batch.model.NDNH_Audit;

public class NDNHAuditBeanWrapperMapper extends BeanWrapperFieldSetMapper {
	
	/**
	 * Map the {@link FieldSet} to an NDNH_AUDIT object retrieved from the enclosing Spring
	 * context, or to a new instance of the required type if no prototype is
	 * available and add PROCESS_DATE (ie current date)
	 * @throws BindException if there is a type conversion or other error (if
	 * the {@link DataBinder} from {@link #createBinder(Object)} has errors
	 * after binding).
	 * 
	 * @throws NotWritablePropertyException if the {@link FieldSet} contains a
	 * field that cannot be mapped to a bean property.
	 * @see org.springframework.batch.item.file.mapping.FieldSetMapper#mapFieldSet(FieldSet)
	 */
    @Override
	public NDNH_Audit mapFieldSet(FieldSet fs) throws BindException {
		NDNH_Audit copy = (NDNH_Audit) super.mapFieldSet(fs);
		copy.setPROCESS_DATE(new java.sql.Date(new java.util.Date().getTime()));
		return copy;
	}	

}
