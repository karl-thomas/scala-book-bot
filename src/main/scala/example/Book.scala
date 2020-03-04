package example

import io.circe._, io.circe.parser._
import scala.util.{Try, Success, Failure}

case class Book(isbn: String)
object Book {
  def fromVolume(vol: Volume): Either[TransformError, Book] = {
    Try(vol.volumeInfo.industryIdentifiers.head.identifier) match {
      case Success(value) => Right(Book(value))
      case Failure(_) => Left(TransformError("No ISBN in response"))
    }
  }
}