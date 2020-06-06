package miscellaneous.markdown

import com.dici.check.Check
import com.dici.files.FileUtils

import scala.collection.mutable.LinkedHashSet

class Document(private var _theme: Theme=Themes.AMAZON_LIGHT) {
	private val doc = {
        val doc  = new org.jsoup.nodes.Document("")
        val root = doc.appendElement("html")
        root.appendElement("head").appendElement("meta").attr("charset","utf-8")
        root.appendElement("body")
        doc.body.appendElement("div").attr("class", "container")
        doc
    }
	
	private val includes  = new LinkedHashSet[String]
	
	def theme                        = _theme
	def appendChild                  = doc.body.child(0).appendChild(_)
	def theme_=(newTheme: Theme)     = { Check.notNull(newTheme); _theme = newTheme }
	def includeJsOrCss(seq: String*) = includes ++= seq
	def includeJs(s: String)         = includes += s"""<script src="$s"></script>"""
	def includeCss(s: String)        = includes += s"""<link rel="stylesheet" href="$s">"""
    def includeRawCss(s: String)     = includes += s"""<style type="text/css">\n$s\n</style>"""
	def create(tagName: String)      = doc.createElement(tagName) 
	def html                         = { includes.foreach(doc.head.append(_)); "<!doctype html>\n" + doc.html }
}

final case class Theme(name: String, editorTheme: String, logo: String, css: String) {
	def toMap = Map("name" -> name, "editorTheme" -> editorTheme, "logo" -> logo, "style" -> css)
}
final object Themes {
	val AMAZON_LIGHT = Theme("amazon-light", "ambiance", resource("resources/Amazon/light/logo.png"), resource("resources/Amazon/light/style.css"))
	val AMAZON_DARK  = Theme("amazon-dark" , "twilight", resource("resources/Amazon/dark/logo.png" ), resource("resources/Amazon/dark/style.css" ))
	val APPLE        = Theme("apple"       , "twilight", resource("resources/Apple/logo.png"       ), resource("resources/Apple/style.css"       ))
	
	private val THEMES      = List(AMAZON_LIGHT, AMAZON_DARK, APPLE).map(theme => (theme.name,theme)).toMap
	def apply(name: String) = THEMES.getOrElse(name,throw new IllegalArgumentException(s"Unknown theme $name"))
	private def resource(path: String) = FileUtils.getResourceAbsolutePath(path,Themes.getClass)
}