package gov.nv.dwss.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;

public class Constants {

	public static final String base_package = "com.cscummings";
	
	@Resource
    @Value("${select_new_SNAP_elig_members}")
    private String select_new_SNAP_elig_members;

	// Job Control Constants
	public static final String sendJobName = "sendNDNHJob";
	public static final String receiveJobName = "receiveNDNHJob";
	public static final String sendStep = "sendStep";
	public static final String receiveStep = "receiveStep";
	
	// Constants for record sent to NDNH
	public static final String subId = "SNP";
	public static final String inHDRId = "HDR";
	public static final String inTRLId = "TRL";
	public static final String inRecId = "MCH";

	//Constants for recrod received from NDNH
	public static final String outHDRId = "MTH";
	public static final String outRecId = "W4M";
	public static final String outNotifyId = "ERM";
	public static final String outTRLId = "MTT";
	
	public static final String NVStateCd = "NV";
	public static final String NVStateNumCd = "32";
	
	public static final int recSize = 1000;
	public static final int RecvRecSize = 1000;
	public static final int SendRecSize = 200;
	
	//Date Constants
	public static final String dateFormat = "yyyyMMdd";
	public static final String maxDate = "9999-12-31";
	public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.dateFormat);
	public static final SimpleDateFormat hyphenDateFormat = new SimpleDateFormat(Constants.yyyy_MM_dd);
	public static final String simpleDateString = simpleDateFormat.format(new Date());

	//Date parse Patterns for validation
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String MM_dd_yyyy = "MM-dd-yyyy";
	public static final String MMddyyyy = "MMddyyyy";
	public static final String yyyyMMdd = "yyyyMMdd";
	
	//String Constants
	public static final String blank = " ";
	public static final String blanks5 = "     ";
	public static final String blanks8 = "        ";	
	public static final String and = "AND";
	public static final String andNot = "AND NOT";
	public static final String select = "SELECT";
	public static final String where = "WHERE";
	public static final String N = "N";
	public static final String Y = "Y";
	
	//Character Constants
	public static final Character blankChar = ' ' ;
	public static final Character commaChar = ',' ;
	public static final Character DASH = '-';
	public static final Character sChar = 's';
	public static final Character zero ='0';
	
	// SQL Query Strings
	public static final String  sysdateSelect = "Select SYSTEM_DATE from ";
	public static final String  TwnSysDateTable = "TWNSYSDATE";
	public static final String nomadsQuery = "Select n FROM NDNHNomads AS n";
	public static final String s1 = "SELECT  T1.ICI, T2.UPI, T3.SSN, T3.FRST_NM , T3.MID_NM, T3.LST_NM, T3.MODFR FROM ";
	public static final String s2 = ".TWNPRGM_CASE_HSTRY T1,";
	public static final String s3 = ".TWNCASE_MEMBER_HST T2,";
	public static final String s4 = ".TWNPERSON T3 WHERE T1.PRGM_CASE_TYPE = 'FS' AND T1.PRGM_CASE_STS IN ('P', 'O') AND T1.RCRT_APL_DT BETWEEN '2017-01-01' AND '2017-01-31'" ; 
	public static final String s5 = "AND T1.PRD_BEG_DT = ";

	/** The Constant SCHEMA_NAME. */
	public static final String SCHEMA_NAME = "##db-schema-name##";
	public static final String begin_date = "##db-begin-dt##";
	public static final String end_date = "##db-end-dt##";
	public static final String min_reCert = "##minRecert##";
	public static final String max_reCert = "##maxRecert##";
	public static final String prd_beginDate = "##prdBeginDt##";
	public static final String reDetDate = "##redetDt##";
	
	//Formatting Constants - for building format string for Aggregator
	public static final String fmt_hdr = "%-";
	public static final String fmt_string = "s%-";
	
	//Error Messages
	public static final String errorMsg = new String();
}
