package com.cscummings.batch.writers;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;

import com.cscummings.batch.common.Constants;
import com.cscummings.batch.model.NDNHNomads;
import com.google.common.base.Strings;

/**
 * A {@link ItemWriter} implementation which also implements {@link FlatFileHeaderCallback} and
 * {@link FlatFileFooterCallback}. A stadard FlatFileItemWriter is taken as a delgate to do 
 * the bulk of the work. This was done to capture the number of records written and add them 
 * to the footer.
 *  <br>
 * 
 * @see FlatFileItemWriter
 * 
 * @author Connie Cummmings 
 */

/*
 * Writes out data gathered from NDNH database, converted to string and written out to flat file
 */
public class NDNHNomadsFileItemWriter implements ItemWriter<NDNHNomads>, ItemStream, FlatFileHeaderCallback, FlatFileFooterCallback {
	private FlatFileItemWriter<NDNHNomads> delegate;    
	private int recordCount = 0;
	
	 public FlatFileItemWriter<NDNHNomads> getDelegate() {
	         return delegate ;
	    }
	 
	 public void setDelegate(FlatFileItemWriter<NDNHNomads> delegate) {
	        this.delegate = delegate;
	    }

	@Override
	public void close() throws ItemStreamException {
		this.delegate.close();

	}

	@Override
	public void open(ExecutionContext arg0) throws ItemStreamException {
		this.delegate.open(arg0);
	}

	@Override
	public void update(ExecutionContext arg0) throws ItemStreamException {
		this.delegate.update(arg0);

	}

	@Override
	public void write(List<? extends NDNHNomads> items) throws Exception {
		delegate.write(items);
		recordCount += items.size();
		
	}
	
	
	/* (non-Javadoc)
	 * @see org.springframework.batch.item.file.FlatFileHeaderCallback#writeHeader(java.io.Writer)
	 */
	
	@Override
	public void writeHeader(Writer writer) throws IOException {
		StringBuilder headerString = new StringBuilder();
		headerString.append(Constants.subId);  // use constants that must be appended to every trailer record
		headerString.append(Constants.inHDRId);
		headerString.append(Constants.NVStateNumCd);
		headerString.append(new SimpleDateFormat(Constants.simpleDateString).format(new Date()));
		// filler for future enhancements
		headerString.append(Strings.padEnd(new String(), 8, Constants.blankChar));
		// calculate batchnumber somehow - perhaps let spring batch provide - for now blank fill
		headerString.append(Strings.padEnd(new String(), 8, Constants.blankChar));
		// trailing filler
		headerString.append(Strings.padEnd(new String(), 168, Constants.blankChar));
		writer.write(headerString.toString());
		
		}

	
	/* (non-Javadoc)
	 * @see org.springframework.batch.item.file.FlatFileFooterCallback#writeHeader(java.io.Writer)
	 */
	@Override
	public void writeFooter(Writer writer) throws IOException {
		StringBuilder footerString = new StringBuilder();
		recordCount = recordCount+2;// #of records plus header and trailer
		footerString.append(Constants.subId);  // use constants that must be appended to every trailer record
		footerString.append(Constants.inTRLId);
		footerString.append(Strings.padStart(String.valueOf(recordCount), 11, Constants.zero));
		footerString.append(Strings.padEnd(new String(), 183, Constants.blankChar)); // filler of blanks 183 spaces
		writer.write(footerString.toString());
	}
	
	/**
	 * After step listener.
	 *
	 * @param stepExecution the step execution
	 * @return the exit status
	 */
	@AfterStep
	public ExitStatus afterStep(StepExecution stepExecution){
		String exitCode = stepExecution.getExitStatus().getExitCode();
			return stepExecution.getExitStatus();
	}

}

