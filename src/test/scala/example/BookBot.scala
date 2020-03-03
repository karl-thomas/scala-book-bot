package example

import org.scalatest._
import io.circe._, io.circe.parser._, io.circe.generic.auto._, io.circe.syntax._

class BookBotSpec extends FunSpec with Matchers {
  val isbn10: String = "0545790352"
  val isbn13: String = "0545790352123"
    val industryIdentifiers = List(
    IndustryIdentifier("ISBN_10", isbn10),
    IndustryIdentifier("ISBN_13", isbn13)
  )
  val book = Map(
    "volumeInfo" -> VolumeInfo("Harry Potter and the Sorcerer's Stone", List("J. K. Rowling"), industryIdentifiers)
  )

  val json = book.asJson

  describe("BookBot") {
    describe("getBook") {
      describe("when the inner request returns a 200") {
        it("returns the response body") {
          assert(BookBot.getBook("harry potter").getOrElse("") contains "Harry")
        }
      }

      describe("when the request fails") {
        // need to figure out http mocking
        ignore("returns an HttpFailure") {

        }
      }
    }


    describe("parseJson") {
      it("returns a map of the json string pass to it") {
        assert(BookBot.parseJson(json.toString).isInstanceOf[Json])
      }
    }
  }
}


