package bookbot.models

sealed trait BotCommand
sealed trait ValidCommand extends BotCommand {
  def keyword: String
}

object BotCommand {
  case class FindBook(message: String) extends ValidCommand {
    val keyword = FindBook.keyword
  }
  object FindBook {
    lazy val keyword = "find"
  }
  case object PingBack extends ValidCommand {
    val keyword = "ping"
  }
  case object Default extends BotCommand

  def fromRawMessage(message: String): BotCommand = {
    val words = message.trim.split(" ")
    words.lift(0) match {
      case Some(PingBack.keyword) => PingBack
      case Some(FindBook.keyword) => FindBook(words.lift(0).get)
      case _ => Default
    }
  }

}