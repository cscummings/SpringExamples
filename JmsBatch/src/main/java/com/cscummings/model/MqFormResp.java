package com.cscummings.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the MQ_FORM_RESP database table.
 * 
 */
@Entity
@Table(name="MQ_FORM_RESP")
public class MqFormResp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="BATCH_ID")
	private int batchId;

	@Column(name="CORRELATION_ID")
	private String correlationId;

	private int ici;

	@Column(name="RESPONSE_RECEIVED_TS")
	private Timestamp responseReceivedTs;

	@Column(name="RESPONSE_STATUS")
	private String responseStatus = "0";

	@Lob
	@Column(name="RESPONSE_XML")
	private String responseXml;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ROWID")
	private int rowid;

	public MqFormResp() {
	}

	public int getBatchId() {
		return this.batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	public String getCorrelationId() {
		return this.correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public int getIci() {
		return this.ici;
	}

	public void setIci(int ici) {
		this.ici = ici;
	}

	public Timestamp getResponseReceivedTs() {
		return this.responseReceivedTs;
	}

	public void setResponseReceivedTs(Timestamp responseReceivedTs) {
		this.responseReceivedTs = responseReceivedTs;
	}

	public String getResponseStatus() {
		return this.responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getResponseXml() {
		return this.responseXml;
	}

	public void setResponseXml(String responseXml) {
		this.responseXml = responseXml;
	}

	public int getRowid() {
		return this.rowid;
	}

	public void setRowid(int rowid) {
		this.rowid = rowid;
	}

	@Override
	public String toString() {
		return "MqFormResp [batchId=" + batchId + ", correlationId=" + correlationId + ", ici=" + ici
				+ ", responseReceivedTs=" + responseReceivedTs + ", responseStatus=" + responseStatus + ", responseXml="
				+ responseXml + ", rowid=" + rowid + "]";
	}

}