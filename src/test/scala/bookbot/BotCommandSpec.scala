package bookbot

import org.scalatest._
import io.circe._, io.circe.parser._, io.circe.generic.auto._, io.circe.syntax._
import models._
import bookbot.models.BotCommand.PingBack
import bookbot.models.BotCommand.Default
import bookbot.models.BotCommand.FindBook

class BotCommandSpec extends FunSpec with Matchers {
  describe("BotCommand") {
    it("should create a PingBack if the first word is the command to pingpack") {
      BotCommand("ping the pings!").shouldBe(PingBack)
    }

    describe("when the first word is the command to find a book") { 
      it("should return a FindBook with a valid TitleAndAutor if search is valid") {
        val title = "The Book"
        val author = "A Person"
        BotCommand(s"find $title by $author") shouldBe(FindBook(s"$title by $author"))
      }
    }

    it("should return Default if no command is found") {
      BotCommand("Whoops! All! Expletives!").shouldBe(Default)
    }
  }
}