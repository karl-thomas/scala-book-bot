package example

import scalaj.http._
import io.circe._, io.circe.parser._
import example.models.errors.{Error, HttpError, TransformError}
import example.models._

object BookBot extends App {

  run(args) match {
    case Left(error) => Console.print(error.getMessage)
    case Right(value) => Console.print(value)
  }

  def run = (getTitleAndAuthor _) andThen (getLink _ tupled)

  def getTitleAndAuthor(arg: Array[String]): (String, String) = (arg(0), arg(1))
  
  def getLink(title: String = "", author: String = ""): Either[Error, String] =
    getBook(title, author)
      .flatMap(parseJson)
      .flatMap(takeFirstBook)
      .flatMap(Book.apply)
      .map(_.linkToGoodreads)
      
  def getBook = BookService.get(Http.apply) _
  
  def parseJson(json: String): Either[Error, GoogleResponse] =
    decode[GoogleResponse](json)
      .left.map(_ => TransformError("Could not parse json"))

  def takeFirstBook(response: GoogleResponse): Either[TransformError, Volume] =
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
