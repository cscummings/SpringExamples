package com.cscummings.batch.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.validator.routines.DateValidator;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.base.Strings;

public class ValidationUtils {
	public static final String[] parsePatterns = { Constants.yyyy_MM_dd, Constants.MM_dd_yyyy,
			Constants.MMddyyyy, Constants.yyyyMMdd };

	public static final String[] databasePattern = { Constants.yyyyMMdd };
	
	public static String validateString(String inString, int len) {
		String validString = Strings.nullToEmpty(inString);
		if (inString.length() < len) {
			Strings.padEnd(inString, len, Constants.blankChar);
		} else if (inString.length() > len) {
			validString = validString.substring(0, len);
		}

		return validString;
	}

	public static String validateDate(String inString, int len) throws ParseException {
		Date checkDate = null;
		String validString = Strings.nullToEmpty(inString);

		if (DateValidator.getInstance().isValid(inString, Constants.yyyyMMdd)) {
			// if its valid we're done
			return validString;
		} else {

			checkDate = DateUtils.parseDate(validString, parsePatterns);
			validString = new SimpleDateFormat(Constants.yyyyMMdd).format(checkDate);
		}

		return validString;
	}

	public static Date stringToDate(String inString) throws ParseException {
		Date checkDate = DateUtils.parseDate(inString, databasePattern);;

		return checkDate;
	}
	
	public static void buildSQLString(StringBuilder inString, String arg) {
		int start = 0;
		int end = 0;
		while (end < inString.length()) {
			start = inString.indexOf(arg, start);
			end = inString.lastIndexOf(arg, end);
		}
	}
}
