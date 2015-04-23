import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class RankingPrinter {
	public static final String ROOT       = "https://app.lestalentsdunumerique.fr";
	public static final String HOME_PAGE  = ROOT + "/concours/list";
	
	public static void main(String[] args) throws IOException { 
		Set<String> candidatesUrl = getCandidatesUrl();
		System.out.println("Found " + candidatesUrl.size() + " candidates");
		
		List<Candidate> ranking = getRanking(candidatesUrl);
		printRanking(ranking);
	}
	
	public static final Set<String> getCandidatesUrl() throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(HOME_PAGE).openStream()))) {
			Set<String> res        = new HashSet<>();
			Pattern  urlPattern = Pattern.compile("/concours/show/(\\d+)(/0)?");
			
			for (String line = br.readLine() ; line != null ; line = br.readLine()) {
				Matcher matcher = urlPattern.matcher(line);
				while (matcher.find()) res.add(ROOT + matcher.group());
			}
			return res;
		} 
	}
	
	private static List<Candidate> getRanking(Set<String> candidatesUrl) {
		return candidatesUrl.stream()
			.map(Main::parseCandidatePageContent)
			.sorted(Comparator.reverseOrder())
			.collect(Collectors.toList());
	}
	
	private static Candidate parseCandidatePageContent(String candidatePage) {
		try {
			Document document  = Jsoup.connect(candidatePage).get();
			
			Element  titleNode = document.getElementById("projet-porteur");
			Element  votesNode = document.getElementById("votes");
			Element  viewsNode = votesNode.parent().parent().child(1).child(0);

			String   title     = titleNode.ownText();
			int      votes     = Integer.parseInt(votesNode.ownText());
			int      views     = Integer.parseInt(viewsNode.ownText());
			
			return new Candidate(title,votes,views);
		} catch (IOException e) {
			return Candidate.UNKOWN;
		}
	}
	
	private static void printRanking(List<Candidate> ranking) {
		int rank = 1;
		for (Candidate candidate : ranking) {
			String display = rank + " " + candidate;
			System.out.println(
				candidate.name.toLowerCase().contains("introvigne") ?
					">>>>" + display + "<<<<" :
					display
			);
			rank++;
		}
	}
	
	public static class Candidate implements Comparable<Candidate> {
		public static final Candidate UNKOWN = new Candidate("UNKNOWN",-1,-1);
		
		public final String name;
		public final int votes;
		public final int views;

		public Candidate(String name, int votes, int views) {
			this.name  = name;
			this.votes = votes;
			this.views = views;
		}

		@Override
		public int compareTo(Candidate that) { return Integer.compare(votes,that.votes); }

		@Override
		public String toString() {
			return "Candidate [name=" + name + ", votes=" + votes + ", views=" + views + "]";
		}
	}
}