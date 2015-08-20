package miscellaneous.utils.collection.richIterator;

import static miscellaneous.utils.exceptions.ExceptionUtils.uncheckExceptionsAndGet;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import miscellaneous.utils.io.IOUtils;

import com.google.common.base.Throwables;

public class RichIterators {
	private RichIterators() { }
	
	@SafeVarargs
	public static <T> RichIterator<T> of(T... elts)                            { return new ArrayRichIterator<>(elts); }
	public static <T> RichIterator<T> fromCollection(Collection<T> collection) { return wrap(collection.iterator())  ; }
	public static <T> GroupedRichIterator<T> from2DArray(T[][] arr) { 
		return GroupedRichIterator.create(new ArrayRichIterator<>(arr).map(col -> new ArrayRichIterator<>(col))); 
	}
	
	public static RichIterator<String> fromLines(File f) {
		BufferedReader br = null;
		try {
			br = Files.newBufferedReader(f.toPath());
			final BufferedReader source = br;
			return new RichIterator<String>() {
				private Deque<String> buffer = new LinkedList<>();
				
				@Override
				protected String nextInternal() {
					if (!hasNext()) throw new NoSuchElementException();
					return !buffer.isEmpty() ? buffer.pop() : uncheckExceptionsAndGet(source::readLine);
				}
				
				@Override
				protected boolean hasNextInternal() {
					if (!buffer.isEmpty()) return true;
					
					String line = uncheckExceptionsAndGet(source::readLine);
					if (line != null) buffer.addLast(line);
					return line != null;
				}

				@Override
				protected void closeInternal() throws IOException {
					source.close();
				}
			};
		} catch (IOException e) {
			IOUtils.closeQuietly(br);
			throw Throwables.propagate(e);
		}
	}
	
	@SuppressWarnings("resource")
	public static <T> RichIterator<T> fromSerializedRecords(File f, Class<T> clazz) {
		FileInputStream   fis = null; 
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(f);
			ois = new ObjectInputStream(fis);
			final ObjectInputStream source = ois;
			return new BufferedRichIterator<T>(new LookAheadIterator<T>() {
				@Override
				public T readNext() throws EOFException, IOException { 
					try {
						return clazz.cast(source.readObject());
					} catch (ClassNotFoundException e) {
						throw new IOException(e);
					} 
				}
			});
		} catch (EOFException e) { 
			return emptyIterator();  
		} catch (IOException e) {
			IOUtils.closeQuietly(fis);
			IOUtils.closeQuietly(ois);
			throw Throwables.propagate(e);
		}
	}

	public static <T> RichIterator<T> wrap(Iterator<T> it) {
		return new RichIterator<T>() {
			@Override protected boolean hasNextInternal()                    { return it.hasNext()         ; }
			@Override protected T       nextInternal   ()                    { return it.next()            ; }
			@Override protected void    closeInternal  () throws IOException { IOUtils.closeIfCloseable(it); }
		};
	}
	
	public static <T> RichIterator<T> prepend(Iterator<T> it, T value) { return prepend(wrap(it),value); }
	
	public static <T> RichIterator<T> prepend(RichIterator<T> it, T value) {
		return new RichIterator<T>() {
			private boolean consumed = false;
			@Override
			protected boolean hasNextInternal() throws Exception { return !consumed || it.hasNextInternal(); }

			@Override
			protected T nextInternal() {
				if (!consumed) {
					consumed = true;
					return value;
				}
				return it.next();
			}
		};
	}
	
	public static <T> RichIterator<T> emptyIterator() { return wrap(Collections.emptyIterator()); }
}


