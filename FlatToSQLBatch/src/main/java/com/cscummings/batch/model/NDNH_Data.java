package com.cscummings.batch.model;

import java.io.Serializable;
//import java.util.Date;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cscummings.batch.common.Constants;
import com.cscummings.batch.common.FieldRange;

/*
 * NDNH SNAP W-4 Output Detail received from NDNH
 */

@Entity
@Table(name = "TWNIVA_NDNH_DATA")
@IdClass(NDNH_Data_PK.class)
public class NDNH_Data implements Serializable {

	@Column(length = 3)
	@Id
	@FieldRange(start=4, end=6)
	String RECORD_TYPE;

	@Column(length = 9)
	@Id
	@FieldRange(start=7, end=15)
	String SSN;

	@Column(length = 45)
	@Id
	@FieldRange(start=372, end=416)
	String EMPLOYER_NAME;

	@Transient
	@Column(length = 3)
	@FieldRange(start=1, end=3)
	private String submitter_type = Constants.subId;

	@Column(length = 1)
	@FieldRange(start=16, end=16)
	private String VERIF_REQ_CD;

	@Transient
	@Column(length = 5)
	@FieldRange(start=17, end=21)
	private String filler1 = Constants.blanks5;

	@Column(length = 10)
	@FieldRange(start=22, end=31)
	private String FIRST_NAME;

	@Column(length = 10)
	@FieldRange(start=32, end=41)
	private String MID_NM;

	@Column(length = 20)
	@FieldRange(start=42, end=61)
	private String LAST_NAME;

	@Transient
	@Column(length = 10)
	@FieldRange(start=62, end=71)
	private String filler2 = new String("          ");

	@Column(length = 8)
	@FieldRange(start=72, end=79)
	private Date W4_PROCESS_DATE;

	@Column(length = 16)
	@FieldRange(start=80, end=95)
	private String W4_FIRST_NM;

	@Column(length = 16)
	@FieldRange(start=96, end=111)
	private String W4_MID_NM;

	@Column(length = 30)
	@FieldRange(start=112, end=141)
	private String W4_LAST_NM;

	@Column(length = 40)
	@FieldRange(start=142, end=181)
	private String W4_EMPL_ADR1;

	@Column(length = 40)
	@FieldRange(start=182, end=221)
	private String W4_EMPL_ADR2;

	@Column(length = 40)
	@FieldRange(start=222, end=261)
	private String W4_EMPL_ADR3;

	@Column(length = 25)
	@FieldRange(start=262, end=286)
	private String W4_EMPL_CITY;

	@Column(length = 2)
	@FieldRange(start=287, end=288)
	private String W4_EMPL_ST;

	@Column(length = 9)
	@FieldRange(start=289, end=297)
	private String W4_EMP_ZIP;

	@Column(length = 2)
	@FieldRange(start=298, end=299)
	private String W4_EMPL_CNTRY_CD;

	@Column(length = 25)
	@FieldRange(start=300, end=324)
	private String W4_EMPL_CNTRY;

	@Column(length = 15)
	@FieldRange(start=325, end=339)
	private String W4_EMPL_CNTRY_ZIP;

	@Column(length = 8)
	@FieldRange(start=340, end=347)
	private Date START_DATE;

	@Column(length = 2)
	@FieldRange(start=348, end=349)
	private String STATE_OF_HIRE;

	@Column(length = 9)
	@FieldRange(start=350, end=358)
	private String EMPLOYER_FED_ID;

	@Column(length = 12)
	@FieldRange(start=359, end=370)
	private String EMPLOYER_ST_ID;

	@Column(length = 1)
	@FieldRange(start=371, end=371)
	private String DOD_STATUS = new String(" ");

	@Column(length = 40)
	@FieldRange(start=417, end=456)
	private String EMPLOYER_ADR1;

	@Column(length = 40)
	@FieldRange(start=457, end=496)	
	private String EMPLOYER_ADR2;

	@Column(length = 40)
	@FieldRange(start=497, end=536)
	private String EMPLOYER_ADR3;

