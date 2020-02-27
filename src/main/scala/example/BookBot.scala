package example

import scalaj.http._
import io.circe._, io.circe.parser._

object BookBot extends App {

  def parseJson(json: String): Json = {
    parse(json).getOrElse(Json.Null)
  }

  def getBook(title: String): String = {
    val response: HttpResponse[String] = Http("https://www.googleapis.com/books/v1/volumes")
      .param("key", sys.env("GOOGLE_API_KEY"))
      .param("q", s"intitle:${title}")
      .asString
    response.body
  }
}


object Book {
  def isbn(book: Book): Decoder.Result[String] = {
    val cursor = book.json.hcursor
    cursor
      .downField("volumeInfo")
      .downField("industryIdentifiers")
      .downArray
      .get[String]("identifier")
  }
}

case class Book(json: Json)
