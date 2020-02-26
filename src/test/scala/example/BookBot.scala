package example

import org.scalatest._
import io.circe._, io.circe.parser._


class BookBotSpec extends FunSpec with Matchers {
  describe("BookBot") {
    describe("getBook") {
      it("gets a response from the api") {
        assert(BookBot.getBook("harry potter").isInstanceOf[String])
      }
    }

    describe("parseJson") {
      it("returns a map of the json string pass to it") {
        val jsonString = """{"a\":\"b\"}"""
        assert(BookBot.parseJson(jsonString).isInstanceOf[Json])
      }
    }
  }
}
