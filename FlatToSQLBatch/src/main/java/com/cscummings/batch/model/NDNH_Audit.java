package com.cscummings.batch.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

/*
 * NDNH SNAP W-4 Output Detail received from NDNH
 */

@Entity
@Table(name = "TWNIVA_NDNH_AUDIT")
@IdClass(NDNH_Audit_PK.class)
public class NDNH_Audit implements Serializable {

	@Column(length = 3)
	@Id
	String RECORD_TYPE;

	@Column(length = 8)
	@Id
	Date PROCESS_DATE;

	@Transient
	@Column(length = 3)
	private String submitter_type = new String("SNP");

	@Column(length = 9)
	String SSN;

	@Column(length = 1)
	private String VERIF_REQ_CD;

	@Column(length = 10)
	private String FIRST_NAME;

	@Column(length = 10)
	private String MID_NM;

	@Column(length = 20)
	private String LAST_NAME;

	@Column(length = 20)
	private String PASSBACK_DATA;

	@Column(length = 1)
	private String REJECT_CD;

	@Column(length = 11)
	private BigInteger W4_RECORDS;

	@Column(length = 11)
	private BigInteger NOTIFY_RECORDS;

	@Column(length = 11)
	private BigInteger TOTAL_RECORDS;

	public String getRECORD_TYPE() {
		return RECORD_TYPE;
	}

	public void setRECORD_TYPE(String rECORD_TYPE) {
		RECORD_TYPE = rECORD_TYPE;
	}

	public Date getPROCESS_DATE() {
		return PROCESS_DATE;
	}

	public void setPROCESS_DATE(Date pROCESS_DATE) {
		PROCESS_DATE = pROCESS_DATE;
	}

	public String getSubmitter_type() {
		return submitter_type;
	}

