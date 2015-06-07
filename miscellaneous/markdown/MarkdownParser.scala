package miscellaneous.markdown

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import scala.io.Source
import scala.collection.JavaConversions
import java.io.File
import scala.collection.mutable.LinkedHashSet
import miscellaneous.utils.check.Check

object MarkdownParser {
	val doc = new Document
	
	def parse(file: File, style: File) = {
		doc.includeCss("http://codemirror.net/theme/%s.css".format(System.getProperty("editorTheme")))
		doc.includeRawCss(Source.fromFile(style).getLines.mkString("\n"))
		doc.includeJsOrCss(
				"""<link rel="stylesheet" href="http://codemirror.net/lib/codemirror.css">""", //
				"""<script src="http://codemirror.net/lib/codemirror.js"></script>""")
				
		optimizeBr(HTMLNode.parse(Source.fromFile(file).getLines.toList))
        	.map(_.elt)
        	.foreach(doc.appendChild(_))
        doc.includeRawCss(".CodeMirror {\n\tborder: 1px solid #eee;\n\theight: auto;\n}")
        doc
	}
        	
    private def optimizeBr(nodes: List[HTMLNode]): List[HTMLNode] = nodes match {
        case (s1: Span) :: Br() :: (s2: Span) :: q => s1 :: Br() :: s2 :: optimizeBr(q)
        case (s1: Span) :: (s2: Span)         :: q => s1 :: Br() :: s2 :: optimizeBr(q)
        case Br()       :: node               :: q => node             :: optimizeBr(q)
        case node       :: Br()               :: q => node             :: optimizeBr(q)
        case t                                :: q => t                :: optimizeBr(q)
        case Nil                                   => Nil
    }
	
	private abstract class HTMLNode(val elt: Element) {
        def appendHTML(htmlContent: String) =
            JavaConversions.asScalaIterator(Jsoup.parseBodyFragment(htmlContent).body.childNodes.iterator).toList.foreach(elt.appendChild(_))
    }

	private object HTMLNode {
        val MAX_TITLE_LEVEL = 6
        
        private implicit class AugmentedString(s: String) {
        	val str = s.trim
        	
	        def hasPrefix (prefix : Char  ) = str.length > 0 && str(0) == prefix && (str.length == 1 || str(1) != prefix)  
	        def trim      (margin : Int   ) = str.drop(margin).dropRight(margin)
	        def wrapBy    (wrapper: String) = wrapper + str + wrapper
	        def wrapByTag(tagName: String)  = s"<$tagName>$str</$tagName>"
	       
	        def isTitle    = str(0) == '#'
	        def isListItem = str.hasPrefix('-')
	        def isCode     = str.startsWith("```")
	        def isBlank    = str.isEmpty
    	}
        
	    def parse(lines: List[String]): List[HTMLNode] = {
	        lines match {
	        	case t :: q if t.isCode =>
	                val language = t.drop(3).trim
	                val content  = q.takeWhile(!_.isCode)
	                Code(content, Code.getMime(language)) :: parse(q.drop(content.length + 1))
	                   
	            case t :: q if t.isListItem =>
	                val items = lines.takeWhile(s => !s.isEmpty && s.isListItem).map(parseListItem)
	                Ul(items) :: parse(lines.drop(items.length))
	                   
	            case t :: q => parseSingleLine(t) :: parse(q)
	            case Nil    => Nil
	        }
	    }
	    private def parseSingleLine(s: String): HTMLNode = if (s.isBlank   ) Br()             else parseNonEmpty (s)
	    private def parseNonEmpty  (s: String): HTMLNode = if (s.isListItem) parseListItem(s) else tryTitle      (s)
	    private def tryTitle       (s: String): HTMLNode = if (s.isTitle   ) parseTitle   (s) else parseParagraph(s)
	              
	    private def parseListItem(s: String) = Li(parseStyledText(s.substring(1).trim))
	    private def parseTitle(s: String) = {
	    	val suffix = s.takeWhile(_ == '#')
	    	val tagName = "h%s".format(Math.min(MAX_TITLE_LEVEL,suffix.length))
	        H(parseStyledText(s.dropWhile(_ == '#').trim), Math.min(MAX_TITLE_LEVEL,suffix.length))
	    }
	      
