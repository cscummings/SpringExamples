package com.cscummings.batch.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.cscummings.batch.common.NomadsProperties;
import com.cscummings.batch.common.ValidationUtils;

@Service
public class NDNHNomadsRowMapper implements RowMapper<NDNHNomads> {

	public static final String SSN = "SSN";
	public static final String FRST_NM = "FRST_NM";
	public static final String MID_NM = "MID_NM";
	public static final String LST_NM = "LST_NM";
	public static final String ICI = "ICI";
	public static final String UPI = "UPI";

	@Autowired
	private NomadsProperties nomadsProps;

	@Override
	public NDNHNomads mapRow(ResultSet rs, int rowNum) throws SQLException  {
		NDNHNomads mn = new NDNHNomads();

		mn.setSSN(ValidationUtils.validateString(rs.getString(SSN), 9));
		mn.setFRST_NM(ValidationUtils.validateString(rs.getString(FRST_NM), 10));
		mn.setMID_NM(ValidationUtils.validateString(rs.getString(MID_NM), 10));
		mn.setLST_NM(ValidationUtils.validateString(rs.getString(LST_NM), 20));
		mn.setICI(ValidationUtils.validateString(rs.getString(ICI), 9));
		mn.setUPI(ValidationUtils.validateString(rs.getString(UPI), 9));

		mn.setFrom_date(nomadsProps.getStartdate());
		mn.setThrough_date(nomadsProps.getEnddate());

		return mn;
	}

}