	@Column(length = 25)
	@FieldRange(start=537, end=561)
	private String EMPLOYER_CITY;

	@Column(length = 2)
	@FieldRange(start=562, end=563)
	private String EMPLOYER_ST;

	@Column(length = 9)
	@FieldRange(start=564, end=572)
	private String EMPLOYER_ZIP;

	@Column(length = 2)
	@FieldRange(start=573, end=574)
	private String EMPLOYER_CNTRY_CD;

	@Column(length = 25)
	@FieldRange(start=575, end=599)
	private String EMPLOYER_CNTRY;

	@Column(length = 15)
	@FieldRange(start=600, end=614)
	private String EMPLOYER_CNTRY_ZIP;

	@Column(length = 40)
	@FieldRange(start=615, end=654)
	private String EMPLR_OPT_ADR1;

	@Column(length = 40)
	@FieldRange(start=655, end=694)
	private String EMPLR_OPT_ADR2;

	@Column(length = 40)
	@FieldRange(start=695, end=734)
	private String EMPLR_OPT_ADR3;

	@Column(length = 25)
	@FieldRange(start=735, end=759)
	private String EMPLR_OPT_CITY;

	@Column(length = 2)
	@FieldRange(start=760, end=761)
	private String EMPLR_OPT_ST;

	@Column(length = 9)
	@FieldRange(start=762, end=770)
	private String EMPLR_OPT_ZIP;

	@Column(length = 2)
	@FieldRange(start=771, end=772)
	private String EMPLR_OPT_CTRY_CD;

	@Column(length = 25)
	@FieldRange(start=773, end=797)
	private String EMPLR_OPT_CTRY;

	@Column(length = 15)
	@FieldRange(start=798, end=812)
	private String EMPLR_OPT_CTRY_ZIP;

	@Transient
	@Column(length = 2)
	@FieldRange(start=813, end=814)
	private String filler3 = new String("  ");

	@Column(length = 20)
	@FieldRange(start=815, end=834)
	private String PASSBACK_DATA;

	@Column(length = 1)
	@FieldRange(start=835, end=835)
	private String W4_MATCH;

	@Column(length = 1)
	@FieldRange(start=836, end=836)
	private String W4_SAME_STATE;

	@Column(length = 8)
	@FieldRange(start=837, end=844)
	private Date W4_FROM_DT;

	@Column(length = 8)
	@FieldRange(start=845, end=852)
	private Date W4_THROUGH_DT;

	@Transient
	@Column(length = 56)
	@FieldRange(start=853, end=908)
	private String filler4 = new String("     ");

	@Column(length = 9)
	@FieldRange(start=909, end=917)
	private String REPORTING_AGENCY;

	@Column(length = 2)
	@FieldRange(start=918, end=919)
	private String REPORT_STATE;

	@Column(length = 58)
	@FieldRange(start=920, end=977)
	private String REPORTING_NAME;

	@Transient
	@Column(length = 23)
	@FieldRange(start=978, end=1000)
	private String filler5 = new String("     ");

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

	public String getSubmitter_type() {
		return submitter_type;
	}