	    private def parseParagraph(s: String) = P(parseStyledText(s))
	    
	    private val EMAIL_REGEX = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$"
	    private val URL_REGEX   = "((https?:\\/\\/|www\\.)([\\w\\._-]+)\\.([a-z\\.]{2,6})|localhost)(:\\d+)?([\\/\\w\\-]*)*(\\?(([\\w-_]+=[\\w-_]*&)*[\\w-_]+=[\\w-_]*)?|\\/?)"
	    private def parseStyledText(s: String)  =  
	    	s.replaceAll("\\s+" .wrapBy("(\\*){2}"),""                                       )
	         .replaceAll("\\s+" .wrapBy("\\*"     ),""                                       )
	         .replaceAll("\\s+" .wrapBy("~~"      ),""                                       )
	         .replaceAll("\\s+" .wrapBy("`"       ),""                                       )
	         .replaceAll("(.*?)".wrapBy("`"       ),"<code class=\"inline-code\">$1</code>"  )
	         .replaceAll("(.*?)".wrapBy("(\\*){2}"),"<span class=\"important\">$2</span>"    )
	         .replaceAll("(.*?)".wrapBy("\\*"     ),"<span class=\"emphasize\">$1</span>"    )
	         .replaceAll("(.*?)".wrapBy("~~"      ),"<span class=\"strikethrough\">$1</span>")
	         .replaceAll(EMAIL_REGEX               ,"<a href=\"mailto:$0\">$0</a>"           )
	         .replaceAll(URL_REGEX                 ,"<a href=\"$0\">$0</a>"                  )
	}
	    
    private case class Br  (                                 ) extends HTMLNode(doc.create("br"       ))
    private case class Li  (text : String                    ) extends HTMLNode(doc.create("li"       )) { appendHTML(text )                                }
    private case class Span(text : String                    ) extends HTMLNode(doc.create("span"     )) { appendHTML(text )                                }
    private case class H   (title: String, level: Int        ) extends HTMLNode(doc.create("h" + level)) { appendHTML(title)                                }
    private case class P   (text : String                    ) extends HTMLNode(doc.create("p"        )) { appendHTML(text )                                }
    private case class Ul  (items: List[Li]                  ) extends HTMLNode(doc.create("ul"       )) { items.foreach(item => elt.appendChild(item.elt)) }
    private case class Code(lines: List[String], mime: String) extends HTMLNode(doc.create("div"      )) {
    	appendHTML(Code.TEMPLATE
    			.replace("${code}",lines.mkString("\n","\n","\n"))
    			.replace("${theme}",System.getProperty("editorTheme"))
    			.replace("${mime}",mime)
    			.replace("${id}",Code.updateId.toString))
    	doc.includeJs("http://codemirror.net/mode/%s".format(Code.MIMES(mime)))
    }
    private object Code {
    	private val TEMPLATE = Source.fromURL(Code.getClass.getResource("resources/code-template.html")).getLines.mkString("\n")
		private val MIMES = Map(
				"text/x-java"   -> "clike/clike.js", "text/x-c++src" -> "clike/clike.js", "text/x-csrc"     -> "clike/clike.js", //
				"text/x-scala"  -> "clike/clike.js", "text/x-stex"   -> "stex/stex.js"  , "text/javascript" -> "javascript/javascript.js", //
				"text/x-python" -> "python/python.js" //
		)
    	
    	private val LANGUAGES = Map (
			"java"  -> "text/x-java" , "cpp" -> "text/x-c++src", "c++"        -> "text/x-c++src"  , "c"       -> "text/x-csrc", //
			"scala" -> "text/x-scala", "tex" -> "text/x-stex"  , "javascript" -> "text/javascript",  "python" -> "text/x-python"  // 
		)
    			
		private var id = -1
		def updateId = { id += 1; id }
    	
    	def getMime(mime: String) = LANGUAGES.getOrElse(mime, LANGUAGES("java"))
    }
}