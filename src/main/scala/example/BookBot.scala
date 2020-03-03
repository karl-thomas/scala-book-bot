package example

import scalaj.http._
import io.circe._, io.circe.parser._
import example.Book

case class TransformError(message: String)
case class HttpError(message: String)

object BookBot extends App {

  def parseJson(json: String): Either[io.circe.Error, GoogleResponse]= {
    decode[GoogleResponse](json)
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

    val code = response.code
    response.code match {
      case 200 => Right(response.body)
      case _ => Left(HttpFailure(s"request failed with a $code error code"))
    }
  }
}
