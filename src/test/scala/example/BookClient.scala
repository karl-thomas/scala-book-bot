package example

import org.scalatest._
import io.circe._, io.circe.parser._, io.circe.generic.auto._, io.circe.syntax._
import org.scalamock.scalatest.MockFactory
import models._
import example.BookService.HttpFunction
import scalaj.http.{Http,HttpRequest,HttpResponse}

class BookServiceSpec extends FunSpec with Matchers with MockFactory {
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
          val httpMock = mock[HttpFunction]
          val mockedRequest = mock[HttpRequest]
          val response = HttpResponse("A Fake Response!", 200, null)
          (mockedRequest.param _ ).expects("key", *).returning(mockedRequest)
          (mockedRequest.param _ ).expects("q", "harry potter+").returning(mockedRequest)
          (mockedRequest.asString _ ).expects().returning(response)
          
          (httpMock.apply _).expects("https://www.googleapis.com/books/v1/volumes").returning(mockedRequest)
          assert(BookService.get(httpMock)("harry potter").getOrElse("") contains "Fake Response!")
        }
      }

      describe("when the request fails") {
        it("with a 400 request code, it returns an HttpFailure") {
          val httpMock = mock[HttpFunction]
          val mockedRequest = mock[HttpRequest]
          val response = HttpResponse("A Fake Response!", 400, null)
          (mockedRequest.param _ ).expects("key", *).returning(mockedRequest)
          (mockedRequest.param _ ).expects("q", *).returning(mockedRequest)
          (mockedRequest.asString _ ).expects().returning(response)
          
          (httpMock.apply _).expects("https://www.googleapis.com/books/v1/volumes").returning(mockedRequest)
          assert(BookService.get(httpMock)("harry potter").isLeft equals true)
        }
      }
    }
  }
}