	public void setSubmitter_type(String submitter_type) {
		this.submitter_type = submitter_type;
	}

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String sSN) {
		SSN = sSN;
	}

	public String getVERIF_REQ_CD() {
		return VERIF_REQ_CD;
	}

	public void setVERIF_REQ_CD(String vERIF_REQ_CD) {
		VERIF_REQ_CD = vERIF_REQ_CD;
	}

	public String getFIRST_NAME() {
		return FIRST_NAME;
	}

	public void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}

	public String getMID_NM() {
		return MID_NM;
	}

	public void setMID_NM(String mID_NM) {
		MID_NM = mID_NM;
	}

	public String getLAST_NAME() {
		return LAST_NAME;
	}

	public void setLAST_NAME(String lAST_NAME) {
		LAST_NAME = lAST_NAME;
	}

	public String getPASSBACK_DATA() {
		return PASSBACK_DATA;
	}

	public void setPASSBACK_DATA(String pASSBACK_DATA) {
		PASSBACK_DATA = pASSBACK_DATA;
	}

	public String getREJECT_CD() {
		return REJECT_CD;
	}

	public void setREJECT_CD(String rEJECT_CD) {
		REJECT_CD = rEJECT_CD;
	}

	public BigInteger getW4_RECORDS() {
		return W4_RECORDS;
	}

	public void setW4_RECORDS(BigInteger w4_RECORDS) {
		W4_RECORDS = w4_RECORDS;
	}

	public BigInteger getNOTIFY_RECORDS() {
		return NOTIFY_RECORDS;
	}

	public void setNOTIFY_RECORDS(BigInteger nOTIFY_RECORDS) {
		NOTIFY_RECORDS = nOTIFY_RECORDS;
	}

	public BigInteger getTOTAL_RECORDS() {
		return TOTAL_RECORDS;
	}

	public void setTOTAL_RECORDS(BigInteger tOTAL_RECORDS) {
		TOTAL_RECORDS = tOTAL_RECORDS;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((FIRST_NAME == null) ? 0 : FIRST_NAME.hashCode());
		result = prime * result + ((LAST_NAME == null) ? 0 : LAST_NAME.hashCode());
		result = prime * result + ((MID_NM == null) ? 0 : MID_NM.hashCode());
		result = prime * result + ((NOTIFY_RECORDS == null) ? 0 : NOTIFY_RECORDS.hashCode());
		result = prime * result + ((PASSBACK_DATA == null) ? 0 : PASSBACK_DATA.hashCode());
		result = prime * result + ((PROCESS_DATE == null) ? 0 : PROCESS_DATE.hashCode());
		result = prime * result + ((RECORD_TYPE == null) ? 0 : RECORD_TYPE.hashCode());
		result = prime * result + ((REJECT_CD == null) ? 0 : REJECT_CD.hashCode());
		result = prime * result + ((SSN == null) ? 0 : SSN.hashCode());
		result = prime * result + ((TOTAL_RECORDS == null) ? 0 : TOTAL_RECORDS.hashCode());
		result = prime * result + ((VERIF_REQ_CD == null) ? 0 : VERIF_REQ_CD.hashCode());
		result = prime * result + ((W4_RECORDS == null) ? 0 : W4_RECORDS.hashCode());
		result = prime * result + ((submitter_type == null) ? 0 : submitter_type.hashCode());
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
		NDNH_Audit other = (NDNH_Audit) obj;
		if (FIRST_NAME == null) {
			if (other.FIRST_NAME != null)
				return false;
		} else if (!FIRST_NAME.equals(other.FIRST_NAME))
			return false;
		if (LAST_NAME == null) {
			if (other.LAST_NAME != null)
				return false;
		} else if (!LAST_NAME.equals(other.LAST_NAME))
			return false;
		if (MID_NM == null) {
			if (other.MID_NM != null)
				return false;
		} else if (!MID_NM.equals(other.MID_NM))
			return false;
		if (NOTIFY_RECORDS == null) {
			if (other.NOTIFY_RECORDS != null)
				return false;
		} else if (!NOTIFY_RECORDS.equals(other.NOTIFY_RECORDS))
			return false;
		if (PASSBACK_DATA == null) {
			if (other.PASSBACK_DATA != null)
				return false;
		} else if (!PASSBACK_DATA.equals(other.PASSBACK_DATA))
			return false;
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
		if (REJECT_CD == null) {
			if (other.REJECT_CD != null)
				return false;
		} else if (!REJECT_CD.equals(other.REJECT_CD))
			return false;
		if (SSN == null) {
			if (other.SSN != null)
				return false;
		} else if (!SSN.equals(other.SSN))
			return false;
		if (TOTAL_RECORDS == null) {
			if (other.TOTAL_RECORDS != null)
				return false;
		} else if (!TOTAL_RECORDS.equals(other.TOTAL_RECORDS))
			return false;
		if (VERIF_REQ_CD == null) {
			if (other.VERIF_REQ_CD != null)
				return false;
		} else if (!VERIF_REQ_CD.equals(other.VERIF_REQ_CD))
			return false;
		if (W4_RECORDS == null) {
			if (other.W4_RECORDS != null)
				return false;
		} else if (!W4_RECORDS.equals(other.W4_RECORDS))
			return false;
		if (submitter_type == null) {
			if (other.submitter_type != null)
				return false;
		} else if (!submitter_type.equals(other.submitter_type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NDNH_Audit [RECORD_TYPE=" + RECORD_TYPE + ", PROCESS_DATE=" + PROCESS_DATE + ", submitter_type="
				+ submitter_type + ", SSN=" + SSN + ", VERIF_REQ_CD=" + VERIF_REQ_CD + ", FIRST_NAME=" + FIRST_NAME
				+ ", MID_NM=" + MID_NM + ", LAST_NAME=" + LAST_NAME + ", PASSBACK_DATA=" + PASSBACK_DATA
				+ ", REJECT_CD=" + REJECT_CD + ", W4_RECORDS=" + W4_RECORDS + ", NOTIFY_RECORDS=" + NOTIFY_RECORDS
				+ ", TOTAL_RECORDS=" + TOTAL_RECORDS + "]";
	}


}

