package com.cscummings.batch.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SNAPW4Record {
	List<SNAPW4OutputDetailLayout> field = new ArrayList<SNAPW4OutputDetailLayout>();

	public List<SNAPW4OutputDetailLayout> getField() { 
		return this.field; 		
	}
		
	public void setField(List<SNAPW4OutputDetailLayout> field) { 
		this.field = field; 
	}

}
