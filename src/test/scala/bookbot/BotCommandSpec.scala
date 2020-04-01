package bookbot

import org.scalatest._
import io.circe._, io.circe.parser._, io.circe.generic.auto._, io.circe.syntax._
import models._
import bookbot.models.BotCommand._

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

    it("should return a default if no command can be found") {
      BotCommand("Whoops! All! Expletives!").shouldBe(NoCommandFound)
    }
  }
}