package miscellaneous.skiller.indexation;

import static miscellaneous.utils.regex.Patterns.BLANKS;
import static miscellaneous.utils.regex.Patterns.EMAIL;
import static miscellaneous.utils.regex.Patterns.URL;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import miscellaneous.utils.collection.HashCounter;

public class TextIngestor {
	public static HashCounter<String> ingest(String s) {
		HashCounter<String> res = new HashCounter<>();
		toStandardWords(s).forEach(res::add);
		return res;
	}
	
	private static List<String> toStandardWords(String str) {
		LinkedList<String> urlsAndEmails = new LinkedList<>();
		List<String> words = Stream
			.of(BLANKS.split(str))
			.map(TextIngestor::replaceApostrophesAndHyphens)
			// very hacky... I know ! But more efficient than recomputing twice
			.filter(s -> {
				boolean matches = URL.or(EMAIL).matches(s);
				if (matches) urlsAndEmails.add(s);
				return !matches;
			})
			.map(s -> s.replaceAll("[^\\p{L}\\d\\s]",""))
			.flatMap(s -> Stream.of(BLANKS.split(s)))
			.filter(s -> !s.isEmpty())
			.map(String::toLowerCase)
			.collect(Collectors.toList());
		
		words.addAll(urlsAndEmails);
		return words;
	}
	
	private static String replaceApostrophesAndHyphens(String s) {
		return s.replace("c'","ce ").replace("j'","je ").replace("t'","te ").replace("m'","me ").replace("s'","se ").replace("l'","le").replace("d'","de ")
				.replace("-"," ");
	}
}
