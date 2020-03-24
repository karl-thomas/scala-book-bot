package bookbot

import org.scalatest._
import io.circe._, io.circe.parser._, io.circe.generic.auto._, io.circe.syntax._
import models._
import bookbot.models.BotCommand.PingBack
import bookbot.models.BotCommand.Default

class BotCommandSpec extends FunSpec with Matchers {
  describe("BotCommand") {
    it("should create a PingBack if the first word is 'ping'") {
      BotCommand.fromRawMessage("ping the pings!").shouldBe(PingBack)
    }

    it("should return Defualt if no command is found") {
      BotCommand.fromRawMessage("Whoops! All! Expletives!").shouldBe(Default)
    }
  }
}