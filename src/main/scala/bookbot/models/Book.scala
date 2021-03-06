package bookbot.models

import io.circe._, io.circe.parser._
import scala.util.{Try, Success, Failure}
import bookbot.models.errors.{TransformError}
import bookbot.models._

object GoodreadsBook {
  def link(isbn: String) = s"https://www.goodreads.com/book/isbn/$isbn"
}

case class Book(isbn: String) {
  def linkToGoodreads: String = GoodreadsBook.link(isbn)
}

object Book {
  def apply(vol: Volume): Either[TransformError, Book] =
    Some(vol)
      .map(_.volumeInfo)
      .flatMap(_.industryIdentifiers)
      .flatMap(_.lift(0))
      .map(_.identifier)
      .map(Book.apply)
      .toRight(TransformError("No ISBN in response"))
}