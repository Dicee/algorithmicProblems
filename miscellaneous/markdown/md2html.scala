package miscellaneous.markdown

import java.io.File
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import miscellaneous.utils.files.FileUtils

object md2html extends App {
//	interesting themes : base16-dark.css, eclipse.css, mbo.css, monokai.css, pastel-on-dark.css
	private val DEFAULT_THEME = Themes.AMAZON_LIGHT
		
	try {
		if (args.length == 0) exitWithError("missing input file name")

        val path = FileUtils.toCanonicalPath(args(0))
	    if (!checkExtension(path)) exitWithError("the input file should have .md or .mardown extension")
	    
	    val options  = DEFAULT_THEME.toMap ++ parseOptions(args.toList.drop(1).map(_.trim)) 
	    val theme    = Theme(options("name"),options("editorTheme"),options("logo"),options("style"))
	    System.setProperty("editorTheme",theme.editorTheme)
	    
		val doc      = MarkdownParser.parse(new File(path))
	    val htmlFile = new File(toHTMLextension(path))
	    val pw       = new PrintWriter(htmlFile)
	    pw.write(doc.html)
	    pw.close()     
	    
	    val destPath = new File(path).getAbsoluteFile.getParent + File.separator
	    copyFiles(theme.logo,destPath + "logo.png" )
	    copyFiles(theme.css ,destPath + "style.css")
	} catch {
		case t: Throwable => exitWithError(t.getMessage)
	}
	
	private type Options = Map[String,String]
	
	private def checkExtension (path: String)                = path.endsWith(".md") || path.endsWith(".markdown")
	private def toHTMLextension(path: String)                = path.substring(0,path.lastIndexOf('.')) + ".html"
	private def copyFiles(srcPath: String, destPath: String) = Files.copy(Paths.get(srcPath),Paths.get(destPath),StandardCopyOption.REPLACE_EXISTING)	

	private def parseOptions   (args: List[String]): Options = args match {
		case key :: value :: q if key.startsWith("--") => parseOptions(q) ++ parseOption(key.drop(2),value)
		case key :: value :: q                         => exitWithError("syntax error in the command")
		case key :: Nil        if isValidOption(key)   => exitWithError("option " + key.drop(2) + " has no assigned value")
		case key :: Nil                                => exitWithError("unexpected token " + key)
		case Nil                                       => Map()
	}
	
	private lazy val VALID_OPTIONS                      = Set("theme", "editorTheme", "logo", "style")
	private def isValidOption(s: String)                = { s.startsWith("--") && VALID_OPTIONS.contains(s.drop(2)) }
	private def parseOption(key: String, value: String) = {
		if (!VALID_OPTIONS.contains(key)) exitWithError(s"$key is not a valid option")	
		if (key == "theme") Themes(value).toMap else Map(key.drop(2) -> value)
	}
	
	private def exitWithError(msg: String)              = { println(s"Error: $msg"); printUsage; exit }
	private def exit                                    = { System.exit(-1); ??? }
	private def printUsage                              = {
		println("SYNTAX: md2html <input> [options...]")
		println("OPTIONS: \n\t--theme <theme>\n\t--logo <path>\n\t--editorTheme <editorTheme>\n\t--style <path>")
	}
}