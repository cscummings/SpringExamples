package com.cscummings.batch;

import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.resource.ListPreparedStatementSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cscummings.batch.common.NomadsProperties;

import com.cscummings.batch.TestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=TestConfiguration.class)
//@TestPropertySource(locations="classpath:application-dev.properties")
@TestPropertySource(properties="nomads.schema=W026DTF2")

public class sqlParsingItemReader {

	@Autowired
	private NomadsProperties nomadsProps;

	
	@Before
	public void setUp() throws Exception {
		nomadsProps = new NomadsProperties();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void substitutionTest() throws Exception {
		String select_new_SNAP_elig_members = items.sql
				.replaceAll("##db-schema-name##", "W026DTF1")
				.replaceAll("##minRecert##", nomadsProps.minRecertApplicationDate())
				.replaceAll("##maxRecert##", nomadsProps.maxRecertApplicationDate())
				.replaceAll("##prdBeginDt##", nomadsProps.periodBeginDate())
				.replaceAll("##redetDt##", nomadsProps.nextReDeterminationDate());
		assertEquals(select_new_SNAP_elig_members,"select * from dual");
		
	}
	

	public void test() {
		ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(items.sql);
		String sql = NamedParameterUtils.substituteNamedParameters(parsedSql, items.parameters);
		assertNull(sql);
		ListPreparedStatementSetter listPreparedStatementSetter = new ListPreparedStatementSetter();
		listPreparedStatementSetter.setParameters(Arrays.asList(NamedParameterUtils.buildValueArray(parsedSql, items.parameters, null)));
		
	}

	public static class items {
		public static final SqlParameterSource parameters = new MapSqlParameterSource();

		
		public static final String sql = "SELECT  T1.ICI, T2.UPI, T3.SSN, T3.FRST_NM , T3.MID_NM, T3.LST_NM " + 
"FROM ##db-schema-name##.TWNPRGM_CASE_HSTRY T1 " +
"    ,##db-schema-name##.TWNCASE_MEMBER_HST T2 " +
"    ,##db-schema-name##.TWNPERSON T3 " +
"WHERE T1.PRGM_CASE_TYPE = 'FS' " +
"AND T1.PRGM_CASE_STS IN ('P', 'O') " +
"AND T1.RCRT_APL_DT >= ##minRecert##  " + 
"AND T1.RCRT_APL_DT <= ##maxRecert##  " +  
"AND T1.PRD_BEG_DT =##prdBeginDt## " +
"AND T1.APPL_ACT_TYPE IN ('FA', 'RO') " +
"AND T1.ICI = T2.ICI " +
"AND T2.UPI = T3.UPI " +
"AND T2.HH_LEFT_DT = '9999-12-31' " +
"AND T2.PRD_END_DT = '9999-12-31' " +
"AND NOT T3.SSN LIKE '000%' " +
"AND NOT SSN LIKE '999%'     " +
"UNION " +
"SELECT  T4.ICI, T2.UPI, T3.SSN, T3.FRST_NM , T3.MID_NM, T3.LST_NM " + 
"FROM ##db-schema-name##.TWNPRGM_CASE_HSTRY T1 " +
"    ,##db-schema-name##.TWNCASE_MEMBER_HST T2 " +
"    ,##db-schema-name##.TWNPERSON T3 " +
"    ,##db-schema-name##.TWNREDET_RCRT_DTLS T4 " +
"WHERE T1.PRGM_CASE_TYPE = 'FS' " +
"AND T1.PRGM_CASE_STS IN ('P', 'O') " +
"AND T1.ICI = T4.ICI " +
"AND T2.ICI = T4.ICI " +
"AND T2.UPI = T3.UPI " +
"AND T4.PRGM_CASE_TYPE = 'FS' " +
"AND T4.NEXT_REDET_DT = ##redetDt##  " +
"AND T2.HH_LEFT_DT = '9999-12-31'   " +
"AND T2.PRD_END_DT = '9999-12-31'   " +
"AND NOT T3.SSN LIKE '000%'   " +
"AND NOT SSN LIKE '999%'     " +
"FOR FETCH ONLY WITH UR;" ;
	}
}
