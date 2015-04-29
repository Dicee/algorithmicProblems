import static java.util.stream.Collectors.groupingBy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class RankingPrinter {
    public static final String ROOT       = "https://app.lestalentsdunumerique.fr";
    public static final String HOME_PAGE  = ROOT + "/concours/list";
    
    public static void main(String[] args) throws IOException {
        Set<String>     candidatesUrl = getCandidatesUrl();
        List<Candidate> candidates    = getAllCandidates(candidatesUrl);
        printGlobalRanking     (getGlobalRanking     (candidates));
        printRankingPerCategory(getRankingPerCategory(candidates));
    }
   
    public static final Set<String> getCandidatesUrl() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(HOME_PAGE).openStream()))) {
            Set<String> res        = new HashSet<>();
            Pattern     urlPattern = Pattern.compile("/concours/show/(\\d+)(/0)?");
           
            for (String line = br.readLine() ; line != null ; line = br.readLine()) {
                Matcher matcher = urlPattern.matcher(line);
                while (matcher.find()) res.add(ROOT + matcher.group());
            }
            System.out.println("Found " + res.size() + " candidates");
            return res;
        }
    }
    
    private static List<Candidate> getAllCandidates(Set<String> candidatesUrl) {
        return candidatesUrl.stream().map(RankingPrinter::parseCandidatePageContent).collect(Collectors.toList());
    }
   
    private static List<Candidate> getGlobalRanking(List<Candidate> candidates) {
        return candidates.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }
   
    private static Candidate parseCandidatePageContent(String candidatePage) {
        try {
            Document document = Jsoup.connect(candidatePage).get();
            return extractCandidate(document);
        } catch (IOException e) {
            return Candidate.UNKOWN;
        }
    }

    private static Candidate extractCandidate(Document document) {
        Element             titleNode    = document.getElementById("projet-porteur");
        Element             categoryNode = document.getElementById("projet-titre");
        return extractCandidateInTypicalCase   (document,titleNode,categoryNode)
	    .orElse(extractCandidateInHackyCase(document,titleNode,categoryNode)
            .orElse(Candidate.UNKOWN));
    }
   
    private static Optional<Candidate> extractCandidateInTypicalCase(Document document, Element titleNode, Element categoryNode) {
        Element votesNode = document.getElementById("votes");
       
        if (votesNode == null) return Optional.empty();
           
        Element viewsNode    = votesNode.parent().parent().child(1).child(0);
        
        String  category     = categoryNode.child(0).text();
        String  title        = titleNode.ownText();
        int     votes        = Integer.parseInt(votesNode.ownText());
        int     views        = Integer.parseInt(viewsNode.ownText());
           
        return Optional.of(new Candidate(category,title,votes,views));
    }

    // Apparently, the developers of the website think that a good way to hide the number of votes and views is to
    // comment them in the rendered HTML code. Let's prove them wrong.
    private static Optional<Candidate> extractCandidateInHackyCase(Document document, Element titleNode, Element categoryNode) {
        Element  parentDiv     = document.select("div.col-sm-8.nopadding").first();
        String   commentedHTML = ((Comment) parentDiv.childNode(1)).getData();
        Document commentDoc    = Jsoup.parse(commentedHTML);
        return extractCandidateInTypicalCase(commentDoc,titleNode,categoryNode);
    }
   
    private static void printGlobalRanking(List<Candidate> ranking) { printRanking(ranking,0); }

    private static Map<String,List<Candidate>> getRankingPerCategory(List<Candidate> candidates) {
        return CollectionUtils.mapValues(
            candidates.stream().collect(groupingBy((Candidate x) -> x.category)),
            list -> list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
    }
    
    private static void printRanking(List<Candidate> ranking, int offset) {
        StringBuilder sb = new StringBuilder();
        for (int i=0 ; i<offset ; i++) sb.append(" ");

        String margin = sb.toString();
        int    rank   = 1;
        for (Candidate candidate : ranking) {
            String display = (rank++) + " " + candidate;
            System.out.println(margin + (candidate.name.toLowerCase().contains("introvigne") ? ">>>> " + display + " <<<<" : display));
        }
    }
    
    private static void printRankingPerCategory(Map<String,List<Candidate>> rankingPerCategory) {
        rankingPerCategory.entrySet().stream().forEach(entry -> {
        	System.out.println("\nCategory : " + entry.getKey() + "\n");
        	printRanking(entry.getValue(),4);
        });
    }
   
    public static class Candidate implements Comparable<Candidate> {
        public static final Candidate UNKOWN = new Candidate("UNKNOWN","UNKNOWN",-1,-1);
       
        public final String category;
        public final String name;
        public final int votes;
        public final int views;

        public Candidate(String category, String name, int votes, int views) {
            this.category = category;
            this.name     = name;
            this.votes    = votes;
            this.views    = views;
        }

        @Override
        public int compareTo(Candidate that) { return Integer.compare(votes,that.votes); }

        @Override
        public String toString() { return "Candidate [category=" + category + ", name=" + name + ", votes=" + votes + ", views=" + views + "]"; }
    }
}
