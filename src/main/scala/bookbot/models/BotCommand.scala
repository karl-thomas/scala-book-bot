package bookbot.models

import bookbot.models.errors.Error
import bookbot.models.errors.Error._
import TitleAndAuthor._

sealed trait BotCommand
sealed trait ValidCommand extends BotCommand {
  def keyword: String
}

object BotCommand {
  case class FindBook(searchString: String) extends ValidCommand {
    val keyword = FindBook.keyword
  }
  object FindBook {
    lazy val keyword = "find"
  }
  case object PingBack extends ValidCommand {
    val keyword = "ping"
  }
  case class PrintError(error: Error) extends BotCommand
  case object Default extends BotCommand
  
  def apply(message: String): BotCommand = {
    val words = message.trim.split(" ", 2).toList
    words match {
      case PingBack.keyword :: _ => PingBack
      case FindBook.keyword :: searchString :: _ => FindBook(searchString)
      case _ => Default
    }
  }
}