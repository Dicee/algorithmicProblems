package miscellaneous.skiller.indexation;

import static miscellaneous.skiller.indexation.model.Table.USERS;
import miscellaneous.skiller.indexation.entities.User;
import miscellaneous.skiller.indexation.factories.UserFactory;
import miscellaneous.skiller.indexation.model.DBManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class IndexUsers extends AbstractIndexer {
	private static final String BASE_URL = "http://skiller.fr/profile/user/";
	
	public static void main(String[] args) {
		UserFactory userFactory = new UserFactory();
		boolean exists = true;
		for (int id=(int) DBManager.newLongId(USERS) ; exists ; id++) {
			try {
				Document doc = Jsoup.connect(BASE_URL + id).get();
				String login = doc.getElementsByTag("title").first().text();
				login        = login.substring(0,login.indexOf(" "));
				userFactory.persist(new User(login,id));
			} catch (Exception e) { 
				exists = false;
			}
		}
	}
}
