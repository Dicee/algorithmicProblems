package miscellaneous.skiller.indexation;

import org.jsoup.Jsoup;

public class AbstractIndexer {
	public static boolean exists(String url) {
		try {
			Jsoup.connect(url).execute();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
