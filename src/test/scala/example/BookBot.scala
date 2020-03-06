package example

import org.scalatest._
import io.circe._, io.circe.parser._, io.circe.generic.auto._, io.circe.syntax._

class BookBotSpec extends FunSpec with Matchers {
  val title = "Harry Potter and the Sorcerer's Stone"
  val isbn10 = "0545790352"
  val isbn13 = "0545790352123"
    val industryIdentifiers = List(
    IndustryIdentifier("ISBN_10", isbn10),
    IndustryIdentifier("ISBN_13", isbn13)
  )
  val book = Volume(VolumeInfo(Some(industryIdentifiers)))
  val bookJson = book.asJson
  val bookJsonString = bookJson.toString
  val googleResponse = GoogleResponse(List(book))
  val googleResponseJson = googleResponse.asJson
  val googleResponseString = googleResponseJson.toString
  

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

    describe("getISBN") {
      it ("returns the isbn for a book title") {
        val isbn = BookBot.getISBN(title)
        assert(isbn.getOrElse("") equals isbn10)
      }
    }

    describe("parseJson") {
      it("decodes a json string into a GoogleResponse instance") {
        val result = BookBot.parseJson(googleResponseString)

        result.map(response => assert(response.items.head equals book))
      }

      it("should handle if json object does not contain a Google response") {
        assert(BookBot.parseJson(bookJsonString).isLeft)
      }
    }

    describe("takeFirstBook") {
      it("should return the first book in a parsed api response") {
        assert(BookBot.takeFirstBook(googleResponse) contains book)
      }

      it("should handle if there is no first book") {
        val empty = GoogleResponse(List())
        assert(BookBot.takeFirstBook(empty).isLeft)
      }
    }
  }
}


