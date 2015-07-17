package miscellaneous.skiller.indexation;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static miscellaneous.skiller.indexation.model.Table.QUESTIONS;
import static miscellaneous.utils.collection.CollectionUtils.reverse;
import static miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ignoreCheckedExceptionsConsumer;
import static miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ignoreCheckedExceptions;
import static miscellaneous.utils.files.FileUtils.getPathRelativeToClass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import miscellaneous.skiller.indexation.entities.AbstractTextualContent;
import miscellaneous.skiller.indexation.entities.Comment;
import miscellaneous.skiller.indexation.entities.Question;
import miscellaneous.skiller.indexation.entities.TextualContent.SourceType;
import miscellaneous.skiller.indexation.entities.WordCount;
import miscellaneous.skiller.indexation.factories.CommentFactory;
import miscellaneous.skiller.indexation.factories.QuestionFactory;
import miscellaneous.skiller.indexation.factories.WordCountFactory;
import miscellaneous.skiller.indexation.model.DBManager;
import miscellaneous.utils.collection.StreamUtils;
import miscellaneous.utils.strings.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IndexWords extends AbstractIndexer {
	private static final String	QUESTION_IDS		= "data/questionIds.txt";
	private static final String	QUESTION_IDS_BACKUP	= QUESTION_IDS + ".bak";
	private static final String	BASE_URL			= "http://skiller.fr/question/";
	private static final int	ID_MAX				= 10000;
	
	public static void main(String[] args) throws IOException {
		backupQuestionIds();
		discoverNewQuestions();
		getMissingQuestions().forEach(ignoreCheckedExceptionsConsumer(id -> {
			Document doc = Jsoup.connect(BASE_URL + id).get();
			updateRecords(extractQuestion(doc, id),extractComments(doc,id));
		}));
	}

	private static Stream<Integer> getMissingQuestions() {
		Scanner sc = ignoreCheckedExceptions(() -> new Scanner(new FileInputStream(getQuestionIdsPath(QUESTION_IDS).toFile())));
		return StreamUtils.iteratorToStream(new Iterator<Integer>() {
			@Override
			public boolean hasNext() { return sc.hasNextInt(); }

			@Override
			public Integer next() { return sc.nextInt(); }
		});
	}

	private static void updateRecords(Question q, List<Comment> comments) {
		WordCountFactory wordCountFactory = new WordCountFactory();
		
		new QuestionFactory().persist(q);
		getWordCounts(q).forEach(wordCountFactory::persist);
		
		CommentFactory commentFactory = new CommentFactory();
		comments.forEach(commentFactory::persist);
		comments.stream().flatMap(x -> getWordCounts(x).stream()).forEach(wordCountFactory::persist);
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

	private static void backupQuestionIds() throws IOException {
		Files.copy(getQuestionIdsPath(QUESTION_IDS),getQuestionIdsPath(QUESTION_IDS_BACKUP),REPLACE_EXISTING);
	}

	private static File discoverNewQuestions() throws IOException {
		int           idMin       = (int) DBManager.newLongId(QUESTIONS);
		List<Integer> questionIds = reverse(IntStream.range(idMin,ID_MAX).filter(i -> exists(BASE_URL + i)).boxed().collect(Collectors.toList()));
		return writeToFile(questionIds);
	}
	
	private static File writeToFile(List<Integer> questionIds) throws IOException {
		Path path = getQuestionIdsPath(QUESTION_IDS);
		BufferedWriter bw = Files.newBufferedWriter(path);
		for (Integer id : questionIds) bw.write(id + "\n");
		bw.close();
		return path.toFile();
	}
	
	private static Path getQuestionIdsPath(String path) {
		return getPathRelativeToClass(IndexWords.class,path);
	}
}
