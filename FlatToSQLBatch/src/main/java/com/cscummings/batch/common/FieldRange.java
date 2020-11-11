package com.cscummings.batch.common;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * Marks a field with range start and end information for
 * fixed length tokenizer
 * {@link org.springframework.batch.item.file.transform.FixedLengthTokenizer} <br>
 * <br>
 * Expected declaration: FieldRange(start=1, end=5)
 * 
 * @author ccummings
 * @since 2.0
 * @see org.springframework.batch.item.file.transform.FixedLengthTokenizer
 */

@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface FieldRange {
	

	public int start() default 0;
	public int end() default 1;
}
