package example

import io.circe._, io.circe.parser._

case class Book(json : Json) {
  def isbn: Decoder.Result[String] = {
    val cursor = json.hcursor
    cursor
      .downField("volumeInfo")
      .downField("industryIdentifiers")
      .downArray
      .get[String]("identifier")
  }
}