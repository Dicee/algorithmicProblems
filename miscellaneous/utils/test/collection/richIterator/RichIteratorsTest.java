package miscellaneous.utils.test.collection.richIterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.Iterator;

import miscellaneous.utils.collection.richIterator.RichIterators;

import org.junit.Test;

import miscellaneous.utils.collection.richIterator.RichIterators;

public class RichIteratorsTest {
	@Test
	public void testDeserializerIterator() throws IOException {
		File tmp = File.createTempFile("ser",null);
		try (FileOutputStream fos = new FileOutputStream(tmp) ; ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject("hey");
			oos.writeObject("how");
			oos.writeObject("are");
			oos.writeObject("you");
		}
		Iterator<String> it = RichIterators.fromSerializedRecords(tmp,String.class);
		assertThat(it.next(),is("hey"));
		assertThat(it.next(),is("how"));
		assertThat(it.next(),is("are"));
		assertThat(it.next(),is("you"));
		assertThat(it.hasNext(),is(false));
	}
	
	@Test
	public void testReadLinesIterator() throws IOException {
		File tmp = File.createTempFile("ser",null);
		try (BufferedWriter bw = Files.newBufferedWriter(tmp.toPath())) {
			bw.write("hey\n");
			bw.write("how are you\n");
			bw.write("man ?");
		}
		Iterator<String> it = RichIterators.fromLines(tmp);
		assertThat(it.next(),is("hey"));
		assertThat(it.next(),is("how are you"));
		assertThat(it.next(),is("man ?"));
		assertThat(it.hasNext(),is(false));
	}
}
