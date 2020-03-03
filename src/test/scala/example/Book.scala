package example

import org.scalatest._
import io.circe._, io.circe.parser._

import example.Book

class BookSpec extends FunSpec with Matchers {
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

  describe("Book") {
    it ("can give you the isbn of the google specific Json object passed to it") {
      val obj = parse(jsonString).getOrElse(Json.Null)
      val book = Book(obj)

      assert(book.isbn contains isbn10)
    }

    it ("returns an exception when the isbn is not found") {
      val obj = Json.fromFields(List("a" -> Json.fromString("b")))
      val book = Book(obj)
      assert(book.isbn.isLeft)
    }
  }
}

