package bookbot

import org.scalatest._
import io.circe._, io.circe.parser._, io.circe.generic.auto._, io.circe.syntax._
import models._

class BookSpec extends FunSpec {
  val isbn10: String = "0545790352"
  val isbn13: String = "0545790352123"
  val industryIdentifiers = List(
    IndustryIdentifier("ISBN_10", isbn10),
    IndustryIdentifier("ISBN_13", isbn13)
  )
  val volume = Volume(VolumeInfo(Some(industryIdentifiers)))

  it("should return a new instance of a book given a volume") {
    assert(Book(volume) contains Book(isbn10))
  }

  it("should result in a left if the Volume does not return a identifier node") {
    val emptyVolume = Volume(VolumeInfo(Some(List())))
    assert(Book(emptyVolume).isLeft)
  }

  it("should give you a link to goodreads") {
    assert(Book(isbn10).linkToGoodreads equals s"https://www.goodreads.com/book/isbn/$isbn10")
  }
}
