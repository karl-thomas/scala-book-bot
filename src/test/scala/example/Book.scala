package example

import org.scalatest._
import io.circe._, io.circe.parser._, io.circe.generic.auto._, io.circe.syntax._

import example.Book

class BookSpec extends FunSpec with Matchers {
  val isbn10: String = "0545790352"
  val isbn13: String = "0545790352123"
  val industryIdentifiers = List(
    IndustryIdentifier("ISBN_10", isbn10),
    IndustryIdentifier("ISBN_13", isbn13)
  )
  val book = Volume(
    VolumeInfo("Harry Potter and the Sorcerer's Stone", List("J. K. Rowling"), industryIdentifiers)
  )
  val json = book.asJson
  
  describe("Book") {
    it ("can give you the isbn of the google specific Json object passed to it") {
      val book = Book(json)
      assert(book.isbn contains isbn10)
    }

    it ("returns an exception when the isbn is not found") {
      val obj = Json.fromFields(List("a" -> Json.fromString("b")))
      val book = Book(obj)
      assert(book.isbn.isLeft)
    }
  }
}


