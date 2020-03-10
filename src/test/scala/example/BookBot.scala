package example

import org.scalatest._
import io.circe._, io.circe.parser._, io.circe.generic.auto._, io.circe.syntax._
import org.scalamock.scalatest.MockFactory

import example.models._

class BookBotSpec extends FunSpec with Matchers with MockFactory {
  val title = "Harry Potter and the Sorcerer's Stone"
  val author = "J.K. Rowling"
  val isbn10 = "0545790352"
  val isbn13 = "0545790352123"
    val industryIdentifiers = List(
    IndustryIdentifier("ISBN_10", isbn10),
    IndustryIdentifier("ISBN_13", isbn13)
  )
  val volume = Volume(VolumeInfo(Some(industryIdentifiers)))
  val volumeJson = volume.asJson
  val volumeJsonString = volumeJson.toString
  val googleResponse = GoogleResponse(List(volume))
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

    describe("getTitleAndAuthor") {
      it("returns the title and author from an array of strings") {
        val args: Array[String] = Array(title, author)
        assert(BookBot.getTitleAndAuthor(args) equals (title, author))
      }
    }

    describe("getLink") {
      it ("returns the goodreads link for a book") {
        val link = BookBot.getLink(title)
        assert(link.getOrElse("") equals Book(isbn10).linkToGoodreads)
      }
    }

    describe("parseJson") {
      it("decodes a json string into a GoogleResponse instance") {
        val result = BookBot.parseJson(googleResponseString)

        result.map(response => assert(response.items.head equals volume))
      }

      it("should handle if json object does not contain a Google response") {
        assert(BookBot.parseJson(volumeJsonString).isLeft)
      }
    }

    describe("takeFirstBook") {
      it("should return the first book in a parsed api response") {
        assert(BookBot.takeFirstBook(googleResponse) contains volume)
      }

      it("should handle if there is no first book") {
        val empty = GoogleResponse(List())
        assert(BookBot.takeFirstBook(empty).isLeft)
      }
    }
  }
}


