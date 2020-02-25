package example

import org.scalatest._

class BookBotSpec extends FunSpec with Matchers {
  describe("BookBot") {
    it("says hello") {
      assert(BookBot.greeting === "hello")
    }
  }
}
