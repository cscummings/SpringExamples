package com.cscummings.batch.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.cscummings.batch.common.Constants;

/*
 * Non-Persisted Entity from NOMADS Query will be used to create input record to be sent to NDNH
 */
public class NDNHNomads implements Serializable {

	@Transient
	@Column(length = 3)
	private String submitter_type = Constants.subId;

	@Transient
	@Column(length = 3)
	private String record_type = Constants.inRecId;

	@Column(length = 9)
	@Id
	private String SSN;

	@Transient
	@Column(length = 1)
	private String verif_CD = Constants.N;

	@Column(length = 10)
	private String FRST_NM;

	@Column(length = 10)
	private String MID_NM;

	@Column(length = 20)
	private String LST_NM;

	@Column(length = 9)
	private String ICI;

	@Column(length = 2)
	private String spaces;

	@Column(length = 9)
	private String UPI;
	@Transient
	@Column(length = 1)
	private String match_IND = Constants.Y;

	@Transient
	@Column(length = 1)
	private String same_state = Constants.N;

	@Column(length = 8)
	private String from_date;

	@Transient
	@Column(length = 8) //default current date
	private String through_date = Constants.simpleDateString;

	@Transient
	@Column(length = 1)
	private String QW_match_CD = Constants.N;

	@Transient
	@Column(length = 1)
	private String QW_same_state = Constants.N;

	@Transient
	@Column(length = 5)
	private String QW_from_rpt_date = Constants.blanks5;

	@Transient
	@Column(length = 5)
	private String QW_through_rpt_date = Constants.blanks5;

	@Transient
	@Column(length = 8)
	private String QW_from_date = Constants.blanks8;

	@Transient
	@Column(length = 8)
	private String QW_through_date = Constants.blanks8;

	@Transient
	@Column(length = 1)
	private String UI_match_CD = Constants.N;

	@Transient
	@Column(length = 1)
	private String UI_same_state = Constants.N;

	@Transient
	@Column(length = 5)
	private String UI_from_rpt_date = Constants.blanks5;

	@Transient
	@Column(length = 5)
	private String UI_through_rpt_date = Constants.blanks5;

	@Transient
	@Column(length = 8)
	private String UI_from_date = Constants.blanks8;

	@Transient
	@Column(length = 8)
	private String UI_through_date = Constants.blanks8;

	@Transient
	@Column(length = 50)
	private String filler = Constants.blank;

	public NDNHNomads() {
		super();
	}

	public NDNHNomads(String sSN, String fRST_NM, String mID_NM, String lST_NM, String iCI, String uPI) {
		super();
		SSN = sSN;
		FRST_NM = fRST_NM;
		MID_NM = mID_NM;
		LST_NM = lST_NM;
		ICI = iCI;
		UPI = uPI;
	}

	public String getSubmitter_type() {
		return submitter_type;
	}

	public void setSubmitter_type(String submitter_type) {
		this.submitter_type = submitter_type;
	}

	public String getRecord_type() {
		return record_type;
	}

