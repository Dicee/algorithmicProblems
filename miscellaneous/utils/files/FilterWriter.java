package utils.io;

import java.io.IOException;
import java.io.Writer;

import utils.various.TextFilter;

public class FilterWriter extends Writer {
	private boolean ignoreFilter = false;
	private Writer writer;
	private TextFilter tf;

	public FilterWriter(Writer w, TextFilter tf) {
		this.writer = w;
		this.tf     = tf;
	}
	
	@Override
	public void close() throws IOException {
		writer.close();		
	}

	@Override
	public void flush() throws IOException {
		writer.flush();		
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		writer.write(cbuf, off, len);		
	}
	
	@Override
	public void write(String s) throws IOException {
		if (!ignoreFilter)
			writer.write(tf.filter(s));
		else
			writer.write(s);
	}
	
	public void writeln(String s) throws IOException {
		if (!ignoreFilter)
			writer.write(tf.filter(s) + "\n");
		else
			writer.write(s + "\n");
	}

	public void setIgnoreFilter(boolean ignoreFilter) {
		this.ignoreFilter = ignoreFilter;
	}

	public boolean ignoresFilter() {
		return ignoreFilter;
	}
}