	public void setSubmitter_type(String submitter_type) {
		this.submitter_type = submitter_type;
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

	public Date getW4_PROCESS_DATE() {
		return W4_PROCESS_DATE;
	}

	public void setW4_PROCESS_DATE(Date w4_PROCESS_DATE) {
		W4_PROCESS_DATE = w4_PROCESS_DATE;
	}

	public String getW4_FIRST_NM() {
		return W4_FIRST_NM;
	}

	public void setW4_FIRST_NM(String w4_FIRST_NM) {
		W4_FIRST_NM = w4_FIRST_NM;
	}

	public String getW4_MID_NM() {
		return W4_MID_NM;
	}

	public void setW4_MID_NM(String w4_MID_NM) {
		W4_MID_NM = w4_MID_NM;
	}

	public String getW4_LAST_NM() {
		return W4_LAST_NM;
	}

	public void setW4_LAST_NM(String w4_LAST_NM) {
		W4_LAST_NM = w4_LAST_NM;
	}

	public String getW4_EMPL_ADR1() {
		return W4_EMPL_ADR1;
	}

	public void setW4_EMPL_ADR1(String w4_EMPL_ADR1) {
		W4_EMPL_ADR1 = w4_EMPL_ADR1;
	}

	public String getW4_EMPL_ADR2() {
		return W4_EMPL_ADR2;
	}

	public void setW4_EMPL_ADR2(String w4_EMPL_ADR2) {
		W4_EMPL_ADR2 = w4_EMPL_ADR2;
	}

	public String getW4_EMPL_ADR3() {
		return W4_EMPL_ADR3;
	}

	public void setW4_EMPL_ADR3(String w4_EMPL_ADR3) {
		W4_EMPL_ADR3 = w4_EMPL_ADR3;
	}

	public String getW4_EMPL_CITY() {
		return W4_EMPL_CITY;
	}

	public void setW4_EMPL_CITY(String w4_EMPL_CITY) {
		W4_EMPL_CITY = w4_EMPL_CITY;
	}

	public String getW4_EMPL_ST() {
		return W4_EMPL_ST;
	}

	public void setW4_EMPL_ST(String w4_EMPL_ST) {
		W4_EMPL_ST = w4_EMPL_ST;
	}

	public String getW4_EMP_ZIP() {
		return W4_EMP_ZIP;
	}

	public void setW4_EMP_ZIP(String w4_EMP_ZIP) {
		W4_EMP_ZIP = w4_EMP_ZIP;
	}

	public String getW4_EMPL_CNTRY() {
		return W4_EMPL_CNTRY;
	}

	public void setW4_EMPL_CNTRY(String w4_EMPL_CNTRY) {
		W4_EMPL_CNTRY = w4_EMPL_CNTRY;
	}

	public String getW4_EMPL_CNTRY_CD() {
		return W4_EMPL_CNTRY_CD;
	}

	public void setW4_EMPL_CNTRY_CD(String w4_EMPL_CNTRY_CD) {
		W4_EMPL_CNTRY_CD = w4_EMPL_CNTRY_CD;
	}

	public String getW4_EMPL_CNTRY_ZIP() {
		return W4_EMPL_CNTRY_ZIP;
	}

	public void setW4_EMPL_CNTRY_ZIP(String w4_EMPL_CNTRY_ZIP) {
		W4_EMPL_CNTRY_ZIP = w4_EMPL_CNTRY_ZIP;
	}

	public Date getSTART_DATE() {
		return START_DATE;
	}

	public void setSTART_DATE(Date sTART_DATE) {
		START_DATE = sTART_DATE;
	}

	public String getSTATE_OF_HIRE() {
		return STATE_OF_HIRE;
	}

	public void setSTATE_OF_HIRE(String sTATE_OF_HIRE) {
		STATE_OF_HIRE = sTATE_OF_HIRE;
	}

	public String getEMPLOYER_FED_ID() {
		return EMPLOYER_FED_ID;
	}

	public void setEMPLOYER_FED_ID(String eMPLOYER_FED_ID) {
		EMPLOYER_FED_ID = eMPLOYER_FED_ID;
	}

	public String getEMPLOYER_ST_ID() {
		return EMPLOYER_ST_ID;
	}

	public void setEMPLOYER_ST_ID(String eMPLOYER_ST_ID) {
		EMPLOYER_ST_ID = eMPLOYER_ST_ID;
	}

	public String getDOD_STATUS() {
		return DOD_STATUS;
	}

	public void setDOD_STATUS(String dOD_STATUS) {
		DOD_STATUS = dOD_STATUS;
	}

	public String getEMPLOYER_ADR1() {
		return EMPLOYER_ADR1;
	}

	public void setEMPLOYER_ADR1(String eMPLOYER_ADR1) {
		EMPLOYER_ADR1 = eMPLOYER_ADR1;
	}

	public String getEMPLOYER_ADR2() {
		return EMPLOYER_ADR2;
	}

	public void setEMPLOYER_ADR2(String eMPLOYER_ADR2) {
		EMPLOYER_ADR2 = eMPLOYER_ADR2;
	}

	public String getEMPLOYER_ADR3() {
		return EMPLOYER_ADR3;
	}

	public void setEMPLOYER_ADR3(String eMPLOYER_ADR3) {
		EMPLOYER_ADR3 = eMPLOYER_ADR3;
	}

	public String getEMPLOYER_CITY() {
		return EMPLOYER_CITY;
	}

	public void setEMPLOYER_CITY(String eMPLOYER_CITY) {
		EMPLOYER_CITY = eMPLOYER_CITY;
	}

	public String getEMPLOYER_ST() {
		return EMPLOYER_ST;
	}

	public void setEMPLOYER_ST(String eMPLOYER_ST) {
		EMPLOYER_ST = eMPLOYER_ST;
	}

	public String getEMPLOYER_ZIP() {
		return EMPLOYER_ZIP;
	}

	public void setEMPLOYER_ZIP(String eMPLOYER_ZIP) {
		EMPLOYER_ZIP = eMPLOYER_ZIP;
	}

	public String getEMPLOYER_CNTRY_CD() {
		return EMPLOYER_CNTRY_CD;
	}

	public void setEMPLOYER_CNTRY_CD(String eMPLOYER_CNTRY_CD) {
		EMPLOYER_CNTRY_CD = eMPLOYER_CNTRY_CD;
	}

	public String getEMPLOYER_CNTRY() {
		return EMPLOYER_CNTRY;
	}

	public void setEMPLOYER_CNTRY(String eMPLOYER_CNTRY) {
		EMPLOYER_CNTRY = eMPLOYER_CNTRY;
	}

	public String getEMPLOYER_CNTRY_ZIP() {
		return EMPLOYER_CNTRY_ZIP;
	}

	public void setEMPLOYER_CNTRY_ZIP(String eMPLOYER_CNTRY_ZIP) {
		EMPLOYER_CNTRY_ZIP = eMPLOYER_CNTRY_ZIP;
	}

	public String getEMPLR_OPT_ADR1() {
		return EMPLR_OPT_ADR1;
	}

	public void setEMPLR_OPT_ADR1(String eMPLR_OPT_ADR1) {
		EMPLR_OPT_ADR1 = eMPLR_OPT_ADR1;
	}

	public String getEMPLR_OPT_ADR2() {
		return EMPLR_OPT_ADR2;
	}

	public void setEMPLR_OPT_ADR2(String eMPLR_OPT_ADR2) {
		EMPLR_OPT_ADR2 = eMPLR_OPT_ADR2;
	}

	public String getEMPLR_OPT_ADR3() {
		return EMPLR_OPT_ADR3;
	}

	public void setEMPLR_OPT_ADR3(String eMPLR_OPT_ADR3) {
		EMPLR_OPT_ADR3 = eMPLR_OPT_ADR3;
	}

	public String getEMPLR_OPT_CITY() {
		return EMPLR_OPT_CITY;
	}

	public void setEMPLR_OPT_CITY(String eMPLR_OPT_CITY) {
		EMPLR_OPT_CITY = eMPLR_OPT_CITY;
	}

	public String getEMPLR_OPT_ST() {
		return EMPLR_OPT_ST;
	}

	public void setEMPLR_OPT_ST(String eMPLR_OPT_ST) {
		EMPLR_OPT_ST = eMPLR_OPT_ST;
	}

	public String getEMPLR_OPT_ZIP() {
		return EMPLR_OPT_ZIP;
	}

	public void setEMPLR_OPT_ZIP(String eMPLR_OPT_ZIP) {
		EMPLR_OPT_ZIP = eMPLR_OPT_ZIP;
	}

	public String getEMPLR_OPT_CTRY_CD() {
		return EMPLR_OPT_CTRY_CD;
	}

	public void setEMPLR_OPT_CTRY_CD(String eMPLR_OPT_CTRY_CD) {
		EMPLR_OPT_CTRY_CD = eMPLR_OPT_CTRY_CD;
	}

	public String getEMPLR_OPT_CTRY() {
		return EMPLR_OPT_CTRY;
	}

	public void setEMPLR_OPT_CTRY(String eMPLR_OPT_CTRY) {
		EMPLR_OPT_CTRY = eMPLR_OPT_CTRY;
	}

	public String getEMPLR_OPT_CTRY_ZIP() {
		return EMPLR_OPT_CTRY_ZIP;
	}

	public void setEMPLR_OPT_CTRY_ZIP(String eMPLR_OPT_CTRY_ZIP) {
		EMPLR_OPT_CTRY_ZIP = eMPLR_OPT_CTRY_ZIP;
	}

	public String getPASSBACK_DATA() {
		return PASSBACK_DATA;
	}

	public void setPASSBACK_DATA(String pASSBACK_DATA) {
		PASSBACK_DATA = pASSBACK_DATA;
	}

	public String getW4_MATCH() {
		return W4_MATCH;
	}

	public void setW4_MATCH(String w4_MATCH) {
		W4_MATCH = w4_MATCH;
	}

	public String getW4_SAME_STATE() {
		return W4_SAME_STATE;
	}

	public void setW4_SAME_STATE(String w4_SAME_STATE) {
		W4_SAME_STATE = w4_SAME_STATE;
	}

	public Date getW4_FROM_DT() {
		return W4_FROM_DT;
	}

	public void setW4_FROM_DT(Date w4_FROM_DT) {
		W4_FROM_DT = w4_FROM_DT;
	}

	public Date getW4_THROUGH_DT() {
		return W4_THROUGH_DT;
	}

	public void setW4_THROUGH_DT(Date w4_THROUGH_DT) {
		W4_THROUGH_DT = w4_THROUGH_DT;
	}

	public String getREPORTING_AGENCY() {
		return REPORTING_AGENCY;
	}

	public void setREPORTING_AGENCY(String rEPORTING_AGENCY) {
		REPORTING_AGENCY = rEPORTING_AGENCY;
	}

	public String getREPORT_STATE() {
		return REPORT_STATE;
	}

	public void setREPORT_STATE(String rEPORT_STATE) {
		REPORT_STATE = rEPORT_STATE;
	}

	public String getREPORTING_NAME() {
		return REPORTING_NAME;
	}

	public void setREPORTING_NAME(String rEPORTING_NAME) {
		REPORTING_NAME = rEPORTING_NAME;
	}
	public String getFiller1() {
		return filler1;
	}

	public void setFiller1(String filler1) {
		this.filler1 = filler1;
	}

	public String getFiller2() {
		return filler2;
	}

	public void setFiller2(String filler2) {
		this.filler2 = filler2;
	}

	public String getFiller3() {
		return filler3;
	}

	public void setFiller3(String filler3) {
		this.filler3 = filler3;
	}

	public String getFiller4() {
		return filler4;
	}

	public void setFiller4(String filler4) {
		this.filler4 = filler4;
	}

	public String getFiller5() {
		return filler5;
	}

	public void setFiller5(String filler5) {
		this.filler5 = filler5;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((EMPLOYER_NAME == null) ? 0 : EMPLOYER_NAME.hashCode());
		result = prime * result + ((SSN == null) ? 0 : SSN.hashCode());
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
		NDNH_Data other = (NDNH_Data) obj;
		if (EMPLOYER_NAME == null) {
			if (other.EMPLOYER_NAME != null)
				return false;
		} else if (!EMPLOYER_NAME.equals(other.EMPLOYER_NAME))
			return false;
		if (SSN == null) {
			if (other.SSN != null)
				return false;
		} else if (!SSN.equals(other.SSN))
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
		return "NDNH_Data [RECORD_TYPE=" + RECORD_TYPE + ", SSN=" + SSN + ", EMPLOYER_NAME=" + EMPLOYER_NAME
				+ ", submitter_type=" + submitter_type + ", VERIF_REQ_CD=" + VERIF_REQ_CD + ", filler1=" + filler1
				+ ", FIRST_NAME=" + FIRST_NAME + ", MID_NM=" + MID_NM + ", LAST_NAME=" + LAST_NAME + ", filler2="
				+ filler2 + ", W4_PROCESS_DATE=" + W4_PROCESS_DATE + ", W4_FIRST_NM=" + W4_FIRST_NM + ", W4_MID_NM="
				+ W4_MID_NM + ", W4_LAST_NM=" + W4_LAST_NM + ", W4_EMPL_ADR1=" + W4_EMPL_ADR1 + ", W4_EMPL_ADR2="
				+ W4_EMPL_ADR2 + ", W4_EMPL_ADR3=" + W4_EMPL_ADR3 + ", W4_EMPL_CITY=" + W4_EMPL_CITY + ", W4_EMPL_ST="
				+ W4_EMPL_ST + ", W4_EMP_ZIP=" + W4_EMP_ZIP + ", W4_EMPL_CNTRY=" + W4_EMPL_CNTRY
				+ ", W4_EMPL_CNTRY_CD=" + W4_EMPL_CNTRY_CD + ", W4_EMPL_CNTRY_ZIP=" + W4_EMPL_CNTRY_ZIP
				+ ", START_DATE=" + START_DATE + ", STATE_OF_HIRE=" + STATE_OF_HIRE + ", EMPLOYER_FED_ID="
				+ EMPLOYER_FED_ID + ", EMPLOYER_ST_ID=" + EMPLOYER_ST_ID + ", DOD_STATUS=" + DOD_STATUS
				+ ", EMPLOYER_ADR1=" + EMPLOYER_ADR1 + ", EMPLOYER_ADR2=" + EMPLOYER_ADR2 + ", EMPLOYER_ADR3="
				+ EMPLOYER_ADR3 + ", EMPLOYER_CITY=" + EMPLOYER_CITY + ", EMPLOYER_ST=" + EMPLOYER_ST
				+ ", EMPLOYER_ZIP=" + EMPLOYER_ZIP + ", EMPLOYER_CNTRY_CD=" + EMPLOYER_CNTRY_CD + ", EMPLOYER_CNTRY="
				+ EMPLOYER_CNTRY + ", EMPLOYER_CNTRY_ZIP=" + EMPLOYER_CNTRY_ZIP + ", EMPLR_OPT_ADR1=" + EMPLR_OPT_ADR1
				+ ", EMPLR_OPT_ADR2=" + EMPLR_OPT_ADR2 + ", EMPLR_OPT_ADR3=" + EMPLR_OPT_ADR3 + ", EMPLR_OPT_CITY="
				+ EMPLR_OPT_CITY + ", EMPLR_OPT_ST=" + EMPLR_OPT_ST + ", EMPLR_OPT_ZIP=" + EMPLR_OPT_ZIP
				+ ", EMPLR_OPT_CTRY_CD=" + EMPLR_OPT_CTRY_CD + ", EMPLR_OPT_CTRY=" + EMPLR_OPT_CTRY
				+ ", EMPLR_OPT_CTRY_ZIP=" + EMPLR_OPT_CTRY_ZIP + ", filler3=" + filler3 + ", W4_MATCH=" + W4_MATCH
				+ ", PASSBACK_DATA=" + PASSBACK_DATA + ", W4_SAME_STATE=" + W4_SAME_STATE + ", W4_FROM_DT=" + W4_FROM_DT
				+ ", W4_THROUGH_DT=" + W4_THROUGH_DT + ", filler4=" + filler4 + ", REPORTING_AGENCY=" + REPORTING_AGENCY
				+ ", REPORT_STATE=" + REPORT_STATE + ", REPORTING_NAME=" + REPORTING_NAME + ", filler5=" + filler5
				+ "]";
	}



}

