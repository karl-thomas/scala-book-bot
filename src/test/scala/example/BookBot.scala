package example

import org.scalatest._
import io.circe._, io.circe.parser._

class BookBotSpec extends FunSpec with Matchers {
  val isbn10: String = "0545790352"
  val isbn13: String = "0545790352123"
  val jsonString: String = raw"""
    {
      "volumeInfo": {
        "title": "Harry Potter and the Sorcerer's Stone",
        "authors": [
          "J. K. Rowling"
        ],
        "industryIdentifiers": [
          {
            "type": "ISBN_10",
            "identifier": "$isbn10"
          },
          {
            "type": "ISBN_13",
            "identifier": "$isbn13"
          }
        ]
      }
    }
  """

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
        assert(BookBot.parseJson(jsonString).isInstanceOf[Json])
      }
    }
  }
}


