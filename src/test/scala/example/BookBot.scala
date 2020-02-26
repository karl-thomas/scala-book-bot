package example

import org.scalatest._

class BookBotSpec extends FunSpec with Matchers {
  describe("BookBot") {
    describe("getBook") {
      it("gets a response from the api") {
        assert(BookBot.getBook("harry potter").isInstanceOf[String])
      }
    }
  }
}
