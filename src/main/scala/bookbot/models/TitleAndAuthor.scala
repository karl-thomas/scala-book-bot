package bookbot.models

import bookbot.models.errors.TransformError
import bookbot.models.errors.Error
import Error.ErrorOr

case class TitleAndAuthorParseError(message: String) extends Error {
  def getMessage = message
}

object TitleAndAuthor {
  type TitleAndAuthor = (String, String)

  def from(input: String): Either[Error, TitleAndAuthor] = {
    val words = input.split("by", 2).map(_.trim).toList

    words match {
      case allStrings if noWordsIn(allStrings) => Left(TitleAndAuthorParseError("No search terms provided"))
      case title :: author :: tail => Right(title, author)
      case searchTerm :: tail => Right(searchTerm, "")
      case _ => Left(TitleAndAuthorParseError("No search terms provided"))
    } 
  }

  private def noWordsIn(list: List[String]) = !list.exists(!_.isEmpty())
}