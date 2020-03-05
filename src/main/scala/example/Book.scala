package example

import io.circe._, io.circe.parser._
import scala.util.{Try, Success, Failure}

case class Book(isbn: String)
object Book {
  def fromVolume(vol: Volume): Either[TransformError, Book] =
    Some(vol)
      .map(_.volumeInfo)
      .map(_.industryIdentifiers)
      .flatMap(_.lift(0))
      .map(_.identifier) match {
        case Some(value) => Right(Book(value))
        case None => Left(TransformError("No ISBN in response"))
      }
}