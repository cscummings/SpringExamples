package com.cscummings.batch.transform;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.springframework.util.StringUtils;

/**
 * Property editor for {@code java.sql.Date},
 * supporting a custom {@code java.text.DateFormat}.
 *
 * <p> DB2 returns Date objects as type java.sql.Date instead of
 * of java.util.Date, the Spring Batch CustomDateEditor does not support java.sql.date 
 * hence in order to move a string value
 * into the data table a special editor has been written.
 * 
 * <p>This editor is not meant to be used as system PropertyEditor but rather
 * as locale-specific sql date editor within custom controller code,
 * parsing user-entered number strings into sql.Date properties of beans
 * and rendering them in usable form.
 *
 * <p>In web MVC code, this editor will typically be registered with
 * {@code binder.registerCustomEditor}.
 *
 * @author Connie Cummings
 * @since 04.17.2017
 * @see java.sql.date
 * @see java.text.DateFormat
 * @see org.springframework.validation.DataBinder#registerCustomEditor
 */

	public class CustomSqlDateEditor extends PropertyEditorSupport {

		private final DateFormat dateFormat;

		private final boolean allowEmpty;

		private final int exactDateLength;


		/**
		 * Create a new CustomSqlDateEditor instance, using the given DateFormat
		 * for parsing and rendering.
		 * <p>The "allowEmpty" parameter states if an empty String should
		 * be allowed for parsing, i.e. get interpreted as null value.
		 * Otherwise, an IllegalArgumentException gets thrown in that case.
		 * @param dateFormat DateFormat to use for parsing and rendering
		 * @param allowEmpty if empty strings should be allowed
		 */
		public CustomSqlDateEditor(DateFormat dateFormat, boolean allowEmpty) {
			this.dateFormat = dateFormat;
			this.allowEmpty = allowEmpty;
			this.exactDateLength = -1;
		}

		/**
		 * Create a new CustomSqlDateEditor instance, using the given DateFormat
		 * for parsing and rendering.
		 * <p>The "allowEmpty" parameter states if an empty String should
		 * be allowed for parsing, i.e. get interpreted as null value.
		 * Otherwise, an IllegalArgumentException gets thrown in that case.
		 * <p>The "exactDateLength" parameter states that IllegalArgumentException gets
		 * thrown if the String does not exactly match the length specified. This is useful
		 * because SimpleDateFormat does not enforce strict parsing of the year part,
		 * not even with {@code setLenient(false)}. Without an "exactDateLength"
		 * specified, the "01/01/05" would get parsed to "01/01/0005". However, even
		 * with an "exactDateLength" specified, prepended zeros in the day or month
		 * part may still allow for a shorter year part, so consider this as just
		 * one more assertion that gets you closer to the intended date format.
		 * @param dateFormat DateFormat to use for parsing and rendering
		 * @param allowEmpty if empty strings should be allowed
		 * @param exactDateLength the exact expected length of the date String
		 */
		public CustomSqlDateEditor(DateFormat dateFormat, boolean allowEmpty, int exactDateLength) {
			this.dateFormat = dateFormat;
			this.allowEmpty = allowEmpty;
			this.exactDateLength = exactDateLength;
		}


		/**
		 * Parse the Date from the given text, using the specified DateFormat.
		 */
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			if (this.allowEmpty && !StringUtils.hasText(text)) {
				// Treat empty String as null value.
				setValue(null);
			}
			else if (text != null && this.exactDateLength >= 0 && text.length() != this.exactDateLength) {
				throw new IllegalArgumentException(
						"Could not parse date: it is not exactly" + this.exactDateLength + "characters long");
			}
			else {
				try {
					java.util.Date date = (java.util.Date) new Date();
					
					date = (java.util.Date) this.dateFormat.parse(text);
					
					java.sql.Date sqlDate = new java.sql.Date(date.getTime());
					//setValue(this.dateFormat.parse(text));
					setValue(sqlDate);
				}
				catch (ParseException ex) {
					throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
				}
			}
		}

		/**
		 * Format the Date as String, using the specified DateFormat.
		 */
		@Override
		public String getAsText() {
			Date value = (Date) getValue();
			return (value != null ? this.dateFormat.format(value) : "");
		}

	}

