package example

import scalaj.http._
import io.circe._, io.circe.parser._
import example.models.errors.{Error, HttpError, TransformError}

object BookBot extends App {
  getISBN("Harry Potter") match {
    case Left(error) => Console.print(error.getMessage)
    case Right(value) => Console.print(value)
  }

  def getISBN(title: String): Either[Error, String] = {
    getBook(title)
      .flatMap(parseJson)
      .flatMap(takeFirstBook)
      .flatMap(Book.fromVolume)
      .map(_.isbn)
  }

  def parseJson(json: String): Either[Error, GoogleResponse] = 
    decode[GoogleResponse](json) match {
      case Left(_: io.circe.Error) => Left(TransformError("Could not parse json"))
      case Right(value: GoogleResponse) => Right(value)
    }

  def takeFirstBook(response: GoogleResponse): Either[TransformError, Volume] =
    response.items.take(1) match {
      case List() => Left(TransformError("No books found in search results"))
      case List(volume) => Right(volume)
    }

  def getBook(title: String): Either[HttpError, String] = {
    val response: HttpResponse[String] = Http("https://www.googleapis.com/books/v1/volumes")
      .param("key", sys.env.getOrElse("GOOGLE_API_KEY", ""))
      .param("q", s"intitle:${title}")
      .asString

    response.code match {
      case 200 => Right(response.body)
      case code => Left(HttpError(s"Search request failed with a $code error code"))
    }
  }
}

object Console {
  def print(message: Any) = {
    println(message)
  }
}
