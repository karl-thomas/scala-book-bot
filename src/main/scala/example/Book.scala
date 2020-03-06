package example

import io.circe._, io.circe.parser._
import scala.util.{Try, Success, Failure}
import example.models.errors.{TransformError}

case class Book(isbn: String)
object Book {
  def fromVolume(vol: Volume): Either[TransformError, Book] =
    Some(vol)
      .map(_.volumeInfo)
      .map(_.industryIdentifiers)
      .flatMap(option => option.getOrElse(List()).lift(0))
      .map(_.identifier)
      .map(Book.apply)
      .toRight(TransformError("No ISBN in response"))
}