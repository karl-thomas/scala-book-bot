package example

import scalaj.http._
import io.circe._, io.circe.parser._
import example.Book

case class HttpFailure(message: String)

object BookBot extends App {

  def parseJson(json: String): Json = {
    parse(json).getOrElse(Json.Null)
  }

  def getBook(title: String): Either[HttpFailure, String] = {
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
