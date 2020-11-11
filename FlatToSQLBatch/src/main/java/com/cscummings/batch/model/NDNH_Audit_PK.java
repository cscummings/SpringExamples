package com.cscummings.batch.model;

import java.io.Serializable;
import java.sql.Date;


public class NDNH_Audit_PK implements Serializable {

	protected String RECORD_TYPE;
	protected Date PROCESS_DATE;

	public NDNH_Audit_PK() {

	}

	public NDNH_Audit_PK(String RECORD_TYPE, Date PROCESS_DATE) {
		this.RECORD_TYPE = RECORD_TYPE;
		this.PROCESS_DATE = PROCESS_DATE;
	}

	public String getRECORD_TYPE() {
		return RECORD_TYPE;
	}

	public void setRECORD_TYPE(String rECORD_TYPE) {
		RECORD_TYPE = rECORD_TYPE;
	}

	public Date getPROCESS_DATE() {
		return PROCESS_DATE;
	}

	public void setPROCESS(Date pROCESS_DATE) {
		PROCESS_DATE = pROCESS_DATE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((PROCESS_DATE == null) ? 0 : PROCESS_DATE.hashCode());
		result = prime * result + ((RECORD_TYPE == null) ? 0 : RECORD_TYPE.hashCode());
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
		NDNH_Audit_PK other = (NDNH_Audit_PK) obj;
		if (PROCESS_DATE == null) {
			if (other.PROCESS_DATE != null)
				return false;
		} else if (!PROCESS_DATE.equals(other.PROCESS_DATE))
			return false;
		if (RECORD_TYPE == null) {
			if (other.RECORD_TYPE != null)
				return false;
		} else if (!RECORD_TYPE.equals(other.RECORD_TYPE))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NDNH_Audit_PK [RECORD_TYPE=" + RECORD_TYPE + ", PROCESS_DATE=" + PROCESS_DATE + "]";
	}


}
