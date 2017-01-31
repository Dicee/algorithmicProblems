package miscellaneous.autoCompletion.guifx

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control.{Button, TextField}
import javafx.scene.input.InputEvent
import javafx.scene.layout.GridPane
import javafx.stage.{Screen, Stage}

import miscellaneous.autoCompletion.{ParrotAutoCompleter, TolerantAutoCompleter}

class AutoCompleterFX extends Application {
  object Constants {
    val Width  = 500
    val Height = 80

    val NumSuggestions = 5
  }

  import Constants._

  private val suggestions: Array[Button] = Array.ofDim(NumSuggestions)
  private val userInput  : TextField     = new TextField

  private val autoCompleter = new ParrotAutoCompleter with TolerantAutoCompleter { override def MaxRetries: Int = 5 }

  override def start(primaryStage: Stage): Unit = {
    val visualBounds = Screen.getPrimary.getVisualBounds

    val mainPane = new GridPane()
    val scene    = new Scene(mainPane, 0, 0)
    primaryStage.setScene(scene)

    initializeComponents(mainPane)

    primaryStage.setX((visualBounds.getWidth  - Width ) / 2)
    primaryStage.setY((visualBounds.getHeight - Height) / 2)
    primaryStage.setHeight(Height)
    primaryStage.setWidth (Width )
    primaryStage.setResizable(false)
    primaryStage.setTitle("Auto-completer")
    primaryStage.show()
  }

  private def initializeComponents(mainPane: GridPane) = {
    mainPane.addRow(0, userInput)
    userInput.setOnKeyReleased(generateSuggestions)
    userInput.setOnMouseClicked(generateSuggestions)
    userInput.setOnAction((event: ActionEvent) => {
      autoCompleter.update(userInput.getText.split("\\s+"): _*)
      userInput.setText("")
      event.consume()
    })

    for (i <- 0 until NumSuggestions) suggestions(i) = createSuggestionButton()

    val pane = new GridPane()
    pane.addRow(0, suggestions: _*)
    mainPane.addRow(1, pane)
  }

  private def createSuggestionButton() = {
    val button = new Button()
    button.setPrefWidth(Width / NumSuggestions)
    button.setFocusTraversable(false)

    button.setOnAction((event: ActionEvent) => {
      val wordAtCaret = findWordAtCaret()
      val suggestion = event.getSource.asInstanceOf[Button].getText

      if (wordAtCaret.nonEmpty && suggestion.nonEmpty) {
        val sentence = wordAtCaret.sentence
        val prefix = sentence.substring(0, wordAtCaret.start)
        val suffix = sentence.substring(wordAtCaret.end) + (if (wordAtCaret.end == sentence.length) " " else "")

        userInput.setText(prefix + suggestion + suffix)
        userInput.positionCaret(wordAtCaret.start + suggestion.length)
        clearSuggestions()
      }
      event.consume()
    })

    button
  }

  private def generateSuggestions(event: InputEvent): Unit = {
    clearSuggestions()
    val wordAtCaret = findWordAtCaret()
    if (wordAtCaret.nonEmpty) {
      val topSuggestions = autoCompleter.topSuggestions(wordAtCaret.value, NumSuggestions)
      topSuggestions.zipWithIndex.foreach { case (suggestion, i) => suggestions(i).setText(suggestion) }
    }
    event.consume()
  }

  private class WordAtCaret(val start: Int, val end: Int, val sentence: String)  {
    def isEmpty : Boolean = start == end
    def nonEmpty: Boolean = !isEmpty
    def value   : String  = sentence.substring(start, end)
  }

  private def findWordAtCaret(): WordAtCaret = {
    val charAtCaretIndex = userInput.getCaretPosition
    val sentence = userInput.getText
    var (start, end) = (charAtCaretIndex, charAtCaretIndex)

    while (start > 0               && !sentence(start - 1).isWhitespace) start -= 1
    while (end   < sentence.length && !sentence(end      ).isWhitespace) end   += 1

    new WordAtCaret(start, end, sentence)
  }

  private def clearSuggestions(): Unit = {
    suggestions.foreach(_.setText(""))
  }
}

object Main {
  def main(args: Array[String]): Unit = Application.launch(classOf[AutoCompleterFX], args:_*)
}
