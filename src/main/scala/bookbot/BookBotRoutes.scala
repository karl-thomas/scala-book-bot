package bookbot.server

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object BookBotRoutes {
  def pingRoutes[F[_]: Sync](ping: PingHandler[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case POST -> Root / "hello" =>
        for {
          resp <- Ok(ping.pingback)
        } yield resp
    }
  }
}