	public void setRecord_type(String record_type) {
		this.record_type = record_type;
	}

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String SSN) {
		this.SSN = SSN;
	}

	public String getVerif_CD() {
		return verif_CD;
	}

	public void setVerif_CD(String verif_CD) {
		this.verif_CD = verif_CD;
	}

	public String getFRST_NM() {
		return FRST_NM;
	}

	public void setFRST_NM(String FRST_NM) {
		this.FRST_NM = FRST_NM;
	}

	public String getMID_NM() {
		return MID_NM;
	}

	public void setMID_NM(String MID_NM) {
		this.MID_NM = MID_NM;
	}

	public String getLST_NM() {
		return LST_NM;
	}

	public void setLST_NM(String LST_NM) {
		this.LST_NM = LST_NM;
	}

	public String getICI() {
		return ICI;
	}

	public void setICI(String ICI) {
		this.ICI = ICI;
	}

	public String getSpaces() {
		return spaces;
	}

	public void setSpaces(String spaces) {
		this.spaces = spaces;
	}

	public String getUPI() {
		return UPI;
	}

	public void setUPI(String UPI) {
		this.UPI = UPI;
	}

	public String getMatch_IND() {
		return match_IND;
	}

	public void setMatch_IND(String match_IND) {
		this.match_IND = match_IND;
	}

	public String getSame_state() {
		return same_state;
	}

	public void setSame_state(String same_state) {
		this.same_state = same_state;
	}

	public String getFrom_date() {
		return from_date;
	}

	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}

	public String getThrough_date() {
		return through_date;
	}

	public void setThrough_date(String through_date) {
		this.through_date = through_date;
	}

	public String getQW_match_CD() {
		return QW_match_CD;
	}

	public void setQW_match_CD(String qW_match_CD) {
		QW_match_CD = qW_match_CD;
	}

	public String getQW_same_state() {
		return QW_same_state;
	}

	public void setQW_same_state(String qW_same_state) {
		QW_same_state = qW_same_state;
	}

	public String getQW_from_rpt_date() {
		return QW_from_rpt_date;
	}

	public void setQW_from_rpt_date(String qW_from_rpt_date) {
		QW_from_rpt_date = qW_from_rpt_date;
	}

	public String getQW_through_rpt_date() {
		return QW_through_rpt_date;
	}

	public void setQW_through_rpt_date(String qW_through_rpt_date) {
		QW_through_rpt_date = qW_through_rpt_date;
	}

	public String getQW_from_date() {
		return QW_from_date;
	}

	public void setQW_from_date(String qW_from_date) {
		QW_from_date = qW_from_date;
	}

	public String getQW_through_date() {
		return QW_through_date;
	}

	public void setQW_through_date(String qW_through_date) {
		QW_through_date = qW_through_date;
	}

	public String getUI_match_CD() {
		return UI_match_CD;
	}

	public void setUI_match_CD(String uI_match_CD) {
		UI_match_CD = uI_match_CD;
	}

	public String getUI_same_state() {
		return UI_same_state;
	}

	public void setUI_same_state(String uI_same_state) {
		UI_same_state = uI_same_state;
	}

	public String getUI_from_rpt_date() {
		return UI_from_rpt_date;
	}

	public void setUI_from_rpt_date(String uI_from_rpt_date) {
		UI_from_rpt_date = uI_from_rpt_date;
	}

	public String getUI_through_rpt_date() {
		return UI_through_rpt_date;
	}

	public void setUI_through_rpt_date(String uI_through_rpt_date) {
		UI_through_rpt_date = uI_through_rpt_date;
	}

	public String getUI_from_date() {
		return UI_from_date;
	}

	public void setUI_from_date(String uI_from_date) {
		UI_from_date = uI_from_date;
	}

	public String getUI_through_date() {
		return UI_through_date;
	}

	public void setUI_through_date(String uI_through_date) {
		UI_through_date = uI_through_date;
	}

	public String getFiller() {
		return filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((QW_from_date == null) ? 0 : QW_from_date.hashCode());
		result = prime * result + ((QW_from_rpt_date == null) ? 0 : QW_from_rpt_date.hashCode());
		result = prime * result + ((QW_match_CD == null) ? 0 : QW_match_CD.hashCode());
		result = prime * result + ((QW_same_state == null) ? 0 : QW_same_state.hashCode());
		result = prime * result + ((QW_through_date == null) ? 0 : QW_through_date.hashCode());
		result = prime * result + ((QW_through_rpt_date == null) ? 0 : QW_through_rpt_date.hashCode());
		result = prime * result + ((UI_from_date == null) ? 0 : UI_from_date.hashCode());
		result = prime * result + ((UI_from_rpt_date == null) ? 0 : UI_from_rpt_date.hashCode());
		result = prime * result + ((UI_match_CD == null) ? 0 : UI_match_CD.hashCode());
		result = prime * result + ((UI_same_state == null) ? 0 : UI_same_state.hashCode());
		result = prime * result + ((UI_through_date == null) ? 0 : UI_through_date.hashCode());
		result = prime * result + ((UI_through_rpt_date == null) ? 0 : UI_through_rpt_date.hashCode());
		result = prime * result + ((filler == null) ? 0 : filler.hashCode());
		result = prime * result + ((FRST_NM == null) ? 0 : FRST_NM.hashCode());
		result = prime * result + ((from_date == null) ? 0 : from_date.hashCode());
		result = prime * result + ((LST_NM == null) ? 0 : LST_NM.hashCode());
		result = prime * result + ((match_IND == null) ? 0 : match_IND.hashCode());
		result = prime * result + ((MID_NM == null) ? 0 : MID_NM.hashCode());
		result = prime * result + ((record_type == null) ? 0 : record_type.hashCode());
		result = prime * result + ((same_state == null) ? 0 : same_state.hashCode());
		result = prime * result + ((SSN == null) ? 0 : SSN.hashCode());
		result = prime * result + ((submitter_type == null) ? 0 : submitter_type.hashCode());
		result = prime * result + ((through_date == null) ? 0 : through_date.hashCode());
		result = prime * result + ((verif_CD == null) ? 0 : verif_CD.hashCode());
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
		NDNHNomads other = (NDNHNomads) obj;
		if (QW_from_date == null) {
			if (other.QW_from_date != null)
				return false;
		} else if (!QW_from_date.equals(other.QW_from_date))
			return false;
		if (QW_from_rpt_date == null) {
			if (other.QW_from_rpt_date != null)
				return false;
		} else if (!QW_from_rpt_date.equals(other.QW_from_rpt_date))
			return false;
		if (QW_match_CD == null) {
			if (other.QW_match_CD != null)
				return false;
		} else if (!QW_match_CD.equals(other.QW_match_CD))
			return false;
		if (QW_same_state == null) {
			if (other.QW_same_state != null)
				return false;
		} else if (!QW_same_state.equals(other.QW_same_state))
			return false;
		if (QW_through_date == null) {
			if (other.QW_through_date != null)
				return false;
		} else if (!QW_through_date.equals(other.QW_through_date))
			return false;
		if (QW_through_rpt_date == null) {
			if (other.QW_through_rpt_date != null)
				return false;
		} else if (!QW_through_rpt_date.equals(other.QW_through_rpt_date))
			return false;
		if (UI_from_date == null) {
			if (other.UI_from_date != null)
				return false;
		} else if (!UI_from_date.equals(other.UI_from_date))
			return false;
		if (UI_from_rpt_date == null) {
			if (other.UI_from_rpt_date != null)
				return false;
		} else if (!UI_from_rpt_date.equals(other.UI_from_rpt_date))
			return false;
		if (UI_match_CD == null) {
			if (other.UI_match_CD != null)
				return false;
		} else if (!UI_match_CD.equals(other.UI_match_CD))
			return false;
		if (UI_same_state == null) {
			if (other.UI_same_state != null)
				return false;
		} else if (!UI_same_state.equals(other.UI_same_state))
			return false;
		if (UI_through_date == null) {
			if (other.UI_through_date != null)
				return false;
		} else if (!UI_through_date.equals(other.UI_through_date))
			return false;
		if (UI_through_rpt_date == null) {
			if (other.UI_through_rpt_date != null)
				return false;
		} else if (!UI_through_rpt_date.equals(other.UI_through_rpt_date))
			return false;
		if (filler == null) {
			if (other.filler != null)
				return false;
		} else if (!filler.equals(other.filler))
			return false;
		if (FRST_NM == null) {
			if (other.FRST_NM != null)
				return false;
		} else if (!FRST_NM.equals(other.FRST_NM))
			return false;
		if (from_date == null) {
			if (other.from_date != null)
				return false;
		} else if (!from_date.equals(other.from_date))
			return false;
		if (LST_NM == null) {
			if (other.LST_NM != null)
				return false;
		} else if (!LST_NM.equals(other.LST_NM))
			return false;
		if (match_IND == null) {
			if (other.match_IND != null)
				return false;
		} else if (!match_IND.equals(other.match_IND))
			return false;
		if (MID_NM == null) {
			if (other.MID_NM != null)
				return false;
		} else if (!MID_NM.equals(other.MID_NM))
			return false;
		if (record_type == null) {
			if (other.record_type != null)
				return false;
		} else if (!record_type.equals(other.record_type))
			return false;
		if (same_state == null) {
			if (other.same_state != null)
				return false;
		} else if (!same_state.equals(other.same_state))
			return false;
		if (SSN == null) {
			if (other.SSN != null)
				return false;
		} else if (!SSN.equals(other.SSN))
			return false;
		if (submitter_type == null) {
			if (other.submitter_type != null)
				return false;
		} else if (!submitter_type.equals(other.submitter_type))
			return false;
		if (through_date == null) {
			if (other.through_date != null)
				return false;
		} else if (!through_date.equals(other.through_date))
			return false;
		if (verif_CD == null) {
			if (other.verif_CD != null)
				return false;
		} else if (!verif_CD.equals(other.verif_CD))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NDNHNomads [submitter_type=" + submitter_type + ", record_type=" + record_type + ", SSN=" + SSN
				+ ", verif_CD=" + verif_CD + ", FRST_NM=" + FRST_NM + ", MID_NM=" + MID_NM + ", LST_NM=" + LST_NM
				+ ", ICI=" + ICI + ", spaces=" + spaces + ", UPI=" + UPI + ", match_IND=" + match_IND + ", same_state="
				+ same_state + ", from_date=" + from_date + ", through_date=" + through_date + ", QW_match_CD="
				+ QW_match_CD + ", QW_same_state=" + QW_same_state + ", QW_from_rpt_date=" + QW_from_rpt_date
				+ ", QW_through_rpt_date=" + QW_through_rpt_date + ", QW_from_date=" + QW_from_date
				+ ", QW_through_date=" + QW_through_date + ", UI_match_CD=" + UI_match_CD + ", UI_same_state="
				+ UI_same_state + ", UI_from_rpt_date=" + UI_from_rpt_date + ", UI_through_rpt_date="
				+ UI_through_rpt_date + ", UI_from_date=" + UI_from_date + ", UI_through_date=" + UI_through_date
				+ ", filler=" + filler + "]";
	}

}
