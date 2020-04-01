package bookbot

import scalaj.http._
import io.circe._, io.circe.parser._
import bookbot.models.errors.{Error, HttpError, TransformError}
import Error.ErrorOr
import bookbot.models.{GoogleResponse, Volume, Book}
import bookbot.models.TitleAndAuthor
import TitleAndAuthor._
import cats.syntax.either._
import cats.implicits._

object BookBot {
  def findLinkFrom(searchString: String) = TitleAndAuthor.from(searchString).flatMap(getBook andThen getLink)
  def getLink(jsonOrNot: ErrorOr[String]): ErrorOr[String] =
    jsonOrNot.right
      .flatMap(json => parseJson(json)
        .flatMap(takeFirstBook)
        .flatMap(Book.apply)
        .map(_.linkToGoodreads)
      )

  def getBook = BookService.get(Http.apply) _ tupled
  
  def parseJson(json: String): ErrorOr[GoogleResponse] =
    decode[GoogleResponse](json)
      .left.map(_ => TransformError("Could not parse json"))

  def takeFirstBook(response: GoogleResponse): ErrorOr[Volume] =
    response.items.take(1) match {
      case List() => Left(TransformError("No books found in search results"))
      case List(volume) => Right(volume)
    }
}

object Console {
  def print(message: Any) = {
    println(message)
  }
}
