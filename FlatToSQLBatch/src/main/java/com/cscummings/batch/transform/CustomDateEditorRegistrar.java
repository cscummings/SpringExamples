package com.cscummings.batch.transform;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.propertyeditors.CustomDateEditor;

public class CustomDateEditorRegistrar implements PropertyEditorRegistrar {

	@Override
	public void registerCustomEditors(PropertyEditorRegistry registry) {
		registry.registerCustomEditor(Date.class, 
                new CustomDateEditor(new SimpleDateFormat("yyyyMMdd"), false));

	}

}
