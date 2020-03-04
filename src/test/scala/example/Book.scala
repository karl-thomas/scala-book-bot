package example

import org.scalatest._
import io.circe._, io.circe.parser._, io.circe.generic.auto._, io.circe.syntax._

import example.Book

class BookSpec extends FunSpec {
  val isbn10: String = "0545790352"
  val isbn13: String = "0545790352123"
    val industryIdentifiers = List(
    IndustryIdentifier("ISBN_10", isbn10),
    IndustryIdentifier("ISBN_13", isbn13)
  )
  val book = Volume(VolumeInfo("Harry Potter and the Sorcerer's Stone", List("J. K. Rowling"), industryIdentifiers))

  describe("fromVolume") {
    it("should return a new instance of a book given a volume") {
      assert(Book.fromVolume(book) contains Book(isbn10))
    }

    it("should handle if json object does not container identifier node") {
      val empty = Volume(VolumeInfo("Harry Potter and the Sorcerer's Stone", List("J. K. Rowling"), List()))
      assert(Book.fromVolume(empty).isLeft)
    }
  }
}


