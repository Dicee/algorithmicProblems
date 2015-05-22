package miscellaneous

import scala.collection.mutable.ArrayStack
import scala.io.Source
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Tag
import java.io.PrintWriter
import java.io.File
import org.jsoup.parser.Parser
import scala.collection.JavaConversions

object md2html extends App {
    def checkExtension(path: String) = path.endsWith(".md") || path.endsWith(".markdown")
    def toHTMLextension(path: String) = path.substring(0,path.lastIndexOf('.')) + ".html"
  
    val doc = {
        val doc = new Document("")
        val root = doc.appendElement("html")
        root.appendElement("head")
        root.appendElement("body")
        doc
    }
   
    val path = "/home/local/ANT/courtino/logbook.md"//args(0)
    if (checkExtension(path)) {
        val htmlNodes = Source.fromFile(path).getLines.map(_.trim).toList.map(HTMLNode.parse)
//        println(htmlNodes.map(_.getClass.getName.dropWhile(_ != '$').drop(1)).mkString("\n"))
        val htmlContent = htmlNodesToElements(optimizeBr(htmlNodes)).mkString("\n")
        val pw = new PrintWriter(new File(toHTMLextension(path)))
        pw.write(htmlContent)
        pw.close()      
    }
   
    def htmlNodesToElements(nodes: List[HTMLNode]): List[Element] = nodes match {
        case (li: Li) :: q =>
            val (items,rest) = groupItems(q)
            val ul           = create("ul")
            (li.elt :: items).foreach(ul.appendChild)
            ul :: htmlNodesToElements(rest)
           
        case t :: q => t.elt :: htmlNodesToElements(q)
        case Nil    => Nil
    }
   
    def optimizeBr(nodes: List[HTMLNode]): List[HTMLNode] = nodes match {
        case (s1: Span) :: Br() :: (s2: Span) :: q => s1 :: Br() :: s2 :: optimizeBr(q)
        case (s1: Span) :: (s2: Span)         :: q => s1 :: Br() :: s2 :: optimizeBr(q)
        case Br()       :: node               :: q => node             :: optimizeBr(q)
        case node       :: Br()               :: q => node             :: optimizeBr(q)
        case t                                :: q => t                :: optimizeBr(q)
        case Nil                                   => Nil
    }
   
    def groupItems(nodes: List[HTMLNode]): (List[Element],List[HTMLNode]) = nodes match {
        case (li: Li) :: q => val res = groupItems(q); (li.elt :: res._1,res._2)
        case _            => (Nil,nodes)
    }
   
    def create(tagName: String) = doc.createElement(tagName)   

    private implicit class AugmentedString(s: String) {
    	def hasPrefix   (prefix : Char  ) = s(0) == prefix && (s.length == 1 || s(1) != prefix)   
    	def trim        (margin : Int   ) = s.drop(margin).dropRight(margin)
    	def wrapBy   (wrapper: String) = wrapper + s + wrapper
    	def wrapByTag   (tagName: String) = s"<$tagName>$s</$tagName>"
    }
    
    sealed abstract class HTMLNode(val elt: Element) {
        def appendHTML(htmlContent: String) = 
            JavaConversions.asScalaIterator(Jsoup.parseBodyFragment(htmlContent).body.childNodes.iterator).toList.foreach(elt.appendChild(_))
    }
    
    final object HTMLNode {
        val MAX_TITLE_LEVEL = 6
        
        def         parse          (s: String): HTMLNode   = if (s.isEmpty    ) Br() else parseNonEmpty  (s)
        private def parseNonEmpty  (s: String): HTMLNode = /*if (isCode(s)) parseCode*/if (isListItem(s)) parseListItem(s)  else tryTitle       (s)
        private def tryTitle       (s: String): HTMLNode   = if (isTitle   (s)) parseTitle   (s)  else parseParagraph(s)
               
        private def parseListItem(s: String) = Li(parseStyledText(s.substring(1).trim))
       
        private def parseTitle(s: String) = {
            val suffix = s.takeWhile(_ == '#')
            val tagName = "h%s".format(Math.min(MAX_TITLE_LEVEL,suffix.length))
            H(parseStyledText(s.dropWhile(_ == '#').trim), Math.min(MAX_TITLE_LEVEL,suffix.length))
        }
       
        private def parseStyledText(s: String)  = s
            .replaceAll("\\s+" .wrapBy("(\\*){2}"),""                   )
            .replaceAll("\\s+" .wrapBy("\\*"     ),""                   )
            .replaceAll("\\s+" .wrapBy("~~"      ),""                   )
            .replaceAll("(.*?)".wrapBy("(\\*){2}"),"<b>$2</b>"          )
            .replaceAll("(.*?)".wrapBy("\\*"     ),"<i>$1</i>"          )
            .replaceAll("(.*?)".wrapBy("~~"      ),"<strike>$1</strike>")
        
        private def parseParagraph(s: String) = P(parseStyledText(s))
       
        private def isTitle(s: String) = s(0) == '#'
        private def isListItem(s: String) = s.hasPrefix('-')
        private def isCode(s: String) = s.startsWith("```")
    }

    final case class Br() extends HTMLNode(create("br"))
    final case class Li(text: String) extends HTMLNode(create("li")) { appendHTML(text) }
    final case class Span(text: String) extends HTMLNode(create("span")) { appendHTML(text) }
    final case class H(title: String, level: Int) extends HTMLNode(create("h" + level)) { appendHTML(title) }
    final case class P(text: String) extends HTMLNode(create("p")) { appendHTML(text) }
}