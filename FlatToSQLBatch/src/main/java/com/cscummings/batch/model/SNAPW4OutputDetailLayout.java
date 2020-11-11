package com.cscummings.batch.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;

/*
 * This class used to define the Ranges of the incoming/outgoing Detail Records
 */

//@XmlRootElement
public class SNAPW4OutputDetailLayout implements Serializable {

	private static final long serialVersionUID = 1L;
	
	String name;
	String location;
	int length;
	
	public String getName() {
		return name;
	}

	public int getLength() {
		return length;
	}
	public String getLocation() {
		return location;
	}
	@XmlAttribute
	public void setName(String name){
		this.name = name;
	}
	@XmlAttribute
	public void setLength(int length){
		this.length = length;
	}
	@XmlAttribute
	public void setLocation(String location){
		this.location = location;
	}
	
	
}
