package gov.nv.dwss.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
* <p>Java class for anonymous complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>
* &lt;complexType>
*   &lt;complexContent>
*     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       &lt;sequence>
*         &lt;element name="BatchID" type="{http://www.w3.org/2001/XMLSchema}int"/>
*         &lt;element name="BatchName" type="{http://www.w3.org/2001/XMLSchema}int"/>
*         &lt;element name="ChanJobsCompleted" type="{http://www.w3.org/2001/XMLSchema}byte"/>
*         &lt;element name="ChanJobsCreated" type="{http://www.w3.org/2001/XMLSchema}byte"/>
*         &lt;element name="ChanJobsErrored" type="{http://www.w3.org/2001/XMLSchema}byte"/>
*         &lt;element name="ChanJobsStopped" type="{http://www.w3.org/2001/XMLSchema}byte"/>
*         &lt;element name="ChanJobsWorkflow" type="{http://www.w3.org/2001/XMLSchema}byte"/>
*         &lt;element name="TransCompleted" type="{http://www.w3.org/2001/XMLSchema}byte"/>
*         &lt;element name="TransErrored" type="{http://www.w3.org/2001/XMLSchema}byte"/>
*         &lt;element name="TransStopped" type="{http://www.w3.org/2001/XMLSchema}byte"/>
*         &lt;element name="TransWorkflow" type="{http://www.w3.org/2001/XMLSchema}byte"/>
*         &lt;element name="TransCount" type="{http://www.w3.org/2001/XMLSchema}byte"/>
*         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
*         &lt;element name="StatusText" type="{http://www.w3.org/2001/XMLSchema}string"/>
*       &lt;/sequence>
*     &lt;/restriction>
*   &lt;/complexContent>
* &lt;/complexType>
* </pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
 "batchID",
 "batchName",
 "chanJobsCompleted",
 "chanJobsCreated",
 "chanJobsErrored",
 "chanJobsStopped",
 "chanJobsWorkflow",
 "transCompleted",
 "transErrored",
 "transStopped",
 "transWorkflow",
 "transCount",
 "status",
 "statusText",
 "correlationID",
 "timeStamp"
})
@XmlRootElement(name = "ExternalJobResponseAPI")
public class ExternalJobResponseAPI implements Serializable {
  private static final long serialversionUID = 129348938L; 
 @XmlElement(name = "BatchID")
 private int batchID;
 @XmlElement(name = "BatchName")
 private int batchName;
 @XmlElement(name = "ChanJobsCompleted")
 private byte chanJobsCompleted;
 @XmlElement(name = "ChanJobsCreated")
 private byte chanJobsCreated;
 @XmlElement(name = "ChanJobsErrored")
 private byte chanJobsErrored;
 @XmlElement(name = "ChanJobsStopped")
 private byte chanJobsStopped;
 @XmlElement(name = "ChanJobsWorkflow")
 private byte chanJobsWorkflow;
 @XmlElement(name = "TransCompleted")
 private byte transCompleted;
 @XmlElement(name = "TransErrored")
 private byte transErrored;
 @XmlElement(name = "TransStopped")
 private byte transStopped;
 @XmlElement(name = "TransWorkflow")
 private byte transWorkflow;
 @XmlElement(name = "TransCount")
 private byte transCount;
 @XmlElement(name = "Status", required = true)
 private String status;
 @XmlElement(name = "StatusText", required = true)
 private String statusText;
 
 @XmlElement(name = "CorrelationID")
 private String correlationID;
 
 @XmlElement(name = "TimeStamp")
 private long timeStamp;

 /**
  * Gets the value of the batchID property.
  * 
  */
 public int getBatchID() {
     return batchID;
 }

 /**
  * Sets the value of the batchID property.
  * 
  */
 public void setBatchID(int value) {
     this.batchID = value;
 }

 /**
  * Gets the value of the batchName property.
  * 
  */
 public int getBatchName() {
     return batchName;
 }

 /**
  * Sets the value of the batchName property.
  * 
  */
 public void setBatchName(int value) {
     this.batchName = value;
 }

 /**
  * Gets the value of the chanJobsCompleted property.
  * 
  */
 public byte getChanJobsCompleted() {
     return chanJobsCompleted;
 }

 /**
  * Sets the value of the chanJobsCompleted property.
  * 
  */
 public void setChanJobsCompleted(byte value) {
     this.chanJobsCompleted = value;
 }

