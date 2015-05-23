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
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.CopyOption
import java.nio.file.StandardCopyOption

object md2html extends App {
	private val CSS        = "resources/style.css"
	private val LIGHT_ICON = "resources/Amazon-light.png"
	private val DARK_ICON  = "resources/Amazon-dark.png"
	
    def checkExtension(path: String) = path.endsWith(".md") || path.endsWith(".markdown")
    def toHTMLextension(path: String) = path.substring(0,path.lastIndexOf('.')) + ".html"
 
    val doc = {
        val doc  = new Document("")
        val root = doc.appendElement("html")
        root.appendElement("head")
        root.appendElement("body")
        doc.body.appendElement("div").attr("class", "container")
        doc
    }
  
	val cssIncludes = scala.collection.mutable.Set("""<link rel="stylesheet" href="style.css">""", """<script src="http://codemirror.net/lib/codemirror.js"></script>""",
			"""<link rel="stylesheet" href="http://codemirror.net/lib/codemirror.css">""", """<link rel="stylesheet" href="http://codemirror.net/theme/ambiance.css">""")
	
    val path = "test.md"//args(0)
    if (checkExtension(path)) {
        optimizeBr(HTMLNode.parse(Source.fromFile(path).getLines.map(_.trim).toList))
        	.map(_.elt)
        	.foreach(doc.body.child(0).appendChild(_))
        
        cssIncludes.foreach(doc.head.append(_))

//		doc.head.append("""<link rel="stylesheet" href="http://codemirror.net/theme/base16-dark.css">""")
//		doc.head.append("""<link rel="stylesheet" href="http://codemirror.net/theme/eclipse.css">""")
//		doc.head.append("""<link rel="stylesheet" href="http://codemirror.net/theme/mbo.css">""")
//		doc.head.append("""<link rel="stylesheet" href="http://codemirror.net/theme/monokai.css">""")
//		doc.head.append("""<link rel="stylesheet" href="http://codemirror.net/theme/pastel-on-dark.css">""")
        
        val cssFile  = new File(new File(path).getAbsoluteFile.getParent + File.separator + "style.css")
        val iconFile = new File(new File(path).getAbsoluteFile.getParent + File.separator + "icon.png")
        val htmlFile = new File(toHTMLextension(path))
        val pw       = new PrintWriter(htmlFile)
        pw.write(doc.html())
        pw.close()     
        
        Files.copy(md2html.getClass.getResourceAsStream(CSS), 
        		   Paths.get(cssFile.getCanonicalPath), 
        		   StandardCopyOption.REPLACE_EXISTING)
        Files.copy(md2html.getClass.getResourceAsStream(LIGHT_ICON), 
        		   Paths.get(iconFile.getCanonicalPath), 
        		   StandardCopyOption.REPLACE_EXISTING)
    }

    def optimizeBr(nodes: List[HTMLNode]): List[HTMLNode] = nodes match {
        case (s1: Span) :: Br() :: (s2: Span) :: q => s1 :: Br() :: s2 :: optimizeBr(q)
        case (s1: Span) :: (s2: Span)         :: q => s1 :: Br() :: s2 :: optimizeBr(q)
        case Br()       :: node               :: q => node             :: optimizeBr(q)
        case node       :: Br()               :: q => node             :: optimizeBr(q)
        case t                                :: q => t                :: optimizeBr(q)
        case Nil                                   => Nil
    }
  
    private def create(tagName: String) = doc.createElement(tagName)  

    private implicit class AugmentedString(s: String) {
        def hasPrefix   (prefix : Char  ) = s.length > 0 && s(0) == prefix && (s.length == 1 || s(1) != prefix)  
        def trim        (margin : Int   ) = s.drop(margin).dropRight(margin)
        def wrapBy   (wrapper: String) = wrapper + s + wrapper
        def wrapByTag   (tagName: String) = s"<$tagName>$s</$tagName>"
       
        def isTitle = s(0) == '#'
        def isListItem = s.hasPrefix('-')
        def isCode = s.startsWith("```")
    }
   
