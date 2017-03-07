package view

import ast._
import checker.Issue.Issues
import checker.{ Error, Warning }
import model.{ FormModel, StyleModel }

import scalafx.application.JFXApp
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.{ HBox, TilePane, VBox }
import scalafx.scene.text.Text

class GUI(issues: Issues, formModel: FormModel, styleModel: StyleModel) extends JFXApp.PrimaryStage {
  private val displayBoxes = formModel.displayQuestions.map { question =>
    question.`type` match {
      case BooleanType => new BooleanQuestion(question).element
      case DateType => new DateQuestion(question).element
      case StringType => new StringQuestion(question).element
      case _: NumericType => new NumericQuestion(question).element
    }
  }
  private val issueBox = {
    val issueMessages = issues.map {
      case Warning(message) => new HBox(new Text(message))
      case Error(message) => new HBox(new Text(message))
    }
    new VBox {
      children = issueMessages
    }
  }

  title.value = "Beheer QL Form"
  width = 600
  height = 800
  scene = new Scene {
    content = new TilePane {
      hgap = 10
      vgap = 10
      padding = Insets(10)
      prefColumns = 1
      children = issueBox +: displayBoxes
    }
  }
}

object GUI extends JFXApp {
  def apply(issues: Issues, formModel: FormModel, styleModel: StyleModel) = new GUI(issues, formModel, styleModel)
}