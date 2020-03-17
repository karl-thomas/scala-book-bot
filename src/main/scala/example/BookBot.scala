package example

import scalaj.http._
import io.circe._, io.circe.parser._
import example.models.errors.{Error, HttpError, TransformError}
import Error.ErrorOr
import example.models.{GoogleResponse, Volume, Book}

object BookBot {
  type TitleAndAuthor = (String, String)

  def findLinkFrom = getTitleAndAuthor _ andThen getBook andThen getLink
  
  def getLink(jsonOrNot: ErrorOr[String]): ErrorOr[String] =
    jsonOrNot.right
      .flatMap(json => parseJson(json)
        .flatMap(takeFirstBook)
        .flatMap(Book.apply)
        .map(_.linkToGoodreads)
      )
      
  def getTitleAndAuthor(arg: Array[String]): TitleAndAuthor = (arg(0), arg(1))

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
