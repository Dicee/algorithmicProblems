package miscellaneous.utils.test.collection.richIterator;

import static miscellaneous.utils.collection.CollectionUtils.listOf;
import static miscellaneous.utils.collection.richIterator.RichIterators.concatIterators;
import static miscellaneous.utils.collection.richIterator.RichIterators.emptyIterator;
import static miscellaneous.utils.collection.richIterator.RichIterators.prepend;
import static miscellaneous.utils.test.collection.richIterator.RichIteratorTestUtils.assertIteratorsAreEqual;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.Iterator;

import miscellaneous.utils.collection.richIterator.RichIterator;
import miscellaneous.utils.collection.richIterator.RichIterators;

import org.junit.Test;

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
	
	@Test
	public void testArray2DIterator() {
		Integer[][] arr = { { 1,2 },{ 3,4 } };
		assertThat(RichIterators.from2DArray(arr).flatMap(RichIterator::toList).toList(), equalTo(listOf(1,2,3,4)));
	}
	
	@Test 
	public void testConcatenation_emptyIterator() {
		assertIteratorsAreEqual(
			concatIterators(RichIterators.of(1, 2, 3), emptyIterator(), RichIterators.of(4, 5)),
			RichIterators.of(1, 2, 3, 4, 5));
	}
	
	@Test 
	public void testConcatenation_emptyIterators() {
		assertIteratorsAreEqual(concatIterators(emptyIterator(), emptyIterator()), emptyIterator());
	}

	@Test 
	public void testConcatenation_prepend() {
		assertIteratorsAreEqual(prepend(4, RichIterators.of(5)), RichIterators.of(4, 5));
	}
	
	@Test 
	public void testConcatenation_prependEmptyIterator() {
		assertIteratorsAreEqual(prepend(4, RichIterators.of(5)), RichIterators.of(4, 5));
	}
}
