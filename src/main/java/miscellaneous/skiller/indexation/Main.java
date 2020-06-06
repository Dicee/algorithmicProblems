package miscellaneous.skiller.indexation;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static com.dici.collection.CollectionUtils.reverse;
import static com.dici.files.FileUtils.getPathRelativeToClass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import miscellaneous.skiller.indexation.entities.AbstractTextualContent;
import miscellaneous.skiller.indexation.entities.Comment;
import miscellaneous.skiller.indexation.entities.Question;
import miscellaneous.skiller.indexation.entities.TextualContent.SourceType;
import miscellaneous.skiller.indexation.entities.WordCount;
import com.dici.strings.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	private static final String	QUESTION_IDS		= "data/questionIds.txt";
	private static final String	QUESTION_IDS_BACKUP	= "data/questionIds.txt.bak";
	private static final String	BASE_URL			= "http://skiller.fr/question/";
	private static final int	ID_MAX				= 8450;
	
	public static void main(String[] args) throws IOException {
//		backupQuestionIds();
//		discoverNewQuestions();
		int id = 8402;//getNewestQuestionId();
		String content = extractQuestionAndCommentsContent(id);
		System.out.println(content + "\n");
//		System.out.println(TextIngestor.ingest(content));
		
		Document doc = Jsoup.connect(BASE_URL + id).get();
		Question q = extractQuestion(doc, id);
		List<Comment> comments = extractComments(doc,id);
		
		List<WordCount> wc = getWordCounts(q);
		wc.addAll(comments.stream().flatMap(x -> getWordCounts(x).stream()).collect(Collectors.toList()));
				
		
		System.out.println(q);
		System.out.println(StringUtils.join("\n",comments));
		System.out.println();
		System.out.println(StringUtils.join("\n",wc));
	}

	private static List<WordCount> getWordCounts(AbstractTextualContent textualContent) {
		return TextIngestor.ingest(textualContent.text()).stream()
						   .map(entry -> new WordCount(textualContent.id(),SourceType.forClass(textualContent.getClass()),entry.getKey(),entry.getValue()))
						   .collect(Collectors.toList());
	}
	
	private static Question extractQuestion(Document doc, int id) {
		Element title    = doc.getElementsByAttributeValueMatching("href","/question/" + id).first();	
		Element question = doc.getElementsByTag("meta").stream().filter(elt -> elt.hasAttr("name") && elt.attr("name").equals("description")).findFirst().get();
		String  author   = title.parent().child(2).child(0).attr("title");
		return new Question(id,StringUtils.join("\n",title.text(),question.attr("content")),author);
	}
	
	private static List<Comment> extractComments(Document doc, int questionId) {
		Elements comments = doc.getElementsByClass("post-body");
		return comments.stream().map(comment -> {
			String author = comment.parent().child(0).child(0).attr("title");
			return new Comment(questionId,comment.text(),author);
		}).collect(Collectors.toList());
	}

	private static String extractQuestionAndCommentsContent(int id) throws IOException {
		Document doc      = Jsoup.connect(BASE_URL + id).get();
		Question question = extractQuestion(doc,id);
		Elements comments = doc.getElementsByClass("post-body");
		return Stream.concat(Stream.of(question.text()),comments.stream().map(Element::text)).collect(Collectors.joining("\n"));
	}
	
	private static void backupQuestionIds() throws IOException {
		Files.copy(getQuestionIdsPath(QUESTION_IDS),getQuestionIdsPath(QUESTION_IDS_BACKUP),REPLACE_EXISTING);
	}

	private static File discoverNewQuestions() throws IOException {
		int           idMin       = getNewestQuestionId();
		List<Integer> questionIds = reverse(IntStream.range(idMin,ID_MAX).filter(i -> exists(BASE_URL + i)).boxed().collect(Collectors.toList()));
		return writeToFile(questionIds);
	}
	
	private static int getNewestQuestionId() throws IOException {
		return Integer.parseInt(Files.newBufferedReader(getQuestionIdsPath(QUESTION_IDS)).readLine());
	}
	
	private static File writeToFile(List<Integer> questionIds) throws IOException {
		Path path = getQuestionIdsPath(QUESTION_IDS);
		BufferedWriter bw = Files.newBufferedWriter(path);
		for (Integer id : questionIds) bw.write(id + "\n");
		bw.close();
		return path.toFile();
	}
	private static Path getQuestionIdsPath(String path) {
		return getPathRelativeToClass(Main.class,path);
	}
	
	public static boolean exists(String url) {
		try {
			Jsoup.connect(url).execute();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
