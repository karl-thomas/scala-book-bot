package bookbot.server

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.UrlForm

object BookBotRoutes {
  def pingRoutes[F[_]: Sync](ping: PingHandler[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case req @ POST -> Root / "hello" =>  
        req.decode[UrlForm] { form => Ok(ping.pingback) }
    }
  }
}