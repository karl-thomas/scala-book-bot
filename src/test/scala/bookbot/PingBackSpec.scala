package bookbot

import org.scalatest._
import io.circe._, io.circe.parser._, io.circe.generic.auto._, io.circe.syntax._
import models._

class PingBackSpec extends FunSpec with Matchers {
  describe("PingBack") {
    it("should return a 'response_type' of 'in_channel' when converted to json") {
      val pingback = PingBack("Hey!").asJson
      assert(pingback.toString contains "in_channel")
    }

    it("should contain the message it was intialized with") {
      val message = "Hey!"
      val pingback = PingBack(message).asJson
      assert(pingback.toString contains message)
    }
  }
}