    sealed abstract class HTMLNode(val elt: Element) {
        def appendHTML(htmlContent: String) =
            JavaConversions.asScalaIterator(Jsoup.parseBodyFragment(htmlContent).body.childNodes.iterator).toList.foreach(elt.appendChild(_))
    }
   
    final object HTMLNode {
        val MAX_TITLE_LEVEL = 6
        
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
       
        private def parseSingleLine(s: String): HTMLNode = if (s.isEmpty   ) Br()             else parseNonEmpty (s)
        private def parseNonEmpty  (s: String): HTMLNode = if (s.isListItem) parseListItem(s) else tryTitle      (s)
        private def tryTitle       (s: String): HTMLNode = if (s.isTitle   ) parseTitle   (s) else parseParagraph(s)
              
        private def parseListItem(s: String) = Li(parseStyledText(s.substring(1).trim))
        private def parseTitle(s: String) = {
            val suffix = s.takeWhile(_ == '#')
            val tagName = "h%s".format(Math.min(MAX_TITLE_LEVEL,suffix.length))
            H(parseStyledText(s.dropWhile(_ == '#').trim), Math.min(MAX_TITLE_LEVEL,suffix.length))
        }
      
        private def parseParagraph(s: String) = P(parseStyledText(s))
        private def parseStyledText(s: String)  = s
            .replaceAll("\\s+" .wrapBy("(\\*){2}"),""                                       )
            .replaceAll("\\s+" .wrapBy("\\*"     ),""                                       )
            .replaceAll("\\s+" .wrapBy("~~"      ),""                                       )
            .replaceAll("(.*?)".wrapBy("(\\*){2}"),"<span class=\"important\">$2</span>"    )
            .replaceAll("(.*?)".wrapBy("\\*"     ),"<span class=\"emphasize\">$1</span>"    )
            .replaceAll("(.*?)".wrapBy("~~"      ),"<span class=\"strikethrough\">$1</span>")
    }

    final case class Br  (                                 ) extends HTMLNode(create("br"       ))
    final case class Li  (text : String                    ) extends HTMLNode(create("li"       )) { appendHTML(text )                                }
    final case class Span(text : String                    ) extends HTMLNode(create("span"     )) { appendHTML(text )                                }
    final case class H   (title: String, level: Int        ) extends HTMLNode(create("h" + level)) { appendHTML(title)                                }
    final case class P   (text : String                    ) extends HTMLNode(create("p"        )) { appendHTML(text )                                }
    final case class Ul  (items: List[Li]                  ) extends HTMLNode(create("ul"       )) { items.foreach(item => elt.appendChild(item.elt)) }
    final case class Code(lines: List[String], mime: String) extends HTMLNode(create("div"      )) {
    	appendHTML(Code.TEMPLATE
    			.replace("${code}",lines.mkString("\n"))
    			.replace("${theme}","ambiance")
    			.replace("${mime}",mime)
    			.replace("${id}",Code.updateId.toString))
     	cssIncludes += """<script src="http://codemirror.net/mode/%s"></script>""".format(Code.MIMES(mime))
    }
    
    final object Code {
    	private val TEMPLATE = Source.fromURL(Code.getClass.getResource("resources/code-template.html")).getLines.mkString("\n")
    	private val MIMES = Map(
    			"text/x-java"   -> "clike/clike.js", "text/x-c++src" -> "clike/clike.js", "text/x-csrc"     -> "clike/clike.js",
				"text/x-scala"  -> "clike/clike.js", "text/x-stex"   -> "stex/stex.js"  , "text/javascript" -> "javascript/javascript.js",
				"text/x-python" -> "python/python.js"
		);
    	
    	private val LANGUAGES = Map (
    			"java" -> "text/x-java", "cpp"        -> "text/x-c++src"  , "c++"     -> "text/x-c++src"  , "c" -> "text/x-csrc", "scala" -> "text/x-scala",
    			"tex"  -> "text/x-stex", "javascript" -> "text/javascript",  "python" -> "text/x-python"   
    	)
    	
    	
    	private var id = -1
    	def updateId = { id += 1; id }
    	
    	def getMime(mime: String) = LANGUAGES.getOrElse(mime, LANGUAGES("java"))
    }
}