 /**
  * Gets the value of the chanJobsCreated property.
  * 
  */
 public byte getChanJobsCreated() {
     return chanJobsCreated;
 }

 /**
  * Sets the value of the chanJobsCreated property.
  * 
  */
 public void setChanJobsCreated(byte value) {
     this.chanJobsCreated = value;
 }

 /**
  * Gets the value of the chanJobsErrored property.
  * 
  */
 public byte getChanJobsErrored() {
     return chanJobsErrored;
 }

 /**
  * Sets the value of the chanJobsErrored property.
  * 
  */
 public void setChanJobsErrored(byte value) {
     this.chanJobsErrored = value;
 }

 /**
  * Gets the value of the chanJobsStopped property.
  * 
  */
 public byte getChanJobsStopped() {
     return chanJobsStopped;
 }

 /**
  * Sets the value of the chanJobsStopped property.
  * 
  */
 public void setChanJobsStopped(byte value) {
     this.chanJobsStopped = value;
 }

 /**
  * Gets the value of the chanJobsWorkflow property.
  * 
  */
 public byte getChanJobsWorkflow() {
     return chanJobsWorkflow;
 }

 /**
  * Sets the value of the chanJobsWorkflow property.
  * 
  */
 public void setChanJobsWorkflow(byte value) {
     this.chanJobsWorkflow = value;
 }

 /**
  * Gets the value of the transCompleted property.
  * 
  */
 public byte getTransCompleted() {
     return transCompleted;
 }

 /**
  * Sets the value of the transCompleted property.
  * 
  */
 public void setTransCompleted(byte value) {
     this.transCompleted = value;
 }

 /**
  * Gets the value of the transErrored property.
  * 
  */
 public byte getTransErrored() {
     return transErrored;
 }

 /**
  * Sets the value of the transErrored property.
  * 
  */
 public void setTransErrored(byte value) {
     this.transErrored = value;
 }

 /**
  * Gets the value of the transStopped property.
  * 
  */
 public byte getTransStopped() {
     return transStopped;
 }

 /**
  * Sets the value of the transStopped property.
  * 
  */
 public void setTransStopped(byte value) {
     this.transStopped = value;
 }

 /**
  * Gets the value of the transWorkflow property.
  * 
  */
 public byte getTransWorkflow() {
     return transWorkflow;
 }

 /**
  * Sets the value of the transWorkflow property.
  * 
  */
 public void setTransWorkflow(byte value) {
     this.transWorkflow = value;
 }

 /**
  * Gets the value of the transCount property.
  * 
  */
 public byte getTransCount() {
     return transCount;
 }

 /**
  * Sets the value of the transCount property.
  * 
  */
 public void setTransCount(byte value) {
     this.transCount = value;
 }

 /**
  * Gets the value of the status property.
  * 
  * @return
  *     possible object is
  *     {@link String }
  *     
  */
 public String getStatus() {
     return status;
 }

 /**
  * Sets the value of the status property.
  * 
  * @param value
  *     allowed object is
  *     {@link String }
  *     
  */
 public void setStatus(String value) {
     this.status = value;
 }

 /**
  * Gets the value of the statusText property.
  * 
  * @return
  *     possible object is
  *     {@link String }
  *     
  */
 public String getStatusText() {
     return statusText;
 }

 /**
  * Sets the value of the statusText property.
  * 
  * @param value
  *     allowed object is
  *     {@link String }
  *     
  */
 public void setStatusText(String value) {
     this.statusText = value;
 }

	public String getCorrelationID() {
	return correlationID;
}

public void setCorrelationID(String correlationID) {
	this.correlationID = correlationID;
}

	public long getTimeStamp() {
	return timeStamp;
}

public void setTimeStamp(long l) {
	this.timeStamp = l;
}

	@Override
	public String toString() {
		return "ExternalJobResponseAPI [batchID=" + batchID + ", batchName=" + batchName + ", chanJobsCompleted="
				+ chanJobsCompleted + ", chanJobsCreated=" + chanJobsCreated + ", chanJobsErrored=" + chanJobsErrored
				+ ", chanJobsStopped=" + chanJobsStopped + ", chanJobsWorkflow=" + chanJobsWorkflow
				+ ", transCompleted=" + transCompleted + ", transErrored=" + transErrored + ", transStopped="
				+ transStopped + ", transWorkflow=" + transWorkflow + ", transCount=" + transCount + ", status="
				+ status + ", statusText=" + statusText + ", correlationID=" + correlationID + ", timeStamp="
				+ timeStamp + "]";
	}

}
