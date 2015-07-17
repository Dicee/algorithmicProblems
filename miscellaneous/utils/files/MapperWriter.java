package miscellaneous.utils.files;

import java.io.IOException;
import java.io.Writer;
import java.util.function.Function;

public class MapperWriter extends Writer {
	private boolean ignoreFilter = false;
	private final Writer writer;
	private final Function<String,String> mapper;

	public MapperWriter(Writer writer, Function<String,String> mapper) {
		this.writer = writer;
		this.mapper = mapper;
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
			writer.write(mapper.apply(s));
		else
			writer.write(s);
	}
	
	public void writeln(String s) throws IOException {
		if (!ignoreFilter)
			writer.write(mapper.apply(s) + "\n");
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
