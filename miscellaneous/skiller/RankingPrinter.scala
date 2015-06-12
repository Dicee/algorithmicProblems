import java.io.BufferedReader
import java.io.InputStreamReader
import scala.io.Source
import scala.util.matching.Regex
import java.io.IOException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.Comment

object Main {
    val ROOT      = "https://app.lestalentsdunumerique.fr"
    val HOME_PAGE = ROOT + "/concours/list"
    
    val candidatesUrl = {
        val urlPattern = new Regex("/concours/show/(\\d+)(/0)?")        
        val res        = Source.fromURL(HOME_PAGE).getLines.flatMap(urlPattern.findAllMatchIn(_).map(ROOT + _.toString).toSet).toList
        println("Found " + res.size + " candidates")
        res
    }
    
    def main(args: Array[String]) {
    	val candidates = candidatesUrl.map(parseCandidatePageContent)
        printGlobalRanking(candidates.sorted.reverse)
        printRankingPerCategory(candidates.groupBy(_.category).mapValues(_.sorted.reverse))
    }
    
    def parseCandidatePageContent(url: String) =
        try {
            extractCandidate(Jsoup.connect(url).get)
        } catch {
            case e: IOException => Candidate.UNKOWN
        }
        
    def extractCandidate(document: Document): Candidate = {
        val titleNode    = document.getElementById("projet-porteur")
        val categoryNode = document.getElementById("projet-titre")
        extractCandidateInTypicalCase  (document,titleNode,categoryNode) getOrElse(
            extractCandidateInHackyCase(document,titleNode,categoryNode).getOrElse(
            Candidate.UNKOWN))
    }
    
    def extractCandidateInTypicalCase(document: Document, titleNode: Element, categoryNode: Element) = {
        val votesNode = document.getElementById("votes")
        if (votesNode == null) None
        else {
        	val viewsNode = votesNode.parent.parent.child(1).child(0);
        	
        	val category  = categoryNode.child(0).text
        	val title     = titleNode.ownText
        	val votes     = votesNode.ownText.toInt
        	val views     = viewsNode.ownText.toInt
        	Some(Candidate(category,title,votes,views))
        }
    }
    
    // Apparently, the developers of the website think that a good way to hide the number of votes and views is to
    // comment them in the rendered HTML code. Let's prove them wrong.
    def extractCandidateInHackyCase(document: Document, titleNode: Element, categoryNode: Element) = {
        val parentDiv     = document.select("div.col-sm-8.nopadding").first
        val commentedHTML = parentDiv.childNode(1).asInstanceOf[Comment].getData
        val commentDoc    = Jsoup.parse(commentedHTML)
        extractCandidateInTypicalCase(commentDoc,titleNode,categoryNode)
    }
    
    def printGlobalRanking(ranking: List[Candidate]) = printRanking(ranking,0)
    
    def printRankingPerCategory(rankingPerCategory: Map[String,List[Candidate]]) = {
        rankingPerCategory.foreach { case (category,ranking) =>  
            println("\nCategory : " + category + "\n")
            printRanking(ranking,4)
        }
    }

    private def printRanking(ranking: List[Candidate], offset: Int) = {
        val margin = " " * offset
        var rank   = 1
        ranking.foreach { candidate =>
            val display = rank + " " + candidate
            println(margin + (if (candidate.name.toLowerCase.contains("introvigne")) ">>>> " + display + " <<<<" else display))
            rank += 1; 
        }
    }
}

case class Candidate(category: String, name: String, votes:Int, views: Int) extends Ordered[Candidate] {
    override def compare(that: Candidate) = Integer.compare(votes,that.votes)
    override def toString                 = "Candidate [category=" + category + ", name=" + name + ", votes=" + votes + ", views=" + views + "]"
}

object Candidate {
    val UNKOWN = Candidate("UNKNOWN","UNKNOWN",-1,-1)
}
