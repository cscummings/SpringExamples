package com.cscummings.batch.model;

import java.io.Serializable;

public class NDNH_Data_PK implements Serializable {

	protected String RECORD_TYPE;
	protected String SSN;
	protected String EMPLOYER_NAME;

	public NDNH_Data_PK() {

	}

	public NDNH_Data_PK(String RECORD_TYPE, String SSN, String EMPLOYER_NAME) {
		this.RECORD_TYPE = RECORD_TYPE;
		this.SSN = SSN;
		this.EMPLOYER_NAME = EMPLOYER_NAME;
	}

	public String getRECORD_TYPE() {
		return RECORD_TYPE;
	}

	public void setRECORD_TYPE(String rECORD_TYPE) {
		RECORD_TYPE = rECORD_TYPE;
	}

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String sSN) {
		SSN = sSN;
	}

	public String getEMPLOYER_NAME() {
		return EMPLOYER_NAME;
	}

	public void setEMPLOYER_NAME(String eMPLOYER_NAME) {
		EMPLOYER_NAME = eMPLOYER_NAME;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((EMPLOYER_NAME == null) ? 0 : EMPLOYER_NAME.hashCode());
		result = prime * result + ((RECORD_TYPE == null) ? 0 : RECORD_TYPE.hashCode());
		result = prime * result + ((SSN == null) ? 0 : SSN.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NDNH_Data_PK other = (NDNH_Data_PK) obj;
		if (EMPLOYER_NAME == null) {
			if (other.EMPLOYER_NAME != null)
				return false;
		} else if (!EMPLOYER_NAME.equals(other.EMPLOYER_NAME))
			return false;
		if (RECORD_TYPE == null) {
			if (other.RECORD_TYPE != null)
				return false;
		} else if (!RECORD_TYPE.equals(other.RECORD_TYPE))
			return false;
		if (SSN == null) {
			if (other.SSN != null)
				return false;
		} else if (!SSN.equals(other.SSN))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NDNH_Data_PK [RECORD_TYPE=" + RECORD_TYPE + ", SSN=" + SSN + ", EMPLOYER_NAME=" + EMPLOYER_NAME + "]";
	}
	
	
}
