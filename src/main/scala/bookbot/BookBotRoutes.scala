package bookbot.server

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.UrlForm
import cats.data.Chain
import bookbot.models.BotCommand
import BotCommand._

object BookBotRoutes {
  def getSlackText(form: UrlForm): String = form.get("text").get(0).getOrElse("")

  def slackRoutes[F[_]: Sync](slack: SlackHandler[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case req @ POST -> Root / "hello" =>  
        req.decode[UrlForm](getSlackText _ andThen BotCommand.apply andThen {
          case FindBook(search) => Ok(slack.findBook(search))
          case PingBack => Ok(slack.pingback)
          case _ => Ok("I don't know what you mean")
        })
    }
  }
}